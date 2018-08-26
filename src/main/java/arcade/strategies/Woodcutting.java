package arcade.strategies;

import arcade.core.Core;
import arcade.data.Constants;
import arcade.data.Methods;
import com.sun.xml.internal.messaging.saaj.util.FinalArrayList;
import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.accessors.Ground;
import org.rev317.min.api.methods.Game;
import org.rev317.min.api.methods.GroundItems;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.GroundItem;
import org.rev317.min.api.wrappers.SceneObject;
import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.rev317.min.api.methods.Players.getMyPlayer;

/**
 * Created by Tristan on 26/08/2018.
 */
public class Woodcutting implements Strategy {
    @Override
    public boolean activate() {
        return Game.isLoggedIn() && !Inventory.isFull();
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
            Core.Tree = SceneObjects.getClosest(Methods.CheckTreeToCut());
            Core.Tree.interact(SceneObjects.Option.CHOP_DOWN);

            //WAIT UNTIL ARRIVED AT TREE
            Time.sleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return Core.Tree.distanceTo() <= 12;
                }
            }, 5000);

            Core.Tree = SceneObjects.getClosest(Methods.CheckTreeToCut());
            Core.Tree.interact(SceneObjects.Option.CHOP_DOWN);

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
