package Main.Utilities;

import Main.Inputs.InputWithPlaceholder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ComponentUtils {
    /**
     * Method to create JTextField with placeholder text property and dimension.
     * JTextField is customized by customizeInputField method.
     * @param placeholder - Text property that should be displayed as placeholder
     * @param dimension - Width and height of input.
     * @return Returns ready to use JTextField with placeholder and customized
     */
    public static JTextField createTextField(String placeholder, Dimension dimension) {
        //InputWithPlaceholder is external class that provides the Factory Methods in order to create
        //inputs with placeholders.
        JTextField inputField = InputWithPlaceholder.createTextFieldInput(placeholder);
        customizeInputField(inputField, dimension);
        return inputField;
    }

    /**
     * Method to create JPasswordField with placeholder text property and dimension.
     * JPasswordField is customized by customizeInputField method.
     * @param placeholder - Text property that should be displayed as placeholder
     * @param dimension - Width and height of input.
     * @return Returns ready to use JPasswordField with placeholder and customized
     */
    public static JPasswordField createPasswordField(String placeholder, Dimension dimension) {

        //InputWithPlaceholder is external class that provides the Factory Methods in order to create
        //inputs with placeholders.
        JPasswordField passwordField = InputWithPlaceholder.createPasswordFieldInput(placeholder);
        customizeInputField(passwordField, dimension);
        return passwordField;
    }

    /**
     * Customizes provided input, sets the text alignment to center and prefferedSize to provided dimension.
     * @param field - Input that should be customized.
     * @param dimension - Width and height of input.
     */
    public static void customizeInputField(JTextField field, Dimension dimension) {
        field.setPreferredSize(dimension);
        field.setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Method that creates panel with specified layoutManager and components.
     * @param layoutManager - Layout Manager that will be set on the panel.
     * @param components - Array of components that should be inserted into panel.
     * @return Returns ready to be used panel with components.
     * @throws IllegalArgumentException If second argument is empty error is being thrown.
     */
    public static JPanel createPanel(LayoutManager layoutManager,JComponent... components) throws IllegalArgumentException{
        if (components == null || components.length == 0) {
            throw new IllegalArgumentException("At least one component must be provided");
        }

        JPanel panel = new JPanel();
        panel.setLayout(layoutManager);
        for (JComponent comp : components) {
            panel.add(comp);
        }
        return panel;
    }

    /**
     *
     * @param text text that will be shown on the button itself
     * @param action override method from ActionListener interface in that case "onClick"
     * @return created button with the onClick event implemented
     */
    public static JButton createBtn(String text, ActionListener action){
        JButton btn = new JButton(text);
        btn.addActionListener(action);
        return btn;
    }
}
