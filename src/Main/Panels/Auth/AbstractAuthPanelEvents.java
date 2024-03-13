package Main.Panels.Auth;

import Main.Database.User;
import Main.Frames.LoginFrame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.swing.*;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


public abstract class AbstractAuthPanelEvents {

    /**
     * Creates an authentication request body based on the provided AuthPanel.
     *
     * @param authPanel The AuthPanel instance.
     * @return A Map representing the authentication request body.
     * @throws IllegalArgumentException If the provided AuthPanel is null, or if it lacks login or password fields.
     */
    protected Map<String, String> createAuthRequestBody(AbstractAuthPanel authPanel) throws IllegalArgumentException{

        if(authPanel==null) throw new IllegalArgumentException("Obiekt nie może być pusty");
        if(authPanel.getLoginInput() == null || authPanel.getPasswordInput()==null) throw new IllegalArgumentException("Panel nie posiada pola loginu lub hasła");
        String username = authPanel.getLoginInput().getText();
        char[] password = authPanel.getPasswordInput().getPassword();
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", new String(password));
        return body;
    }

    protected void handleSuccessfulAuthenticationResponse(LoginFrame loginFrame, String message, HttpResponse<String> response){
        if(response.statusCode()==200){
            User user = User.getInstance();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            user.setUsername(jsonObject.get("username").getAsString());
            loginFrame.handleSuccessfulAuthentication();
        }

            JOptionPane.showMessageDialog(loginFrame, message);
    }


    /**
     * Toggles the enabled state of the provided JButton with a delay.
     * The button is disabled for a specified duration and then re-enabled.
     *
     * @param btn The JButton to toggle the enabled state.
     */
    protected void toggleSubmitBtnEnabled(JButton btn){
        SwingUtilities.invokeLater(()->{
            btn.setEnabled(false);
            Timer enableBtn = new Timer(2000, e -> {
                btn.setEnabled(true);
                ((Timer) e.getSource()).stop();
            });
            enableBtn.start();
        });
    }



}
