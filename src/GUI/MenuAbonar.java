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
import Exceptions.OperacionInvalida;
import Models.Banco;
import Request.RequestSaldo;
import Utils.DataBuilder;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class MenuAbonar extends JFrame {

    private final int ancho, alto;
    private JPanel panelCentral;
    private JLabel LblWelcome, LblId, LblValor;
    private JTextField TxtFldNumero, TxtFldValor;
    private JButton BtnConfirmar;
    private Http http = Http.getInstance();
    private Gson gson = new Gson();
    private Cuenta cuentaAbonante;

    public MenuAbonar(Cuenta cuenta) {
        ancho = 700;
        alto = 255;
        this.cuentaAbonante = cuenta;
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

        TxtFldNumero = new JTextField();
        TxtFldNumero.setSize(170, 30);
        TxtFldNumero.setLocation(220, 65);
        TxtFldNumero.setHorizontalAlignment(JTextField.CENTER);
        TxtFldNumero.setFont(new Font("Arial", Font.BOLD, 15));
        panelCentral.add(TxtFldNumero);

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

    private void sendRequest(int valor) throws OperacionInvalida {
        if (cuentaAbonante.verificarRetiro(valor)) { // VERIFICAR QUE EL MONTO SEA VÁLIDO
            throw new OperacionInvalida("Saldo insuficiente");
        } else {
            RequestSaldo saldo = new RequestSaldo();
            saldo.setSaldo(cuentaAbonante.getData()[0].getSaldo() - valor);
            String data = gson.toJson(saldo);

            http.PUT("/account/edit/?id=" + cuentaAbonante.getData()[0].getIdCuenta(), data);
            String d = http.GET("/account/view/?id=" + cuentaAbonante.getData()[0].getIdCuenta());
            Cuenta nuevaCuenta = gson.fromJson(d, Cuenta.class);
            JOptionPane.showMessageDialog(null, "Operación Finalizada su nuevo saldo es de: $" + nuevaCuenta.getData()[0].getSaldo() + " .");
            dispose();
        }
    }

    public void initListeners() {
        BtnConfirmar.addActionListener((event) -> {
            try {
                Cuenta cuentaAbonar = Banco.getCuenta(TxtFldNumero.getText());
                if (Banco.verificarCuentaValida(Long.valueOf(TxtFldNumero.getText()), cuentaAbonante)) { // VERIFICAR VALIDEZ DE LA CUENTA
                    throw new OperacionInvalida("Mismo número de cuenta");
                } else {
                    if (cuentaAbonar.getStatus() == 404) {
                        throw new OperacionInvalida("Cuenta no valida");
                    }
                }

                sendRequest(Integer.parseInt(TxtFldValor.getText()));

                RequestSaldo saldo = new RequestSaldo();
                saldo.setSaldo(cuentaAbonar.getData()[0].getSaldo() + Integer.parseInt(TxtFldValor.getText()));

                String data = gson.toJson(saldo);
                http.PUT("/account/abonoCuenta/?id=" + Long.parseLong(TxtFldNumero.getText()), data);

                DataBuilder.CreateOPClient(cuentaAbonar, "'OPID_001'", "'" + cuentaAbonar.getData()[0].getIdCuenta() + "'", TxtFldValor.getText());

            } catch (OperacionInvalida ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Solo valores numéricos enteros", "Error", JOptionPane.ERROR_MESSAGE);
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
