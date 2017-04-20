package view;

import javax.swing.*;
import java.util.List;

/**
 * Created by Matus Cuper on 19.4.2017.
 *
 * This class represents only information dialog
 */
public class InformationDialog {

    public InformationDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public InformationDialog(List<String> messages) {
        StringBuilder message = new StringBuilder();
        for (String m : messages) {
            message.append(m);
            message.append("\n");
        }
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
