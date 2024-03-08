package Main.Panels.Auth;




import Main.Utilities.ComponentUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** AbstractClass which containes mutual variables for LoginPanel and RegisterPanel for the LoginFrame class component.
 * It uses the ComponentUtils class which creates most basic components without specific modifiers.
 * */
public abstract class AbstractAuthPanel extends JPanel {

    //Inputs size
    private final Dimension inputDimension;

    //Things used in both Login, Register panels
//    protected JPanel inputPanel;
//    protected JPanel buttonsPanel;
    protected JTextField loginInput;
    protected JPasswordField passwordInput;
    protected JButton submitBtn;
    protected JButton switchFormBtn;
    protected JButton continueAsGuestBtn;

    protected IBtnEventHandler eventHandler;

    protected AbstractAuthPanel(){
        inputDimension = new Dimension(250, 40);
    }
    //Custom input size constructor
    protected AbstractAuthPanel(Dimension dimension){
        this.inputDimension = dimension;
    }


    /**
     *
     * @param placeholder - Placeholder for the input, placeholder will be shown if user does not interact with the field
     * @return - Returns JTextField component, with Horizontal text-alignment center
     */
    protected JTextField createTextField(String placeholder) {

        return ComponentUtils.createTextField(placeholder, inputDimension);
    }
    /**
     *
     * @param placeholder - Placeholder for the input, placeholder will be shown if user does not interact with the field
     * @return - Returns JPasswordField component, with Horizontal text-alignment center
     */
    protected JPasswordField createPasswordField(String placeholder) {

        return ComponentUtils.createPasswordField(placeholder, inputDimension);
    }

    /**
     *
     * @param field - JTextField and inherited fields, that should be customized
     * @param dimension - Custom dimension ec. new Dimension(height, width) for the input.
     */
    protected void customizeInputField(JTextField field, Dimension dimension) {
        ComponentUtils.customizeInputField(field,dimension);
    }

    /**
     *
     * @param text - Text for the button
     * @param action - ActionListener ec. e-> someMethod(). This method should be executed after user clicks button.
     * @return - Returns ready to be used button with eventListener.
     */
    protected JButton createBtn(String text, ActionListener action){
        return ComponentUtils.createBtn(text, action);
    }

    /**
     *
     * @param layoutManager - LayoutManager that should be used for panel, FlowLayouts/GridLayouts ect.
     * @param components - List of components that should be implemented inside the panel provided as coma separated values, or an array.
     * @return - Returns ready to be used panel with components aligned into designated layout manager.
     */
    protected JPanel createPanel (LayoutManager layoutManager, JComponent... components){
        return ComponentUtils.createPanel(layoutManager, components);
    }

    /**
     *
     * @param method - Method that should be executed on input change.
     * @param components - List of components to which the method should be connected. Provided as coma separated values, or an array.
     * @throws IllegalArgumentException - Throws IllegalArgumentException, if the components list is null or whatever the component is null
     * there is no point in adding a method to null object.
     */
    protected void addEventListenerToInputs(Runnable method, JTextComponent... components) throws IllegalArgumentException{
        if(components==null) throw new IllegalArgumentException("Array of components cannot be null");
        for(JTextComponent comp : components){
            if(comp==null) throw new IllegalArgumentException("Component in an array cannot be null");
            comp.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    //Make sure that the code runs on EDT just in case
                    SwingUtilities.invokeLater(method);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    //Make sure that the code runs on EDT just in case
                    SwingUtilities.invokeLater(method);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    //Make sure that the code runs on EDT just in case
                    SwingUtilities.invokeLater(method);
                }
            });
        }

    }


    //Getters created, so it would be easier to validate inputs in separate class
    public JPasswordField getPasswordInput(){
        return passwordInput;
    }
    public JTextField getLoginInput(){
        return loginInput;
    }
    public JButton getSubmitBtn(){
        return submitBtn;
    }
}
