/*
 * Copyright (c) 2018, Lotto <https://github.com/devLotto>
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
package net.runelite.client.plugins.cluescrolls.clues;

import com.google.common.collect.ImmutableMap;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import net.runelite.api.Varbits;
import net.runelite.api.annotations.Varbit;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.cluescrolls.ClueScrollPlugin;
import static net.runelite.client.plugins.cluescrolls.clues.Enemy.ANCIENT_WIZARDS;
import static net.runelite.client.plugins.cluescrolls.clues.Enemy.ARMADYLEAN_GUARD;
import static net.runelite.client.plugins.cluescrolls.clues.Enemy.ARMADYLEAN_OR_BANDOSIAN_GUARD;
import static net.runelite.client.plugins.cluescrolls.clues.Enemy.BANDOSIAN_GUARD;
import static net.runelite.client.plugins.cluescrolls.clues.Enemy.BRASSICAN_MAGE;
import static net.runelite.client.plugins.cluescrolls.clues.Enemy.SARADOMIN_WIZARD;
import static net.runelite.client.plugins.cluescrolls.clues.Enemy.ZAMORAK_WIZARD;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

@Getter
public class CoordinateClue extends ClueScroll implements LocationClueScroll
{
	@Getter
	@Builder
	private static class CoordinateClueInfo
	{
		private final String directions;
		@Getter(onMethod_ = {@Varbit})
		@Builder.Default
		private final int lightSourceVarbitId = -1;
		private final Enemy enemy;
	}

	static final ImmutableMap<WorldPoint, CoordinateClueInfo> CLUES = new ImmutableMap.Builder<WorldPoint, CoordinateClueInfo>()
		// Medium
		.put(new WorldPoint(2479, 3158, 0), CoordinateClueInfo.builder().directions("South of fruit tree patch, west of Tree Gnome Village.").build())
		.put(new WorldPoint(2887, 3154, 0), CoordinateClueInfo.builder().directions("West of Banana plantation on Karamja.").build())
		.put(new WorldPoint(2743, 3151, 0), CoordinateClueInfo.builder().directions("Entrance of Brimhaven dungeon.").build())
		.put(new WorldPoint(3184, 3150, 0), CoordinateClueInfo.builder().directions("South of Lumbridge Swamp.").build())
		.put(new WorldPoint(3217, 3177, 0), CoordinateClueInfo.builder().directions("East of Lumbridge Swamp.").build())
		.put(new WorldPoint(3007, 3144, 0), CoordinateClueInfo.builder().directions("Near the entrance to the Asgarnian Ice Dungeon, south of Port Sarim (AIQ).").build())
		.put(new WorldPoint(2896, 3119, 0), CoordinateClueInfo.builder().directions("Near Karambwan fishing spot (DKP).").build())
		.put(new WorldPoint(2697, 3207, 0), CoordinateClueInfo.builder().directions("Centre of Moss Giant Island, west of Brimhaven.").build())
		.put(new WorldPoint(2679, 3110, 0), CoordinateClueInfo.builder().directions("North of Hazelmere's house (CLS).").build())
		.put(new WorldPoint(3510, 3074, 0), CoordinateClueInfo.builder().directions("East of Uzer (DLQ).").build())
		.put(new WorldPoint(3160, 3251, 0), CoordinateClueInfo.builder().directions("West of trapdoor leading to H.A.M Hideout.").build())
		.put(new WorldPoint(2643, 3252, 0), CoordinateClueInfo.builder().directions("South of Ardougne Zoo, North of Tower of Life (DJP).").build())
		.put(new WorldPoint(2322, 3061, 0), CoordinateClueInfo.builder().directions("South-west of Castle wars (BKP).").build())
		.put(new WorldPoint(2875, 3046, 0), CoordinateClueInfo.builder().directions("North of nature altar, north of Shilo Village (CKR).").build())
		.put(new WorldPoint(2849, 3033, 0), CoordinateClueInfo.builder().directions("West of nature altar, north of Shilo Village (CKR).").build())
		.put(new WorldPoint(2848, 3296, 0), CoordinateClueInfo.builder().directions("North of Crandor island.").build())
		.put(new WorldPoint(2583, 2990, 0), CoordinateClueInfo.builder().directions("Feldip Hills, south-east of Gu'Thanoth (AKS).").build())
		.put(new WorldPoint(3179, 3344, 0), CoordinateClueInfo.builder().directions("In the cow pen north of the Lumbridge windmill.").build())
		.put(new WorldPoint(2383, 3370, 0), CoordinateClueInfo.builder().directions("West of the outpost").build())
		.put(new WorldPoint(3312, 3375, 0), CoordinateClueInfo.builder().directions("North-west of Exam Centre, on the hill.").build())
		.put(new WorldPoint(3121, 3384, 0), CoordinateClueInfo.builder().directions("North-east of Draynor Manor, near River Lum.").build())
		.put(new WorldPoint(3430, 3388, 0), CoordinateClueInfo.builder().directions("West of Mort Myre Swamp (BKR).").build())
		.put(new WorldPoint(2920, 3403, 0), CoordinateClueInfo.builder().directions("South-east of Taverley, near Lady of the Lake.").build())
		.put(new WorldPoint(2594, 2899, 0), CoordinateClueInfo.builder().directions("South-east of Feldip Hills, by the crimson swifts (AKS).").build())
		.put(new WorldPoint(2387, 3435, 0), CoordinateClueInfo.builder().directions("West of Tree Gnome Stronghold, near the pen containing terrorbirds.").build())
		.put(new WorldPoint(2512, 3467, 0), CoordinateClueInfo.builder().directions("Baxtorian Falls (Bring rope).").build())
		.put(new WorldPoint(2381, 3468, 0), CoordinateClueInfo.builder().directions("West of Tree Gnome Stronghold, north of the pen with terrorbirds.").build())
		.put(new WorldPoint(3005, 3475, 0), CoordinateClueInfo.builder().directions("Ice Mountain, west of Edgeville Monastery.").build())
		.put(new WorldPoint(2585, 3505, 0), CoordinateClueInfo.builder().directions("By the shore line north of the Coal Trucks.").build())
		.put(new WorldPoint(3443, 3515, 0), CoordinateClueInfo.builder().directions("South of Slayer Tower (CKS).").build())
		.put(new WorldPoint(2416, 3516, 0), CoordinateClueInfo.builder().directions("Tree Gnome Stronghold, west of Grand Tree, near swamp.").build())
		.put(new WorldPoint(3429, 3523, 0), CoordinateClueInfo.builder().directions("South of Slayer Tower (CKS).").build())
		.put(new WorldPoint(2363, 3531, 0), CoordinateClueInfo.builder().directions("North-east of Eagles' Peak (AKQ).").build())
		.put(new WorldPoint(2919, 3535, 0), CoordinateClueInfo.builder().directions("East of Burthorpe pub.").build())
		.put(new WorldPoint(3548, 3560, 0), CoordinateClueInfo.builder().directions("Inside Fenkenstrain's Castle.").build())
		.put(new WorldPoint(1476, 3566, 0), CoordinateClueInfo.builder().directions("Graveyard of Heroes in west Shayzien.").build())
		.put(new WorldPoint(2735, 3638, 0), CoordinateClueInfo.builder().directions("East of Rellekka, north-west of Golden Apple Tree (AJR).").build())
		.put(new WorldPoint(2681, 3653, 0), CoordinateClueInfo.builder().directions("Rellekka, in the garden of the south-east house.").build())
		.put(new WorldPoint(2537, 3881, 0), CoordinateClueInfo.builder().directions("Miscellania (CIP).").build())
		.put(new WorldPoint(2828, 3234, 0), CoordinateClueInfo.builder().directions("Southern coast of Crandor.").build())
		.put(new WorldPoint(1247, 3726, 0), CoordinateClueInfo.builder().directions("Just inside the Farming Guild").build())
		.put(new WorldPoint(3770, 3898, 0), CoordinateClueInfo.builder().directions("On the small island north-east of Fossil Island's mushroom forest.").build())
		.put(new WorldPoint(1659, 3111, 0), CoordinateClueInfo.builder().directions("Dig west of the Bazaar in Civitas illa Fortis.").build())
		// Hard
		.put(new WorldPoint(2209, 3161, 0), CoordinateClueInfo.builder().directions("North-east of Tyras Camp (BJS if 76 Agility).").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2181, 3206, 0), CoordinateClueInfo.builder().directions("South of Iorwerth Camp.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3081, 3209, 0), CoordinateClueInfo.builder().directions("Small Island (CLP).").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3399, 3246, 0), CoordinateClueInfo.builder().directions("Behind the PvP Arena.").build())
		.put(new WorldPoint(2699, 3251, 0), CoordinateClueInfo.builder().directions("Little island (AIR).").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3546, 3251, 0), CoordinateClueInfo.builder().directions("North-east of Burgh de Rott.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3544, 3256, 0), CoordinateClueInfo.builder().directions("North-east of Burgh de Rott.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2841, 3267, 0), CoordinateClueInfo.builder().directions("Crandor island.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3168, 3041, 0), CoordinateClueInfo.builder().directions("Bedabin Camp.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2542, 3031, 0), CoordinateClueInfo.builder().directions("Gu'Tanoth, may require 20gp.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2581, 3030, 0), CoordinateClueInfo.builder().directions("Gu'Tanoth island, enter cave north-west of Feldip Hills (AKS).").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2961, 3024, 0), CoordinateClueInfo.builder().directions("Ship yard (DKP).").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2339, 3311, 0), CoordinateClueInfo.builder().directions("East of Prifddinas on Arandar mountain pass.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3440, 3341, 0), CoordinateClueInfo.builder().directions("Nature Spirit's grotto (BIP).").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2763, 2974, 0), CoordinateClueInfo.builder().directions("Cairn Isle, west of Shilo Village (CKR).").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3138, 2969, 0), CoordinateClueInfo.builder().directions("West of Bandit Camp in Kharidian Desert.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2924, 2963, 0), CoordinateClueInfo.builder().directions("On the southern part of eastern Karamja, west of the gnome glider.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2838, 2914, 0), CoordinateClueInfo.builder().directions("Kharazi Jungle, near water pool (CKR).").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3441, 3419, 0), CoordinateClueInfo.builder().directions("Mort Myre Swamp (BKR).").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2950, 2902, 0), CoordinateClueInfo.builder().directions("South-east of Kharazi Jungle.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2775, 2891, 0), CoordinateClueInfo.builder().directions("South-west of Kharazi Jungle.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3113, 3602, 0), CoordinateClueInfo.builder().directions("Wilderness. South-west of Ferox Enclave (level 11).").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(2892, 3675, 0), CoordinateClueInfo.builder().directions("On the summit of Trollheim.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3168, 3677, 0), CoordinateClueInfo.builder().directions("Wilderness. Graveyard of Shadows.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(2853, 3690, 0), CoordinateClueInfo.builder().directions("Entrance to the troll Stronghold.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3305, 3692, 0), CoordinateClueInfo.builder().directions("Wilderness. West of eastern green dragons.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(3055, 3696, 0), CoordinateClueInfo.builder().directions("Wilderness. Bandit Camp.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(3302, 3696, 0), CoordinateClueInfo.builder().directions("Wilderness. West of eastern green dragons.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(1479, 3699, 0), CoordinateClueInfo.builder().directions("Lizardman Canyon (DJR).").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2712, 3732, 0), CoordinateClueInfo.builder().directions("North-east of Rellekka (DKS).").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2970, 3749, 0), CoordinateClueInfo.builder().directions("Wilderness. Forgotten Cemetery.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(3094, 3764, 0), CoordinateClueInfo.builder().directions("Wilderness. Mining site north of Bandit Camp.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(3311, 3769, 0), CoordinateClueInfo.builder().directions("Wilderness. South of the Silk Chasm (Venenatis).").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(1460, 3782, 0), CoordinateClueInfo.builder().directions("Lovakengj, near burning man.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3244, 3792, 0), CoordinateClueInfo.builder().directions("Wilderness. South-east of Lava Dragon Isle by some Chaos Dwarves.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(3140, 3804, 0), CoordinateClueInfo.builder().directions("Wilderness. North of black chinchompa hunter area.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(2946, 3819, 0), CoordinateClueInfo.builder().directions("Wilderness. Chaos Temple (level 38).").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(3771, 3825, 0), CoordinateClueInfo.builder().directions("Fossil Island. East of Museum Camp.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3013, 3846, 0), CoordinateClueInfo.builder().directions("Wilderness. West of Lava Maze, before KBD's lair.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(3058, 3884, 0), CoordinateClueInfo.builder().directions("Wilderness. Near runite ore north of Lava Maze.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(3290, 3889, 0), CoordinateClueInfo.builder().directions("Wilderness. Demonic Ruins.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(3770, 3897, 0), CoordinateClueInfo.builder().directions("Small Island north of Fossil Island.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(2505, 3899, 0), CoordinateClueInfo.builder().directions("Small Island north-west of Miscellania (AJS).").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3285, 3942, 0), CoordinateClueInfo.builder().directions("Wilderness. Rogues' Castle.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(3159, 3959, 0), CoordinateClueInfo.builder().directions("Wilderness. North of Deserted Keep, west of Resource Area.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(3039, 3960, 0), CoordinateClueInfo.builder().directions("Wilderness. Pirates' Hideout.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(2987, 3963, 0), CoordinateClueInfo.builder().directions("Wilderness. West of Wilderness Agility Course.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(3189, 3963, 0), CoordinateClueInfo.builder().directions("Wilderness. North of Resource Area, near magic axe hut.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(2341, 3697, 0), CoordinateClueInfo.builder().directions("North-east of the Piscatoris Fishing Colony bank.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(3143, 3774, 0), CoordinateClueInfo.builder().directions("In level 32 Wilderness, by the black chinchompa hunting area.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(2970, 3913, 0), CoordinateClueInfo.builder().directions("Frozen Waste Plateau, south-west of Wilderness Agility Course.").enemy(ZAMORAK_WIZARD).build())
		.put(new WorldPoint(1410, 3611, 0), CoordinateClueInfo.builder().directions("Lake Molch dock west of Shayzien Encampment.").enemy(SARADOMIN_WIZARD).build())
		.put(new WorldPoint(1409, 3483, 0), CoordinateClueInfo.builder().directions("South of Shayziens' Wall.").enemy(SARADOMIN_WIZARD).build())
		// Elite
		.put(new WorldPoint(2357, 3151, 0), CoordinateClueInfo.builder().directions("Lletya.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3587, 3180, 0), CoordinateClueInfo.builder().directions("Meiyerditch.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2820, 3078, 0), CoordinateClueInfo.builder().directions("Tai Bwo Wannai. Hardwood Grove. 100 Trading sticks or elite Karamja diary completion is needed to enter.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3811, 3060, 0), CoordinateClueInfo.builder().directions("Small island north-east of Mos Le'Harmless.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).lightSourceVarbitId(Varbits.FIRE_PIT_MOS_LE_HARMLESS).build())
		.put(new WorldPoint(2180, 3282, 0), CoordinateClueInfo.builder().directions("North of Iorwerth Camp.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2870, 2997, 0), CoordinateClueInfo.builder().directions("North-east corner in Shilo Village.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3302, 2988, 0), CoordinateClueInfo.builder().directions("On top of a cliff to the west of Pollnivneach.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2511, 2980, 0), CoordinateClueInfo.builder().directions("Just south of Gu'Tanoth, west of gnome glider (AKS).").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2732, 3372, 0), CoordinateClueInfo.builder().directions("Legends' Guild.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3573, 3425, 0), CoordinateClueInfo.builder().directions("North of Dessous's tomb from Desert Treasure.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3828, 2848, 0), CoordinateClueInfo.builder().directions("East of Harmony Island.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3225, 2838, 0), CoordinateClueInfo.builder().directions("South of Desert Treasure pyramid.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(1773, 3510, 0), CoordinateClueInfo.builder().directions("Ruins north of the Hosidius mine.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3822, 3562, 0), CoordinateClueInfo.builder().directions("North-east of Dragontooth Island. Bring a Ghostspeak Amulet and 25 Ecto-tokens to reach the island.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3603, 3564, 0), CoordinateClueInfo.builder().directions("North of the wrecked ship, outside of Port Phasmatys.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2936, 2721, 0), CoordinateClueInfo.builder().directions("Eastern shore of Crash Island.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2697, 2705, 0), CoordinateClueInfo.builder().directions("South-west of Ape Atoll.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2778, 3678, 0), CoordinateClueInfo.builder().directions("Mountain Camp.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2827, 3740, 0), CoordinateClueInfo.builder().directions("West of the entrance to the Ice Path, where the Troll child resides.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2359, 3799, 0), CoordinateClueInfo.builder().directions("Neitiznot.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2194, 3807, 0), CoordinateClueInfo.builder().directions("Pirates' Cove.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2700, 3808, 0), CoordinateClueInfo.builder().directions("Northwestern part of the Trollweiss and Rellekka Hunter area (DKS).").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3215, 3835, 0), CoordinateClueInfo.builder().directions("Wilderness. Lava Dragon Isle.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3369, 3894, 0), CoordinateClueInfo.builder().directions("Wilderness. Fountain of Rune.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2065, 3923, 0), CoordinateClueInfo.builder().directions("Outside the western wall on Lunar Isle.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3188, 3933, 0), CoordinateClueInfo.builder().directions("Wilderness. Resource Area. An entry fee of 7,500 coins is required, or less if Wilderness Diaries have been completed.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3043, 3940, 0), CoordinateClueInfo.builder().directions("Wilderness. South of Pirates' Hideout.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3380, 3963, 0), CoordinateClueInfo.builder().directions("Wilderness. North of Volcano.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3051, 3736, 0), CoordinateClueInfo.builder().directions("East of the Wilderness Obelisk in 28 Wilderness.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2316, 3814, 0), CoordinateClueInfo.builder().directions("West of Neitiznot, near the bridge.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2872, 3937, 0), CoordinateClueInfo.builder().directions("Weiss.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2484, 4016, 0), CoordinateClueInfo.builder().directions("Northeast corner of the Island of Stone.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2222, 3331, 0), CoordinateClueInfo.builder().directions("Prifddinas, west of the Tower of Voices").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3560, 3987, 0), CoordinateClueInfo.builder().directions("Lithkren. Digsite pendant teleport if unlocked, otherwise take rowboat from west of Mushroom Meadow Mushtree.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2318, 2954, 0), CoordinateClueInfo.builder().directions("North-east corner of the Isle of Souls (BJP).").enemy(BANDOSIAN_GUARD).build())
		.put(new WorldPoint(2094, 2889, 0), CoordinateClueInfo.builder().directions("West side of the Isle of Souls.").enemy(ARMADYLEAN_GUARD).build())
		.put(new WorldPoint(1451, 3509, 0), CoordinateClueInfo.builder().directions("Ruins of Morra.").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(3318, 2706, 0), CoordinateClueInfo.builder().directions("Necropolis mine").enemy(ARMADYLEAN_OR_BANDOSIAN_GUARD).build())
		.put(new WorldPoint(1557, 3183, 0), CoordinateClueInfo.builder().directions("North of Ortus Farm").enemy(ARMADYLEAN_GUARD).build())
		// Master
		.put(new WorldPoint(2178, 3209, 0), CoordinateClueInfo.builder().directions("South of Iorwerth Camp.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(2155, 3100, 0), CoordinateClueInfo.builder().directions("South of Port Tyras (BJS if 76 Agility).").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(2217, 3092, 0), CoordinateClueInfo.builder().directions("Poison Waste island (DLR).").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(3830, 3060, 0), CoordinateClueInfo.builder().directions("Small island located north-east of Mos Le'Harmless.").enemy(BRASSICAN_MAGE).lightSourceVarbitId(Varbits.FIRE_PIT_MOS_LE_HARMLESS).build())
		.put(new WorldPoint(2834, 3271, 0), CoordinateClueInfo.builder().directions("Crandor island.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(2732, 3284, 0), CoordinateClueInfo.builder().directions("Witchaven.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(3622, 3320, 0), CoordinateClueInfo.builder().directions("Meiyerditch. Outside mine.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(2303, 3328, 0), CoordinateClueInfo.builder().directions("East of Prifddinas.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(3570, 3405, 0), CoordinateClueInfo.builder().directions("North of Dessous's tomb from Desert Treasure.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(2840, 3423, 0), CoordinateClueInfo.builder().directions("Water Obelisk Island.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(3604, 3564, 0), CoordinateClueInfo.builder().directions("North of the wrecked ship, outside of Port Phasmatys (ALQ).").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(3085, 3569, 0), CoordinateClueInfo.builder().directions("Wilderness. Obelisk of Air.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(2934, 2727, 0), CoordinateClueInfo.builder().directions("Eastern shore of Crash Island.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(1451, 3695, 0), CoordinateClueInfo.builder().directions("West side of Lizardman Canyon with Lizardman shaman.").enemy(ANCIENT_WIZARDS).build())
		.put(new WorldPoint(2538, 3739, 0), CoordinateClueInfo.builder().directions("Waterbirth Island. Bring a pet rock and rune thrownaxe OR have 85 agility.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(1248, 3751, 0), CoordinateClueInfo.builder().directions("In the north wing of the Farming Guild.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(1698, 3792, 0), CoordinateClueInfo.builder().directions("Arceuus church.").enemy(ANCIENT_WIZARDS).build())
		.put(new WorldPoint(2951, 3820, 0), CoordinateClueInfo.builder().directions("Wilderness. Chaos Temple (level 38).").enemy(ANCIENT_WIZARDS).build())
		.put(new WorldPoint(2202, 3825, 0), CoordinateClueInfo.builder().directions("Pirates' Cove, between Lunar Isle and Rellekka.").enemy(ANCIENT_WIZARDS).build())
		.put(new WorldPoint(1761, 3853, 0), CoordinateClueInfo.builder().directions("Arceuus essence mine (CIS).").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(2090, 3863, 0), CoordinateClueInfo.builder().directions("South of Lunar Isle, west of Astral altar.").enemy(ANCIENT_WIZARDS).build())
		.put(new WorldPoint(1442, 3878, 0), CoordinateClueInfo.builder().directions("Northern area of the Lovakengj Sulphur Mine. Facemask or Slayer Helmet recommended.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(3380, 3929, 0), CoordinateClueInfo.builder().directions("Wilderness. Near Volcano.").enemy(ANCIENT_WIZARDS).build())
		.put(new WorldPoint(3188, 3939, 0), CoordinateClueInfo.builder().directions("Wilderness. Resource Area.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(3304, 3941, 0), CoordinateClueInfo.builder().directions("Wilderness. East of Rogues' Castle.").enemy(ANCIENT_WIZARDS).build())
		.put(new WorldPoint(3028, 3928, 0), CoordinateClueInfo.builder().directions("Wilderness. South-east of Agility Training Area.").enemy(BRASSICAN_MAGE).build())
		.put(new WorldPoint(1769, 3418, 0), CoordinateClueInfo.builder().directions("Crabclaw Isle").enemy(ANCIENT_WIZARDS).build())
		.build();

	private final String text;
	@Getter(AccessLevel.PRIVATE)
	private final WorldPoint location;
	/**
	 * For regions which are mirrored, the location of the clue in the mirrored region.
	 */
	@Nullable
	private final WorldPoint mirrorLocation;

	public CoordinateClue(String text, WorldPoint location, WorldPoint mirrorLocation)
	{
		this.text = text;
		this.location = location;
		this.mirrorLocation = mirrorLocation;

		final CoordinateClueInfo clueInfo = CLUES.get(location);
		if (clueInfo != null)
		{
			setFirePitVarbitId(clueInfo.getLightSourceVarbitId());
			setRequiresLight(clueInfo.getLightSourceVarbitId() != -1);
			setEnemy(clueInfo.getEnemy());
		}
		setRequiresSpade(true);
	}

	@Override
	public WorldPoint getLocation(ClueScrollPlugin plugin)
	{
		return location;
	}

	@Override
	public WorldPoint[] getLocations(ClueScrollPlugin plugin)
	{
		if (mirrorLocation != null)
		{
			return new WorldPoint[]{location, mirrorLocation};
		}
		else
		{
			return new WorldPoint[]{location};
		}
	}

	@Override
	public void makeOverlayHint(PanelComponent panelComponent, ClueScrollPlugin plugin)
	{
		panelComponent.getChildren().add(TitleComponent.builder().text("Coordinate Clue").build());

		final CoordinateClueInfo solution = CLUES.get(location);

		if (solution != null)
		{
			panelComponent.getChildren().add(LineComponent.builder()
				.left(solution.getDirections())
				.build());
			panelComponent.getChildren().add(LineComponent.builder().build());
		}

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Click the clue scroll on your world map to see dig location.")
			.build());

		renderOverlayNote(panelComponent, plugin);
	}

	@Override
	public void makeWorldOverlayHint(Graphics2D graphics, ClueScrollPlugin plugin)
	{
		for (WorldPoint worldPoint : getLocations(plugin))
		{
			LocalPoint localLocation = LocalPoint.fromWorld(plugin.getClient(), worldPoint);

			if (localLocation != null)
			{
				OverlayUtil.renderTileOverlay(plugin.getClient(), graphics, localLocation, plugin.getSpadeImage(), Color.ORANGE);
			}
		}
	}

	@Override
	public int[] getConfigKeys()
	{
		return new int[]{location.hashCode()};
	}
}
