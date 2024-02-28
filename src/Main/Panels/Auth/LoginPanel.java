package Main.Panels.Auth;

import Main.Frames.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginPanel extends AbstractAuthPanel {

    private final int GRID_LAYOUT_ROWS = 2;
    private final int GRID_LAYOUT_COLS = 1;
    private IBtnEventHandler eventHandler;

    //private final LoginFrame loginFrame;


    //Constructor requires the frame on which the methods should be executed
    public LoginPanel(LoginFrame loginFrame){


        eventHandler = new LoginPanelEvents(loginFrame);
       // this.loginFrame = loginFrame;
        createPanel();
    }

    //Constructor with custom inputs size
    public LoginPanel(Dimension dimension, LoginFrame loginFrame){
        super(dimension);
        //this.loginFrame = loginFrame;
        eventHandler = new LoginPanelEvents(loginFrame);
        createPanel();
    }

    //Constructor for hypothetical case when someone wanted to use different event handler
    public LoginPanel(IBtnEventHandler eventHandler){
       // this.loginFrame=loginFrame;
        this.eventHandler = eventHandler;
        createPanel();
    }

    private void createPanel(){
        // UI creation logic
        this.setLayout(new GridLayout(GRID_LAYOUT_ROWS, GRID_LAYOUT_COLS));
        this.add(createInputPanel());
        this.add(createBtnPanel());
    }

    private JPanel createInputPanel(){
        //Create Panel with inputs from AbstractAuthPanel function
        loginInput = createTextField("Login");
        passwordInput = createPasswordField("Hasło");

        //ArrayList<JComponent> inputList = new ArrayList<>(Arrays.asList(loginInput,passwordInput));
       // LayoutManager layout = new GridLayout(inputList.size(), 1);

        return super.createPanel(new GridLayout(2, 1), loginInput, passwordInput);
    }
    private JPanel createBtnPanel(){
        submitBtn = new JButton("Zaloguj się");

        //Switch the LoginFrame view to register panel
        switchFormBtn = createBtn("Zarejestruj się", e -> eventHandler.switchView());



        continueAsGuestBtn = new JButton("Kontynuuj jako gość");

        //Create Panel with buttons from AbstractAuthPanel function
        //ArrayList<JComponent> btnList = new ArrayList<>(Arrays.asList(submitBtn,switchFormBtn,continueAsGuestBtn));
        //LayoutManager layout = new FlowLayout(FlowLayout.CENTER,5,5);

        return super.createPanel(new FlowLayout(FlowLayout.CENTER,5,5), submitBtn, switchFormBtn, continueAsGuestBtn);
    }
}
