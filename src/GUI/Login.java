package GUI;

import javax.swing.JFrame;
//
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
//
import Connection.Http;
import Exceptions.TarjetaVencida;
import Models.Banco;
import Models.Atm;
import Request.RequestTarjeta;
import Responses.Cuenta.Cuenta;
import com.google.gson.Gson;
//
import Utils.DataBuilder;
import java.awt.Color;
import javax.swing.JOptionPane;

public class Login extends JFrame {

    private final int ancho, alto;

    private JLabel LblId, LblPass;
    private JTextField TxtFldId;
    private JPasswordField TxtFldPass;
    private JButton BtnLogin;

    private Atm atm;
    private Banco banco;
    private Http http = Http.getInstance();
    private Gson gson = new Gson();
    private static Login login;

    public static Login getLogin() {
        login.resetValues();
        return login;
    }

    public static Login getLogin(Atm atm, Banco banco) {
        if (login == null) {
            login = new Login(atm, banco);
        }
        return login;
    }

    private Login(Atm atm, Banco banco) {
        this.banco = banco;
        this.atm = atm;
        ancho = 250;
        alto = 155;
    }

    public String cambioContra(char[] pass) {
        String res = "";
        for (char e : pass) {
            res += e;
        }
        return res;
    }

    public void resetValues() {
        LblId.setVisible(true);
        TxtFldId.setVisible(true);
        TxtFldId.setText("");
        LblPass.setVisible(false);
        TxtFldPass.setVisible(false);
        TxtFldPass.setText("");
        BtnLogin.setText("Confirmar");
    }

    public void initComponents() {
        LblId = new JLabel("ID: ");
        LblId.setSize(70, 30);
        LblId.setLocation(5, 20);
        add(LblId);
        LblId.setVisible(true);

        LblPass = new JLabel("Password: ");
        LblPass.setSize(70, 30);
        LblPass.setLocation(5, 20);
        add(LblPass);
        LblPass.setVisible(false);

        TxtFldId = new JTextField();
        TxtFldId.setSize(150, 30);
        TxtFldId.setLocation(75, 20);
        add(TxtFldId);
        TxtFldId.setVisible(true);

        TxtFldPass = new JPasswordField();
        TxtFldPass.setSize(150, 30);
        TxtFldPass.setLocation(75, 20);
        add(TxtFldPass);
        TxtFldPass.setVisible(false);

        BtnLogin = new JButton("Confirmar");
        BtnLogin.setSize(100, 30);
        BtnLogin.setLocation((this.getWidth() - BtnLogin.getWidth()) / 2, 75);
        BtnLogin.setBackground(new Color(42, 205, 112));
        BtnLogin.setForeground(Color.WHITE);
        BtnLogin.setFocusable(false);
        add(BtnLogin);
    }

    public void initTemplate() {
        setLayout(null);
        setTitle("ATM");
        setSize(new Dimension(ancho, alto));
        setLocationRelativeTo(null);

        initComponents();
        initListeners();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        setVisible(true);
    }

    public void initListeners() {
        BtnLogin.addActionListener((ae) -> {
            if (BtnLogin.getActionCommand().equals("Confirmar")) {
                try {
                    if (Banco.verificarVeracidadTarjeta(atm.getTarjeta(), TxtFldId.getText())) { // VALIDAR VERACIDAD DE LA TARJETA
                        if (!atm.verificarNumeroIntentos(TxtFldId.getText())) {   // VERIFICAR NÚMERO DE INTENTOS MENOR A 3 POR TARJETA
                            TxtFldId.setVisible(false);
                            LblId.setVisible(false);
                            LblPass.setVisible(true);
                            TxtFldPass.setVisible(true);
                            BtnLogin.setText("Login");
                        } else {
                            JOptionPane.showMessageDialog(null, "Número de intentos excedido", "Error", JOptionPane.ERROR_MESSAGE);
                            TxtFldId.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "ID no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
                        TxtFldId.setText("");
                    }
                } catch (TarjetaVencida ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    TxtFldId.setText("");
                }
            } else if (BtnLogin.getActionCommand().equals("Login")) {
                if (Banco.validarIdentidadUsuario(atm.getTarjeta(), cambioContra(TxtFldPass.getPassword()), TxtFldId.getText())) { // VALIDAR IDENTIDAD DEL USUARIO
                    setVisible(false);
                    Interfaz interfaz = Interfaz.getInterfaz(TxtFldId.getText(), atm);
                    String respuesta = http.GET("/account/get/?id=" + TxtFldId.getText());
                    Cuenta cuenta = gson.fromJson(respuesta, Cuenta.class);
                    DataBuilder.CreateOPClient(cuenta, "'OPID_010'", "NOACC", "NOVALUE");
                } else {
                    if (atm.verificarNumeroIntentos(TxtFldId.getText())) { // VERIFICAR NÚMERO DE INTENTOS MENOR A 3 POR TARJETA 
                        JOptionPane.showMessageDialog(null, "Número de intentos excedido", "Error", JOptionPane.ERROR_MESSAGE);
                        TxtFldId.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Password incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                        TxtFldPass.setText("");
                        RequestTarjeta reqTar = new RequestTarjeta();
                        atm.getTarjeta().bringData(TxtFldId.getText());
                        reqTar.setNumeroIntentos(atm.getTarjeta().getData()[0].getNumeroIntentos() + 1);
                        String data = gson.toJson(reqTar);
                        http.PUT("/card/wrong-access/?id=" + (TxtFldId.getText()), data);
                        if (reqTar.getNumeroIntentos() == 3) {
                            JOptionPane.showMessageDialog(null, "Número de intentos excedido", "Error", JOptionPane.ERROR_MESSAGE);
                            resetValues();
                        }
                    }
                }
            }
        }
        );
    }
}
