package Main.Panels.Auth;

import Main.Database.User;
import Main.Frames.LoginFrame;
import Main.Utilities.requestExecutor;

import javax.swing.*;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//*Class created only to provide implementations for BtnHandlers on AuthPanel*/
public class RegisterPanelEvents implements IBtnEventHandler {

    private final String API_ENDPOINT = "http://localhost:8080/api/registration";
    private final LoginFrame loginFrame;
    private final RegisterPanel registerPanel;

    private JButton submitBtn;
    private Timer debounceTimer;
    private final InputsValidator inputsValidator;


    /**
     * Constructor to create eventHandler.
     * It creates instance of InputsValidator.
     * Sets debounceTimer to run validateInputs method after 250 milliseconds by default.
     * @param loginFrame - Main LoginFrame instance
     * @param registerPanel - registerPanel instance on which the events should be executed.
     */
    public RegisterPanelEvents(LoginFrame loginFrame, RegisterPanel registerPanel){
        this.loginFrame = loginFrame;
        this.registerPanel = registerPanel;
        this.submitBtn = registerPanel.getSubmitBtn();



        inputsValidator = new InputsValidator(
                registerPanel.getLoginInput(),
                registerPanel.getPasswordInput(),
                registerPanel.getRepeatPasswordInput()
        );

        debounceTimer = new Timer(250, e->validateInputs());

    }
    /**
     * Switches the loginFrame view into loginView.
     */
    @Override
    public void switchView(){
        loginFrame.switchToLoginView();
    }

    /**
     * Handles the logic of validating and sending API request in order to authenticate values provided by user.
     * Values are being acquired by registerPanel instance getters.
     */
    @Override
    public void handleSubmit() {
       if(validateInputs()){
           String username = registerPanel.getLoginInput().getText();
           char[] password = registerPanel.getPasswordInput().getPassword();

           //Hash Password in order to insert that to the database, hashing method accepts String.
//           String hashedPassword = PasswordHashing.hashPassword(new String(password));

           //Open separate thread for Database operation
           //TODO: Implement SwingWorker as long as code has not been moved to the API, in order to ensure thread safety.
           new Thread(() -> {
               Map<String, String> body = new HashMap<>();
               body.put("username", username);
               body.put("password", new String(password));

               HttpResponse<String> response = requestExecutor.sendPostRequest(API_ENDPOINT, body);

               handleRegisterResponse(response);

           }).start();

         //  loginFrame.handleSuccesfullAuthentication(new User());
       }
       else{
           return;
       }
    }

    /**
     * Method that runs inputsValidator.validate() method and stops debounceTimer after it finishes.
     * @return Boolean value if inputs were properly validated or not.
     */
    @Override
    public boolean validateInputs(){
        //If everything is okay, enable the register button option.
        //TODO: Do the same validation in the API backend later on.
        boolean validated = inputsValidator.validate();

        //TODO: Change that into JavaFX property
        submitBtn.setEnabled(validated);

        debounceTimer.stop();
        return validated;
    }

    /**
     * resets debounceTimer which runs validateInputs method.
     * @param time Time in milliseconds of debounce delay.
     */
    @Override
    public void debounceValidateInputs(int time) {
        debounceTimer.stop();
        debounceTimer.setDelay(time);
        debounceTimer.start();
    }

    /**
     * Handles the response that comes from backend API.
     * @param response - Response that we get after calling API Endpoint for user registration
     */
    private void handleRegisterResponse(HttpResponse<String> response){
        if(response==null) return;

        String message;

        switch (response.statusCode()){
            case 200:
                message = "Rejestracja przebiegła pomyślnie";
                break;
            case 409:
                message = "Nazwa użytkownika jest zajęta.";
                break;
            case 400:
                message = "Nazwa uzytkownika lub haslo nie spelniaja wymagań";
                break;
            default:
                message = "Coś poszło nie tak, prosimy spróbować później";
                break;
        }
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(loginFrame, message);
        });
    }

}
