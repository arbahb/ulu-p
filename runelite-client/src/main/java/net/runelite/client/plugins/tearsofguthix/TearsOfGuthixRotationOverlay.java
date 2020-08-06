/*
 * Copyright (c) 2020, Truth Forger <https://github.com/Blackberry0Pie>
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
package net.runelite.client.plugins.tearsofguthix;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;
import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class TearsOfGuthixRotationOverlay extends OverlayPanel
{
	private final TearsOfGuthixPlugin plugin;
	private final TearsOfGuthixConfig config;
	private final Client client;

	@Inject
	private TearsOfGuthixRotationOverlay(TearsOfGuthixPlugin plugin, TearsOfGuthixConfig config, Client client)
	{
		this.plugin = plugin;
		this.config = config;
		this.client = client;
		setPosition(OverlayPosition.TOP_LEFT);
		setPriority(OverlayPriority.LOW);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.showRotationOverlay())
		{
			return null;
		}

		if (client.getLocalPlayer().getWorldLocation().getRegionID() != plugin.TOG_REGION)
		{
			return null;
		}

		panelComponent.setPreferredSize(new Dimension(155, 0));
		panelComponent.getChildren().add(TitleComponent.builder()
			.text("Tears of Guthix")
			.color(Color.ORANGE)
			.build());
		panelComponent.getChildren().add(LineComponent.builder().left("").build());

		Color color = config.badTearsColor();
		String string = plugin.getRotation();

		if (string == plugin.WAIT_STRING)
		{
			color = Color.WHITE;
		}
		else if (string.contains("BBB"))
		{
			color = config.goodTearsColor();
		}

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Rotation:")
			.leftColor(Color.WHITE)
			.right(string)
			.rightColor(color)
			.build());

		return super.render(graphics);
	}
}
