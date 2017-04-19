package view;

import javafx.stage.Stage;

import javax.swing.*;

/**
 * Created by Matus Cuper on 19.4.2017.
 *
 * This class represents error dialog window
 */
public class ErrorDialog extends Stage {

    public ErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
