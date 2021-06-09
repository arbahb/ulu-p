/*
 * Copyright (c) 2018, James Swindle <wilingua@gmail.com>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * Copyright (c) 2018, Shaun Dreclin <shaundreclin@gmail.com>
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
package net.runelite.client.plugins.slayer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.util.List;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NPCComposition;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.util.ColorUtil;

public class TargetClickboxOverlay extends Overlay
{
	private final Client client;
	private final SlayerConfig config;
	private final SlayerPlugin plugin;

	@Inject
	TargetClickboxOverlay(Client client, SlayerConfig config, SlayerPlugin plugin)
	{
		this.client = client;
		this.config = config;
		this.plugin = plugin;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (config.highlightTargets() == SlayerTaskHighlightMode.NONE)
		{
			return null;
		}

		List<NPC> targets = plugin.getHighlightedTargets();
		for (NPC target : targets)
		{
			if (config.highlightTargets() == SlayerTaskHighlightMode.HULL)
			{
				renderTargetOverlay(graphics, target, config.getTargetColor());
			}
			else
			{
				renderTargetTiles(graphics, target, config.getTargetColor());
			}
		}

		return null;
	}

	private void renderTargetOverlay(Graphics2D graphics, NPC actor, Color color)
	{
		Shape objectClickbox = actor.getConvexHull();
		if (objectClickbox != null)
		{
			renderPoly(graphics, objectClickbox, color);
		}
	}

	private void renderTargetTiles(Graphics2D graphics, NPC actor, Color color)
	{
		NPCComposition npcComposition = actor.getTransformedComposition();
		if (npcComposition != null)
		{
			int size = npcComposition.getSize();
			LocalPoint lp = actor.getLocalLocation();
			Polygon tilePoly = Perspective.getCanvasTileAreaPoly(client, lp, size);
			if(tilePoly != null)
			{
				renderPoly(graphics, tilePoly, color);
			}
		}
	}

	private void renderPoly(Graphics2D graphics, Shape shape, Color color)
	{
		graphics.setColor(color);
		graphics.setStroke(new BasicStroke(2));
		graphics.draw(shape);
		graphics.setColor(ColorUtil.colorWithAlpha(color, color.getAlpha() / 12));
		graphics.fill(shape);
	}
}
