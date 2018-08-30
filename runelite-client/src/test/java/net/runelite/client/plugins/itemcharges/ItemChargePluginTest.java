/*
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
package net.runelite.client.plugins.itemcharges;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.ItemComposition;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.ui.overlay.OverlayManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ItemChargePluginTest
{

	@Mock
	@Bind
	private Client client;

	@Mock
	@Bind
	private OverlayManager overlayManager;

	@Mock
	@Bind
	private ItemChargeConfig itemChargeConfig;

	@Mock
	@Bind
	private ItemConfigRegistry registry;

	@Inject
	private ItemChargePlugin itemChargePlugin;

	@Before
	public void before()
	{
		Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);
	}

	@Test
	public void dodgyChecksOnExamine()
	{
		String dodgyCheck = "Your dodgy necklace has 10 charges left.";
		ChatMessage chatMessage = new ChatMessage(ChatMessageType.SERVER, "", dodgyCheck, "");
		itemChargePlugin.onChatMessage(chatMessage);
		verify(registry).setCharges(ItemWithVarCharge.DODGY_NECKLACE, 10);
	}

	@Test
	public void dodgyChecksOnProtect()
	{
		String dodgyProtect = "Your dodgy necklace protects you. It has 9 charges left.";
		ChatMessage chatMessage = new ChatMessage(ChatMessageType.SERVER, "", dodgyProtect, "");
		itemChargePlugin.onChatMessage(chatMessage);
		verify(registry).setCharges(ItemWithVarCharge.DODGY_NECKLACE, 9);
	}

	@Test
	public void dodgyChecksOnOneLeft()
	{
		String dodgyProtect1 = "Your dodgy necklace protects you. <col=ff0000>It has 1 charge left.</col>";
		ChatMessage chatMessage = new ChatMessage(ChatMessageType.SERVER, "", dodgyProtect1, "");
		itemChargePlugin.onChatMessage(chatMessage);
		verify(registry).setCharges(ItemWithVarCharge.DODGY_NECKLACE, 1);
	}

	@Test
	public void dodgyChecksOnDepletionAndNotifies()
	{
		String dodgyBreak = "Your dodgy necklace protects you. <col=ff0000>It then crumbles to dust.</col>";
		ItemComposition itemCompMock = Mockito.mock(ItemComposition.class);
		when(itemCompMock.getName()).thenReturn("Dodgy necklace");
		ChatMessage chatMessage = new ChatMessage(ChatMessageType.SERVER, "", dodgyBreak, "");
		itemChargePlugin.onChatMessage(chatMessage);
		verify(registry).setCharges(ItemWithVarCharge.DODGY_NECKLACE, 10);
		verify(registry).sendNotificationIfEnabled(ItemWithVarCharge.DODGY_NECKLACE, "Your Dodgy Necklace has crumbled to dust.");
	}

	@Test
	public void ringOfRecoilChecksOnExamine()
	{
		String recoilCheck = "You can inflict 32 more points of damage before a ring will shatter.";
		ChatMessage chatMessage = new ChatMessage(ChatMessageType.SERVER, "", recoilCheck, "");
		itemChargePlugin.onChatMessage(chatMessage);
		verify(registry).setCharges(ItemWithVarCharge.RING_OF_RECOIL, 32);
	}
}