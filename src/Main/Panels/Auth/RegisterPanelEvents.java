package Main.Panels.Auth;

import Main.Frames.LoginFrame;
import Main.Utilities.requestExecutor;

import javax.swing.*;
import java.net.http.HttpResponse;
import java.util.Map;

//*Class created only to provide implementations for BtnHandlers on AuthPanel*/
public class RegisterPanelEvents extends AbstractAuthPanelEvents implements IAuthEventHandler {

    private final String API_ENDPOINT = "http://localhost:8080/api/registration";
    private final LoginFrame loginFrame;
    private final RegisterPanel registerPanel;

    private final JButton submitBtn;
    private final Timer debounceTimer;
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
       if(inputsValidator.validate()){

           Map<String, String> body = createAuthRequestBody(registerPanel);

           //Open separate thread for API request
           //TODO: Implement SwingWorker as long as code has not been moved to the API, in order to ensure thread safety.
           new Thread(() -> {
               HttpResponse<String> response = requestExecutor.sendPostRequest(API_ENDPOINT, body);
               handleRegisterResponse(response);
           }).start();

         //  loginFrame.handleSuccessfulAuthentication(new User());
       }
       else{
           SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(loginFrame, "Nazwa użytkownika lub hasło są nieprawidłowe."));
       }
    }

    /**
     * Method that runs inputsValidator.validate() method and stops debounceTimer after it finishes.     */

    public void validateInputs(){
        //If everything is okay, enable the register button option.
        //TODO: Do the same validation in the API backend later on.
        boolean validated = inputsValidator.validate();

        //TODO: Change that into JavaFX property
        submitBtn.setEnabled(validated);

        debounceTimer.stop();

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
     * Method created, so it would be easier to separate logic for both loginPanel and registerPanel
     */
   @Override
    public void handleEnterKeyInput(){
        if(inputsValidator.validate()) handleSubmit();
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
        SwingUtilities.invokeLater(() -> super.handleSuccessfulAuthenticationResponse(loginFrame, message, response));
    }



}
