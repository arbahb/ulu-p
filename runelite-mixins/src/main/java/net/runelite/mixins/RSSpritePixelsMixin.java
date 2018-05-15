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
package net.runelite.mixins;

import java.awt.image.BufferedImage;
import net.runelite.api.mixins.Copy;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Replace;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSSpritePixels;

@Mixin(RSSpritePixels.class)
public abstract class RSSpritePixelsMixin implements RSSpritePixels
{
	@Shadow("clientInstance")
	private static RSClient client;

	@Inject
	@Override
	public BufferedImage toBufferedImage()
	{
		BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

		toBufferedImage(img);

		return img;
	}

	@Inject
	@Override
	public void toBufferedImage(BufferedImage img)
	{
		int width = getWidth();
		int height = getHeight();

		if (img.getWidth() != width || img.getHeight() != height)
		{
			throw new IllegalArgumentException("Image bounds do not match SpritePixels");
		}

		int[] pixels = getPixels();
		int[] transPixels = new int[pixels.length];

		for (int i = 0; i < pixels.length; i++)
		{
			if (pixels[i] != 0)
			{
				transPixels[i] = pixels[i] | 0xff000000;
			}
		}

		img.setRGB(0, 0, width, height, transPixels, 0, width);
	}

	@Copy("drawOnMinimap")
	abstract void rs$drawOnMinimap(int x, int y, int width, int height, int xOffset, int yOffset, int rotation,
									int zoom, int[] xOffsets, int[] yOffsets);

	@Replace("drawOnMinimap")
	public void rl$drawOnMinimap(int x, int y, int width, int height, int xOffset, int yOffset, int rotation, int zoom,
									int[] xOffsets, int[] yOffsets)
	{
		if (client.isHdMinimap())
		{
			try
			{
				int[] graphicsPixels = client.getGraphicsPixels();

				int[] spritePixels = getPixels();
				int spriteWidth = getWidth();

				int centerX = -width / 2;
				int centerY = -height / 2;
				int rotSin = (int)(Math.sin((double)rotation / 326.11D) * 65536.0D);
				int rotCos = (int)(Math.cos((double)rotation / 326.11D) * 65536.0D);
				rotSin = rotSin * zoom >> 8;
				rotCos = rotCos * zoom >> 8;
				int posX = centerY * rotSin + centerX * rotCos + (xOffset << 16);
				int posY = centerY * rotCos - centerX * rotSin + (yOffset << 16);
				int pixelIndex = x + y * client.getGraphicsPixelsWidth();

				for (y = 0; y < height; ++y)
				{
					int spriteOffsetX = xOffsets[y];
					int graphicsPixelIndex = pixelIndex + spriteOffsetX;
					int spriteX = posX + rotCos * spriteOffsetX;
					int spriteY = posY - rotSin * spriteOffsetX;

					for (x = -yOffsets[y]; x < 0; ++x)
					{
						// bilinear interpolation
						int x1 = spriteX >> 16;
						int y1 = spriteY >> 16;
						int x2 = x1 + 1;
						int y2 = y1 + 1;
						int c1 = spritePixels[x1 + y1 * spriteWidth];
						int c2 = spritePixels[x2 + y1 * spriteWidth];
						int c3 = spritePixels[x1 + y2 * spriteWidth];
						int c4 = spritePixels[x2 + y2 * spriteWidth];
						int u1 = (spriteX >> 8) - (x1 << 8);
						int v1 = (spriteY >> 8) - (y1 << 8);
						int u2 = (x2 << 8) - (spriteX >> 8);
						int v2 = (y2 << 8) - (spriteY >> 8);
						int a1 = u2 * v2;
						int a2 = u1 * v2;
						int a3 = u2 * v1;
						int a4 = u1 * v1;
						int r = (c1 >> 16 & 0xff) * a1 + (c2 >> 16 & 0xff) * a2 +
								(c3 >> 16 & 0xff) * a3 + (c4 >> 16 & 0xff) * a4 & 0xff0000;
						int g = (c1 >> 8 & 0xff) * a1 + (c2 >> 8 & 0xff) * a2 +
								(c3 >> 8 & 0xff) * a3 + (c4 >> 8 & 0xff) * a4 >> 8 & 0xff00;
						int b = (c1 & 0xff) * a1 + (c2 & 0xff) * a2 +
								(c3 & 0xff) * a3 + (c4 & 0xff) * a4 >> 16;
						graphicsPixels[graphicsPixelIndex++] = r | g | b;
						spriteX += rotCos;
						spriteY -= rotSin;
					}

					posX += rotSin;
					posY += rotCos;
					pixelIndex += client.getGraphicsPixelsWidth();
				}
			}
			catch (Exception e)
			{
				// ignored
			}
		}
		else
		{
			rs$drawOnMinimap(x, y, width, height, xOffset, yOffset, rotation, zoom, xOffsets, yOffsets);
		}
	}
}
