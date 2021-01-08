package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {

    private JPanel panel;

    public Frame() {
        setTitle("ATM");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(500, 500);
        initComponents();
        initListeners();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initComponents() {

    }

    public void initListeners() {

    }

}
