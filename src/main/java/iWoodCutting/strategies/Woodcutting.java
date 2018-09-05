package iWoodCutting.strategies;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
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
        if (getMyPlayer().getAnimation() == -1) {
            Core.CurrentStatus = "Chopping tree";
            Logger.addMessage("iWoodcutting: Chopping tree", true);

            SceneObject tree = SceneObjects.getClosest(Methods.CheckTreeToCut());

            if (Core.GUITree != "Progressive" && Bank.getBank().distanceTo() <= 3) {
                Walking.walkTo(new Tile(tree.getLocalRegionX(), tree.getLocalRegionY()));
                Time.sleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return getMyPlayer().getLocation().distanceTo() <= 3;
                    }
                }, 2000);
            }


            tree.interact(SceneObjects.Option.CHOP_DOWN);
            Time.sleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return getMyPlayer().getAnimation() == Constants.AXE_SWINGING_ID;
                }
            }, 2000);
            Time.sleep(500, 850);

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
