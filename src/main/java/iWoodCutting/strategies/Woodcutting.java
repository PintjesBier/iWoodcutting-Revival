package iWoodCutting.strategies;

import iWoodCutting.core.Core;
import iWoodCutting.data.Constants;
import iWoodCutting.data.Methods;
import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.SceneObject;
import org.rev317.min.api.wrappers.Tile;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.rev317.min.api.methods.Players.getMyPlayer;

/**
 * Created by Tristan on 26/08/2018.
 */
public class Woodcutting implements Strategy {
    //VARIABLES
    private Tile TreeTile;
    private SceneObject Tree;
    private int TreeID;

    @Override
    public boolean activate() {
        return !Inventory.isFull()
                && SceneObjects.getClosest(Methods.CheckTreeToCut()) != null
                && Interfaces.getOpenInterfaceId() == -1;
    }

    @Override
    public void execute()
    {
        //VARIABLES
        List<Integer> RandomDrops = new ArrayList<Integer>();
        RandomDrops.add(Constants.CLUE_NEST_EASY);
        RandomDrops.add(Constants.CLUE_NEST_MEDIUM);
        RandomDrops.add(Constants.CLUE_NEST_HARD);
        RandomDrops.add(Constants.CLUE_NEST_MASTER);
        RandomDrops.add(Constants.LUMBERJACK_BOOTS_ID);
        RandomDrops.add(Constants.LUMBERJACK_BOTTOM_ID);
        RandomDrops.add(Constants.LUMBERJACK_TOP_ID);
        RandomDrops.add(Constants.LUMBERJACK_HAT_ID);
        RandomDrops.add(Constants.CLUE_NEST_EASY);
        RandomDrops.add(Constants.MBOX_ID);

        //WOODCUTTING CLASS
        if (getMyPlayer().getAnimation() == -1)
        {
            Core.CurrentStatus = "Chopping tree";
            Logger.addMessage("iWoodcutting: Chopping tree", true);

            TreeID = Methods.CheckTreeToCut();

            if (SceneObjects.getClosest(TreeID).distanceTo() >= 2)
            {
                Core.ExitTile.walkTo();

                //WAIT UNTIL ARRIVED AT TREE
                Time.sleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return Core.ExitTile == getMyPlayer().getLocation();
                    }
                }, 3000);
            }

            Tree = SceneObjects.getClosest(Methods.CheckTreeToCut());
/*            TreeTile = new Tile(Core.Tree.getLocalRegionX(),Core.Tree.getLocalRegionY(),0);
            Walking.walkTo(TreeTile);

            //WAIT UNTIL ARRIVED AT TREE
            Time.sleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Core.Tree.distanceTo() <= 15;
                }
            }, 5000);

            Core.Tree = SceneObjects.getClosest(Methods.CheckTreeToCut());*/
            Tree.interact(SceneObjects.Option.CHOP_DOWN);

            //WAIT FOR ACTION
            Time.sleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return getMyPlayer().getAnimation() == Constants.AXE_SWINGING_ID;
                }
            }, 2000);
        }

        //LOOP THROUGH RANDOM DROPS
        for (final int i : RandomDrops)
        {
         if ((GroundItems.getClosest(i) != null) && GroundItems.getClosest(i).distanceTo() <= 20)
         {
             try
             {
                 Core.CurrentStatus = "Taking random drop";
                 Logger.addMessage("iWoodcutting: Taking random drop", true);
                 GroundItems.getClosest(i).take();

                 //WAIT FOR ACTION
                 Time.sleep(new SleepCondition() {
                     @Override
                     public boolean isValid() {
                         return Inventory.contains(i);
                     }
                 }, 2000);
             }
             catch (Exception e)
             {
                 e.printStackTrace();
             }
         }
        }
        
    }
}
