package net.runelite.client.plugins.wintertodt;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.components.PanelComponent;
import javax.inject.Inject;
import java.awt.*;

@Slf4j
public class WintertodtOverlay extends Overlay
{
	private final Client client;
	private WintertodtConfig config;

	//Strings for PanelComponent
	String safeFires = "";
	String downFires = "";
	String needReapir = "";
	String pyromancerHelp = "";

	//UI Component
	PanelComponent pnc = new PanelComponent();

	@Inject
	public WintertodtOverlay(Client client, WintertodtConfig cfg)
    {
		this.client = client;
		this.config = cfg;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		//Checks if in Wintertodt or the Wintertodt Snowy Path and runs Render Void.
		if (client.getLocalPlayer().getWorldLocation().getRegionID() == 6462 || client.getLocalPlayer().getWorldLocation().getRegionID() == 6461)
		{
			runWintertodtUI(graphics);
		}
		return null;
	}

	private void runWintertodtUI(Graphics2D graphics)
	{
		//Cleans Up previous render
		safeFires = "";
		downFires = "";
		needReapir = "";
		pyromancerHelp = "";
		pnc.getLines().clear();

		//Main UI
		Widget wtBase = client.getWidget(WidgetInfo.WINTERTODT_BASE);

		//Checks if the Wintertodt UI exists
		if (wtBase != null)
		{
			//UI
			Widget wtOverlayBAR = client.getWidget(WidgetInfo.WINTERTODT_BASE_BAR);
			Widget wtOverlayUI = client.getWidget(WidgetInfo.WINTERTODT_BASE_UI);
			//Widgets
			Widget wtEnergy = client.getWidget(WidgetInfo.WINTERTODT_ENERGY);
			Widget wtPoints = client.getWidget(WidgetInfo.WINTERTODT_POINTS);
			Widget wtSWF = client.getWidget(WidgetInfo.WINTERTODT_FIRE_SW);
			Widget wtNWF = client.getWidget(WidgetInfo.WINTERTODT_FIRE_NW);
			Widget wtNEF = client.getWidget(WidgetInfo.WINTERTODT_FIRE_NE);
			Widget wtSEF = client.getWidget(WidgetInfo.WINTERTODT_FIRE_SE);
			Widget wtSWP = client.getWidget(WidgetInfo.WINTERTODT_PYRO_SW);
			Widget wtNWP = client.getWidget(WidgetInfo.WINTERTODT_PYRO_NW);
			Widget wtNEP = client.getWidget(WidgetInfo.WINTERTODT_PYRO_NE);
			Widget wtSEP = client.getWidget(WidgetInfo.WINTERTODT_PYRO_SE);
			Widget wtReturn = client.getWidget(WidgetInfo.WINTERTODT_RETURN);

			//Puts UI off-screen because it can't read if hidden -195
			wtReturn.setRelativeX(-195);
			wtOverlayBAR.setHidden(true);
			wtOverlayUI.setHidden(true);

			//Return Text
			if (wtReturn.getText().contains(": "))
			{
				String returnSplitter[] = wtReturn.getText().split(": ");
				pnc.getLines().add(new PanelComponent.Line(
						"Returns:",
						Color.WHITE,
						returnSplitter[1],
						Color.GREEN)
				);
			}

			//Energy Text
			if (wtEnergy.getText().contains(": "))
			{
				String energySplitter[] = wtEnergy.getText().split(": ");
				pnc.getLines().add(new PanelComponent.Line(
						"Energy: ",
						Color.WHITE,
						energySplitter[1],
						getEnergyColor(Integer.parseInt(energySplitter[1].replaceAll("%", "")))
				));
			}

			//Point Text
			if (wtPoints.getText().contains(("<br>")))
			{
				String pointSplitter[] = wtPoints.getText().split("<br>");
				pnc.getLines().add(new PanelComponent.Line(
						pointSplitter[0] + ":",
						Color.WHITE,
						pointSplitter[1],
						getColor(Integer.parseInt(pointSplitter[1])))
				);
			}

			//Gathers Fire States
			if (wtSWF.getSpriteId() == 1399)
			{
				safeFires += ", SW";
			} else
				{
				downFires += ", SW";
			}

			if (wtNWF.getSpriteId() == 1399)
			{
				safeFires += ", NW";
			} else
				{
				downFires += ", NW";
			}

			if (wtNEF.getSpriteId() == 1399)
			{
				safeFires += ", NE";
			} else
				{
				downFires += ", NE";
			}

			if (wtSEF.getSpriteId() == 1399)
			{
				safeFires += ", SE";
			} else
				{
				downFires += ", SE";
			}

			//Gathers Pyromancers States
			if (wtSWP.getSpriteId() == 1400)
			{
				pyromancerHelp += ", SW";
			}
			if (wtNWP.getSpriteId() == 1400)
			{
				pyromancerHelp += ", NW";
			}
			if (wtNEP.getSpriteId() == 1400)
			{
				pyromancerHelp += ", NE";
			}
			if (wtSEP.getSpriteId() == 1400)
			{
				pyromancerHelp += ", SE";
			}

			//Lit Fires Text
			pnc.getLines().add(new PanelComponent.Line("Lit Fires:  ",
					Color.WHITE,
					safeFires.replaceFirst(", ", ""),
					config.highColor())
			);

			//Unlit Fires Text
			pnc.getLines().add(new PanelComponent.Line("Unlit Fires: ",
					Color.WHITE,
					downFires.replaceFirst(", ", ""),
					config.lowColor())
			);

			//Pyromancers Text
			pnc.getLines().add(new PanelComponent.Line("Pyros:  ",
					Color.WHITE,
					pyromancerHelp.replaceFirst(", ", ""),
					config.lowColor())
			);

			//Renders
			pnc.setWidth(160);
			pnc.setBackgroundColor(new Color(config.panelColor().getRed(), config.panelColor().getGreen(), config.panelColor().getBlue(), 156));
			pnc.render(graphics);
		}
	}

	//Visual Feedback for points
	public Color getColor(int points)
	{
		if (points < 250)
		{
			return config.lowColor();
		}
		else if (points >= 250 && points < 500)
		{
			return config.medColor();
		}
		else if (points >= 500)
		{
			return config.highColor();
		}
		return Color.WHITE;
	}

	//Visual Feedback for Energy
	public Color getEnergyColor(int points)
	{
		if (points <= 20)
		{
			return config.lowColor();
		}
		else if (points > 20 && points <= 60)
		{
			return config.medColor();
		}
		else if (points > 60 && points <= 100)
		{
			return config.highColor();
		}
		return Color.WHITE;
	}
}
