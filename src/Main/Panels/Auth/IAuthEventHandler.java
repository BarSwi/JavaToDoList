package Main.Panels.Auth;

public interface IAuthEventHandler {
    void switchView();

    void handleSubmit();
   // boolean validateInputs();
    void debounceValidateInputs(int time);
    void handleEnterKeyInput();
}
