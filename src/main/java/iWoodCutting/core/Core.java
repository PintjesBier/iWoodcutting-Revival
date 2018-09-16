package iWoodCutting.core;


import iWoodCutting.GUI.GUI;
import iWoodCutting.antiban.WhitelistAntiBan;
import iWoodCutting.data.Area;
import iWoodCutting.strategies.Banking;
import iWoodCutting.strategies.DataGathering;
import iWoodCutting.strategies.Relog;
import iWoodCutting.strategies.Woodcutting;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Skill;
import org.rev317.min.api.wrappers.SceneObject;
import org.rev317.min.api.wrappers.Tile;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Tristan on 14/03/2018.
 */

@ScriptManifest(author = "iTunes",
        category = Category.WOODCUTTING,
        description = "",
        name = "iWoodcutting",
        servers = { "Revival" },
        version = 1.1)

public class Core extends Script implements Paintable {
    //VARIABLES
    private final ArrayList<Strategy> strategies = new ArrayList<>();
    public static String CurrentStatus = "Starting";
    public static boolean DataGathered = false;
    public static int StartLevel;
    public static String GUITree;

    //TIMER
    private static org.parabot.environment.api.utils.Timer Timer = new org.parabot.environment.api.utils.Timer();

    //AREAS
    public static Area BankArea = new Area(new Tile(3463, 2743), new Tile(3463, 2754), new Tile(3474, 2754), new Tile(3474, 2743));

    //TILES
    public static Tile WoodCuttingTeleportTile = new Tile(3458, 2725, 0);

    @Override
    public boolean onExecute() {
        strategies.add(new DataGathering());
        strategies.add(new WhitelistAntiBan());
        strategies.add(new Relog());
        strategies.add(new Woodcutting());
        strategies.add(new Banking());
        provide(strategies);

        //LOAD GUI
        GUI gui = new GUI();
        while(gui.isVisible()) {
            Time.sleep(100);
        }

        return true;
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    //START: Code generated using Enfilade's Easel
    private final Color color1 = new Color(7, 7, 184, 178);
    private final Color color2 = new Color(255, 255, 255, 178);

    private final BasicStroke stroke1 = new BasicStroke(1);

    private final Font font1 = new Font("Arial", 1, 12);

    private String FormatNumber(double number) {
        if (number >= 1000 && number < 1000000) {
            return new DecimalFormat("#,###.0").format(number / 1000) + "K";
        } else if (number >= 1000000 && number < 1000000000) {
            return new DecimalFormat("#,###.0").format(number / 1000000) + "M";
        } else if (number >= 1000000000) {
            return new DecimalFormat("#,###.00").format(number / 1000000000) + "B";
        }

        return "" + number;
    }

    @Override
    public void paint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;

        //PAINT
        g.setColor(color1);
        g.fillRect(277, 195, 235, 139);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRect(277, 195, 235, 139);
        g.setFont(font1);
        g.drawString("Status: ", 285, 210);
        g.drawString(Core.CurrentStatus, 380, 210);
        g.drawString("Level:  ", 285, 225);
        g.drawString(String.valueOf(Skill.WOODCUTTING.getLevel()) + " (+" + (String.valueOf(Skill.WOODCUTTING.getLevel() - StartLevel)) + ")", 380, 225);
        g.drawString("Time elapsed:  ", 285, 240);
        g.drawString(Timer.toString(), 380, 240);
/*        g.drawString("Current wave:  ", 285, 255);
        g.drawString(String.valueOf(WavesCompleted), 380, 255);*/
/*        g.drawString("Total waves:  ", 285, 270);
        g.drawString(String.valueOf(TotalWavesCompleted), 380, 270);*/

    }
}

