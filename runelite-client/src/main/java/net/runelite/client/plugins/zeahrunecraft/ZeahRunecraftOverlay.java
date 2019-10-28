/*
 * Copyright (c) 2019, Pinibot <https://github.com/Pinibot>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.zeahrunecraft;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

class ZeahRunecraftOverlay extends Overlay
{
	private static final int MAX_DISTANCE_TO_DRAW_STONES = 2500;

	private final Client client;
	private final ZeahRunecraftPlugin plugin;
	private final ZeahRunecraftConfig config;

	@Inject
	private ZeahRunecraftOverlay(final Client client, final ZeahRunecraftPlugin plugin,
			final ZeahRunecraftConfig config)
	{
		super(plugin);
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
		this.client = client;
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public Dimension render(final Graphics2D graphics)
	{
		if (config.showDenseRunestoneStatus())
		{
			LocalPoint playerLocation = this.client.getLocalPlayer().getLocalLocation();
			Point mousePosition = this.client.getMouseCanvasPosition();

			this.plugin.getRunestones().forEach((runestone) ->
			{
				GameObject runestoneGameObject = runestone.getGameObject();

				if (runestoneGameObject.getLocalLocation().distanceTo(playerLocation) < MAX_DISTANCE_TO_DRAW_STONES)
				{
					Shape clickbox = runestoneGameObject.getClickbox();

					Color highlightColor = getDenseRunestoneHighlightColor(runestone.isDepleted(), plugin.isMining());
					Color translucentHighlightColor = new Color(highlightColor.getRed(), highlightColor.getGreen(),
							highlightColor.getBlue(), 50);

					OverlayUtil.renderHoverableArea(graphics, clickbox, mousePosition, translucentHighlightColor,
							highlightColor, highlightColor.darker());
				}

			});
		}

		return null;
	}

	private Color getDenseRunestoneHighlightColor(final boolean isDepleted, final boolean isMining)
	{
		Color highlightColor = Color.GREEN;
		if (isDepleted)
		{
			highlightColor = Color.RED;
		}
		else if (isMining)
		{
			highlightColor = Color.ORANGE;
		}

		return highlightColor;
	}
}
