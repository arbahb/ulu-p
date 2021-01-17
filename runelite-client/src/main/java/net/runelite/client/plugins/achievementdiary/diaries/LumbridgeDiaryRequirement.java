/*
 * Copyright (c) 2018, Marshall <https://github.com/marshdevs>
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
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
package net.runelite.client.plugins.achievementdiary.diaries;

import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.runelite.api.Skill;
import net.runelite.client.plugins.achievementdiary.GenericDiaryRequirement;
import net.runelite.client.util.requirements.SkillRequirement;
import net.runelite.client.util.requirements.QuestStatusRequirement;
import net.runelite.client.util.requirements.CombatLevelRequirement;

public class LumbridgeDiaryRequirement extends GenericDiaryRequirement
{
	public LumbridgeDiaryRequirement()
	{
		// EASY
		add("Complete a lap of the Draynor Village agility course.",
			new SkillRequirement(Skill.AGILITY, 10));
		add("Slay a Cave bug beneath Lumbridge Swamp.",
			new SkillRequirement(Skill.SLAYER, 7));
		add("Have Sedridor teleport you to the Essence Mine.",
			new QuestStatusRequirement(Quest.RUNE_MYSTERIES, QuestState.FINISHED));
		add("Craft some water runes.",
			new SkillRequirement(Skill.RUNECRAFT, 5),
			new QuestStatusRequirement(Quest.RUNE_MYSTERIES, QuestState.FINISHED));
		add("Chop and burn some oak logs in Lumbridge.",
			new SkillRequirement(Skill.WOODCUTTING, 15),
			new SkillRequirement(Skill.FIREMAKING, 15));
		add("Catch some Anchovies in Al Kharid.",
			new SkillRequirement(Skill.FISHING, 15));
		add("Bake some Bread on the Lumbridge kitchen range.",
			new QuestStatusRequirement(Quest.COOKS_ASSISTANT, QuestState.FINISHED));
		add("Mine some Iron ore at the Al Kharid mine.",
			new SkillRequirement(Skill.MINING, 15));

		// MEDIUM
		add("Complete a lap of the Al Kharid agility course.",
			new SkillRequirement(Skill.AGILITY, 20));
		add("Grapple across the River Lum.",
			new SkillRequirement(Skill.AGILITY, 8),
			new SkillRequirement(Skill.STRENGTH, 19),
			new SkillRequirement(Skill.RANGED, 37));
		add("Purchase an upgraded device from Ava.",
			new SkillRequirement(Skill.RANGED, 50),
			new QuestStatusRequirement(Quest.ANIMAL_MAGNETISM, QuestState.FINISHED));
		add("Travel to the Wizards' Tower by Fairy ring.",
			new QuestStatusRequirement(Quest.FAIRYTALE_II__CURE_A_QUEEN, QuestState.IN_PROGRESS));
		add("Cast the teleport to Lumbridge spell.",
			new SkillRequirement(Skill.MAGIC, 31));
		add("Catch some Salmon in Lumbridge.",
			new SkillRequirement(Skill.FISHING, 30));
		add("Craft a coif in the Lumbridge cow pen.",
			new SkillRequirement(Skill.CRAFTING, 38));
		add("Chop some willow logs in Draynor Village.",
			new SkillRequirement(Skill.WOODCUTTING, 30));
		add("Pickpocket Martin the Master Gardener.",
			new SkillRequirement(Skill.THIEVING, 38));
		add("Get a slayer task from Chaeldar.",
			new CombatLevelRequirement(70),
			new QuestStatusRequirement(Quest.LOST_CITY, QuestState.FINISHED));
		add("Catch an Essence or Eclectic impling in Puro-Puro.",
			new SkillRequirement(Skill.HUNTER, 42),
			new QuestStatusRequirement(Quest.LOST_CITY));
		add("Craft some Lava runes at the fire altar in Al Kharid.",
			new SkillRequirement(Skill.RUNECRAFT, 23),
			new QuestStatusRequirement(Quest.RUNE_MYSTERIES, QuestState.FINISHED));

		// HARD
		add("Cast Bones to Peaches in Al Kharid palace.",
			new SkillRequirement(Skill.MAGIC, 60));
		add("Squeeze past the jutting wall on your way to the cosmic altar.",
			new SkillRequirement(Skill.AGILITY, 46),
			new QuestStatusRequirement(Quest.LOST_CITY, QuestState.FINISHED));
		add("Craft 56 Cosmic runes simultaneously.",
			new SkillRequirement(Skill.RUNECRAFT, 59),
			new QuestStatusRequirement(Quest.LOST_CITY));
		add("Travel from Lumbridge to Edgeville on a Waka Canoe.",
			new SkillRequirement(Skill.WOODCUTTING, 57));
		add("Collect at least 100 Tears of Guthix in one visit.",
			new QuestStatusRequirement(Quest.TEARS_OF_GUTHIX, QuestState.FINISHED));
		add("Take the train from Dorgesh-Kaan to Keldagrim.",
			new QuestStatusRequirement(Quest.ANOTHER_SLICE_OF_HAM, QuestState.FINISHED));
		add("Purchase some Barrows gloves from the Lumbridge bank chest.",
			new QuestStatusRequirement(Quest.RECIPE_FOR_DISASTER, QuestState.FINISHED));
		add("Pick some Belladonna from the farming patch at Draynor Manor.",
			new SkillRequirement(Skill.FARMING, 63));
		add("Light your mining helmet in the Lumbridge castle basement.",
			new SkillRequirement(Skill.FIREMAKING, 65));
		add("Recharge your prayer at Clan Wars with Smite activated.",
			new SkillRequirement(Skill.PRAYER, 52));
		add("Craft, string and enchant an Amulet of Power in Lumbridge.",
			new SkillRequirement(Skill.CRAFTING, 70),
			new SkillRequirement(Skill.MAGIC, 57));

		// ELITE
		add("Steal from a Dorgesh-Kaan rich chest.",
			new SkillRequirement(Skill.THIEVING, 78),
			new QuestStatusRequirement(Quest.DEATH_TO_THE_DORGESHUUN, QuestState.FINISHED));
		add("Pickpocket Movario on the Dorgesh-Kaan Agility course.",
			new SkillRequirement(Skill.AGILITY, 70),
			new SkillRequirement(Skill.RANGED, 70),
			new SkillRequirement(Skill.STRENGTH, 70),
			new QuestStatusRequirement(Quest.DEATH_TO_THE_DORGESHUUN, QuestState.FINISHED));
		add("Chop some magic logs at the Mage Training Arena.",
			new SkillRequirement(Skill.WOODCUTTING, 75));
		add("Smith an Adamant platebody down Draynor sewer.",
			new SkillRequirement(Skill.SMITHING, 88));
		add("Craft 140 or more Water runes at once.",
			new SkillRequirement(Skill.RUNECRAFT, 76),
			new QuestStatusRequirement(Quest.RUNE_MYSTERIES, QuestState.FINISHED));
	}
}
