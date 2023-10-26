package com.example.spotify;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Objects;

public class LoginController {
    public Button login;
    public PasswordField password;
    public TextField username;
    @FXML
    private Label textLogin;

    @FXML
    protected void onLoginClick() {
        if(Objects.equals(username.getText(), "admin")){
            if(Objects.equals(password.getText(), "1234")){
                textLogin.setText("Credenziali corrette");
            }
        }

    }

}