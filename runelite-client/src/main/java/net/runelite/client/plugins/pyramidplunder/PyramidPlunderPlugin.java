/*
 * Copyright (c) 2018, Steffen Hauge <steffen.oerum.hauge@hotmail.com>
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
package net.runelite.client.plugins.pyramidplunder;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import static net.runelite.api.ItemID.PHARAOHS_SCEPTRE;
import net.runelite.api.Player;
import net.runelite.api.Varbits;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

@PluginDescriptor(
	name = "PyramidPlunder",
	description = "Highlights doors and spear traps in pyramid plunder and adds a numerical timer",
	tags = {"pyramidplunder", "pyramid", "plunder", "overlay", "skilling", "thieving"},
	enabledByDefault = false
)

@Slf4j
public class PyramidPlunderPlugin extends Plugin
{
	private static final int PYRAMIND_PLUNDER_REGION_ID = 7749;

	@Inject
	private Client client;

	@Inject
	private PyramidPlunderConfig config;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Inject
	private ItemManager itemManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private PyramidPlunderOverlay pyramidPlunderOverlay;

	@Provides
	PyramidPlunderConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PyramidPlunderConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(pyramidPlunderOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(pyramidPlunderOverlay);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!config.showTimer())
		{
			removeTimer();
		}
	}

	private void removeTimer()
	{
		infoBoxManager.removeIf(infoBox -> infoBox instanceof PyramidPlunderTimer);
	}

	private void showTimer()
	{
		removeTimer();
		infoBoxManager.addInfoBox(new PyramidPlunderTimer(this, itemManager.getImage(PHARAOHS_SCEPTRE)));
	}

	@Subscribe
	public void onGameStateChange(GameStateChanged event)
	{
		switch (event.getGameState())
		{
			case HOPPING:
			case LOGIN_SCREEN:
				removeTimer();
				break;
			case LOADING:
			case LOGGED_IN:
				Player local = client.getLocalPlayer();
				if (local == null)
				{
					return;
				}

				WorldPoint location = local.getWorldLocation();
				if (location.getRegionID() != PYRAMIND_PLUNDER_REGION_ID)
				{
					removeTimer();
				}
				break;
		}
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged event)
	{
		if (client.getVar(Varbits.PYRAMID_PLUNDER_TIMER) == 1 && config.showTimer())
		{
			showTimer();
		}
	}
}
