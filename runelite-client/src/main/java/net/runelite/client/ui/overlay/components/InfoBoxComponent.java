/*
 * Copyright (c) 2016-2017, Adam <Adam@sigterm.info>
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
package net.runelite.client.ui.overlay.components;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import lombok.Setter;
import net.runelite.client.ui.overlay.RenderableEntity;

@Setter
public class InfoBoxComponent implements RenderableEntity
{
	private static final int BOX_SIZE = 35;
	private static final int SEPARATOR = 2;
	private static final int SCALE_FACTOR = 2;

	private boolean scale = false;
	private String text;
	private Color color = Color.WHITE;
	private Color backgroundColor = ComponentConstants.STANDARD_BACKGROUND_COLOR;
	private Point position = new Point();
	private BufferedImage image;

	@Override
	public Dimension render(Graphics2D graphics)
	{
		final FontMetrics metrics = graphics.getFontMetrics();
		final Rectangle bounds = new Rectangle(position.x, position.y, BOX_SIZE, BOX_SIZE);
		final Rectangle scaledBounds = new Rectangle(position.x, position.y, BOX_SIZE / SCALE_FACTOR, BOX_SIZE / SCALE_FACTOR);
		final BackgroundComponent backgroundComponent = new BackgroundComponent();
		backgroundComponent.setBackgroundColor(backgroundColor);

		if (scale)
			backgroundComponent.setRectangle(scaledBounds);
		else
			backgroundComponent.setRectangle(bounds);
		
		backgroundComponent.render(graphics);

		if (Objects.nonNull(image))
		{
			if (scale)
			{
				BufferedImage resizedImage = new BufferedImage(image.getWidth() / SCALE_FACTOR, image.getHeight() / SCALE_FACTOR, image.TYPE_INT_ARGB);
				Graphics2D g = resizedImage.createGraphics();
				g.drawImage( image, 0, 0, image.getWidth() / SCALE_FACTOR, image.getHeight() / SCALE_FACTOR, null);
				g.dispose();
				image = resizedImage;

				graphics.drawImage(image,
					position.x + (BOX_SIZE / SCALE_FACTOR - image.getWidth()) / 2,
					position.y + (BOX_SIZE / SCALE_FACTOR - image.getHeight()) / 2, null);
			}
			else
			{
				graphics.drawImage(image,
					position.x + (BOX_SIZE - image.getWidth()) / 2,
					position.y + (BOX_SIZE - image.getHeight()) / 2, null);
			}
		}

		final TextComponent textComponent = new TextComponent();
		textComponent.setColor(color);
		textComponent.setText(text);

		if (scale)
			textComponent.setPosition(new Point(
				position.x + ((BOX_SIZE) / SCALE_FACTOR),
				position.y + 14 ));
		else
			textComponent.setPosition(new Point(
				position.x + ((BOX_SIZE - metrics.stringWidth(text)) / 2),
				position.y + BOX_SIZE - SEPARATOR));

		textComponent.render(graphics);
		return new Dimension(BOX_SIZE, BOX_SIZE);
	}
}
