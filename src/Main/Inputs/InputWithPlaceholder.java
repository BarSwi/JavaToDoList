package Main.Inputs;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


//CustomInput class gives the ability to create JTextComponents and inherited with dynamic placeholders.
public class InputWithPlaceholder<T extends JTextComponent>{

    private static final char DEFAULT_ECHO_CHAR = (char) 0;
    private static final char PASSWORD_MASK_CHAR = '\u2022';

    private boolean inputChanged;
    private final T inputComponent;
    private final String placeholder;

    /**
     *  Creates placeholder for input. Placeholder is the text property that disappears when user focuses input, or if user provided any value.
     * @param placeholder - Text property that should be displayed on input
     * @param inputComponent - InputComponent to which placeholder should be applied.
     */
    private InputWithPlaceholder(final String placeholder, T inputComponent){

        //Check for the null exception
        if(inputComponent==null) {
            throw new IllegalArgumentException("input component cannot be null");
        }
        //Check for empty placeholder exception
        if(placeholder.isEmpty()){
            throw new IllegalArgumentException("Placeholder cannot be empty");
        }

        this.inputComponent = inputComponent;
        this.inputChanged = false;
        this.placeholder = placeholder;

        inputComponent.setText(placeholder);



        setPasswordMask(DEFAULT_ECHO_CHAR);

        // Set listener to detect if user changed the input manually
       addKeyListenerToInput();

       //Set listener to detect Focus on input and adjust placeholder accordingly
       addFocusListenerToInput();

    }


    /**
     * Sets echoChar if the input is type of JPasswordField
     * @param echoChar - EchoChar set
     */
    private void setPasswordMask(char echoChar) {
        if (inputComponent instanceof JPasswordField) {
            ((JPasswordField) inputComponent).setEchoChar(echoChar);
        }
    }

    /**
     * Adds new keyListener to inputComponent
     */
    private void addKeyListenerToInput() {
        inputComponent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                inputChanged = true;
            }
        });
    }

    /**
     * Checks if input is focused or not, if focus is gained and user have not provided any value text property disappears.
     * If input loses focus and user have not provided any value, placeholders text property appears back.
     */
    private void addFocusListenerToInput() {
        inputComponent.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!inputChanged && inputComponent.getText().equals(placeholder)) {
                    inputComponent.setText("");
                    setPasswordMask(PASSWORD_MASK_CHAR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (inputComponent.getText().isEmpty()) {
                    inputChanged = false;
                    inputComponent.setText(placeholder);
                    setPasswordMask(DEFAULT_ECHO_CHAR);
                }
            }
        });
    }

    /**
     *  Factory method that returns JTextField with placeholder.
     * @param placeholder - Text property that will be applied to input.
     * @return JTextField with placeholder
     */
    public static JTextField createTextFieldInput(String placeholder) {
        return new InputWithPlaceholder<>(placeholder, new JTextField()).getInputComponent();
    }

    /**
     *  Factory method that returns JPasswordField with placeholder.
     * @param placeholder - Text property that will be applied to input.
     * @return JPasswordField with placeholder
     */
    public static JPasswordField createPasswordFieldInput(String placeholder) {
        return new InputWithPlaceholder<>(placeholder, new JPasswordField()).getInputComponent();
    }


    /**
     *  Getter that returns created input
     * @return generic inputComponent.
     */
    private T getInputComponent() {
        return inputComponent;
    }
}
