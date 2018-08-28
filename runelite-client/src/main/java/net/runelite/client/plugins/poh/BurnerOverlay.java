/*
 * Copyright (c) 2018, Seth <Sethtroll3@gmail.com>
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
package net.runelite.client.plugins.poh;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.text.DecimalFormat;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.TileObject;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.components.ProgressPieComponent;
import net.runelite.client.ui.overlay.components.TextComponent;

import static net.runelite.client.plugins.poh.PohPlugin.*;

public class BurnerOverlay extends Overlay
{
	private final Client client;
	private final PohConfig config;
	private final PohPlugin plugin;
	private final TextComponent textComponent = new TextComponent();

	@Inject
	public BurnerOverlay(Client client, PohConfig config, PohPlugin plugin)
	{
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
		this.client = client;
		this.config = config;
		this.plugin = plugin;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!config.showBurner())
		{
			return null;
		}
		//Get each object in POH
		plugin.getPohObjects().forEach((object, tile) ->
		{
			//If planes match
			if (tile.getPlane() == client.getPlane())
			{
				//If there is a unlit burner, mark it red
				if (getBURNER_UNLIT().contains(object.getId()))
				{
					drawBurner(graphics, object, Color.RED);
				}
				//If there is a lit burner
				else if (getBURNER_LIT().contains(object.getId()))
				{
					if (config.showBurnerTime())
					{
						if (plugin.getTimerMap().containsKey(tile))
						{
							double sec = plugin.getTimerMap().get(tile);
							DecimalFormat df = new DecimalFormat("#.##");
							sec = Double.valueOf(df.format(sec));
							String timeLeft = Double.toString(sec);

							ProgressPieComponent pieTimer = new ProgressPieComponent();
							pieTimer.setPosition(object.getCanvasLocation());
							pieTimer.setProgress(1 - (sec / plugin.getSecondsLeft()));
							//Change textcolor depening on seconds remaining on burner
							if (sec > 20)
							{
								renderPie(graphics, Color.GREEN, Color.GREEN, pieTimer);
							}
							else if (sec >= 10 && sec <= 20)
							{
								//drawBurner(graphics, timeLeft, object, Color.ORANGE, 200);
								renderPie(graphics, Color.ORANGE, Color.ORANGE, pieTimer);
							}
							else if (sec < 10 && sec != 0)
							{
								//drawBurner(graphics, timeLeft, object, Color.RED, 200);
								renderPie(graphics, Color.RED, Color.RED, pieTimer);
							}
							else
							{
								drawBurner(graphics, object, Color.RED);
							}
						}
					}
				}
			}
		});
		return null;
	}

	private void drawBurner(Graphics2D graphics, TileObject tileObject, Color color)
	{
		Polygon poly = tileObject.getCanvasTilePoly();
		if (poly != null)
		{
			OverlayUtil.renderPolygon(graphics, poly, color);
		}
	}

	private void renderPie(Graphics2D graphics, Color fillColor, Color borderColor, ProgressPieComponent pieProgress)
	{
		pieProgress.setFill(fillColor);
		pieProgress.setBorderColor(borderColor);
		pieProgress.render(graphics);
	}
}
