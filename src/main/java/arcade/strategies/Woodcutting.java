package arcade.strategies;

import arcade.core.Core;
import arcade.data.Constants;
import arcade.data.Methods;
import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Game;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.SceneObjects;

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
        //WOODCUTTING CLASS
        if (getMyPlayer().getAnimation() == -1)
        {
            Core.CurrentStatus = "Chopping tree";
            Logger.addMessage("iWoodcutting: Chopping tree", true);
            SceneObjects.getClosest(Methods.CheckTreeToCut()).interact(SceneObjects.Option.CHOP_DOWN);

            //WAIT FOR ACTION
            Time.sleep(new SleepCondition() {
                @Override
                public boolean isValid() {
                    return getMyPlayer().getAnimation() == Constants.AXE_SWINGING_ID;
                }
            }, 2000);
        }
    }
}
