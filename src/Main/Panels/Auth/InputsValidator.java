package Main.Panels.Auth;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputsValidator{

    //TODO: Change hardcoded 255 value into API request for DB metadata
    private final int MAX_INPUT_LENGTH = 255;
    private final int MIN_PASSWORD_LENGTH = 8;
    private final Color INVALID_INPUT_COLOR = new Color(250,100,100);
    private final Color VALID_INPUT_COLOR = Color.white;
    private final JTextField usernameInput;
    private final JPasswordField passwordInput;
    private final JPasswordField repeatPasswordInput;

    //Eventual placeholders for inputs
    private final String usernamePlaceholder;
    private final char[] passwordPlaceholder;
    private final char[] repeatPasswordPlaceholder;


    /**
     * Constructor creates the InputValidator instance, arguments are usernameInput, passwordInput and repeatPasswordInput. Order is important,
     * but it is allowed to provide null values if there is need to validate only some inputs not all of them.
     *
     *
     * @param usernameInput First argument has to be usernameInput of register form
     * @param passwordInput Second argument has to be passwordField of register form (do not change the order of password/repeatPassword)
     * @param repeatPasswordInput Third argument has to be repeatPasswordField of register form.
     */
    public InputsValidator(JTextField usernameInput, JPasswordField passwordInput, JPasswordField repeatPasswordInput) {
        this.usernameInput = usernameInput;
        this.passwordInput =  passwordInput;
        this.repeatPasswordInput = repeatPasswordInput;

        //Set up placeholders
        usernamePlaceholder = (usernameInput == null) ? "" : usernameInput.getText();
        passwordPlaceholder = (passwordInput == null) ? new char[0] : passwordInput.getPassword();
        repeatPasswordPlaceholder = (repeatPasswordInput == null) ? new char[0] : repeatPasswordInput.getPassword();
    }

    /**
     * Method that validates the Password field based on basic password validation rules.
     * If field is invalid it changes the appearance of UI.
     *
     * @param passwordInput - Password field that should be validated.
     * @return Returns the boolean value whenever password is valid or not.
     */
    public boolean validatePassword(JPasswordField passwordInput){

        char[] passwordValue = passwordInput.getPassword();

        //If value provided is equal to placeholder return immediately false
        if(Arrays.equals(passwordValue, passwordPlaceholder)) return false;

        if(passwordValue.length < MIN_PASSWORD_LENGTH || passwordValue.length > MAX_INPUT_LENGTH){

            if(passwordValue.length != 0){
                customizeInputAfterValidation(passwordInput, INVALID_INPUT_COLOR);
            }
            else customizeInputAfterValidation(passwordInput, VALID_INPUT_COLOR);
            return false;
        }
        customizeInputAfterValidation(passwordInput, VALID_INPUT_COLOR);
        return true;
    }

    /**
     * Method that validates the RepeatPasswordField and checks if value of the field is same as value of passwordInput.
     * If field is invalid it changes the appearance of UI.
     *
     * @param passwordInput - Password field that should be validated.
     * @param repeatPasswordInput - RepeatPasswordInput that should be compared to the passwordInput
     * @return Returns the boolean value whenever both passwords are the same.
     */
    public boolean validateRepeatPassword(JPasswordField passwordInput, JPasswordField repeatPasswordInput){

        char[] passwordFirst = passwordInput.getPassword();
        char[] passwordSecond = repeatPasswordInput.getPassword();

        //If value provided is equal to placeholder return immediately false
        if(Arrays.equals(passwordSecond, repeatPasswordPlaceholder)) return false;

        //No need to check if passwords matches, if the first one is invalid.
        if(!validatePassword(passwordInput)) return false;

        if(!Arrays.equals(passwordFirst, passwordSecond)) {
            if(passwordSecond.length != 0 ) {
                customizeInputAfterValidation(repeatPasswordInput, INVALID_INPUT_COLOR);
            }
            else customizeInputAfterValidation(repeatPasswordInput, VALID_INPUT_COLOR);

            return false;
        }

        customizeInputAfterValidation(repeatPasswordInput, VALID_INPUT_COLOR);
        return true;
    }

    /**
     * Method that validates the username field based on basic username validation rules.
     * If field is invalid it changes the appearance of UI.
     *
     * @param usernameInput - Username field that should be validated accordingly to basic validation rules.
     * @return Returns the boolean value whenever username value is valid or not.
     */
    public boolean validateUsername(JTextField usernameInput){
        String username = usernameInput.getText();

        //If value provided is equal to placeholder return immediately false
        if(username.equals(usernamePlaceholder)) return false;

        if(username.length() > MAX_INPUT_LENGTH){
            customizeInputAfterValidation(usernameInput, INVALID_INPUT_COLOR);
            return false;
        }

        customizeInputAfterValidation(usernameInput, VALID_INPUT_COLOR);
        return true;
    }

    /**
     *
     * @param comp - Component to which the changes will be applied
     * @param color - Specific color for UI changes
     */
    private void customizeInputAfterValidation(JTextComponent comp, Color color){
        //  comp.setBorder(BorderFactory.createLineBorder(color));
        comp.setBackground(color);
    }

    /**
     * Validates all the inputs provided to the class constructor. If one of inputs provided is null it skips the validation for that input.
     * Either way we can use separately all the methods from InputsValidator, or simply this which executes all of them accordingly.
     * @return - Returns the boolean value if all the conditions are valid.
     */
    public boolean validate(){
        List<Boolean> conditions = new ArrayList<>();
        if(usernameInput!=null) conditions.add(validateUsername(usernameInput));
        if(passwordInput!=null) conditions.add(validatePassword(passwordInput));
        if(repeatPasswordInput!=null && passwordInput!=null) conditions.add(validateRepeatPassword(passwordInput, repeatPasswordInput));

        //Checks if all the list values are true
        return conditions.stream().allMatch(Boolean::booleanValue);
    }

}
