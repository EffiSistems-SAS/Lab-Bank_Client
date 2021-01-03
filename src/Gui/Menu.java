package Gui;

import javax.swing.JFrame;
//
import java.awt.Dimension;

public class Menu extends JFrame{
    
    private final int ancho,alto;
    
    public Menu(){
        ancho = 800;
        alto = 600;
        
    }
    
    public void initComponents(){
    }
    
    
    public void initTemplate(){
        setLayout(null);
        setTitle("ATM");
        setSize(new Dimension(ancho,alto));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
    public void initListeners(){
    }
    
}