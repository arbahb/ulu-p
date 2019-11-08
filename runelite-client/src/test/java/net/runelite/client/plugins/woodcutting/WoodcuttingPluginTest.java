/*
 * Copyright (c) 2019, Jordan Zomerlei <jordan@zomerlei.com>
 * Copyright (c) 2019, Adam <Adam@sigterm.info>
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
package net.runelite.client.plugins.woodcutting;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import java.util.Map;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.client.Notifier;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.xptracker.XpTrackerService;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.inject.Inject;

@RunWith(MockitoJUnitRunner.class)
public class WoodcuttingPluginTest
{
	private static final Map<String, Boolean> ALL_MESSAGES;

	static
	{
		ImmutableMap.Builder<String, Boolean> map = ImmutableMap.builder();
		map.put("You get some fake messages.", Boolean.FALSE);
		map.put("You get some oak logs.", Boolean.TRUE);
		map.put("Fake message that ends with logs.", Boolean.FALSE);
		map.put("You get an arctic log.", Boolean.TRUE);
		map.put("You get some logs.", Boolean.TRUE);
		map.put("You get some maple logs.", Boolean.TRUE);
		map.put("You get an awful headache.", Boolean.FALSE);

		ALL_MESSAGES = map.build();
	}

	private static final String BIRDS_NEST_MESSAGE = "A bird's nest falls out of the tree.";

	@Inject
	WoodcuttingPlugin woodcuttingPlugin;

	@Mock
	@Bind
	WoodcuttingConfig woodcuttingConfig;

	@Mock
	@Bind
	Notifier notifier;

	@Mock
	@Bind
	Client client;

	@Mock
	@Bind
	InfoBoxManager infoBoxManager;

	@Mock
	@Bind
	ItemManager itemManager;

	@Mock
	@Bind
	XpTrackerService xpTrackerService;

	@Mock
	@Bind
	WoodcuttingOverlay woodcuttingOverlay;

	@Mock
	@Bind
	OverlayManager overlayManager;

	@Before
	public void before()
	{
		Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);
	}

	@Test
	public void testOnChatMessage()
	{
		WoodcuttingSession woodcuttingSession;

		for (String message : ALL_MESSAGES.keySet())
		{
			// reset the plugin
			woodcuttingPlugin = new WoodcuttingPlugin();
			ChatMessage chatMessage = new ChatMessage(null, ChatMessageType.SPAM, "", message, "", 0);
			woodcuttingPlugin.onChatMessage(chatMessage);
			woodcuttingSession = woodcuttingPlugin.getSession();

			if (ALL_MESSAGES.get(message))
			{
				assertNotNull(woodcuttingSession);
				assertNotNull(woodcuttingSession.getLastLogCut());
			}
			else
			{
				assertNull(woodcuttingSession);
			}
		}
	}

	@Test
	public void testBirdsNest()
	{
		ChatMessage chatMessageEvent = new ChatMessage(null, ChatMessageType.SPAM, "", BIRDS_NEST_MESSAGE, "", 0);

		when(woodcuttingConfig.showNestNotification()).thenReturn(true);
		woodcuttingPlugin.onChatMessage(chatMessageEvent);
		verify(notifier).notify("A bird nest has spawned!");

		when(woodcuttingConfig.showNestNotification()).thenReturn(false);
		woodcuttingPlugin.onChatMessage(chatMessageEvent);
		verifyNoMoreInteractions(notifier);
	}

	@Test
	public void testDurationExpired()
	{
		when(woodcuttingConfig.statTimeout()).thenReturn(1);
		WoodcuttingSession woodcuttingSession;

		woodcuttingPlugin.onGameTick(new GameTick());
		ChatMessage chatMessage = new ChatMessage(null, ChatMessageType.SPAM, "", "You get some oak logs.", "", 0);
		woodcuttingPlugin.onChatMessage(chatMessage);

		woodcuttingSession = woodcuttingPlugin.getSession();
		woodcuttingPlugin.onGameTick(new GameTick());

		assertNotNull(woodcuttingSession);
		assertNotNull(woodcuttingSession.getLastLogCut());

		when(woodcuttingConfig.statTimeout()).thenReturn(0);
		woodcuttingPlugin.onGameTick(new GameTick());
		woodcuttingSession = woodcuttingPlugin.getSession();

		assertNull(woodcuttingSession);
	}
}