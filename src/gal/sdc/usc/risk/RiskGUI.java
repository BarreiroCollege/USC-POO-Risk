package gal.sdc.usc.risk;

import gal.sdc.usc.risk.jugar.Menu;
import javax.swing.UIManager;

public class RiskGUI {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        Menu.jugarConGui();
    }

}
