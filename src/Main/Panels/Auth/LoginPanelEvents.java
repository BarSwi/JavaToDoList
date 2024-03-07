package Main.Panels.Auth;

import Main.Frames.LoginFrame;
import Main.Utilities.requestExecutor;

import javax.swing.*;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


//*Class created only to provide implementations for BtnHandlers on AuthPanel*/
public class LoginPanelEvents implements IBtnEventHandler {
    private final String API_ENDPOINT = "http://localhost:8080/api/authentication";

    private final int MAX_INPUT_LENGTH = 255;

    private final LoginFrame loginFrame;
    private final LoginPanel loginPanel;
    private JButton submitBtn;
    LoginPanelEvents(LoginFrame loginFrame, LoginPanel loginPanel){
        this.loginFrame = loginFrame;
        this.loginPanel = loginPanel;
    }
    @Override
    public void switchView(){
        loginFrame.switchToRegisterView();
    }

    @Override
    public void handleSubmit() {
        if(validateInputs()){
            new Thread(() -> {
                String username = loginPanel.getLoginInput().getText();
                char[] password = loginPanel.getPasswordInput().getPassword();

                Map<String, String> body = new HashMap<>();
                body.put("username", username);
                body.put("password", new String(password));

                HttpResponse<String> response = requestExecutor.sendPostRequest(API_ENDPOINT, body);

                handleLoginSubmit(response);
            }).start();

            //TODO: Send API request to authenticate the user, and put that into User class
        }
    }

    @Override
    public boolean validateInputs() {
        String username = loginPanel.getLoginInput().getText();
        char[] password = loginPanel.getPasswordInput().getPassword();

        return username.length() <= MAX_INPUT_LENGTH && password.length <= MAX_INPUT_LENGTH;
    }
    @Override
    public void debounceValidateInputs(int time){
        return;
    }

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
            JOptionPane.showMessageDialog(loginFrame, message);
        });
    }
}
