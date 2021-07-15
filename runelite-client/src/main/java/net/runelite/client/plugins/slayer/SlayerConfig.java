/*
 * Copyright (c) 2017, Seth <Sethtroll3@gmail.com>
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

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Units;

@ConfigGroup(SlayerConfig.GROUP_NAME)
public interface SlayerConfig extends Config
{
	String GROUP_NAME = "slayer";

	// Key names for stored task values
	String TASK_NAME_KEY = "taskName";
	String AMOUNT_KEY = "amount";
	String INIT_AMOUNT_KEY = "initialAmount";
	String TASK_LOC_KEY = "taskLocation";
	String STREAK_KEY = "streak";
	String POINTS_KEY = "points";

	@ConfigItem(
		position = 1,
		keyName = "infobox",
		name = "Task InfoBox",
		description = "Display task information in an InfoBox"
	)
	default boolean showInfobox()
	{
		return true;
	}

	@ConfigItem(
		position = 2,
		keyName = "itemoverlay",
		name = "Count on Items",
		description = "Display task count remaining on slayer items"
	)
	default boolean showItemOverlay()
	{
		return true;
	}

	@ConfigItem(
		position = 3,
		keyName = "superiornotification",
		name = "Superior foe notification",
		description = "Toggles notifications on superior foe encounters"
	)
	default boolean showSuperiorNotification()
	{
		return true;
	}

	@ConfigItem(
		position = 4,
		keyName = "statTimeout",
		name = "InfoBox Expiry",
		description = "Set the time until the InfoBox expires"
	)
	@Units(Units.MINUTES)
	default int statTimeout()
	{
		return 5;
	}

	@ConfigSection(
			name = "Target Highlighting",
			description = "Slayer target highlighting",
			position = 5,
			closedByDefault = true
	)
	String slayerHighlighting = "slayerHighlighting";

	@ConfigItem(
			position = 5,
			keyName = "highlightTargets",
			name = "Highlight Targets",
			description = "Highlight monsters you can kill for your current slayer assignment",
			section = slayerHighlighting
	)
	default boolean highlightTargets()
	{
		return false;
	}

	@ConfigItem(
			position = 6,
			keyName = "filterHighlightTargetLevel",
			name = "Filter Target Levels",
			description = "Filter the highlighting of monsters by combat levels",
			section = slayerHighlighting
	)
	default boolean filterHighlightTargetLevel()
	{
		return false;
	}

	@ConfigItem(
		position = 7,
		keyName = "minHighlightTargetLevel",
		name = "Highlight Min Level",
		description = "Highlight monsters above a combat level threshold",
		section = slayerHighlighting
	)
	default int minHighlightTargetLevel()
	{
		return 0;
	}

	@ConfigItem(
			position = 8,
			keyName = "maxHighlightTargetLevel",
			name = "Highlight Max Level",
			description = "Highlight monsters below a combat level threshold",
			section = slayerHighlighting
	)
	default int maxHighlightTargetLevel()
	{
		return 9001;
	}

	@Alpha
	@ConfigItem(
			position = 9,
			keyName = "targetColor",
			name = "Target Color",
			description = "Color of the highlighted targets",
			section = slayerHighlighting
	)
	default Color getTargetColor()
	{
		return Color.RED;
	}

	@ConfigItem(
		position = 10,
		keyName = "weaknessPrompt",
		name = "Show Monster Weakness",
		description = "Show an overlay on a monster when it is weak enough to finish off (Only Lizards, Gargoyles & Rockslugs)"
	)
	default boolean weaknessPrompt()
	{
		return true;
	}

	@ConfigItem(
		position = 11,
		keyName = "taskCommand",
		name = "Task Command",
		description = "Configures whether the slayer task command is enabled<br> !task"
	)
	default boolean taskCommand()
	{
		return true;
	}
}
