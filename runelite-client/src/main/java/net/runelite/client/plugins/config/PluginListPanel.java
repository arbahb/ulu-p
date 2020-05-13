/*
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
package net.runelite.client.plugins.config;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigDescriptor;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.events.ExternalPluginsChanged;
import net.runelite.client.events.PluginChanged;
import net.runelite.client.externalplugins.ExternalPluginManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginCategory;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginInstantiationException;
import net.runelite.client.plugins.PluginManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.DynamicGridLayout;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.MultiplexingPluginPanel;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.util.Text;

@Slf4j
@Singleton
class PluginListPanel extends PluginPanel
{
	private static final String RUNELITE_GROUP_NAME = RuneLiteConfig.class.getAnnotation(ConfigGroup.class).value();
	private static final String PINNED_PLUGINS_CONFIG_KEY = "pinnedPlugins";

	private final ConfigManager configManager;
	private final PluginManager pluginManager;
	private final ScheduledExecutorService executorService;
	private final Provider<ConfigPanel> configPanelProvider;
	private final List<PluginConfigurationDescriptor> fakePlugins = new ArrayList<>();

	@Inject
	RuneLiteConfig runeLiteConfig;

	@Getter
	private final ExternalPluginManager externalPluginManager;

	@Getter
	private final MultiplexingPluginPanel muxer;

	private final IconTextField searchBar;
	private final JScrollPane scrollPane;
	private final FixedWidthPanel mainPanel;
	private Map<PluginCategory, List<PluginListItem>> pluginMap = new TreeMap<>();
	private Map<PluginCategory, JLabel> categoryLabels = new TreeMap<>();

	@Inject
	public PluginListPanel(
		ConfigManager configManager,
		PluginManager pluginManager,
		ExternalPluginManager externalPluginManager,
		ScheduledExecutorService executorService,
		EventBus eventBus,
		Provider<ConfigPanel> configPanelProvider,
		Provider<PluginHubPanel> pluginHubPanelProvider)
	{
		super(false);

		this.configManager = configManager;
		this.pluginManager = pluginManager;
		this.externalPluginManager = externalPluginManager;
		this.executorService = executorService;
		this.configPanelProvider = configPanelProvider;

		muxer = new MultiplexingPluginPanel(this)
		{
			@Override
			protected void onAdd(PluginPanel p)
			{
				eventBus.register(p);
			}

			@Override
			protected void onRemove(PluginPanel p)
			{
				eventBus.unregister(p);
			}
		};

		searchBar = new IconTextField();
		searchBar.setIcon(IconTextField.Icon.SEARCH);
		searchBar.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 30));
		searchBar.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		searchBar.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
		searchBar.getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				onSearchBarChanged();
			}

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				onSearchBarChanged();
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				onSearchBarChanged();
			}
		});

		setLayout(new BorderLayout());
		setBackground(ColorScheme.DARK_GRAY_COLOR);

		JPanel topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		topPanel.setLayout(new BorderLayout(0, BORDER_OFFSET));
		topPanel.add(searchBar, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);

		mainPanel = new FixedWidthPanel();
		mainPanel.setBorder(new EmptyBorder(8, 10, 10, 10));
		mainPanel.setLayout(new DynamicGridLayout(0, 1, 0, 5));
		mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		JButton externalPluginButton = new JButton("Plugin Hub");
		externalPluginButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		externalPluginButton.setLayout(new BorderLayout(0, BORDER_OFFSET));
		externalPluginButton.addActionListener(l -> muxer.pushState(pluginHubPanelProvider.get()));

		JPanel northPanel = new FixedWidthPanel();
		northPanel.setLayout(new BorderLayout());
		northPanel.add(mainPanel, BorderLayout.NORTH);
		northPanel.add(externalPluginButton, BorderLayout.SOUTH);

		scrollPane = new JScrollPane(northPanel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);
	}

	void rebuildPluginList()
	{
		final List<String> pinnedPlugins = getPinnedPluginNames();

		pluginMap = initPluginMap();
		categoryLabels = initCategoryLabels();

		// populate pluginList with all non-hidden plugins
		Stream.concat(
			fakePlugins.stream(),
			pluginManager.getPlugins().stream()
				.filter(plugin -> !plugin.getClass().getAnnotation(PluginDescriptor.class).hidden())
				.map(plugin ->
				{
					PluginDescriptor descriptor = plugin.getClass().getAnnotation(PluginDescriptor.class);
					Config config = pluginManager.getPluginConfigProxy(plugin);
					ConfigDescriptor configDescriptor = config == null ? null : configManager.getConfigDescriptor(config);

					return new PluginConfigurationDescriptor(
						descriptor.name(),
						descriptor.category(),
						descriptor.description(),
						descriptor.tags(),
						plugin,
						config,
						configDescriptor);
				})
		).forEach(desc ->
		{
			PluginListItem listItem = new PluginListItem(this, desc, runeLiteConfig);
			pluginMap.get(listItem.getPluginConfig().getCategory()).add(listItem);
			listItem.setPinned(pinnedPlugins.contains(desc.getName()));
		});

		pluginMap.values().forEach(list -> list.sort(Comparator.comparing(p -> p.getPluginConfig().getName())));
		getPluginList().forEach(p ->
		{
			if (p.getPluginConfig().isForcePinned())
			{
				p.setPinned(true);
			}
		});
		mainPanel.removeAll();
		refresh();
	}

	private Map<PluginCategory, List<PluginListItem>> initPluginMap()
	{
		final Map<PluginCategory, List<PluginListItem>> map = new TreeMap<>();

		for (PluginCategory c : PluginCategory.values())
		{
			map.put(c, new ArrayList<>());
		}

		return map;
	}

	private Map<PluginCategory, JLabel> initCategoryLabels()
	{
		final Map<PluginCategory, JLabel> map = new TreeMap<>();

		for (PluginCategory c : PluginCategory.values())
		{
			final JLabel categoryLabel = new JLabel(c.toString(), SwingConstants.LEFT);
			categoryLabel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(8, 0, 6, 0),
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorScheme.BRAND_ORANGE)));
			categoryLabel.setFont(FontManager.getRunescapeBoldFont());
			categoryLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
			map.put(c, categoryLabel);
		}

		return map;
	}

	void addFakePlugin(PluginConfigurationDescriptor... descriptor)
	{
		Collections.addAll(fakePlugins, descriptor);
	}

	void refresh()
	{
		// update enabled / disabled status of all items
		getPluginList().forEach(listItem ->
		{
			final Plugin plugin = listItem.getPluginConfig().getPlugin();
			if (plugin != null)
			{
				listItem.setPluginEnabled(pluginManager.isPluginEnabled(plugin));
			}
		});

		int scrollBarPosition = scrollPane.getVerticalScrollBar().getValue();

		onSearchBarChanged();
		searchBar.requestFocusInWindow();
		validate();

		scrollPane.getVerticalScrollBar().setValue(scrollBarPosition);
	}

	void openWithFilter(String filter)
	{
		searchBar.setText(filter);
		onSearchBarChanged();
		muxer.pushState(this);
	}

	private void onSearchBarChanged()
	{
		final String text = searchBar.getText();

		pluginMap.values().forEach(l -> l.forEach(mainPanel::remove));
		categoryLabels.values().forEach(mainPanel::remove);

		showMatchingPlugins(true, text);
		showMatchingPlugins(false, text);

		revalidate();
	}

	private void showMatchingPlugins(boolean pinned, String text)
	{
		if (runeLiteConfig.categorisePluginList())
		{
			showMatchingPluginsCategorised(pinned, text);
			return;
		}

		List<PluginListItem> pluginList = getPluginList();
		pluginList.sort(Comparator.comparing(p -> p.getPluginConfig().getName()));
		if (text.isEmpty())
		{
			pluginList.stream().filter(item -> pinned == item.isPinned()).forEach(mainPanel::add);
			return;
		}

		final String[] searchTerms = text.toLowerCase().split(" ");
		pluginList.forEach(item ->
		{
			if (pinned == item.isPinned() && Text.matchesSearchTerms(searchTerms, item.getKeywords()))
			{
				mainPanel.add(item);
			}
		});
	}

	private void showMatchingPluginsCategorised(boolean pinned, String text)
	{
		if (text.isEmpty())
		{
			if (pinned)
			{
				addPinnedPluginsToPanel(null);
			}
			else
			{
				addPluginCategoryToPanel(item -> !item.isPinned());
			}
			return;
		}

		final String[] searchTerms = text.toLowerCase().split(" ");
		if (pinned)
		{
			addPinnedPluginsToPanel(searchTerms);
		}
		else
		{
			addPluginCategoryToPanel(item -> !item.isPinned() && Text.matchesSearchTerms(searchTerms, item.getKeywords()));
		}
	}

	private void addPinnedPluginsToPanel(String[] searchTerms)
	{
		Predicate<PluginListItem> filter = item ->
			item.isPinned() && (searchTerms == null || Text.matchesSearchTerms(searchTerms, item.getKeywords()));

		pluginMap.values().stream().flatMap(List::stream).filter(filter).forEach(mainPanel::add);
	}

	private void addPluginCategoryToPanel(Predicate<PluginListItem> filter)
	{
		pluginMap.keySet().forEach(category ->
		{
			List<PluginListItem> listItems = pluginMap.get(category).stream()
				.filter(filter).collect(Collectors.toList());

			// Don't display category header if there are no remaining plugins to list
			if (listItems.size() == 0)
			{
				return;
			}
			mainPanel.add(categoryLabels.get(category));
			listItems.forEach(mainPanel::add);
		});
	}

	void openConfigurationPanel(String configGroup)
	{
		for (PluginListItem pluginListItem : getPluginList())
		{
			if (pluginListItem.getPluginConfig().getName().equals(configGroup))
			{
				openConfigurationPanel(pluginListItem.getPluginConfig());
				break;
			}
		}
	}

	void openConfigurationPanel(Plugin plugin)
	{
		for (PluginListItem pluginListItem : getPluginList())
		{
			if (pluginListItem.getPluginConfig().getPlugin() == plugin)
			{
				openConfigurationPanel(pluginListItem.getPluginConfig());
				break;
			}
		}
	}

	void openConfigurationPanel(PluginConfigurationDescriptor plugin)
	{
		ConfigPanel panel = configPanelProvider.get();
		panel.init(plugin);
		muxer.pushState(panel);
	}

	void startPlugin(Plugin plugin)
	{
		pluginManager.setPluginEnabled(plugin, true);

		try
		{
			pluginManager.startPlugin(plugin);
		}
		catch (PluginInstantiationException ex)
		{
			log.warn("Error when starting plugin {}", plugin.getClass().getSimpleName(), ex);
		}
	}

	void stopPlugin(Plugin plugin)
	{
		pluginManager.setPluginEnabled(plugin, false);

		try
		{
			pluginManager.stopPlugin(plugin);
		}
		catch (PluginInstantiationException ex)
		{
			log.warn("Error when stopping plugin {}", plugin.getClass().getSimpleName(), ex);
		}
	}

	private List<String> getPinnedPluginNames()
	{
		final String config = configManager.getConfiguration(RUNELITE_GROUP_NAME, PINNED_PLUGINS_CONFIG_KEY);

		if (config == null)
		{
			return Collections.emptyList();
		}

		return Text.fromCSV(config);
	}

	void savePinnedPlugins()
	{
		final String value = getPluginList().stream()
			.filter(PluginListItem::isPinned)
			.map(p -> p.getPluginConfig().getName())
			.collect(Collectors.joining(","));

		configManager.setConfiguration(RUNELITE_GROUP_NAME, PINNED_PLUGINS_CONFIG_KEY, value);
	}

	List<PluginListItem> getPluginList()
	{
		return pluginMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
	}

	@Subscribe
	public void onPluginChanged(PluginChanged event)
	{
		SwingUtilities.invokeLater(this::refresh);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("runelite") && event.getKey().equals("categorisePluginList"))
		{
			rebuildPluginList();
		}
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(PANEL_WIDTH + SCROLLBAR_WIDTH, super.getPreferredSize().height);
	}

	@Override
	public void onActivate()
	{
		super.onActivate();

		if (searchBar.getParent() != null)
		{
			searchBar.requestFocusInWindow();
		}
	}

	@Subscribe
	private void onExternalPluginsChanged(ExternalPluginsChanged ev)
	{
		SwingUtilities.invokeLater(this::rebuildPluginList);
	}
}
