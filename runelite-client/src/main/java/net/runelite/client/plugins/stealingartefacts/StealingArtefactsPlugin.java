/*
 * Copyright (c) 2019, honeyhoney <https://github.com/honeyhoney>
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
package net.runelite.client.plugins.stealingartefacts;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.Client;

import net.runelite.api.GameState;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import net.runelite.api.Player;
import net.runelite.api.Varbits;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.stream.Stream;

@PluginDescriptor(
		name = "Stealing Artefacts",
		description = "Show the location and/or status of your stealing artefact task.",
		tags = {"stealing", "artefact", "thieving"},
		enabledByDefault = false
)
public class StealingArtefactsPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private StealingArtefactsOverlay overlay;

	@Getter(AccessLevel.PACKAGE)
	private StealingArtefactState stealingArtefactState;

	private static final ImmutableSet<Integer> PORT_PISCARILIUS_REGIONS = ImmutableSet.of(6970, 7226);

	private static final ImmutableSet<Integer> ARTEFACT_ITEM_IDS = ImmutableSet.of(ItemID.STOLEN_PENDANT,
			ItemID.STOLEN_GARNET_RING, ItemID.STOLEN_CIRCLET, ItemID.STOLEN_FAMILY_HEIRLOOM, ItemID.STOLEN_JEWELRY_BOX);

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);

		if (client.getGameState() == GameState.LOGGED_IN)
		{
			clientThread.invoke(() ->
			{
				stealingArtefactState = StealingArtefactState.getStealingArtefactState(client.getVar(Varbits.STEALING_ARTEFACT_STATE));
			});
		}
		else
		{
			stealingArtefactState = null;
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	public boolean isInPortPiscariliusRegion()
	{
		Player player = client.getLocalPlayer();
		if (player != null)
		{
			return PORT_PISCARILIUS_REGIONS.contains(player.getWorldLocation().getRegionID());
		}

		return false;
	}

	private boolean hasArtefact()
	{
		ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);

		if (inventory != null)
		{
			final Stream<Item> items = Arrays.stream(inventory.getItems());
			return items.anyMatch(item -> ARTEFACT_ITEM_IDS.contains(item.getId()));
		}

		return false;
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged event)
	{
		int stealingArtefactStateVarbit = client.getVar(Varbits.STEALING_ARTEFACT_STATE);
		StealingArtefactState state = StealingArtefactState.getStealingArtefactState(stealingArtefactStateVarbit);

		if (state != StealingArtefactState.DELIVER_ARTEFACT)
		{
			stealingArtefactState = state;
		}
	}

	@Subscribe
	public void onItemContainerChanged(final ItemContainerChanged event)
	{
		if (event.getItemContainer() == client.getItemContainer(InventoryID.INVENTORY))
		{
			if (hasArtefact())
			{
				stealingArtefactState = StealingArtefactState.DELIVER_ARTEFACT;
			}
			else if (stealingArtefactState == StealingArtefactState.DELIVER_ARTEFACT)
			{
				stealingArtefactState = StealingArtefactState.FAILURE;
			}
		}
	}
}
