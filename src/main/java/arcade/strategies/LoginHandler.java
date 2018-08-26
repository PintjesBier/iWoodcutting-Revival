package arcade.strategies;

import arcade.core.Core;
import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Game;

import java.awt.event.KeyEvent;

/**
 * Created by Tristan on 16/08/2018.
 */
public class LoginHandler implements Strategy {
    @Override
    public boolean activate()
    {
        return (!Game.isLoggedIn());
    }

    @Override
    public void execute()
    {
        Core.CurrentStatus = "logging in";
        Logger.addMessage("iArcade: Logging in", true);

        Time.sleep(20000);
        Keyboard.getInstance().clickKey(KeyEvent.VK_ENTER);
        Time.sleep(250);
        Keyboard.getInstance().clickKey(KeyEvent.VK_ENTER);
        Time.sleep(new SleepCondition() {
            @Override
            public boolean isValid() {
                return Game.isLoggedIn();
            }
        }, 6000);

        org.parabot.environment.api.utils.Time.sleep(new SleepCondition() {
            @Override
            public boolean isValid() {
                return Game.isLoggedIn();
            }
        }, 6000);

        org.parabot.environment.api.utils.Time.sleep(500);
    }
}
