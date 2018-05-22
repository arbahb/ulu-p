/*
 * Copyright (c) 2018, Kamiel, <https://github.com/Kamielvf>
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
package net.runelite.client.plugins.screenmarkers;

import com.google.common.base.Strings;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.ConfigChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.OverlaysChanged;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.screenmarkers.ui.ScreenMarkerPluginPanel;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.PluginToolbar;
import net.runelite.client.ui.overlay.Overlay;

@PluginDescriptor(
	name = "Screen Markers"
)
@Slf4j
public class ScreenMarkerPlugin extends Plugin
{
	private static final String PLUGIN_NAME = "Screen Markers";
	private static final String CONFIG_GROUP = "screenmarkers";
	private static final String CONFIG_KEY = "markers";
	private static final String ICON_FILE = "panel_icon.png";
	private static final String DEFAULT_MARKER_NAME = "Marker";
	private static final Dimension DEFAULT_SIZE = new Dimension(2, 2);
	private static final Gson GSON = new GsonBuilder()
		.registerTypeAdapter(ScreenMarker.class, new ScreenMarkerDeserializer())
		.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.FINAL)
		.create();

	@Inject
	private EventBus eventBus;

	@Inject
	private ConfigManager configManager;

	@Inject
	private MouseManager mouseManager;

	@Inject
	private PluginToolbar pluginToolbar;

	@Inject
	private ScreenMarkerCreationOverlay overlay;

	private ScreenMarkerMouseListener mouseListener;
	private ScreenMarkerPluginPanel pluginPanel;
	private NavigationButton navigationButton;

	@Getter
	private List<ScreenMarker> screenMarkers = new ArrayList<>();

	@Getter(AccessLevel.PACKAGE)
	private ScreenMarker currentMarker;

	@Getter
	private boolean creatingScreenMarker = false;

	@Override
	public Collection<Overlay> getOverlays()
	{
		List<Overlay> overlays = new ArrayList<>();
		overlays.add(overlay);

		for (ScreenMarker marker : screenMarkers)
		{
			overlays.add(marker.getOverlay());
		}

		return overlays;
	}

	@Override
	protected void startUp() throws Exception
	{
		screenMarkers = loadConfig(null);
		pluginPanel = injector.getInstance(ScreenMarkerPluginPanel.class);
		pluginPanel.init();

		BufferedImage icon;
		synchronized (ImageIO.class)
		{
			icon = ImageIO.read(ScreenMarkerPlugin.class.getResourceAsStream(ICON_FILE));
		}

		navigationButton = NavigationButton.builder()
			.name(PLUGIN_NAME)
			.icon(icon)
			.panel(pluginPanel)
			.build();

		pluginToolbar.addNavigation(navigationButton);

		mouseListener = new ScreenMarkerMouseListener(this);
	}

	@Override
	protected void shutDown() throws Exception
	{
		pluginToolbar.removeNavigation(navigationButton);
		setMouseListenerEnabled(false);
		creatingScreenMarker = false;
		screenMarkers.clear();

		pluginPanel = null;
		currentMarker = null;
		mouseListener = null;
		navigationButton = null;
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (screenMarkers.isEmpty() && event.getGroup().equals(CONFIG_GROUP) && event.getKey().equals(CONFIG_KEY))
		{
			screenMarkers = loadConfig(event.getNewValue());
		}
	}

	public void setMouseListenerEnabled(boolean enabled)
	{
		if (enabled)
		{
			mouseManager.registerMouseListener(mouseListener);
		}
		else
		{
			mouseManager.unregisterMouseListener(mouseListener);
		}
	}

	public void startCreation(Point location)
	{
		currentMarker = new ScreenMarker(
			new Rectangle(location, DEFAULT_SIZE),
			DEFAULT_MARKER_NAME,
			pluginPanel.getSelectedBorderThickness(),
			pluginPanel.getSelectedColor(),
			pluginPanel.getSelectedFillColor(),
			true
		);
		creatingScreenMarker = true;
	}

	public void finishCreation(boolean aborted)
	{
		if (!aborted)
		{
			setMouseListenerEnabled(false);
			pluginPanel.setCreationEnabled(false);
			screenMarkers.add(currentMarker);
			pluginPanel.rebuild();
			updateConfig();
			fireOverlaysChangedEvent();
		}

		creatingScreenMarker = false;
		currentMarker = null;
	}

	public void deleteMarker(ScreenMarker marker)
	{
		screenMarkers.remove(marker);
		pluginPanel.rebuild();
		updateConfig();
		fireOverlaysChangedEvent();
	}

	public void resizeMarker(Point point, int dx, int dy)
	{
		//TODO: Allow resizing below base point
		Dimension currentSize = currentMarker.getBounds().getSize();
		currentMarker.getRenderable().setPreferredSize(new Dimension(currentSize.width + dx, currentSize.height + dy));
	}

	public void updateConfig()
	{
		if (screenMarkers.isEmpty())
		{
			configManager.unsetConfiguration(CONFIG_GROUP, CONFIG_KEY);
			return;
		}

		String json = GSON.toJson(screenMarkers);
		configManager.setConfiguration(CONFIG_GROUP, CONFIG_KEY, json);
	}

	private List<ScreenMarker> loadConfig(String json)
	{
		if (json == null)
		{
			json = configManager.getConfiguration(CONFIG_GROUP, CONFIG_KEY);
		}

		if (Strings.isNullOrEmpty(json))
		{
			return new ArrayList<>();
		}

		return GSON.fromJson(json, new TypeToken<ArrayList<ScreenMarker>>()
		{
		}.getType());
	}

	private void fireOverlaysChangedEvent()
	{
		eventBus.post(new OverlaysChanged());
	}
}
