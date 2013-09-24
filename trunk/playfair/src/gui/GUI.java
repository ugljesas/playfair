package gui;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import playfair.Playfair;
import playfair.PlayfairKey;
import playfair.breaking.SimulatedAnnealing;

public class GUI extends JFrame {

    private JTextArea decryptedArea;
    private JTextField keyArea;
    private JTextArea encryptedArea;
    private JTextArea brokenArea;
    private JLabel brokenLabel;
    private JButton encryptBtn;
    private JButton decryptBtn;
    private JButton breakBtn;
    private JTextField maxTempField;
    private JTextField tempStepField;
    private JTextField iterationsField;
    private final Font STD_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    private final Font BOLD_FONT = new Font(Font.MONOSPACED, Font.BOLD, 12);

    public GUI() {
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	 
    	
    	JPanel centerPanel = new JPanel(new FlowLayout());
    	
    	
        decryptedArea =new JTextArea("");
        // formatting
        decryptedArea.setFont(STD_FONT);
        decryptedArea.setColumns(30);
        decryptedArea.setRows(15);
        decryptedArea.setLineWrap(true);
        JScrollPane decryptedPane = new JScrollPane(decryptedArea);
        decryptedPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        //displays the polybus square created
        keyArea = new JTextField("Key");
        //more formatting...
        keyArea.setFont(STD_FONT);
        keyArea.setColumns(20);
        
        //holds *encrypted* text
        encryptedArea = new JTextArea("");
        //even more formatting
        encryptedArea.setFont(STD_FONT);
        encryptedArea.setColumns(30);
        encryptedArea.setLineWrap(true);
        encryptedArea.setRows(15);
        
        JScrollPane encryptedPane = new JScrollPane(encryptedArea);
        
        encryptedPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        centerPanel.add(decryptedPane);
        centerPanel.add(keyArea);
        centerPanel.add(encryptedPane);
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        //gets text from decryptedArea and encrypts it.
        //displays encrypted text in encryptedArea.
        encryptBtn = new JButton("ENCRYPT");
        encryptBtn.setFont(BOLD_FONT);
        //gets text from encryptedArea and decrypts it.
        //displays plaintext in decryptedArea.
        decryptBtn = new JButton("DECRYPT");
        decryptBtn.setFont(BOLD_FONT);
        encryptBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = keyArea.getText();
				Playfair playfair = new Playfair(key);
				
				String text = decryptedArea.getText();
				
				encryptedArea.setText(playfair.encrypt(text));
				
				
			}
		});
        decryptBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = keyArea.getText();
				Playfair playfair = new Playfair(key);
				
				String text = encryptedArea.getText();
				
				decryptedArea.setText(playfair.decrypt(text));
			}
		});
        
        bottomPanel.add(encryptBtn);
        bottomPanel.add(decryptBtn);
        
        maxTempField = new JTextField("10");
        maxTempField.setFont(STD_FONT);
        maxTempField.setColumns(7);
        
        JLabel maxTempLabel = new JLabel("maxTemp =");
        maxTempLabel.setLabelFor(maxTempField);
        
        tempStepField = new JTextField("0.1");
        tempStepField.setFont(STD_FONT);
        tempStepField.setColumns(7);
        
        JLabel tempStepLabel = new JLabel("tempStep =");
        tempStepLabel.setLabelFor(tempStepField);
        
        iterationsField = new JTextField("10000");
        iterationsField.setFont(STD_FONT);
        iterationsField.setColumns(7);
        
        JLabel iterationsLabel = new JLabel("iterations =");
        iterationsLabel.setLabelFor(iterationsField);
        
        breakBtn = new JButton("Break!");
        breakBtn.setFont(BOLD_FONT);
        breakBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					Playfair sol = new Playfair(keyArea.getText());
					
					double maxTemp = Double.parseDouble(maxTempField.getText());
					double step = Double.parseDouble(tempStepField.getText());
					int iterationsOnTemp = Integer.parseInt(iterationsField.getText());
					SimulatedAnnealing sa = new SimulatedAnnealing(maxTemp, step, iterationsOnTemp);
					String cipherText = encryptedArea.getText();
					
					System.out.println("Solution is on: " + sa.getFitness(cipherText, sol));
					
					PlayfairKey key = sa.findKey(cipherText);
					Playfair pf = new Playfair(key);
					String plainText = pf.decrypt(cipherText);
					brokenArea.setText(plainText);
					brokenLabel.setText("Maximum fitness found : " + sa.getFitness(cipherText, pf));
				}
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				}
				
			}
		});
        
        bottomPanel.add(maxTempLabel);
        bottomPanel.add(maxTempField);
        bottomPanel.add(tempStepLabel);
        bottomPanel.add(tempStepField);
        bottomPanel.add(iterationsLabel);
        bottomPanel.add(iterationsField);
        
        bottomPanel.add(breakBtn);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        JPanel bobottomPanel = new JPanel();
        bobottomPanel.setLayout(new BoxLayout(bobottomPanel,BoxLayout.Y_AXIS));
        brokenLabel = new JLabel(" ");

        brokenArea =new JTextArea("");
        //formatting
        brokenArea.setFont(STD_FONT);
        brokenArea.setColumns(30);
        //brokenArea.setMaximumSize(new Dimension(400,260));
        brokenArea.setRows(15);
        brokenArea.setLineWrap(true);
        JScrollPane brokenPane = new JScrollPane(brokenArea);
        brokenPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bottomPanel.add(brokenPane);
        bobottomPanel.add(brokenLabel);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel,BorderLayout.CENTER);
        mainPanel.add(bobottomPanel,BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
        pack();
    }
}

