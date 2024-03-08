package Main.Panels.Auth;

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
}
