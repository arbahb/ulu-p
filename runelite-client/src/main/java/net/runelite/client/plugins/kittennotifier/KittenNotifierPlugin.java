package net.runelite.client.plugins.kittennotifier;
import com.google.inject.Provides;
import net.runelite.api.ChatMessageType;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.NpcActionChanged;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.NPC;
import net.runelite.api.Client;
import javax.inject.Inject;

@PluginDescriptor
(
	name = "Kitten Notifier",
	description = "Sends a notification when your kitten needs food, attention, or is grown.",
	tags = {"kitten", "cat", "notifications"}
)
public class KittenNotifierPlugin extends Plugin
{
	@Inject
	private Notifier notifier;

	@Inject
	private KittenNotifierConfig config;

	@Inject
	private Client client;

	@Provides
	KittenNotifierConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(KittenNotifierConfig.class);
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (event.getType() == ChatMessageType.SERVER && !config.catOwned())
		{
			if (!config.absolutelyNeeded())
			{
				if (event.getMessage().contentEquals("<col=ef1020>Your kitten is hungry.</col>"))
				{
					notifier.notify("Your kitten is hungry.");
				}
				if (event.getMessage().contentEquals("<col=ef1020>Your kitten wants attention.</col>"))
				{
					notifier.notify("Your kitten wants attention.");
				}
			}
			if (event.getMessage().contentEquals("<col=ef1020>Your kitten is very hungry.</col>"))
			{
				notifier.notify("Your kitten is very hungry.");
			}
			if (event.getMessage().contentEquals("<col=ef1020>Your kitten really wants attention.</col>"))
			{
				notifier.notify("Your kitten really wants attention.");
			}
		}
	}

	@Subscribe
	public void onNpcActionChanged(NpcActionChanged event)
	{
		if (!config.catOwned())
		{
			for (NPC npc : client.getNpcs())
			{
				if (npc.getInteracting() != null && npc.getName().contentEquals("Cat") && !config.catOwned() && npc.getInteracting().getName().contentEquals(client.getLocalPlayer().getName()))
				{
					config.catOwned(true);
					notifier.notify("Your kitten has grown into a cat.");
				}
			}
		}
	}

	@Subscribe
	public void onNpcSpawned(NpcSpawned event)
	{
		if (event.getNpc().getName().contentEquals("Kitten") && event.getNpc().getInteracting().getName().contentEquals(client.getLocalPlayer().getName()))
		{
			config.catOwned(false);
		}
		else if (event.getNpc().getName().contentEquals("Cat") && event.getNpc().getInteracting().getName().contentEquals(client.getLocalPlayer().getName()))
		{
			config.catOwned(true);
		}
	}
}
