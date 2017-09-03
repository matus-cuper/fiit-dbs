package view;

import javax.swing.*;
import java.util.List;

/**
 * Created by Matus Cuper on 19.4.2017.
 *
 * Pop up window with given message, only informational character,
 * message explains to user, what happened on server side
 */
public class InformationDialog {

    /**
     * Pop up window
     * @param message contains information about 1 event
     */
    public InformationDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Pop up window
     * @param messages contains information about multiple events
     */
    public InformationDialog(List<String> messages) {
        StringBuilder message = new StringBuilder();
        for (String m : messages) {
            message.append(m);
            message.append("\n");
        }
        JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
