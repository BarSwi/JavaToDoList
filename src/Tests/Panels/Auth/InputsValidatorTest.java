package Tests.Panels.Auth;

import Main.Panels.Auth.InputsValidator;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class InputsValidatorTest {
    private InputsValidator inputsValidator;
    @Before
    public void setUp(){
        inputsValidator = new InputsValidator(new JTextField(), new JPasswordField(), new JPasswordField());
    }
    @Test
    public void passwordValidationTest(){
        JPasswordField invalidPassword = new JPasswordField("test");
        JPasswordField validPassword = new JPasswordField("test12345");

        assertTrue(inputsValidator.validatePassword(validPassword));
        assertFalse(inputsValidator.validatePassword(invalidPassword));
        assertFalse(inputsValidator.validatePassword(null));

    }

    @Test
    public void usernameValidationTest(){
        JTextField invalidUsername = new JTextField("");
        JTextField validUsername = new JTextField("1");

        assertTrue(inputsValidator.validateUsername(validUsername));
        assertFalse(inputsValidator.validateUsername(invalidUsername));
        assertFalse(inputsValidator.validateUsername(null));
    }

    @Test
    public void repeatPasswordValidationTest(){
        JPasswordField baseInvalidPassword = new JPasswordField("1234");
        JPasswordField baseValidPassword = new JPasswordField("12345678");


        assertTrue(inputsValidator.validateRepeatPassword(baseValidPassword, baseValidPassword));
        assertFalse(inputsValidator.validateRepeatPassword(baseInvalidPassword, baseInvalidPassword));
        assertFalse(inputsValidator.validateRepeatPassword(baseValidPassword, baseInvalidPassword));

        assertFalse(inputsValidator.validateRepeatPassword(null, baseValidPassword));
        assertFalse(inputsValidator.validateRepeatPassword( baseValidPassword, null));
        assertFalse(inputsValidator.validateRepeatPassword(null, null));
    }
}
