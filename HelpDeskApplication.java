package com.kbs.helpdesk;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.lang.Math;
//Packages used for parsing XML Data from external File
import org.xml.sax.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
/*
 * Author: Abdikarim Abuker Hassan
 * Student ID: 21304298  
 * Module: Knowledge-based Systems  
 */
public class HelpDeskApplication extends JFrame implements KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Jpanel setup and it is being populated with sub-classes.
	JPanel jPanel=new JPanel();
	JTextArea chatArea=new JTextArea(20,50);
	JTextArea customerTextArea=new JTextArea(2,50);
	JScrollPane scrollFunctionallity=new JScrollPane(
		chatArea,
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
	);
	//String array in order to provide data for the application.
	String[][] kbsChatBot={
		//standard greetings
		{"hi","hello","hey"},
		{"Great", "hi","hello","hey"},
		//question greetings
		{"how are you","how r you","how r u","how are u"},
		{"good Thanks","doing well Thanks","Feeling Good Thanks"},
		//yes
		{"yes","Yea","Well"},
		{"no","NOT POSSIBLE","NO!"},
		//default
		{"I have no Information about this query","Sorry I couldn't Understand","no idea about this","Please rephrase",
		"(System is unavailable, due to LOL)"}
	};
	
	
	// Getter Method of Document interface for xml parsing .
	private static Document getDocument(String answerDocument) {
		try {
			//Using Factory Method of Document Builder Class to perform operations
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();// Setting up new Instances
			factory.setIgnoringComments(true); //Ignoring any Comments
			factory.setIgnoringElementContentWhitespace(true); //Ignoring White Spaces
			factory.setValidating(true); // Ignoring any possible XML Validation Errors
			
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			//Using Document Builder parse method to get the XML File name
			return docBuilder.parse(new InputSource(answerDocument));
		
		} catch (Exception e) { // Catching any possible Exception.
			//Raising Errors as Stings for debugging
			System.out.println(e.getMessage());
		}
		
		return null;
	}
// Application UI building is done in the constructor so when it runs everthing shows up.
	public HelpDeskApplication(){
		super("Help Desk - KBS Application");
		setSize(600,400);
		setLocation(400, 200);
		setResizable(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		chatArea.setEditable(false);
		customerTextArea.addKeyListener(this);
		
		
		jPanel.add(scrollFunctionallity);
		jPanel.add(customerTextArea);
		jPanel.setBackground(new Color(100,200,50));
		add(jPanel);
		//jPanel.setFont("Aerial"));
		
		setVisible(true);
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			customerTextArea.setEditable(false);
			//Takes the question asked and stores it into string
			String query=customerTextArea.getText();
			customerTextArea.setText("");
			addText("-->Customer:\t"+query);
			query.trim();
			while(
				query.charAt(query.length()-1)=='!' ||
				query.charAt(query.length()-1)=='.' ||
				query.charAt(query.length()-1)=='?'
			){
				query=query.substring(0,query.length()-1);
			}
			query.trim();
			byte reply=0;
			/*
			0:is used for searching through kbsChatBot[][] Multi-dimension array for matches
			1:is used for in case when it didn't find anything
			2:is used for if found anything
			*/
			//-----check for matches----
			int counter=0;//which group to check
			while(reply==0){
				if(inArray(query.toLowerCase(),kbsChatBot[counter*2])){
					reply=2;
					int w=(int)Math.floor(Math.random()*kbsChatBot[(counter*2)+1].length);
					addText("\n-->System\t"+kbsChatBot[(counter*2)+1][w]);
				}
				counter++;
				if(counter*2==kbsChatBot.length-1 && reply==0){
					reply=1;
				}
			}
			
			//default
			if(reply==1){
				int q=(int)Math.floor(Math.random() * kbsChatBot[kbsChatBot.length-1].length);
				addText("\n-->System\t"+ kbsChatBot[kbsChatBot.length-1][q]);
			}
			addText("\n");
		}
	}
	// Key listener for customer's text field.
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			customerTextArea.setEditable(true);
		}
	}
	//Key typed event listener
	public void keyTyped(KeyEvent e){}
	//Add ing text Sting array
	public void addText(String inputText){
		chatArea.setText(chatArea.getText()+inputText);
	}
	//Searching in Sting array for possible match for the question being asked by the user
	public boolean inArray(String textInput,String[] arrayText){
		boolean matchFinder=false;
		for(int i=0;i<arrayText.length;i++){
			if(arrayText[i].equals(textInput)){
				matchFinder=true;
			}
		}
		return matchFinder; // if found any match then return
	}
}