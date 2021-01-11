package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

//
import Responses.Cuenta.Cuenta;
import Connection.Http;
import com.google.gson.Gson;
import Exceptions.RetiroInvalido;
import Request.*;
import Utils.DataBuilder;

public class MenuRetirar extends JFrame {
    
    private final int ancho, alto;
    
    private JPanel panelCentral;
    private JLabel LblWelcome;
    private JButton BtnUno, BtnDos, BtnTres, BtnCuatro, BtnCinco, BtnSeis;
    private Http http = Http.getInstance();
    private Gson gson = new Gson();
    private Cuenta cuenta;
    
    public MenuRetirar(Cuenta cuenta) {
        ancho = 700;
        alto = 255;
        this.cuenta = cuenta;
    }
    
    public void initComponents() {
        
        panelCentral = new JPanel();
        panelCentral.setSize(400, 205);
        panelCentral.setLocation((getWidth() - panelCentral.getWidth()) / 2, 5);
        panelCentral.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelCentral.setLayout(null);
        add(panelCentral);
        
        LblWelcome = new JLabel("Retiro de fondos");
        LblWelcome.setSize(getWidth(), 30);
        LblWelcome.setLocation((panelCentral.getWidth() - LblWelcome.getWidth()) / 2, 10);
        LblWelcome.setHorizontalAlignment(JLabel.CENTER);
        LblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
        panelCentral.add(LblWelcome);
        
        BtnUno = new JButton("$20.000");
        BtnUno.setSize(140, 30);
        BtnUno.setLocation(5, 60);
        BtnUno.setBackground(new Color(42, 205, 112));
        BtnUno.setForeground(Color.WHITE);
        BtnUno.setFocusable(false);
        add(BtnUno);
        
        BtnDos = new JButton("$50.000");
        BtnDos.setSize(140, 30);
        BtnDos.setLocation(5, 100);
        BtnDos.setBackground(new Color(42, 205, 112));
        BtnDos.setForeground(Color.WHITE);
        BtnDos.setFocusable(false);
        add(BtnDos);
        
        BtnTres = new JButton("$100.000");
        BtnTres.setSize(140, 30);
        BtnTres.setLocation(5, 140);
        BtnTres.setBackground(new Color(42, 205, 112));
        BtnTres.setForeground(Color.WHITE);
        BtnTres.setFocusable(false);
        add(BtnTres);
        
        BtnCuatro = new JButton("$200.000");
        BtnCuatro.setSize(125, 30);
        BtnCuatro.setLocation(5 + BtnTres.getWidth() + 5 + panelCentral.getWidth() + 5, 60);
        BtnCuatro.setBackground(new Color(42, 205, 112));
        BtnCuatro.setForeground(Color.WHITE);
        BtnCuatro.setFocusable(false);
        add(BtnCuatro);
        
        BtnSeis = new JButton("$500.000");
        BtnSeis.setSize(125, 30);
        BtnSeis.setLocation(5 + BtnTres.getWidth() + 5 + panelCentral.getWidth() + 5, 100);
        BtnSeis.setBackground(new Color(42, 205, 112));
        BtnSeis.setForeground(Color.WHITE);
        BtnSeis.setFocusable(false);
        add(BtnSeis);
        
        BtnCinco = new JButton("Otro Valor");
        BtnCinco.setSize(BtnCuatro.getWidth(), 30);
        BtnCinco.setBackground(new Color(42, 205, 112));
        BtnCinco.setForeground(Color.WHITE);
        BtnCinco.setLocation(getWidth() - BtnCinco.getWidth() - 20, 140);
        BtnCinco.setFocusable(false);
        add(BtnCinco);
    }
    
    private void sendRequest(int valor) throws RetiroInvalido {
        if (valor > cuenta.getData()[0].getSaldo()) {
            throw new RetiroInvalido("Saldo insuficiente");
        } else {
            RequestSaldo saldo = new RequestSaldo();
            saldo.setSaldo(cuenta.getData()[0].getSaldo() - valor);
            String data = gson.toJson(saldo);
            http.PUT("/account/edit/?id=" + cuenta.getData()[0].getIdCuenta(), data);
            String d = http.GET("/account/view/?id=" + cuenta.getData()[0].getIdCuenta());
            Cuenta nuevaCuenta = gson.fromJson(d, Cuenta.class);
            
            DataBuilder.CreateOPClient(cuenta, "'OPID_002'", "NOACC", String.valueOf(valor));
            
            JOptionPane.showMessageDialog(null, "Operación Finalizada su nuevo saldo es de: $" + nuevaCuenta.getData()[0].getSaldo() + " .");
            dispose();
        }
    }
    
    public void initListeners() {
        BtnUno.addActionListener((event) -> {
            try {
                sendRequest(20000);
            } catch (RetiroInvalido ex) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        BtnDos.addActionListener((event) -> {
            try {
                sendRequest(50000);
            } catch (RetiroInvalido ex) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        BtnTres.addActionListener((event) -> {
            try {
                sendRequest(100000);
            } catch (RetiroInvalido ex) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        BtnCuatro.addActionListener((event) -> {
            try {
                sendRequest(200000);
            } catch (RetiroInvalido ex) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        BtnSeis.addActionListener((event) -> {
            try {
                sendRequest(500000);
            } catch (RetiroInvalido ex) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        BtnCinco.addActionListener((event) -> {
            try {
                String res = JOptionPane.showInputDialog(null, "Ingrese la cantidad a retirar", "Otro", JOptionPane.INFORMATION_MESSAGE);
                if (res != null) {
                    int valor = Integer.parseInt(res);
                    sendRequest(valor);
                }
            } catch (RetiroInvalido ex) {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex2) {
                JOptionPane.showMessageDialog(null, "Valor invalido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
    }
    
    public void initTemplate() {
        setLayout(null);
        setTitle("ATM");
        setSize(new Dimension(ancho, alto));
        setLocationRelativeTo(null);
        
        initComponents();
        initListeners();
        
        this.getContentPane().setBackground(new Color(250, 249, 249));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
}
