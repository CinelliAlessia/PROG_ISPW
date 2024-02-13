package view.view2;

import engineering.bean.*;

public class HomePageCLI<T extends ClientBean>{
    private T clientBean;

    public void setClientBean(T clientBean) {
        this.clientBean = clientBean;
    }

    public void start() {
        System.out.println("Benvenuto nella Home Page: Siamo ancora in fase di sviluppo");
    }
}
