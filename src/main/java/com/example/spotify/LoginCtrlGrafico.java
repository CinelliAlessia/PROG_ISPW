package com.example.spotify;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Objects;

public class LoginCtrlGrafico {
    public Button login;
    public PasswordField password;
    public TextField username;
    @FXML
    private Label textLogin;

    @FXML
    protected void onLoginClick() {
        String user = username.getText();
        String pass = password.getText();
        LoginCtrlApplicativo loginCtrlApp = new LoginCtrlApplicativo();
        if (loginCtrlApp.processLogin(user,pass)) {
            textLogin.setText("Credenziali corrette");
        } else {
            textLogin.setText("Credenziali errate");
        }
    }

}