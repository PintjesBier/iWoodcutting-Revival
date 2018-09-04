package iWoodCutting.GUI;

import iWoodCutting.core.Core;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Tristan on 4/09/2018.
 */
public class GUI extends JFrame {
    private JPanel panelMain;
    private JLabel lblTitel;
    private JLabel lblSubTitle;
    private JComboBox cmbTree;
    private JLabel lblTree;
    private JButton btnStart;

    public GUI() {
        setTitle("Configure script");
        setContentPane(panelMain);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Core.GUITree = cmbTree.getSelectedItem().toString();
                dispose();
            }
        });
    }
}
