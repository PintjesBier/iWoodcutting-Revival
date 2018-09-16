package iWoodCutting.strategies;

import iWoodCutting.core.Core;
import iWoodCutting.data.Constants;
import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.*;
import org.rev317.min.api.wrappers.Tile;
import sun.rmi.runtime.Log;

import static org.rev317.min.api.methods.Players.getMyPlayer;

/**
 * Created by Tristan on 26/08/2018.
 */
public class Banking implements Strategy {
    @Override
    public boolean activate() {
        return Inventory.isFull();
    }

    @Override
    public void execute() {
        try {

            //UPDATE
            Logger.addMessage("iWoodcutting: Banking", true);
            Core.CurrentStatus = "Banking";

            //OPEN BANK
            if (!Bank.isOpen())
            {
                if ((SceneObjects.getNearest(Constants.BANK_ID).length > 0))
                {
                    if (Bank.getBank().getLocation().isWalkable())
                    {
                        Logger.addMessage("1", true);
                        Bank.open();
                        Time.sleep(new SleepCondition() {
                            @Override
                            public boolean isValid() {
                                return Bank.isOpen();
                            }
                        }, 7000);
                    }
                    else if (!Bank.getBank().getLocation().isWalkable())
                    {
                        Logger.addMessage("3", true);

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
                        }, 7000);
                    }

                }
                else if (SceneObjects.getClosest(Constants.BANK_CHEST_GUILD_ID) != null)
                {
                    Logger.addMessage("YESSS", true);
/*                    SceneObjects.getNearest(Constants.BANK_CHEST_GUILD_ID)[1].getLocation().walkTo();
                    Time.sleep(850, 1500);*/

                    SceneObjects.getNearest(Constants.BANK_CHEST_GUILD_ID)[1].interact(SceneObjects.Option.USE);

                    Time.sleep(new SleepCondition() {
                        @Override
                        public boolean isValid() {
                            return Bank.isOpen();
                        }
                    }, 7000);
                }
            }

            //DEPOSIT ITEMS
            if (Bank.isOpen()) {
                Bank.depositAllExcept(Constants.IRON_AXE_ID, Constants.RUNE_AXE_ID, Constants.INFERNAL_AXE_ID);
                Time.sleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return !Inventory.contains(Constants.TREE_LOG_ID, Constants.OAK_TREE_LOG_ID, Constants.MAPLE_TREE_LOG_ID, Constants.YEW_TREE_LOG_ID, Constants.MAGIC_TREE_LOG_ID);
                    }
                }, 3000);

                Bank.close();
                Time.sleep(new SleepCondition() {
                    @Override
                    public boolean isValid() {
                        return !Bank.isOpen();
                    }
                }, 1500);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
