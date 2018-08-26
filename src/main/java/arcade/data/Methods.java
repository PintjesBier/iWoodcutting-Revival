package arcade.data;

import arcade.core.Core;
import org.parabot.core.ui.Logger;
import org.parabot.environment.api.utils.Random;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.rev317.min.api.methods.Skill;

import java.awt.event.KeyEvent;

public class Methods
{
    //VARIABLES
    private static int TreeID;

    //WOODCUTING
    public static int CheckTreeToCut ()
    {
        if (Skill.WOODCUTTING.getRealLevel() < 15)
        {
            TreeID = Constants.TREE_ID;
        }
        else if (Skill.WOODCUTTING.getRealLevel() >= 15 && Skill.WOODCUTTING.getRealLevel() < 45)
        {
            TreeID = Constants.OAK_TREE_ID;
        }
        else if (Skill.WOODCUTTING.getRealLevel() >= 45 && Skill.WOODCUTTING.getRealLevel() < 60)
        {
            TreeID = Constants.MAPLE_TREE_ID;
        }
        else if (Skill.WOODCUTTING.getRealLevel() >= 60)
        {
            TreeID = Constants.YEW_TREE_ID;
        }

        return TreeID;
    }

    //FUNCTIONAL
    public static String stripNonDigits(final CharSequence input)
    {
        final StringBuilder sb = new StringBuilder(input.length());

        for (int i = 0; i < input.length(); i++) {
            final char c = input.charAt(i);
            if (c > 47 && c < 58) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    //ANTI AFK
    public static void ANTIAFK()
    {
        Logger.addMessage("iArcade: Performing antiAFK", true);
        final int[]  keys   = { KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT };

        if (Random.between(0,4) == 2)
        {
            Core.CurrentStatus = "Anti AFK";
            int keyCode = keys[Random.between(0, keys.length)];
            Keyboard.getInstance().pressKey(keyCode);
            Time.sleep(Random.between(800,1500));
            Keyboard.getInstance().releaseKey(keyCode);
            Core.CurrentStatus = "Waiting...";
        }
    }


}