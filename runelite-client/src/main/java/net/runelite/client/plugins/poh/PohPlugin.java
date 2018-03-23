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

import com.google.common.collect.Sets;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.DecorativeObject;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import static net.runelite.api.ObjectID.INCENSE_BURNER;
import static net.runelite.api.ObjectID.INCENSE_BURNER_13209;
import static net.runelite.api.ObjectID.INCENSE_BURNER_13210;
import static net.runelite.api.ObjectID.INCENSE_BURNER_13211;
import static net.runelite.api.ObjectID.INCENSE_BURNER_13212;
import static net.runelite.api.ObjectID.INCENSE_BURNER_13213;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.events.ConfigChanged;
import net.runelite.api.events.DecorativeObjectDespawned;
import net.runelite.api.events.DecorativeObjectSpawned;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

@PluginDescriptor(
	name = "Player-owned House"
)
public class PohPlugin extends Plugin
{
	static final Set<Integer> BURNER_UNLIT = Sets.newHashSet(INCENSE_BURNER, INCENSE_BURNER_13210, INCENSE_BURNER_13212);
	static final Set<Integer> BURNER_LIT = Sets.newHashSet(INCENSE_BURNER_13209, INCENSE_BURNER_13211, INCENSE_BURNER_13213);

	@Getter(AccessLevel.PACKAGE)
	private final Map<TileObject, Tile> pohObjects = new HashMap<>();

	@Inject
	private PohConfig config;

	@Inject
	private PohOverlay overlay;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Inject
	private ItemManager itemManager;

	@Inject
	private BurnerOverlay burnerOverlay;

	@Provides
	PohConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PohConfig.class);
	}

	@Override
	public Collection<Overlay> getOverlays()
	{
		return Arrays.asList(overlay, burnerOverlay);
	}

	@Override
	protected void startUp() throws Exception
	{
		overlay.updateConfig();
	}

	@Override
	protected void shutDown() throws Exception
	{
		pohObjects.clear();
		infoBoxManager.removeIf(t -> t instanceof BurnerTimer);
	}

	@Subscribe
	public void updateConfig(ConfigChanged event)
	{
		overlay.updateConfig();
		if(!config.showBurnerTimer()){
			infoBoxManager.removeIf(t -> t instanceof BurnerTimer);
		}
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		GameObject gameObject = event.getGameObject();
		if (BURNER_LIT.contains(gameObject.getId()) || BURNER_UNLIT.contains(gameObject.getId()) || PohIcons.getIcon(gameObject.getId()) != null)
		{
			pohObjects.put(gameObject, event.getTile());
		}
		if(config.showBurnerTimer())
		{
			if (BURNER_LIT.contains(gameObject.getId()))
			{
				infoBoxManager.removeIf(t -> t instanceof BurnerTimer);
				BurnerTimer timer = new BurnerTimer(config.burnerTimer(), itemManager.getImage(594));
				timer.setTooltip("Lit Torch");
				infoBoxManager.addInfoBox(timer);
			}
		}
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned event)
	{
		GameObject gameObject = event.getGameObject();
		pohObjects.remove(gameObject);
	}

	@Subscribe
	public void onDecorativeObjectSpawned(DecorativeObjectSpawned event)
	{
		DecorativeObject decorativeObject = event.getDecorativeObject();
		if (PohIcons.getIcon(decorativeObject.getId()) != null)
		{
			pohObjects.put(decorativeObject, event.getTile());
		}
	}

	@Subscribe
	public void onDecorativeObjectDespawned(DecorativeObjectDespawned event)
	{
		DecorativeObject decorativeObject = event.getDecorativeObject();
		pohObjects.remove(decorativeObject);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOADING)
		{
			pohObjects.clear();
		}
	}
}
