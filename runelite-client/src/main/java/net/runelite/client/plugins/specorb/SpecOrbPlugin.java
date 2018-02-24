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
package net.runelite.client.plugins.specorb;

import com.google.inject.Provides;
import com.google.common.eventbus.Subscribe;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.Overlay;

@PluginDescriptor(
	name = "Special Attack Orb"
)
public class SpecOrbPlugin extends Plugin
{
	private Image minimapOrbBackground;
	private Image specialAttackIcon;

	@Inject
	private SpecOrbOverlay overlay;

	@Provides
	SpecOrbConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(SpecOrbConfig.class);
	}

	@Override
	public Overlay getOverlay()
	{
		return overlay;
	}

	@Override
	protected void startUp() throws Exception
	{
		overlay.updateConfig();
		minimapOrbBackground = ImageIO.read(getClass().getResourceAsStream("minimap_orb_background.png"));
		specialAttackIcon = ImageIO.read(getClass().getResourceAsStream("special_orb_icon.png"));
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged event)
	{
		overlay.onVarbitChanged(event);
	}

	@Subscribe
	public void onTick(GameTick event)
	{
		overlay.onTick(event);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("specOrb"))
		{
			overlay.updateConfig();
		}
	}

	public Image getMinimapOrbBackground()
	{
		return minimapOrbBackground;
	}

	public Image getSpecialAttackIcon()
	{
		return specialAttackIcon;
	}
}
