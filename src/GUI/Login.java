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
import GUI.Interfaz;
import com.google.gson.Gson;
//
import Responses.Tarjeta.*;
import java.awt.Color;
import javax.swing.JOptionPane;

public class Login extends JFrame {

    private final int ancho, alto;

    private JLabel LblId, LblPass;
    private JTextField TxtFldId;
    private JPasswordField TxtFldPass;
    private JButton BtnLogin;

    private Http http = Http.getInstance();
    private Tarjeta tarjeta = null;

    public Login() {
        ancho = 250;
        alto = 155;
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

    public String cambioContra(char[] pass) {

        String res = "";
        for (char e : pass) {
            res += e;
        }

        return res;
    }

    public void initListeners() {
        BtnLogin.addActionListener((ae) -> {
            if (BtnLogin.getActionCommand().equals("Confirmar")) {
                String res = http.GET("/card/view/?id=" + TxtFldId.getText());
                Gson gson = new Gson();
                tarjeta = gson.fromJson(res, Tarjeta.class);
                if (tarjeta.getStatus() == 200) {
                    TxtFldId.setVisible(false);
                    LblId.setVisible(false);
                    LblPass.setVisible(true);
                    TxtFldPass.setVisible(true);
                    BtnLogin.setText("Login");
                } else {
                    JOptionPane.showMessageDialog(null, "ID no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (BtnLogin.getActionCommand().equals("Login")) {
                if (tarjeta.getData()[0].getContraseña().equals(cambioContra(TxtFldPass.getPassword()))) {
                    dispose();
                    Interfaz interfaz = new Interfaz(TxtFldId.getText());
                    interfaz.initTemplate();
                } else {
                    JOptionPane.showMessageDialog(null, "Password incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
