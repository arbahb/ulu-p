/*
 * Copyright (c) 2018, Dreyri <https://github.com/Dreyri>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.minimap;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import java.awt.Color;
import java.util.Arrays;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.SpritePixels;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.WidgetHiddenChanged;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Minimap",
	description = "Customize the color of minimap dots",
	tags = {"items", "npcs", "players"}
)
public class MinimapPlugin extends Plugin
{
	private static final int NUM_MAPDOTS = 6;

	@Inject
	private Client client;

	@Inject
	private MinimapConfig config;

	private SpritePixels[] originalDotSprites;

	@Provides
	private MinimapConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(MinimapConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		updateMinimapWidgetVisibility(config.hideMinimap());
		storeOriginalDots();
		replaceMapDots();
	}

	@Override
	protected void shutDown() throws Exception
	{
		updateMinimapWidgetVisibility(false);
		restoreOriginalDots();
	}

	@Subscribe
	public void onGameStateChange(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGIN_SCREEN && originalDotSprites == null)
		{
			storeOriginalDots();
			replaceMapDots();
		}
	}

	@Subscribe
	public void configChanged(ConfigChanged event)
	{
		if (!event.getGroup().equals("minimap"))
		{
			return;
		}

		if (event.getKey().equals("hideMinimap"))
		{
			updateMinimapWidgetVisibility(config.hideMinimap());
			return;
		}

		if (event.getKey().equals("leftAlignOrbs"))
		{
			if (config.leftAlignOrbs())
			{
				moveOrbs();
			}
			else
			{
				resetOrbs();
			}
		}

		replaceMapDots();
	}

	@Subscribe
	public void onWidgetHiddenChange(WidgetHiddenChanged event)
	{
		updateMinimapWidgetVisibility(config.hideMinimap());
	}

	private void updateMinimapWidgetVisibility(boolean enable)
	{
		final Widget resizableStonesWidget = client.getWidget(WidgetInfo.RESIZABLE_MINIMAP_STONES_WIDGET);

		if (resizableStonesWidget != null)
		{
			resizableStonesWidget.setHidden(enable);
		}

		final Widget resizableNormalWidget = client.getWidget(WidgetInfo.RESIZABLE_MINIMAP_WIDGET);

		if (resizableNormalWidget != null && !resizableNormalWidget.isSelfHidden())
		{
			for (Widget widget : resizableNormalWidget.getStaticChildren())
			{
				if (widget.getId() != WidgetInfo.RESIZABLE_VIEWPORT_BOTTOM_LINE_LOGOUT_BUTTON.getId() &&
					widget.getId() != WidgetInfo.RESIZABLE_MINIMAP_LOGOUT_BUTTON.getId())
				{
					widget.setHidden(enable);
				}
			}
		}
	}

	private void replaceMapDots()
	{
		SpritePixels[] mapDots = client.getMapDots();

		if (mapDots == null)
		{
			return;
		}

		Color[] minimapDotColors = getColors();
		for (int i = 0; i < mapDots.length && i < minimapDotColors.length; ++i)
		{
			mapDots[i] = MinimapDot.create(this.client, minimapDotColors[i]);
		}
	}

	private Color[] getColors()
	{
		Color[] colors = new Color[NUM_MAPDOTS];
		colors[0] = config.itemColor();
		colors[1] = config.npcColor();
		colors[2] = config.playerColor();
		colors[3] = config.friendColor();
		colors[4] = config.teamColor();
		colors[5] = config.clanColor();
		return colors;
	}

	private void storeOriginalDots()
	{
		SpritePixels[] originalDots = client.getMapDots();

		if (originalDots == null)
		{
			return;
		}

		originalDotSprites = Arrays.copyOf(originalDots, originalDots.length);
	}

	private void restoreOriginalDots()
	{
		SpritePixels[] mapDots = client.getMapDots();

		if (originalDotSprites == null || mapDots == null)
		{
			return;
		}

		System.arraycopy(originalDotSprites, 0, mapDots, 0, mapDots.length);
	}
	@Subscribe
	public void onWidgetLoaded(WidgetLoaded event)
	{
		if (event.getGroupId() == WidgetID.MINIMAP_GROUP_ID)
		{
			if (config.leftAlignOrbs())
			{
				moveOrbs();
			}
		}
	}

	private void moveOrbs()
	{
		Widget healthOrb = client.getWidget(WidgetInfo.MINIMAP_HEALTH_ORB);
		Widget prayerOrb = client.getWidget(WidgetInfo.MINIMAP_PRAYER_ORB);
		Widget runOrb = client.getWidget(WidgetInfo.MINIMAP_RUN_ORB);
		Widget specOrb = client.getWidget(WidgetInfo.MINIMAP_SPEC_ORB);

		if (healthOrb != null)
		{
			healthOrb.setRelativeY(33);
			healthOrb.setOriginalY(33);
		}

		if (prayerOrb != null)
		{
			prayerOrb.setRelativeY(65);
			prayerOrb.setOriginalY(65);
		}

		if (runOrb != null)
		{
			runOrb.setRelativeX(0);
			runOrb.setRelativeY(97);
			runOrb.setOriginalX(0);
			runOrb.setOriginalY(97);
		}

		if (specOrb != null)
		{
			specOrb.setRelativeX(0);
			specOrb.setRelativeY(129);
			specOrb.setOriginalX(0);
			specOrb.setOriginalY(129);
		}
	}

	private void resetOrbs()
	{
		Widget healthOrb = client.getWidget(WidgetInfo.MINIMAP_HEALTH_ORB);
		Widget prayerOrb = client.getWidget(WidgetInfo.MINIMAP_PRAYER_ORB);
		Widget runOrb = client.getWidget(WidgetInfo.MINIMAP_RUN_ORB);
		Widget specOrb = client.getWidget(WidgetInfo.MINIMAP_SPEC_ORB);

		if (healthOrb != null)
		{
			healthOrb.setRelativeY(37);
			healthOrb.setOriginalY(37);
		}

		if (prayerOrb != null)
		{
			prayerOrb.setRelativeY(71);
			prayerOrb.setOriginalY(71);
		}

		if (runOrb != null)
		{
			runOrb.setRelativeX(10);
			runOrb.setRelativeY(103);
			runOrb.setOriginalX(10);
			runOrb.setOriginalY(103);
		}

		if (specOrb != null)
		{
			specOrb.setRelativeX(32);
			specOrb.setRelativeY(128);
			specOrb.setOriginalX(32);
			specOrb.setOriginalY(128);
		}
	}
}