package com.datec.soadre.core.util;

import javax.swing.*;
import org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel;

public class LookAndFeelUtil {

    public static void setWindowsLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new SubstanceDustLookAndFeel());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar Look and Feel");
        }
    }

}
