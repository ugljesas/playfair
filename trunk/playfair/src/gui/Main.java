package gui;

public class Main {

	
    public static void main(String[] args) {
        // TODO code application logic here
        GUI gui = new GUI();
        gui.setSize(700, 600);
        
        gui.setResizable(false);
        gui.setLocationRelativeTo(null);
        gui.setTitle("Playfair");
        gui.setVisible(true);
    }
}


