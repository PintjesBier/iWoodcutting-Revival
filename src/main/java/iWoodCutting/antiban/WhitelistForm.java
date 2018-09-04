package iWoodCutting.antiban;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WhitelistForm extends JFrame {
    private JPanel contentPane;
    private JTextArea txtWhitelist;
    private JButton confirmButton;
    private JCheckBox chkEnable;

    private ArrayList<String> names = new ArrayList<>();

    public WhitelistForm() {
        setTitle("Configure whitelist");
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parseInput(txtWhitelist.getText());
                dispose();
            }
        });
        chkEnable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox cb = (JCheckBox) e.getSource();
                txtWhitelist.setEnabled(cb.isSelected());
            }
        });
    }

    private void parseInput(final String text) {
        System.out.println(text);
        String[] strs = text.split("\\r?\\n");
        for(String s : strs) {
             s = s.trim();
             if(!s.equals("")) {
                 names.add(s.toLowerCase());
             }
        }
    }

    public ArrayList<String> getNames() {
        if(!chkEnable.isSelected()) return null;
        if(names.size() == 0) new ArrayList<>();
        return names;
    }
}
