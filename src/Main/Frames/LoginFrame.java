package Main.Frames;


import javax.swing.*;
import java.awt.*;

import Main.Database.User;
import Main.Frames.AbstractFrame;
import Main.Panels.Auth.LoginPanel;
import Main.Panels.Auth.RegisterPanel;



public class LoginFrame extends AbstractFrame {

    private  final String loginView = "LoginPanel";
    private  final String registerView = "RegisterPanel";
    CardLayout cl;
    JPanel containerPanel;


   // public LoginFrame(){}
    public LoginFrame(String title){
    super(title);
    cl = new CardLayout();

    containerPanel = new JPanel();
    containerPanel.setLayout(cl);
    containerPanel.add(new LoginPanel(this), loginView);

    containerPanel.add(new RegisterPanel(this), registerView);

    cl.show(containerPanel, loginView);


    //Set up the frame
    this.setLayout(new BorderLayout());
    this.add(containerPanel);
   //this.setResizable(false);
    this.setMinimumSize(new Dimension(250,250));
    this.setLocationRelativeTo(null); // Center the frame on the screen

    this.pack();


}

    /**
     * Switches to the LoginView, therefore to LoginPanel
     */
    public  void switchToLoginView(){
        cl.show(containerPanel, loginView);
    }
    /**
     * Switches to the RegisterView, therefore to RegisterPanel
     */
    public  void switchToRegisterView(){
        cl.show(containerPanel, registerView);
    }

    /**
     * Method that should be called when authentication is successful
     * Closes the LoginFrame and opens Main Application
     */
    public void handleSuccessfulAuthentication(){
        this.dispose();
    }



}
