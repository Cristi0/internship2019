package UserInterface;

import Model.ATM;
import Service.Controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

public class UI {
    Controller controller;

    public UI() {
        //introducerea datelor din problema care sunt variabile: timpul limita si catitatea de bani care trebuie scoasa
        this.controller = new Controller(LocalDateTime.parse("2019-03-19T14:00"), BigDecimal.valueOf(7500));
    }
    public void run(){
        show();
    }

    //afisam atm-urile prin care mergem
    public void show() {
        for(ATM a : controller.getAtmsRoute()){
            System.out.println(a);
        }

    }
}
