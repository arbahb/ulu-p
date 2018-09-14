/*
 * Copyright (c) 2018, Psikoi <https://github.com/psikoi>
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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
package net.runelite.client.plugins.loottracker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.PluginErrorPanel;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.StackFormatter;

class LootTrackerPanel extends PluginPanel
{
	private static final ImageIcon SINGLE_LOOT_VIEW;
	private static final ImageIcon SINGLE_LOOT_VIEW_FADED;
	private static final ImageIcon SINGLE_LOOT_VIEW_HOVER;
	private static final ImageIcon GROUPED_LOOT_VIEW;
	private static final ImageIcon GROUPED_LOOT_VIEW_FADED;
	private static final ImageIcon GROUPED_LOOT_VIEW_HOVER;
	private static final ImageIcon BACK_ARROW_ICON;
	private static final ImageIcon BACK_ARROW_ICON_HOVER;

	private static final String HTML_LABEL_TEMPLATE =
		"<html><body style='color:%s'>%s<span style='color:white'>%s</span></body></html>";

	// When there is no loot, display this
	private final PluginErrorPanel errorPanel = new PluginErrorPanel();

	// Handle loot boxes
	private final JPanel logsContainer = new JPanel();

	// Handle overall session data
	private final JPanel overallPanel = new JPanel();
	private final JLabel overallKillsLabel = new JLabel();
	private final JLabel overallGpLabel = new JLabel();
	private final JLabel overallIcon = new JLabel();

	// Details and navigation
	private final JPanel actionsContainer = new JPanel();
	private final JLabel detailsTitle = new JLabel();
	private final JLabel backBtn = new JLabel();
	private final JLabel singleLootBtn = new JLabel();
	private final JLabel groupedLootBtn = new JLabel();

	// Log collection
	private final List<LootTrackerRecord> records = new ArrayList<>();
	private final List<LootTrackerBox> boxes = new ArrayList<>();

	private final ItemManager itemManager;
	private final LootTrackerPlugin plugin;

	private boolean groupLoot;
	private String currentView;

	static
	{
		final BufferedImage singleLootImg = ImageUtil.getResourceStreamFromClass(LootTrackerPlugin.class, "single_loot_icon.png");
		final BufferedImage groupedLootImg = ImageUtil.getResourceStreamFromClass(LootTrackerPlugin.class, "grouped_loot_icon.png");
		final BufferedImage backArrowImg = ImageUtil.getResourceStreamFromClass(LootTrackerPlugin.class, "back_icon.png");

		SINGLE_LOOT_VIEW = new ImageIcon(singleLootImg);
		SINGLE_LOOT_VIEW_FADED = new ImageIcon(ImageUtil.alphaOffset(singleLootImg, -180));
		SINGLE_LOOT_VIEW_HOVER = new ImageIcon(ImageUtil.alphaOffset(singleLootImg, -220));

		GROUPED_LOOT_VIEW = new ImageIcon(groupedLootImg);
		GROUPED_LOOT_VIEW_FADED = new ImageIcon(ImageUtil.alphaOffset(groupedLootImg, -180));
		GROUPED_LOOT_VIEW_HOVER = new ImageIcon(ImageUtil.alphaOffset(groupedLootImg, -220));

		BACK_ARROW_ICON = new ImageIcon(backArrowImg);
		BACK_ARROW_ICON_HOVER = new ImageIcon(ImageUtil.alphaOffset(backArrowImg, -180));
	}

	LootTrackerPanel(final LootTrackerPlugin plugin, final ItemManager itemManager)
	{
		this.itemManager = itemManager;
		this.plugin = plugin;

		setBorder(new EmptyBorder(6, 6, 6, 6));
		setBackground(ColorScheme.DARK_GRAY_COLOR);
		setLayout(new BorderLayout());

		// Create layout panel for wrapping
		final JPanel layoutPanel = new JPanel();
		layoutPanel.setLayout(new BoxLayout(layoutPanel, BoxLayout.Y_AXIS));
		add(layoutPanel, BorderLayout.NORTH);

		actionsContainer.setLayout(new BorderLayout());
		actionsContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		actionsContainer.setPreferredSize(new Dimension(0, 30));
		actionsContainer.setBorder(new EmptyBorder(5, 5, 5, 10));
		actionsContainer.setVisible(false);

		final JPanel viewControls = new JPanel(new GridLayout(1, 2, 10, 0));
		viewControls.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		singleLootBtn.setIcon(SINGLE_LOOT_VIEW);
		singleLootBtn.setToolTipText("Show each kill separately");
		singleLootBtn.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				changeGrouping(false);
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent)
			{
				singleLootBtn.setIcon(groupLoot ? SINGLE_LOOT_VIEW_FADED : SINGLE_LOOT_VIEW);
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				singleLootBtn.setIcon(groupLoot ? SINGLE_LOOT_VIEW_HOVER : SINGLE_LOOT_VIEW);
			}
		});

		groupedLootBtn.setIcon(GROUPED_LOOT_VIEW);
		groupedLootBtn.setToolTipText("Group loot by source");
		groupedLootBtn.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				changeGrouping(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent)
			{
				groupedLootBtn.setIcon(groupLoot ? GROUPED_LOOT_VIEW : GROUPED_LOOT_VIEW_FADED);
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				groupedLootBtn.setIcon(groupLoot ? GROUPED_LOOT_VIEW : GROUPED_LOOT_VIEW_HOVER);
			}
		});

		viewControls.add(groupedLootBtn);
		viewControls.add(singleLootBtn);
		changeGrouping(true);

		final JPanel leftTitleContainer = new JPanel(new BorderLayout(5, 0));
		leftTitleContainer.setBackground(ColorScheme.DARKER_GRAY_COLOR);

		detailsTitle.setForeground(Color.WHITE);

		backBtn.setIcon(BACK_ARROW_ICON);
		backBtn.setVisible(false);
		backBtn.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				currentView = null;
				backBtn.setVisible(false);
				detailsTitle.setText("");
				rebuild();
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent)
			{
				backBtn.setIcon(BACK_ARROW_ICON);
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				backBtn.setIcon(BACK_ARROW_ICON_HOVER);
			}
		});

		leftTitleContainer.add(backBtn, BorderLayout.WEST);
		leftTitleContainer.add(detailsTitle, BorderLayout.CENTER);

		actionsContainer.add(leftTitleContainer, BorderLayout.WEST);
		actionsContainer.add(viewControls, BorderLayout.EAST);

		// Create panel that will contain overall data
		overallPanel.setBorder(new EmptyBorder(8, 10, 8, 10));
		overallPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		overallPanel.setLayout(new BorderLayout());
		overallPanel.setVisible(false);

		// Add icon and contents
		final JPanel overallInfo = new JPanel();
		overallInfo.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		overallInfo.setLayout(new GridLayout(2, 1));
		overallInfo.setBorder(new EmptyBorder(2, 10, 2, 0));
		overallKillsLabel.setFont(FontManager.getRunescapeSmallFont());
		overallGpLabel.setFont(FontManager.getRunescapeSmallFont());
		overallInfo.add(overallKillsLabel);
		overallInfo.add(overallGpLabel);
		overallPanel.add(overallIcon, BorderLayout.WEST);
		overallPanel.add(overallInfo, BorderLayout.CENTER);

		// Create reset all menu
		final JMenuItem reset = new JMenuItem("Reset All");
		reset.addActionListener(e ->
		{
			// If not in detailed view, remove all, otherwise only remove for the currently detailed title
			records.removeIf(r -> r.matches(currentView));
			boxes.removeIf(b -> b.matches(currentView));
			updateOverall();
			logsContainer.removeAll();
			logsContainer.repaint();
		});

		// Create popup menu
		final JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
		popupMenu.add(reset);
		overallPanel.setComponentPopupMenu(popupMenu);

		// Create loot boxes wrapper
		logsContainer.setLayout(new BoxLayout(logsContainer, BoxLayout.Y_AXIS));
		layoutPanel.add(actionsContainer);
		layoutPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		layoutPanel.add(overallPanel);
		layoutPanel.add(logsContainer);

		// Add error pane
		errorPanel.setContent("Loot trackers", "You have not received any loot yet.");
		add(errorPanel);
	}

	void loadHeaderIcon(BufferedImage img)
	{
		overallIcon.setIcon(new ImageIcon(img));
	}

	/**
	 * Adds a new entry to the plugin.
	 * Creates a subtitle, adds a new entry and then passes off to the render methods, that will decide
	 * how to display this new data.
	 */
	void add(final String eventName, final int actorLevel, LootTrackerItem[] items)
	{
		final String subTitle = actorLevel > -1 ? "(lvl-" + actorLevel + ")" : "";
		final LootTrackerRecord record = new LootTrackerRecord(eventName, subTitle, items, System.currentTimeMillis());
		records.add(record);
		buildBox(record);
	}

	/**
	 * Changes grouping mode of panel
	 *
	 * @param group if loot should be grouped or not
	 */
	private void changeGrouping(boolean group)
	{
		groupLoot = group;
		rebuild();
		groupedLootBtn.setIcon(group ? GROUPED_LOOT_VIEW : GROUPED_LOOT_VIEW_FADED);
		singleLootBtn.setIcon(group ? SINGLE_LOOT_VIEW_FADED : SINGLE_LOOT_VIEW);
	}

	/**
	 * After an item changed it's ignored state, iterate all the records and make
	 * sure all items of the same name also get updated
	 */
	void updateIgnoredRecords()
	{
		for (LootTrackerRecord r : records)
		{
			for (LootTrackerItem item : r.getItems())
			{
				if (plugin.isIgnored(item.getName()) != item.isIgnored())
				{
					item.setIgnored(plugin.isIgnored(item.getName()));
				}
			}
		}
	}

	/**
	 * Rebuilds all the boxes from scratch using existing listed records, depending on the grouping mode.
	 */
	void rebuild()
	{
		logsContainer.removeAll();
		boxes.clear();
		records.forEach(this::buildBox);
		updateOverall();
		logsContainer.revalidate();
		logsContainer.repaint();
	}

	/**
	 * This method decides what to do with a new record, if a similar log exists, it will
	 * add its items to it, updating the log's overall price and kills. If not, a new log will be created
	 * to hold this entry's information.
	 */
	private void buildBox(LootTrackerRecord record)
	{
		// If this record is not part of current view, return
		if (!record.matches(currentView))
		{
			return;
		}

		// Group all similar loot together
		if (groupLoot)
		{
			for (LootTrackerBox box : boxes)
			{
				if (box.matches(record))
				{
					box.combine(record);
					updateOverall();
					return;
				}
			}
		}

		// Show main view
		remove(errorPanel);
		actionsContainer.setVisible(true);
		overallPanel.setVisible(true);

		// Create box
		final LootTrackerBox box = new LootTrackerBox(itemManager, plugin.getConfig(), record.getTitle(), record.getSubTitle(), (name, ignored) ->
		{
			plugin.toggleItem(name, ignored);
			updateIgnoredRecords();
			rebuild();
		});
		box.combine(record);

		// Create popup menu
		final JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
		box.setComponentPopupMenu(popupMenu);

		// Create reset menu
		final JMenuItem reset = new JMenuItem("Reset");
		reset.addActionListener(e ->
		{
			records.removeAll(box.getRecords());
			boxes.remove(box);
			updateOverall();
			logsContainer.remove(box);
			logsContainer.repaint();
		});

		popupMenu.add(reset);

		// Create details menu
		final JMenuItem details = new JMenuItem("View details");
		details.addActionListener(e ->
		{
			currentView = record.getTitle();
			detailsTitle.setText(currentView);
			backBtn.setVisible(true);
			rebuild();
		});

		popupMenu.add(details);

		// Add box to panel
		boxes.add(box);
		logsContainer.add(box, 0);

		// Update overall
		updateOverall();
	}

	private void updateOverall()
	{
		final long overallGp = boxes.stream().mapToLong(LootTrackerBox::getTotalPrice).sum();
		final int overallKills = boxes.stream().mapToInt(LootTrackerBox::getTotalKills).sum();
		overallKillsLabel.setText(htmlLabel("Total count: ", overallKills));
		overallGpLabel.setText(htmlLabel("Total value: ", overallGp));
	}

	private static String htmlLabel(String key, long value)
	{
		final String valueStr = StackFormatter.quantityToStackSize(value);
		return String.format(HTML_LABEL_TEMPLATE, ColorUtil.toHexColor(ColorScheme.LIGHT_GRAY_COLOR), key, valueStr);
	}
}
