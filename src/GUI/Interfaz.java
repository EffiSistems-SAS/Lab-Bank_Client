package GUI;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.time.LocalDateTime;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import Connection.Http;
import Responses.Cuenta.*;
import com.google.gson.Gson;

public class Interfaz extends JFrame {

    private final int ancho, alto;

    private JPanel panelCentral;
    private JLabel LblWelcome, LblHora = new JLabel();
    private JButton BtnAbonar, BtnRetirar, BtnConsultar, BtnQuit;
    private Http http = Http.getInstance();
    private Timer timer = new Timer(1000, (e) -> {
        LocalDateTime a = LocalDateTime.now();
        LblHora.setText("<html><body><center>Hora actual<br>" + a.getDayOfMonth() + " / " + a.getMonthValue() + " / " + a.getYear() + "<br>" + a.getHour() + " : " + a.getMinute() + " : " + a.getSecond() + "</center></body></html>");
        repaint();
    });
    private String id;
    private Gson gson = new Gson();

    public Interfaz(String id) {
        ancho = 700;
        alto = 255;
        timer.start();
        this.id = id;
    }

    public void initComponents() {

        panelCentral = new JPanel();
        panelCentral.setSize(400, 205);
        panelCentral.setLocation((getWidth() - panelCentral.getWidth()) / 2, 5);
        panelCentral.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelCentral.setLayout(null);
        add(panelCentral);

        LblWelcome = new JLabel("Bienvenido a su cuenta");
        LblWelcome.setSize(getWidth(), 30);
        LblWelcome.setLocation((panelCentral.getWidth() - LblWelcome.getWidth()) / 2, 10);
        LblWelcome.setHorizontalAlignment(JLabel.CENTER);
        LblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
        LblWelcome.setBackground(new Color(47, 53, 58));
        panelCentral.add(LblWelcome);

        LblHora.setSize(200, 100);
        LblHora.setLocation((panelCentral.getWidth() - LblHora.getWidth()) / 2, 40);
        LblHora.setHorizontalAlignment(JLabel.CENTER);
        LblHora.setFont(new Font("Arial", Font.BOLD, 17));
        LblHora.setBackground(new Color(47, 53, 58));
        panelCentral.add(LblHora);

        BtnAbonar = new JButton("Abonar a terceros");
        BtnAbonar.setSize(140, 30);
        BtnAbonar.setLocation(5, 60);
        BtnAbonar.setBackground(new Color(42, 205, 112));
        BtnAbonar.setForeground(Color.WHITE);
        BtnAbonar.setFocusable(false);
        add(BtnAbonar);

        BtnRetirar = new JButton("Retirar fondos");
        BtnRetirar.setSize(140, 30);
        BtnRetirar.setLocation(5, 130);
        BtnRetirar.setBackground(new Color(42, 205, 112));
        BtnRetirar.setForeground(Color.WHITE);
        BtnRetirar.setFocusable(false);
        add(BtnRetirar);

        BtnConsultar = new JButton("Consultar saldo");
        BtnConsultar.setSize(125, 30);
        BtnConsultar.setLocation(5 + BtnAbonar.getWidth() + 5 + panelCentral.getWidth() + 5, 60);
        BtnConsultar.setBackground(new Color(42, 205, 112));
        BtnConsultar.setForeground(Color.WHITE);
        BtnConsultar.setFocusable(false);
        add(BtnConsultar);

        BtnQuit = new JButton("Cerrar sesión");
        BtnQuit.setSize(BtnConsultar.getWidth(), 30);
        BtnQuit.setBackground(new Color(42, 205, 112));
        BtnQuit.setForeground(Color.WHITE);
        BtnQuit.setLocation(getWidth() - BtnQuit.getWidth() - 20, 130);
        BtnQuit.setFocusable(false);
        add(BtnQuit);
    }

    public void initTemplate() {
        setLayout(null);
        setTitle("ATM");
        setSize(new Dimension(ancho, alto));
        setLocationRelativeTo(null);

        initComponents();
        initListeners();

        this.getContentPane().setBackground(new Color(250, 249, 249));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void initListeners() {
        BtnAbonar.addActionListener(ae -> {
            String respuesta = http.GET("/account/get/?id=" + id);
            Cuenta cuenta = gson.fromJson(respuesta, Cuenta.class);

            MenuAbonar menu = new MenuAbonar(cuenta);
            menu.initTemplate();
        });

        BtnRetirar.addActionListener(ae -> {
            String respuesta = http.GET("/account/get/?id=" + id);
            Cuenta cuenta = gson.fromJson(respuesta, Cuenta.class);

            MenuRetirar menu = new MenuRetirar(cuenta);
            menu.initTemplate();

        });

        BtnConsultar.addActionListener(ae -> {
            String respuesta = http.GET("/account/get/?id=" + id);
            Cuenta cuenta = gson.fromJson(respuesta, Cuenta.class);
            JOptionPane.showMessageDialog(this, "Su saldo es de $" + cuenta.getData()[0].getSaldo());

        });
        BtnQuit.addActionListener(ae -> {
            dispose();
            Login newLogin = new Login();
            newLogin.initTemplate();
        });
    }

}
