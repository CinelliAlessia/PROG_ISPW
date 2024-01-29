package model;

import java.util.ArrayList;

public class Guest extends User {

    public Guest() {
        // Chiamata al costruttore della classe base User senza fornire alcun dato aggiuntivo
        super("", "", "", new ArrayList<>());
        supervisor = false;
        registered = false;
    }
}
