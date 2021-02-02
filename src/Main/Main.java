package Main;

import Models.Banco;
import Models.Atm;

public class Main {

    public static void main(String[] args) {
        Banco newBanco = new Banco();
        newBanco.setAtm(new Atm(newBanco));
    }

}
