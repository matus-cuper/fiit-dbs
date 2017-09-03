package view;

import javafx.stage.Stage;

import javax.swing.*;

/**
 * Created by Matus Cuper on 19.4.2017.
 *
 * Pop up window with given title and message
 */
public class ErrorDialog extends Stage {

    /**
     * Pop up window
     * @param title of window
     * @param message explains to user, how would user input looks like
     */
    public ErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
