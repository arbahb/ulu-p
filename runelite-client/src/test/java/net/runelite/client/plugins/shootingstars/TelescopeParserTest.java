/*
 * Copyright (c) 2021, Hexagon <hexagon@fking.work>
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
package net.runelite.client.plugins.shootingstars;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@Slf4j
@AllArgsConstructor
@RunWith(Parameterized.class)
public class TelescopeParserTest
{

	@Parameters(name = "{index}: Region {1}")
	public static List<Object[]> testTable()
	{
		return Arrays.asList(new Object[][]{

				{
					"You see a shooting star! The star looks like it will land in Asgarnia in<br>the next 49 to 51 minutes.",
					StarRegion.ASGARNIA,
					Duration.parse("PT49M"),
					Duration.parse("PT51M")
				},
				{
					"You see a shooting star! The star looks like it will land on Crandor<br>or Karamja in the next 9 to 11 minutes.",
					StarRegion.CRANDOR_KARAMJA,
					Duration.parse("PT9M"),
					Duration.parse("PT11M")
				},
				{
					"You see a shooting star! The star looks like it will land in the Feldip<br>Hills or on the Isle of Souls in the next 1 hour 50 minutes to 1 hour<br>52 minutes.",
					StarRegion.FELDIP_HILLS,
					Duration.parse("PT1H50M"),
					Duration.parse("PT1H52M")
				},
				{
					"You see a shooting star! The star looks like it will land on Fossil<br>Island or on Mos Le'Harmless in the next 24 to 26 minutes.",
					StarRegion.FOSSIL_ISLAND,
					Duration.parse("PT24M"),
					Duration.parse("PT26M")
				},
				{
					"You see a shooting star! The star looks like it will land in the<br>Fremennik Lands or on Lunar Isle in the next 18 to 20 minutes.",
					StarRegion.FREMENNIK_LANDS,
					Duration.parse("PT18M"),
					Duration.parse("PT20M")
				},
				{
					"You see a shooting star! The star looks like it will land in Great<br>Kourend in the next 30 to 32 minutes.",
					StarRegion.GREAT_KOUREND,
					Duration.parse("PT30M"),
					Duration.parse("PT32M")
				},
				{
					"You see a shooting star! The star looks like it will land in Kandarin<br>in the next 1 to 3 minutes.",
					StarRegion.KANDARIN,
					Duration.parse("PT1M"),
					Duration.parse("PT3M")
				},
				{
					"You see a shooting star! The star looks like it will land in the Kebos<br>Lowlands in the next 1 hour 30 minutes to 1 hour 32 minutes.",
					StarRegion.KEBOS_LOWLANDS,
					Duration.parse("PT1H30M"),
					Duration.parse("PT1H32M")
				},
				{
					"You see a shooting star! The star looks like it will land in the<br>Kharidian Desert in the next 57 to 59 minutes.",
					StarRegion.KHARIDIAN_DESERT,
					Duration.parse("PT57M"),
					Duration.parse("PT59M")
				},
				{
					"You see a shooting star! The star looks like it will land in Misthalin in<br>the next 53 to 55 minutes.",
					StarRegion.MISTHALIN,
					Duration.parse("PT53M"),
					Duration.parse("PT55M")
				},
				{
					"You see a shooting star! The star looks like it will land in Morytania<br>in the next 1 hour 27 minutes to 1 hour 29 minutes.",
					StarRegion.MORYTANIA,
					Duration.parse("PT1H27M"),
					Duration.parse("PT1H29M")
				},
				{
					"You see a shooting star! The star looks like it will land in Piscatoris<br>or the Gnome Stronghold in the next 1 hour 37 minutes to 1 hour<br>39 minutes.",
					StarRegion.PISCATORIS_GNOME_STRONGHOLD,
					Duration.parse("PT1H37M"),
					Duration.parse("PT1H39M")
				},
				{
					"You see a shooting star! The star looks like it will land in Tirannwn<br>in the next 1 hour 54 minutes to 1 hour 56 minutes.",
					StarRegion.TIRANNWN,
					Duration.parse("PT1H54M"),
					Duration.parse("PT1H56M")
				},
				{
					"You see a shooting star! The star looks like it will land in the<br>Wilderness in the next 1 hour 48 minutes to 1 hour 50 minutes.",
					StarRegion.WILDERNESS,
					Duration.parse("PT1H48M"),
					Duration.parse("PT1H50M")
				}
			}
		);
	}

	private final String message;
	private final StarRegion expectedRegion;
	private final Duration expectedEarliest;
	private final Duration expectedLatest;

	@Test
	public void testExtractRegion()
	{
		StarRegion region = TelescopeParser.extractStarRegion(message);
		assertEquals(expectedRegion, region);
	}

	@Test
	public void testExtractDurations()
	{
		Duration earliestDuration = TelescopeParser.extractEarliestDuration(message);
		Duration latestDuration = TelescopeParser.extractLatestDuration(message);
		assertEquals(expectedEarliest, earliestDuration);
		assertEquals(expectedLatest, latestDuration);
	}
}
