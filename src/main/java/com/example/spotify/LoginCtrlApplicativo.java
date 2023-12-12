package com.example.spotify;

public class LoginCtrlApplicativo {

    public boolean processLogin(String username, String password) {
        // Esempio di verifica delle credenziali rigida
        // Credenziali errate
        return username.equals("admin") && password.equals("1234"); // Credenziali corrette
    }
}