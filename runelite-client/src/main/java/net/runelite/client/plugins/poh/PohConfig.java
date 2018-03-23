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

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(
	keyName = "poh",
	name = "Player-owned House",
	description = "Configuration for the POH plugin"
)
public interface PohConfig extends Config
{
	@ConfigItem(
		keyName = "showPortals",
		name = "Show Portals",
		description = "Configures whether to display teleport portals",
		position = 1
	)
	default boolean showPortals()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showAltar",
		name = "Show Altar",
		description = "Configures whether or not the altar is displayed",
		position = 2
	)
	default boolean showAltar()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showGlory",
		name = "Show Glory mount",
		description = "Configures whether or not the mounted glory is displayed",
		position = 3
	)
	default boolean showGlory()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showPools",
		name = "Show Pools",
		description = "Configures whether or not the pools are displayed",
		position = 4
	)
	default boolean showPools()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showRepairStand",
		name = "Show Repair stand",
		description = "Configures whether or not the repair stand is displayed",
		position = 5
	)
	default boolean showRepairStand()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showExitPortal",
		name = "Show Exit portal",
		description = "Configures whether or not the exit portal is displayed",
		position = 6
	)
	default boolean showExitPortal()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showBurner",
		name = "Show Unlit/Lit burner",
		description = "Configures whether or not unlit/lit burners are displayed",
		position = 7
	)
	default boolean showBurner()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showTimer",
		name = "Show the burner timer",
		description = "[HOST ONLY]",
		position = 8
	)
	default boolean showBurnerTimer()
	{
		return false;
	}

	@ConfigItem(
		keyName = "burnerTimer",
		name = "Burner timer (s)",
		description = "Will indicate when burners run out",
		position = 9
	)
	default int burnerTimer()
	{
		return 120;
	}
}
