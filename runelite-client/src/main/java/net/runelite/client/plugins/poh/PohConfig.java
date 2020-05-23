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
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;

@ConfigGroup("poh")
public interface PohConfig extends Config
{
	@ConfigItem(
		keyName = "showAltar",
		name = "Show Altar",
		description = "Configures whether or not the altar is displayed",
		position = 1
	)
	default boolean showAltar()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showDigsitePendant",
		name = "Show Digsite Pendant",
		description = "Configures whether or not the Digsite Pendant is displayed",
		position = 2
	)
	default boolean showDigsitePendant()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showExitPortal",
		name = "Show Exit portal",
		description = "Configures whether or not the exit portal is displayed",
		position = 3
	)
	default boolean showExitPortal()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showMagicTravel",
		name = "Show Fairy/ Spirit Tree/ Obelisk",
		description = "Configures whether or not the Fairy ring, Spirit tree or Obelisk is displayed",
		position = 4
	)
	default boolean showMagicTravel()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showGlory",
		name = "Show Glory mount",
		description = "Configures whether or not the mounted glory is displayed",
		position = 5
	)
	default boolean showGlory()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showBurner",
		name = "Show Incense Burner timers",
		description = "Configures whether or not unlit/lit burners are displayed",
		position = 6
	)
	default boolean showBurner()
	{
		return true;
	}

	@ConfigItem(
		keyName = "notifyBurner",
		name = "Burner Notifications",
		description = "Configures whether or not to send a notification before burners enter the random timer phase",
		position = 7
	)
	default boolean notifyBurner()
	{
		return false;
	}

	@ConfigItem(
		keyName = "notifyBurnerLeadTime",
		name = "Notification Lead Time",
		description = "Configures the lead time in seconds that a notification will be sent, before burner enters random timer phase.",
		position = 8
	)
	@Range(max = 130)
	@Units(Units.SECONDS)
	default int notifyBurnerLeadTime()
	{
		return 5;
	}

	@ConfigItem(
		keyName = "showJewelleryBox",
		name = "Show Jewellery Box",
		description = "Configures whether or not the jewellery box is displayed",
		position = 9
	)
	default boolean showJewelleryBox()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showMythicalCape",
		name = "Show Mythical Cape",
		description = "Configures whether or not the Mythical Cape is displayed",
		position = 10
	)
	default boolean showMythicalCape()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showPools",
		name = "Show Pools",
		description = "Configures whether or not the pools are displayed",
		position = 11
	)
	default boolean showPools()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showPortalNexus",
		name = "Show Portal Nexus",
		description = "Configures whether or not the Portal Nexus is displayed",
		position = 12
	)
	default boolean showPortalNexus()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showPortals",
		name = "Show Portals",
		description = "Configures whether to display teleport portals",
		position = 13
	)
	default boolean showPortals()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showRepairStand",
		name = "Show Repair stand",
		description = "Configures whether or not the repair stand is displayed",
		position = 14
	)
	default boolean showRepairStand()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showSpellbook",
		name = "Show Spellbook altar",
		description = "Configures whether or not the Spellbook altar is displayed",
		position = 15
	)
	default boolean showSpellbook()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showXericsTalisman",
		name = "Show Xeric's Talisman",
		description = "Configures whether or not the Xeric's Talisman is displayed",
		position = 16
	)
	default boolean showXericsTalisman()
	{
		return true;
	}
}
