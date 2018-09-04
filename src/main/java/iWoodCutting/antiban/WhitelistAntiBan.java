package iWoodCutting.antiban;

import iWoodCutting.randoms.IRandomEvent;
import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.wrappers.Player;

import java.util.ArrayList;
public class WhitelistAntiBan implements Strategy {
    private ArrayList<String> whitelist;
    private boolean enabled = true;
    private IRandomEvent[] randoms;

    public WhitelistAntiBan() {
        WhitelistForm whitelistForm = new WhitelistForm();
        while(whitelistForm.isVisible()) {
            Time.sleep(1000);
        }

        whitelist = whitelistForm.getNames();
        if(whitelist == null) {
            enabled = false;
        } else {
            whitelist.add(Players.getMyPlayer().getName().toLowerCase());
        }
    }

    public WhitelistAntiBan(IRandomEvent[] randoms) {
        this.randoms = randoms;
        WhitelistForm whitelistForm = new WhitelistForm();
        while(whitelistForm.isVisible()) {
            Time.sleep(1000);
        }

        whitelist = whitelistForm.getNames();
        if(whitelist == null) {
            enabled = false;
        } else {
            whitelist.add(Players.getMyPlayer().getName().toLowerCase());
        }
    }

    @Override
    public boolean activate() {
        boolean activate = false;
        try {
            activate = playersClose();
            setRandoms(activate);
        } catch(NullPointerException ex) {
            ex.printStackTrace();
        }
        return enabled && activate;
    }

    @Override
    public void execute() {
        Time.sleep(1000);
    }

    private boolean playersClose() throws NullPointerException {
        if(whitelist == null) return false;
        for(Player p : Players.getPlayers()) {
            if(!whitelist.contains(p.getName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void setRandoms(boolean enable) throws NullPointerException {
        for(IRandomEvent r: randoms) {
            if(enable) r.enable();
            if(!enable) r.disable();
        }
    }
}
