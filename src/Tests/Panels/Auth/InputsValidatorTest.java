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
    public void validateValidPassword() {
        JPasswordField validPassword = new JPasswordField("test12345");
        assertTrue(inputsValidator.validatePassword(validPassword));
    }

    @Test
    public void validateInvalidPassword() {
        JPasswordField invalidPassword = new JPasswordField("test");
        assertFalse(inputsValidator.validatePassword(invalidPassword));
    }

    @Test
    public void validateNullPassword() {
        assertFalse(inputsValidator.validatePassword(null));
    }

    @Test
    public void validUsernameValidationTest() {
        JTextField validUsername = new JTextField("1");
        assertTrue(inputsValidator.validateUsername(validUsername));
    }

    @Test
    public void invalidUsernameValidationTest() {
        JTextField invalidUsername = new JTextField("");
        assertFalse(inputsValidator.validateUsername(invalidUsername));
    }

    @Test
    public void nullUsernameValidationTest() {
        assertFalse(inputsValidator.validateUsername(null));
    }

    @Test
    public void validateMatchingValidPasswords() {
        JPasswordField validPassword = new JPasswordField("12345678");
        assertTrue(inputsValidator.validateRepeatPassword(validPassword, validPassword));
    }

    @Test
    public void validateMatchingInvalidPasswords() {
        JPasswordField invalidPassword = new JPasswordField("1234");
        assertFalse(inputsValidator.validateRepeatPassword(invalidPassword, invalidPassword));
    }

    @Test
    public void validateMismatchedPasswords() {
        JPasswordField validPassword = new JPasswordField("12345678");
        JPasswordField invalidPassword = new JPasswordField("1234");
        assertFalse(inputsValidator.validateRepeatPassword(validPassword, invalidPassword));
    }

    @Test
    public void validateNullFirstPassword() {
        JPasswordField validPassword = new JPasswordField("12345678");
        assertFalse(inputsValidator.validateRepeatPassword(null, validPassword));
    }

    @Test
    public void validateNullSecondPassword() {
        JPasswordField validPassword = new JPasswordField("12345678");
        assertFalse(inputsValidator.validateRepeatPassword(validPassword, null));
    }

    @Test
    public void validateNullPasswords() {
        assertFalse(inputsValidator.validateRepeatPassword(null, null));
    }
}
