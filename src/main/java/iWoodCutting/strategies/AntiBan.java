package iWoodCutting.strategies;

import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.wrappers.Area;
import org.rev317.min.api.wrappers.Player;
import org.rev317.min.api.wrappers.Tile;

import java.awt.event.KeyEvent;
import java.util.Random;

import static org.rev317.min.api.methods.Walking.walkTo;

/**
 * Created by Tristan on 4/09/2018.
 */
public class AntiBan implements Strategy {
    Area area = new Area(new Tile(3303, 2661), new Tile(3309,2665), new Tile(3309,2661), new Tile(3303,2661));
    private int treshold = 2;

    @Override
    public boolean activate() {
        return playersClose() && new Tile(3307,2662).distanceTo() < 5;
    }

    @Override
    public void execute() {
        Logger.addMessage("Too many players to close! Pausing", true);
        Time.sleep(10000);
        if(Players.getPlayers().length > 2) {
            Logger.addMessage("Still overcrowded, leaving", true);
            Time.sleep(1000, 30000);
            walkTo(getRandomTile(area));
            Time.sleep(1000);
            Keyboard.getInstance().sendKeys("::home");
            Time.sleep(500);
            Keyboard.getInstance().clickKey(KeyEvent.VK_ENTER);
            Time.sleep(3000);
        }

    }

    private Tile getRandomTile(Area area) {
        Random r = new Random();
        int t = (int)(Math.random() * ((area.getTiles().length - 1) +1) + 1);
        return area.getTiles()[t];
    }

    private boolean playersClose() {
        int count = 0;
        for(Player p : Players.getPlayers()) {
            if(p.distanceTo() < 11) count++;
        }
        if(count > treshold) return true;
        return false;
    }
}
