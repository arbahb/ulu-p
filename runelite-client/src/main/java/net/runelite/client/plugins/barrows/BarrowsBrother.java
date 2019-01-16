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
package net.runelite.client.plugins.barrows;

import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ObjectID;
import net.runelite.api.Varbits;
import net.runelite.api.coords.WorldPoint;

@RequiredArgsConstructor
public enum BarrowsBrother
{
	AHRIM("Ahrim", new WorldPoint(3566, 3289, 0), Varbits.BARROWS_KILLED_AHRIM, ObjectID.SARCOPHAGUS_20770),
	DHAROK("Dharok", new WorldPoint(3575, 3298, 0), Varbits.BARROWS_KILLED_DHAROK, ObjectID.SARCOPHAGUS_20720),
	GUTHAN("Guthan", new WorldPoint(3577, 3283, 0), Varbits.BARROWS_KILLED_GUTHAN, ObjectID.SARCOPHAGUS_20722),
	KARIL("Karil", new WorldPoint(3566, 3275, 0), Varbits.BARROWS_KILLED_KARIL, ObjectID.SARCOPHAGUS_20771),
	TORAG("Torag", new WorldPoint(3553, 3283, 0), Varbits.BARROWS_KILLED_TORAG, ObjectID.SARCOPHAGUS_20721),
	VERAC("Verac", new WorldPoint(3557, 3298, 0), Varbits.BARROWS_KILLED_VERAC, ObjectID.SARCOPHAGUS_20772);

	@Getter
	private final String name;
	@Getter
	private final WorldPoint location;
	@Getter
	private final Varbits killedVarbit;
	@Getter
	private final int sarcophagus;

	private final static ImmutableMap<Integer, BarrowsBrother> brothersBySarcophagus = ImmutableMap.copyOf(
		Arrays.stream(BarrowsBrother.values())
			.collect(Collectors.toMap(
				BarrowsBrother::getSarcophagus,
				brother -> brother
			))
	);

	public static BarrowsBrother findBySarcophagus(int sarcophagus)
	{
		return brothersBySarcophagus.get(sarcophagus);
	}
}
