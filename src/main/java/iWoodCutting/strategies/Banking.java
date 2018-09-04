package iWoodCutting.strategies;

import iWoodCutting.core.Core;
import iWoodCutting.data.Constants;
import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Bank;
import org.rev317.min.api.methods.Game;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import static org.rev317.min.api.methods.Players.getMyPlayer;


/**
 * Created by Tristan on 26/08/2018.
 */
public class Banking implements Strategy {
    @Override
    public boolean activate() {
        return Game.isLoggedIn() && Inventory.isFull();
    }

    @Override
    public void execute()
    {
        if (Inventory.isFull())
        {
            //UPDATE
            Logger.addMessage("iWoodcutting: Banking", true);
            Core.CurrentStatus = "Banking";

            //OPEN BANK
            if (!Core.BankArea.contains(getMyPlayer().getLocation()) && (Bank.getBank().distanceTo() >= 20)) {
                //TELEPORT TO WC
                Menu.sendAction(315, 0, 0, 1167);
                Time.sleep(550);
                Menu.sendAction(315, 0, 0, 2496);
                Time.sleep(850);

                //WAIT FOR TELEPORT
                Time.sleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return Core.WoodCuttingTeleportTile == getMyPlayer().getLocation();
                    }
                }, 4500);

                Bank.open();
                Time.sleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return Bank.isOpen();
                    }
                },5000);
            }
            else if (Bank.getBank().distanceTo() < 20)
            {
                Bank.open();
                Time.sleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return Bank.isOpen();
                    }
                },5000);
            }

            if (Bank.isOpen())
            {
                Bank.depositAllExcept(Constants.IRON_AXE_ID, Constants.RUNE_AXE_ID);
                Time.sleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return !Inventory.contains(Constants.TREE_LOG_ID, Constants.OAK_TREE_LOG_ID, Constants.MAPLE_TREE_LOG_ID, Constants.YEW_TREE_LOG_ID);
                    }
                },5000);

                Bank.close();
            }
        }

    }
}
