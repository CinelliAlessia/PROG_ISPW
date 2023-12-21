package controllerApplicativo;

import engineering.bean.LoginBean;

public class LoginCtrlApplicativo {
    // implemento la logica dello use case
    public boolean verificaCredenziali(LoginBean bean) {
        // Esempio di verifica delle credenziali rigida
        // Credenziali errate
        return bean.getEmail().equals("admin@gmail.com") && bean.getPassword().equals("1234"); // Credenziali corrette
    }


}