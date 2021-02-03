package net.runelite.client.plugins.examine;

import net.runelite.api.Point;
import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.examinebox.ExamineBox;
import net.runelite.client.ui.overlay.examinebox.ExamineBoxManager;

import javax.inject.Inject;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Deque;
import java.util.List;

import static net.runelite.api.widgets.WidgetInfo.TO_CHILD;
import static net.runelite.api.widgets.WidgetInfo.TO_GROUP;

public class ExamineOverlay extends Overlay
{
    private final Client client;
    private final ExaminePlugin examinePlugin;
    private final ExamineBoxManager examineBoxManager;
    private final int EXAMINE_DURATION = 3;
    @Inject
    public ExamineOverlay(Client client, ExaminePlugin examinePlugin, ExamineBoxManager examineBoxManager){
        this.client = client;
        this.examinePlugin = examinePlugin;
        this.examineBoxManager = examineBoxManager;
    }



    @Override
    public Dimension render(Graphics2D graphics)
    {
        Deque<ProcessedExamine> processedExamine = examinePlugin.getProcessedExamine();
        if(!processedExamine.isEmpty()){
            ProcessedExamine examine = processedExamine.peek();
            Duration timeDiff = Duration.between(examine.getCreated(), Instant.now());
            if (timeDiff.compareTo(Duration.ofSeconds(EXAMINE_DURATION)) < 0 && processedExamine.size() == 1)
            {
               ExamineType type = examine.getType();
               switch (type){
                   case NPC:
                       final NPC[] cachedNPCs = client.getCachedNPCs();
                       final NPC npc = cachedNPCs[examine.getId()];
                       Shape objectClickbox = Perspective.getClickbox(client,npc.getModel(),npc.getOrientation(),npc.getLocalLocation());
                       Rectangle box = objectClickbox.getBounds();
                       examineBoxManager.setExamineBox(new ExamineBox(examine.getMessage(),(int)box.getX(),(int)box.getY()));
                       break;
                   case ITEM:
                       int widgetGroup = TO_GROUP(examine.getWidgetId());
                       int widgetChild = TO_CHILD(examine.getWidgetId());
                       Widget widget = client.getWidget(widgetGroup, widgetChild);
                       if(widget!= null){
                           WidgetItem widgetItem = widget.getWidgetItem(examine.getActionParam());
                           if(widgetItem != null){
                               Rectangle slotBounds = widgetItem.getCanvasBounds();
                               examineBoxManager.setExamineBox(new ExamineBox(examine.getMessage(),(int)slotBounds.getX(),(int)slotBounds.getHeight() + (int)slotBounds.getY()));
                           }
                       }
                       break;
                   case ITEM_BANK_EQ:
                       final Widget bankItemContainer = client.getWidget(WidgetInfo.BANK_ITEM_CONTAINER);
                       if(bankItemContainer!=null){
                           Widget[] bankItemContainerChildren = bankItemContainer.getChildren();
                           if(bankItemContainerChildren!=null){
                               for(Widget child: bankItemContainerChildren){
                                   if (child != null && !child.isSelfHidden() && child.getItemId() > -1 && child.getItemId() == examine.getId())
                                   {
                                       examineBoxManager.setExamineBox(new ExamineBox(examine.getMessage() ,(int)child.getBounds().getX(),(int)child.getBounds().getY()));
                                   }
                               }
                           }

                           Widget w = client.getWidget(TO_GROUP(examine.getWidgetId()), TO_CHILD(examine.getWidgetId()));
                           if(w!= null){
                               Widget widgetItem = w.getChild(examine.getActionParam());
                               if(widgetItem != null){
                                   Rectangle slotBounds = widgetItem.getBounds();
                                   examineBoxManager.setExamineBox(new ExamineBox(examine.getMessage(),(int)slotBounds.getX(),(int)slotBounds.getHeight() + (int)slotBounds.getY()));
                               }
                           }
                       }
                       break;
                   case ITEM_GROUND:
                   case OBJECT:
                       Scene scene = client.getScene();
                       Tile[][][] tiles = scene.getTiles();
                       Tile tile = tiles[client.getPlane()][examine.getActionParam()][examine.getWidgetId()];
                       if(tile != null){
                           switch (type){
                               case ITEM_GROUND:
                                   List<TileItem> groundItems = tile.getGroundItems();
                                   if(groundItems!=null && !groundItems.isEmpty()){
                                       for(TileItem tileItem: groundItems){
                                           if(tileItem!=null){
                                               if(examine.getId() == tileItem.getId()){
                                                   Point point = Perspective.localToCanvas(client,tile.getLocalLocation(),tile.getPlane());
                                                   examineBoxManager.setExamineBox(new ExamineBox(examine.getMessage(),point.getX(),point.getY()));
                                               }
                                           }
                                       }
                                   }
                                   break;
                               case OBJECT:

                                   GameObject[] gameObjects = tile.getGameObjects();
                                   if (gameObjects != null) {
                                       for (GameObject gameObject : gameObjects)
                                       {
                                           if (gameObject != null)
                                           {
                                               if(examine.getId() == gameObject.getId()){
                                                   Shape convexHull = gameObject.getConvexHull();
                                                   examineBoxManager.setExamineBox(new ExamineBox(examine.getMessage(),(int)convexHull.getBounds().getX(),(int)convexHull.getBounds().getY()));

                                               }
                                           }
                                       }
                                   }
                                   break;
                           }
                       }

                       break;
               }
            } else {
                processedExamine.pop();
            }

        }

        return null;
    }

}
