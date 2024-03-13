package Main.Panels.Auth;

import Main.Frames.LoginFrame;
import Main.Utilities.requestExecutor;

import javax.swing.*;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


//*Class created only to provide implementations for BtnHandlers on AuthPanel*/
public class LoginPanelEvents extends AbstractAuthPanelEvents implements IBtnEventHandler {
    private final String API_ENDPOINT = "http://localhost:8080/api/authentication";

    private final int MAX_INPUT_LENGTH = 255;

    private final LoginFrame loginFrame;
    private final LoginPanel loginPanel;
    private JButton submitBtn;

    /**
     * Constructor to create eventHandler.
     * @param loginFrame - Main LoginFrame instance
     * @param loginPanel - loginPanel instance on which the events should be executed.
     */
    public LoginPanelEvents(LoginFrame loginFrame, LoginPanel loginPanel){
        this.loginFrame = loginFrame;
        this.loginPanel = loginPanel;
    }

    /**
     * Switches the loginFrame view into registerView.
     */
    @Override
    public void switchView(){
        loginFrame.switchToRegisterView();
    }

    /**
     * Handles the logic of validating and sending API request in order to authenticate values provided by user.
     * Values are being acquired by loginPanel instance getters.
     */
    @Override
    public void handleSubmit() {
        if(validateInputs()){
            new Thread(() -> {
                Map<String, String> body = createAuthRequestBody(loginPanel);
                HttpResponse<String> response = requestExecutor.sendPostRequest(API_ENDPOINT, body);

                handleLoginSubmit(response);
            }).start();

            //TODO: Send API request to authenticate the user, and put that into User class
        }
    }

    /**
     * Simple validation logic for inputs of loginPanel provided to constructor.
     * @return Returns true if inputs are valid and false if they are not.
     */
    @Override
    public boolean validateInputs() {
        String username = loginPanel.getLoginInput().getText();
        char[] password = loginPanel.getPasswordInput().getPassword();

        return username.length() <= MAX_INPUT_LENGTH && password.length <= MAX_INPUT_LENGTH;
    }

    /**
     * Necessary method from IBtnEventHandler
     * @param time - Time in milliseconds after which debounced function should be called.
     */
    @Override
    public void debounceValidateInputs(int time){
        return;
    }

    /**
     * Handles the response that comes from backend API.
     * @param response - Response that we get after calling API Endpoint for user authentication
     */
    private void handleLoginSubmit(HttpResponse<String> response){
        if (response == null) return;

        String message;

        switch (response.statusCode()) {
            case 200:
                message = "Użytkownik zalogowany pomyślnie.";
                break;
            case 401:
                message = "Niepoprawne dane logowania.";
                break;
            default:
                message = "Coś poszło nie tak, prosimy spróbować później.";
                break;
        }

        SwingUtilities.invokeLater(() -> {
           super.handleSuccessfulAuthenticationResponse(loginFrame, message, response);
        });
    }
}
