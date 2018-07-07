/*
 * Copyright (c) 2017, Kronos <https://github.com/KronosDesign>
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
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
package net.runelite.client.plugins.devtools;

import java.awt.Color;
import java.awt.GridLayout;
import javax.inject.Inject;
import javax.swing.JButton;
import javax.swing.JPanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

@Slf4j
public class DevToolsPanel extends PluginPanel
{
	private final Client client;
	private final DevToolsPlugin plugin;

	private final WidgetInspector widgetInspector;
	private final VarInspector varInspector;

	@Inject
	public DevToolsPanel(Client client, DevToolsPlugin plugin, WidgetInspector widgetInspector, VarInspector varInspector)
	{
		super();
		this.client = client;
		this.plugin = plugin;
		this.widgetInspector = widgetInspector;
		this.varInspector = varInspector;

		setBackground(ColorScheme.DARK_GRAY_COLOR);

		add(createOptionsPanel());
	}

	private JPanel createOptionsPanel()
	{
		final JPanel container = new JPanel();
		container.setBackground(ColorScheme.DARK_GRAY_COLOR);
		container.setLayout(new GridLayout(0, 2, 3, 3));

		final JButton renderPlayersBtn = new JButton("It's coming home");
		renderPlayersBtn.addActionListener(e ->
		{
			highlightButton(renderPlayersBtn);
			plugin.togglePlayers();
		});
		container.add(renderPlayersBtn);

		final JButton renderNpcsBtn = new JButton("It's coming home");
		renderNpcsBtn.addActionListener(e ->
		{
			highlightButton(renderNpcsBtn);
			plugin.toggleNpcs();
		});
		container.add(renderNpcsBtn);

		final JButton renderGroundItemsBtn = new JButton("It's coming home");
		renderGroundItemsBtn.addActionListener(e ->
		{
			highlightButton(renderGroundItemsBtn);
			plugin.toggleGroundItems();
		});
		container.add(renderGroundItemsBtn);

		final JButton renderGroundObjectsBtn = new JButton("It's coming home");
		renderGroundObjectsBtn.addActionListener(e ->
		{
			highlightButton(renderGroundObjectsBtn);
			plugin.toggleGroundObjects();
		});
		container.add(renderGroundObjectsBtn);

		final JButton renderGameObjectsBtn = new JButton("It's coming home");
		renderGameObjectsBtn.addActionListener(e ->
		{
			highlightButton(renderGameObjectsBtn);
			plugin.toggleGameObjects();
		});
		container.add(renderGameObjectsBtn);

		final JButton renderWallsBtn = new JButton("It's coming home");
		renderWallsBtn.addActionListener(e ->
		{
			highlightButton(renderWallsBtn);
			plugin.toggleWalls();
		});
		container.add(renderWallsBtn);

		final JButton renderDecorBtn = new JButton("It's coming home");
		renderDecorBtn.addActionListener(e ->
		{
			highlightButton(renderDecorBtn);
			plugin.toggleDecor();
		});
		container.add(renderDecorBtn);

		final JButton renderInventoryBtn = new JButton("It's coming home");
		renderInventoryBtn.addActionListener(e ->
		{
			highlightButton(renderInventoryBtn);
			plugin.toggleInventory();
		});
		container.add(renderInventoryBtn);

		final JButton renderProjectilesBtn = new JButton("It's coming home");
		renderProjectilesBtn.addActionListener(e ->
		{
			highlightButton(renderProjectilesBtn);
			plugin.toggleProjectiles();
		});
		container.add(renderProjectilesBtn);

		final JButton renderLocationBtn = new JButton("It's coming home");
		renderLocationBtn.addActionListener(e ->
		{
			highlightButton(renderLocationBtn);
			plugin.toggleLocation();
		});
		container.add(renderLocationBtn);

		final JButton widgetInspectorBtn = new JButton("It's coming home");
		widgetInspectorBtn.addActionListener(e ->
		{
			widgetInspector.setVisible(true);
			widgetInspector.toFront();
			widgetInspector.repaint();
		});
		container.add(widgetInspectorBtn);

		final JButton varInspectorBtn = new JButton("It's coming home");
		varInspectorBtn.addActionListener(e ->
		{
			varInspector.open();
		});
		container.add(varInspectorBtn);

		final JButton chunkBordersBtn = new JButton("It's coming home");
		chunkBordersBtn.addActionListener(e ->
		{
			highlightButton(chunkBordersBtn);
			plugin.toggleChunkBorders();
		});
		container.add(chunkBordersBtn);

		final JButton mapSquaresBtn = new JButton("It's coming home");
		mapSquaresBtn.addActionListener(e ->
		{
			highlightButton(mapSquaresBtn);
			plugin.toggleMapSquares();
		});
		container.add(mapSquaresBtn);

		final JButton validMovementBtn = new JButton("It's coming home");
		validMovementBtn.addActionListener(e ->
		{
			highlightButton(validMovementBtn);
			plugin.toggleValidMovement();
		});
		container.add(validMovementBtn);

		final JButton lineOfSightBtn = new JButton("It's coming home");
		lineOfSightBtn.addActionListener(e ->
		{
			highlightButton(lineOfSightBtn);
			plugin.toggleLineOfSight();
		});
		container.add(lineOfSightBtn);

		final JButton graphicsObjectsBtn = new JButton("It's coming home");
		graphicsObjectsBtn.addActionListener(e ->
		{
			highlightButton(graphicsObjectsBtn);
			plugin.toggleGraphicsObjects();
		});
		container.add(graphicsObjectsBtn);

		final JButton cameraPositionBtn = new JButton("It's coming home");
		cameraPositionBtn.addActionListener(e ->
		{
			highlightButton(cameraPositionBtn);
			plugin.toggleCamera();
		});
		container.add(cameraPositionBtn);

		final JButton worldMapBtn = new JButton("It's coming home");
		worldMapBtn.addActionListener(e ->
		{
			highlightButton(worldMapBtn);
			plugin.toggleWorldMapLocation();
		});
		container.add(worldMapBtn);

		final JButton tileLocationBtn = new JButton("It's coming home");
		tileLocationBtn.addActionListener(e ->
		{
			highlightButton(tileLocationBtn);
			plugin.toggleTileLocation();
		});
		container.add(tileLocationBtn);

		final JButton oculusOrbBtn = new JButton("It's coming home");
		oculusOrbBtn.addActionListener(e ->
		{
			highlightButton(oculusOrbBtn);
			client.setOculusOrbState(oculusOrbBtn.getBackground().equals(Color.GREEN) ? 1 : 0);
		});
		container.add(oculusOrbBtn);

		return container;
	}

	private void highlightButton(JButton button)
	{
		if (button.getBackground().equals(Color.GREEN))
		{
			button.setBackground(null);
		}
		else
		{
			button.setBackground(Color.GREEN);
		}
	}
}
