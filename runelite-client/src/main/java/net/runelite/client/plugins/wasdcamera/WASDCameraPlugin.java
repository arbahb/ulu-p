/*
 * Copyright (c) 2018, Abexlry <abexlry@gmail.com>
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
package net.runelite.client.plugins.wasdcamera;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import java.awt.event.KeyEvent;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.WidgetHiddenChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
		name = "WASD Camera",
		description = "Allows use of WASD keys for camera movement with 'Press Enter to Chat'",
		tags = {"wasd", "camera", "chat"}
)

public class WASDCameraPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private KeyManager keyManager;

	@Inject
	private WASDCameraOverlay overlay;

	@Inject
	public WASDCameraConfig config;

	@Inject
	private WASDCameraListener inputListener;

	private static final int W_KEY = KeyEvent.VK_W;
	private static final int A_KEY = KeyEvent.VK_A;
	private static final int S_KEY = KeyEvent.VK_S;
	private static final int D_KEY = KeyEvent.VK_D;

	private static final int ENTER_KEY = KeyEvent.VK_ENTER;
	private static final int SLASH_KEY = KeyEvent.VK_SLASH;

	private static final int[] WIDGET_HIDDEN_OVERRIDES = {10616876, 10616877};
	private static final int[] WIDGET_CLICKED_OVERRIDES = {38993941, 38993954, 36241409, 10616859, 36241417};

	public boolean canType;
	public boolean inFocus;
	public boolean loggedIn;

	@Provides
	WASDCameraConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(WASDCameraConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		keyManager.registerKeyListener(inputListener);
		overlayManager.add(overlay);
		canType = false;
	}

	@Override
	protected void shutDown() throws Exception
	{
		keyManager.unregisterKeyListener(inputListener);
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onGameStateChanged(final GameStateChanged e)
	{
		if (e.getGameState() == GameState.HOPPING || e.getGameState() == GameState.LOGIN_SCREEN)
		{
			loggedIn = false;
		}
		else
		{
			loggedIn = true;
		}
	}

	@Subscribe
	public void onFocusChanged(FocusChanged f)
	{
		inFocus = f.isFocused();
		overlay.updateOverlay();
	}

	@Subscribe
	public void onWidgetHiddenChanged(WidgetHiddenChanged e)
	{
		Widget w = e.getWidget();

		if (isWidgetHiddenOverride(w.getId()))
		{
			canType = w.isHidden();
		}

		overlay.updateOverlay();
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked e)
	{
		String o = e.getMenuOption();

		if (isWidgetClickedOverride(e.getWidgetId()))
		{
			if (o.startsWith("Search") || o.startsWith("Report"))
			{
				canType = true;
			}
			else
			{
				canType = false;
			}
		}

		overlay.updateOverlay();
	}

	public boolean canHandle()
	{
		return loggedIn && inFocus;
	}

	private boolean isWidgetHiddenOverride(int id)
	{
		for (int i = 0; i < WIDGET_HIDDEN_OVERRIDES.length; i++)
		{
			if (id == WIDGET_HIDDEN_OVERRIDES[i])
			{
				return true;
			}
		}
		return false;
	}

	private boolean isWidgetClickedOverride(int id)
	{
		for (int i = 0; i < WIDGET_CLICKED_OVERRIDES.length; i++)
		{
			if (id == WIDGET_CLICKED_OVERRIDES[i])
			{
				return true;
			}
		}
		return false;
	}

	public void handleKeyPress(KeyEvent e)
	{
		if (!canType)
		{
			switch (e.getKeyCode())
			{
				case W_KEY:
					e.setKeyCode(KeyEvent.VK_UP);
					break;
				case A_KEY:
					e.setKeyCode(KeyEvent.VK_LEFT);
					break;
				case S_KEY:
					e.setKeyCode(KeyEvent.VK_DOWN);
					break;
				case D_KEY:
					e.setKeyCode(KeyEvent.VK_RIGHT);
					break;
				case ENTER_KEY:
				case SLASH_KEY:
					canType = true;
					break;
			}
		}
		else
		{
			switch (e.getKeyCode())
			{
				case ENTER_KEY:
					canType = false;
					break;
			}
		}

		overlay.updateOverlay();
	}

	public void handleKeyRelease(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case W_KEY:
				e.setKeyCode(KeyEvent.VK_UP);
				break;
			case A_KEY:
				e.setKeyCode(KeyEvent.VK_LEFT);
				break;
			case S_KEY:
				e.setKeyCode(KeyEvent.VK_DOWN);
				break;
			case D_KEY:
				e.setKeyCode(KeyEvent.VK_RIGHT);
				break;
		}
	}
}