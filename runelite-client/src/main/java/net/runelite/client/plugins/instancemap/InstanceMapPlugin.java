/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
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
package net.runelite.client.plugins.instancemap;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Binder;
import com.google.inject.Provides;
import net.runelite.api.Client;
import net.runelite.api.RLKeyAdapter;
import net.runelite.api.RLMouseAdapter;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MapRegionChanged;
import net.runelite.api.events.WidgetMenuOptionClicked;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.menus.WidgetMenuOption;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.Overlay;

import javax.inject.Inject;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;

import static net.runelite.api.widgets.WidgetInfo.WORLD_MAP;

@PluginDescriptor(
	name = "Instance Map"
)
public class InstanceMapPlugin extends Plugin
{
	private final WidgetMenuOption openMapOption = new WidgetMenuOption("Show", "Instance Map", WidgetInfo.WORLD_MAP);
	private final WidgetMenuOption ascendOption = new WidgetMenuOption("Ascend", "Instance Map", WidgetInfo.WORLD_MAP);
	private final WidgetMenuOption descendOption = new WidgetMenuOption("Descend", "Instance Map", WidgetInfo.WORLD_MAP);
	private final RLKeyAdapter keyPressedListener = new RLKeyAdapter()
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			if (e.isConsumed())
			{
				return;
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE && e.isShiftDown() && !overlay.isMapShown())
			{
				overlay.setShowMap(true);
				openMapOption.setMenuOption("Hide");
				e.consume();
				setPressConsumed(true);
			}
			else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && overlay.isMapShown())
			{
				overlay.setShowMap(false);
				openMapOption.setMenuOption("Show");
				e.consume();
				setPressConsumed(true);
			}
			super.keyPressed(e);
		}
	};
	private final RLMouseAdapter mouseWheelListener = new RLMouseAdapter()
	{
		@Override
		public void mouseWheelMoved(MouseWheelEvent e)
		{
			if (e.isConsumed() || !overlay.isMapShown())
			{
				return;
			}
			if (e.isShiftDown())
			{
				if (e.getWheelRotation() > 0)
				{
					overlay.onDescend();
					e.consume();
					setMouseWheelMovedConsumed(true);
				}
				else if (e.getWheelRotation() < 0)
				{
					overlay.onAscend();
					e.consume();
					setMouseWheelMovedConsumed(true);
				}
			}
			super.mouseWheelMoved(e);
		}
	};

	@Inject
	private InstanceMapConfig config;

	@Inject
	private InstanceMapOverlay overlay;

	@Inject
	private MenuManager menuManager;

	@Inject
	private Client client;

	@Override
	public void configure(Binder binder)
	{
		binder.bind(InstanceMapOverlay.class);
	}

	@Provides
	InstanceMapConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(InstanceMapConfig.class);
	}

	private void addCustomOptions()
	{
		menuManager.addManagedCustomMenu(openMapOption);
		menuManager.addManagedCustomMenu(descendOption);
		menuManager.addManagedCustomMenu(ascendOption);
		client.getKeyboard().getOnKeyEvents().add(keyPressedListener);
		client.getMouseWheelHandler().getOnMouseWheelMovedEvents().add(mouseWheelListener);
		//client.getMouseWheelHandler().getOnMouseWheelMovedEvents().add(mouseWheelListener);
//		client.getCanvas().addKeyListener(keyPressedListener);
//		client.getCanvas().addMouseWheelListener(mouseWheelListener);

	}

	private void removeCustomOptions()
	{
		menuManager.removeManagedCustomMenu(openMapOption);
		menuManager.removeManagedCustomMenu(descendOption);
		menuManager.removeManagedCustomMenu(ascendOption);
		client.getKeyboard().getOnKeyEvents().remove(keyPressedListener);
		client.getMouseWheelHandler().getOnMouseWheelMovedEvents().remove(mouseWheelListener);
//		client.getCanvas().removeKeyListener(keyPressedListener);
//		client.getCanvas().removeMouseWheelListener(mouseWheelListener);
	}

	@Override
	protected void startUp() throws Exception
	{
		if (config.enabled())
		{
			addCustomOptions();
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		removeCustomOptions();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (config.enabled())
		{
			addCustomOptions();
		}
		else
		{
			removeCustomOptions();
		}
	}

	@Subscribe
	public void regionChange(MapRegionChanged event)
	{
		overlay.onRegionChange(event);
	}

	@Subscribe
	public void gameStateChange(GameStateChanged event)
	{
		overlay.onGameStateChange(event);
	}

	private boolean clickedOptionEquals(WidgetMenuOptionClicked event, WidgetMenuOption widgetMenuOption)
	{
		return event.getMenuOption().equals(widgetMenuOption.getMenuOption()) && event.getMenuTarget().equals(widgetMenuOption.getMenuTarget());
	}

	@Subscribe
	public void onWidgetMenuOptionClicked(WidgetMenuOptionClicked event)
	{
		if (!config.enabled() || event.getWidget() != WORLD_MAP)
		{
			return;
		}

		if (clickedOptionEquals(event, openMapOption))
		{
			overlay.setShowMap(!overlay.isMapShown());

			if (overlay.isMapShown())
			{
				openMapOption.setMenuOption("Hide");
			}
			else
			{
				openMapOption.setMenuOption("Show");
			}
		}
		else if (clickedOptionEquals(event, ascendOption))
		{
			overlay.onAscend();
		}
		else if (clickedOptionEquals(event, descendOption))
		{
			overlay.onDescend();
		}
	}

	@Override
	public Overlay getOverlay()
	{
		return overlay;
	}
}
