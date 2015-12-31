package miltos.diploma;

import java.io.*;
import java.net.*;
import java.awt.event.WindowListener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.lang.Thread;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.Line;

public class RulesetCreator  extends Frame  implements ActionListener   {
	
/* Declare the GUI Components */
static JFrame frame;				// The frame that implements the app's GUI
static TextField rulesetName;		// The text field needed to type the exact ruleset name
static TextField specificRuleName;  // The text field needed to type the specific rule, of the selected ruleset, that will be inclued
static TextField excludeRule;		// The exact name of the rule that should be excluded
static TextField fileName;			// The text field for providing the file name of the ruleset.
static JTextArea console;			// A text area to print messages to the user
static JButton create;				// A button for creating a new ruleset.
static JButton save;				// A button for saving the ruleset.
static JButton add;					// A button for adding the new rule to the ruleset.
BufferedWriter writer;

	public RulesetCreator(String title){
		
		super(title);
		
		Color gray=new Color(240,240,240);			/* Setting the background color */
		setLayout(new FlowLayout());			/* Setting the layout of the GUI */
		setBackground(gray);					/* Setting the background color */
	//
		//addWindowListener(this);				/* Adding the certain window listener for reacting to the user's
//												   commands for closing and minimizing the window.
//													*/
		
		rulesetName = new TextField();
		specificRuleName = new TextField();
		excludeRule = new TextField();
		fileName = new TextField();
		console = new JTextArea(10,40);
		JScrollPane sp=new JScrollPane(console);
		sp.setVerticalScrollBarPolicy(
			    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		create = new JButton("Create");
		add = new JButton("Add Rule");
		save = new JButton("Save");
		
		rulesetName.setColumns(20);
		specificRuleName.setColumns(20);
		excludeRule.setColumns(20);
		fileName.setColumns(20);
		
		add(fileName);
		add(create);
		add(save);
		add(rulesetName);
		add(specificRuleName);
		add(excludeRule);
		add(add);
		add(console);
		
		create.addActionListener(this);
		save.addActionListener(this);
		add.addActionListener(this);
	}
	
	public static void main(String[] args) throws Exception{
		
		RulesetCreator rc = new RulesetCreator("PMD RuleSet Creator");
		
		rc.setSize(500,250);				  /* Setting the size of the frame */
		rc.setVisible(true);				  /* Making the frame visible to the user */
		
	}
	
		public void actionPerformed(ActionEvent e){
			
			System.out.println("Action Listener Invoked");
			
			if(e.getSource()==create){
				System.out.println("Source found -> Create Button Pressed");
				String tempFileName = fileName.getText();
				console.append("The requested file name is: " + tempFileName + "\n");
				System.out.println("The requested file name is: " + tempFileName);
			 try{
				 	 writer = new BufferedWriter(
		                new FileWriter(tempFileName));
				 	console.append("Ruleset successfully opened" + "\n");
				 	System.out.println("Ruleset successfully opened");
				 	
				 	/* Writing the header */
				 	writer.write("<?xml version=\"1.0\"?>");
				 	writer.newLine();
				 	writer.write("<ruleset name=\"myRuleset\""
				 			+ " xmlns=\"http://pmd.sourceforge.net/ruleset/2.0.0\""
				 			+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
				 			+ " xsi:schemaLocation=\"http://pmd.sourceforge.net/ruleset/2.0.0 http://pmd.sourceforge.net/ruleset_2_0_0.xsd\" >");
				 	writer.newLine();
			 }catch(IOException ex){
				    System.out.println(" Exception !!!");
				 	System.out.println(ex.getMessage());
			 }
			 	fileName.setText("");
			 	
			}else if(e.getSource()==add){
				System.out.println("Source found -> Add Button Pressed");
				
				/* Check the fields and add the appropriate entry */
				try{
					writer.newLine();
					
					if("".equals(specificRuleName.getText())){
						writer.write("<rule ref=\"rulesets/java/" + rulesetName.getText() + "\"/>");
						System.out.println("<rule ref=\"rulesets/java/" + rulesetName.getText() + "\"/>");
					}else{
						writer.write("<rule ref=\"rulesets/java/" + rulesetName.getText() + "/" + specificRuleName.getText() + "\"/>");
						System.out.println("<rule ref=\"rulesets/java/" + rulesetName.getText() + "/" + specificRuleName.getText() + "\"/>");
					}
						
					writer.newLine();
				}catch(IOException ex){
					System.out.println("IOException caught while adding a rule to the ruleset.");
					System.out.println(ex.getMessage());
				}
				
				/* Inform the user that his rule is succesfully added to the ruleset */
				console.append("Rule entry: " + rulesetName.getText() + "/" + specificRuleName.getText() + " successfully added! \n");
				
				/* Clear the fields to accept the new entry */
				specificRuleName.setText("");
				rulesetName.setText("");
				
				
				
			}else if(e.getSource()==save){
				System.out.println("Source found -> Save Button Pressed");
				
				console.append("The ruleset is saved!! Check it out!!" + "\n");
				try{
					//writer.newLine();
					writer.write("</ruleset>");
					writer.flush();
					writer.close();
				}catch(IOException ex){
					System.out.println(ex.getMessage());
				}
					
			}else{
					System.out.println("False Alarm!!");
				
			}
			System.out.println();
		}
		
	
  }


