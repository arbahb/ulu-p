/*
 * Copyright (c) 2017, Devin French <https://github.com/devinfrench>
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
package net.runelite.client.plugins.idlenotifier;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup(
	keyName = "idlenotifier",
	name = "Idle Notifier",
	description = "Configuration for the idle notifier plugin"
)
public interface IdleNotifierConfig extends Config
{
	@ConfigItem(
		keyName = "animationidle",
		name = "Idle Animation Notification",
		description = "Configures if the idle animation notification is enabled",
		position = 1
	)
	default boolean animationIdle()
	{
		return true;
	}
	
	@ConfigItem(
		keyName = "animationidledelay",
		name = "Idle Animation Notification Delay (s)",
		description = "The animation notification delay after the player is idle",
		position = 2
	)
	default int getAnimationIdleNotificationDelay()
	{
		return 5;
	}	

	@ConfigItem(
		keyName = "combatidle",
		name = "Combat Idle Notification",
		description = "Configures if the combat idle notification is enabled",
		position = 3
	)
	default boolean combatIdle()
	{
		return true;
	}

	@ConfigItem(
		keyName = "combatidledelay",
		name = "Combat Idle Notification Delay (s)",
		description = "The notification delay after the player is idle",
		position = 4
	)
	default int getCombatIdleNotificationDelay()
	{
		return 5;
	}

	@ConfigItem(
		keyName = "hitpointslimit",
		name = "Hitpoints Notification Threshold",
		description = "The amount of hitpoints to send a notification at",
		position = 5
	)
	default int getHitpointsThreshold()
	{
		return 15;
	}

	@ConfigItem(
		keyName = "prayerlimit",
		name = "Prayer Notification Threshold",
		description = "The amount of prayer points to send a notification at",
		position = 6
	)
	default int getPrayerThreshold()
	{
		return 15;
	}
}