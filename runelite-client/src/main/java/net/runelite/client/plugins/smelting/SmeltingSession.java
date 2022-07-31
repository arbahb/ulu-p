/*
 * Copyright (c) 2019, Stephen <stepzhu@umich.edu>
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
package net.runelite.client.plugins.smelting;

import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

class SmeltingSession
{
	@Getter(AccessLevel.PACKAGE)
	@Setter
	private int barsSmelted;

	@Getter(AccessLevel.PACKAGE)
	@Setter
	private int barsSmeltedSinceHrReset;

	@Getter(AccessLevel.PACKAGE)
	@Setter
	private int extraBarsSmelted;

	@Getter(AccessLevel.PACKAGE)
	@Setter
	private int extraBarsSmeltedSinceHrReset;

	@Getter(AccessLevel.PACKAGE)
	@Setter
	private int cannonBallsSmelted;

	@Getter(AccessLevel.PACKAGE)
	@Setter
	private Instant lastItemSmelted;

	void increaseBarsSmelted(int amount)
	{
		barsSmelted += amount;
		lastItemSmelted = Instant.now();
	}

	void increaseBarsSmeltedSinceHrReset(int amount)
	{
		barsSmeltedSinceHrReset += amount;
	}

	void increaseExtraBarsSmelted(int amount)
	{
		extraBarsSmelted += amount;
	}

	void increaseExtraBarsSmeltedSinceHrReset(int amount)
	{
		extraBarsSmeltedSinceHrReset += amount;
	}

	void increaseCannonBallsSmelted(int amount)
	{
		cannonBallsSmelted += amount;
		lastItemSmelted = Instant.now();
	}
}