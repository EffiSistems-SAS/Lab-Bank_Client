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
import Request.RequestSaldo;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class MenuAbonar extends JFrame {

    private final int ancho, alto;

    private JPanel panelCentral;
    private JLabel LblWelcome, LblId, LblValor;
    private JTextField TxtFldId, TxtFldValor;
    private JButton BtnConfirmar;
    private Http http = Http.getInstance();
    private Gson gson = new Gson();
    private Cuenta cuenta, cuentaAbono;

    public MenuAbonar(Cuenta cuenta) {
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

        LblWelcome = new JLabel("Abono de fondos");
        LblWelcome.setSize(getWidth(), 30);
        LblWelcome.setLocation((panelCentral.getWidth() - LblWelcome.getWidth()) / 2, 10);
        LblWelcome.setHorizontalAlignment(JLabel.CENTER);
        LblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
        panelCentral.add(LblWelcome);

        LblId = new JLabel("Número de cuenta a abonar:");
        LblId.setSize(200, 30);
        LblId.setLocation(5, 65);
        LblId.setHorizontalAlignment(JLabel.CENTER);
        LblId.setFont(new Font("Arial", Font.BOLD, 15));
        panelCentral.add(LblId);

        LblValor = new JLabel("Valor a abonar:");
        LblValor.setSize(200, 30);
        LblValor.setLocation(5, 120);
        LblValor.setHorizontalAlignment(JLabel.CENTER);
        LblValor.setFont(new Font("Arial", Font.BOLD, 15));
        panelCentral.add(LblValor);

        TxtFldId = new JTextField();
        TxtFldId.setSize(170, 30);
        TxtFldId.setLocation(220, 65);
        TxtFldId.setHorizontalAlignment(JTextField.CENTER);
        TxtFldId.setFont(new Font("Arial", Font.BOLD, 15));
        panelCentral.add(TxtFldId);

        TxtFldValor = new JTextField();
        TxtFldValor.setSize(170, 30);
        TxtFldValor.setLocation(220, 120);
        TxtFldValor.setHorizontalAlignment(JTextField.CENTER);
        TxtFldValor.setFont(new Font("Arial", Font.BOLD, 15));
        panelCentral.add(TxtFldValor);

        BtnConfirmar = new JButton("Confirmar");
        BtnConfirmar.setSize(140, 30);
        BtnConfirmar.setLocation((panelCentral.getWidth() - BtnConfirmar.getWidth()) / 2, 170);
        BtnConfirmar.setBackground(new Color(42, 205, 112));
        BtnConfirmar.setForeground(Color.WHITE);
        BtnConfirmar.setFocusable(false);
        panelCentral.add(BtnConfirmar);

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
            JOptionPane.showMessageDialog(null, "Operación Finalizada su nuevo saldo es de: $" + nuevaCuenta.getData()[0].getSaldo() + " .");
            dispose();
        }
    }

    public void initListeners() {
        BtnConfirmar.addActionListener((event) -> {
            try {

                if (Integer.parseInt(TxtFldId.getText()) == cuenta.getData()[0].getNumero()) {
                    throw new RetiroInvalido("Mismo número de cuenta");
                }

                sendRequest(Integer.parseInt(TxtFldValor.getText()));

                String respuesta = http.GET("/account/getNumCuenta/?id=" + Integer.parseInt(TxtFldId.getText()));
                Cuenta cuenta = gson.fromJson(respuesta, Cuenta.class);

                RequestSaldo saldo = new RequestSaldo();
                saldo.setSaldo(cuenta.getData()[0].getSaldo() + Integer.parseInt(TxtFldValor.getText()));

                String data = gson.toJson(saldo);
                http.PUT("/account/abonoCuenta/?id=" + Integer.parseInt(TxtFldId.getText()), data);

            } catch (RetiroInvalido ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
