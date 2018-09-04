package iWoodCutting.strategies;

import iWoodCutting.core.Core;
import org.parabot.core.ui.Logger;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Game;
import org.rev317.min.api.methods.Skill;

/**
 * Created by Tristan on 21/03/2018.
 */
public class DataGathering implements Strategy
{
    @Override
    public boolean activate() {
        return !Core.DataGathered && Game.isLoggedIn();
    }

    @Override
    public void execute()
    {
        Logger.addMessage("iWoodcutting: gathering data", true);
        Core.CurrentStatus = "Gathering data";

        Core.StartLevel = Skill.WOODCUTTING.getLevel();

        Core.DataGathered = true;
        Logger.addMessage("iWoodcutting: Data gathered", true);
        Core.CurrentStatus = "Data gathered";
    }
}
