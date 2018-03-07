/*
 * Copyright (c) 2018, Seth <https://github.com/sethtroll>
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
package net.runelite.client.plugins.fightcave;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(
	keyName = "fightcave",
	name = "Fight Cave",
	description = "Configuration for the fight cave plugin"
)
public interface FightCaveConfig extends Config
{
	@ConfigItem(
		keyName = "showWave",
		name = "Show current wave",
		description = "Configures whether or not to display the current wave",
		position = 1
	)
	default boolean showWave()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showJad",
		name = "Show prayers for Jad",
		description = "Configures whether or not to display the correct prayer for jad",
		position = 2
	)
	default boolean showJad()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showTimer",
		name = "Show Fight cave timer",
		description = "Configures whether or not to display the fight cave timer",
		position = 3
	)
	default boolean showTimer()
	{
		return true;
	}
}
