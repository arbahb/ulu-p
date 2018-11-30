/*
 * Copyright (c) 2018, Joshua Filby <joshua@filby.me>
 * Copyright (c) 2018, Jordan Atwood <jordan.atwood423@gmail.com>
 * Copyright (c) 2018, Haashi <https://github.com/Haashi>
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
package net.runelite.client.plugins.virtuallevels;

import com.google.common.eventbus.Subscribe;
import javax.inject.Inject;

import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.Experience;
import net.runelite.api.Skill;
import net.runelite.api.events.ScriptCallbackEvent;
import net.runelite.api.events.SkillLeveledUp;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.events.PluginChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
	name = "Virtual Levels",
	description = "Configuration for the virtual levels plugin.",
	tags = {"skill", "total", "max"},
	enabledByDefault = false
)
public class VirtualLevelsPlugin extends Plugin
{
	private static final String TOTAL_LEVEL_TEXT_PREFIX = "Total level:<br>";

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ChatMessageManager chatMessageManager;

	@Override
	protected void shutDown()
	{
		clientThread.invoke(this::simulateSkillChange);
	}

	@Subscribe
	public void onPluginChanged(PluginChanged pluginChanged)
	{
		// this is guaranteed to be called after the plugin has been registered by the eventbus. startUp is not.
		if (pluginChanged.getPlugin() == this)
		{
			clientThread.invoke(this::simulateSkillChange);
		}
	}

	@Subscribe
	public void onScriptCallbackEvent(ScriptCallbackEvent e)
	{
		final String eventName = e.getEventName();

		final int[] intStack = client.getIntStack();
		final int intStackSize = client.getIntStackSize();
		final String[] stringStack = client.getStringStack();
		final int stringStackSize = client.getStringStackSize();

		switch (eventName)
		{
			case "skillTabBaseLevel":
				final int skillId = intStack[intStackSize - 2];
				final Skill skill = Skill.values()[skillId];
				final int exp = client.getSkillExperience(skill);

				// alter the local variable containing the level to show
				intStack[intStackSize - 1] = Experience.getLevelForXp(exp);
				break;
			case "skillTabMaxLevel":
				// alter max level constant
				intStack[intStackSize - 1] = Experience.MAX_VIRT_LEVEL;
				break;
			case "skillTabTotalLevel":
				int level = 0;

				for (Skill s : Skill.values())
				{
					if (s == Skill.OVERALL)
					{
						continue;
					}

					level += Experience.getLevelForXp(client.getSkillExperience(s));
				}

				stringStack[stringStackSize - 1] = TOTAL_LEVEL_TEXT_PREFIX + level;
				break;
		}
	}

	private void simulateSkillChange()
	{
		// this fires widgets listening for all skill changes
		for (Skill skill : Skill.values())
		{
			if (skill != Skill.OVERALL)
			{
				client.queueChangedSkill(skill);
			}
		}
	}

	@Subscribe
	public void onSkillLeveledUp(SkillLeveledUp event)
	{
		final Skill skill = event.getSkill();
		final int lvl = Experience.getLevelForXp(client.getSkillExperience(skill));
		if (lvl > 99)
		{
			final String message = new ChatMessageBuilder()
			.append(ChatColorType.HIGHLIGHT)
			.append("Congratulations, you've just advanced your " + skill.getName() + " level. You are now level " + lvl + ".")
			.build();

			chatMessageManager.queue(
				QueuedMessage.builder()
					.type(ChatMessageType.GAME)
					.runeLiteFormattedMessage(message)
					.build());
			}
	}

}
