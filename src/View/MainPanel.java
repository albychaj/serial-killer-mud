package View;

import java.io.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;

import Model.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Model.AddChatMessageCommand;

public class MainPanel extends JPanel
{
	private static final long serialVersionUID = 818711182821925316L;
	private JTextArea chatArea, commandArea; 
	private JTextField textField; // field where user enters text
	private JButton enterButton;
	
	private ObjectOutputStream output; // output stream to server
	private String clientName;
	
	public MainPanel(String clientName, ObjectOutputStream output)
	{
		this.output = output;
		this.clientName = clientName;
		
		this.setLayout(new BorderLayout());
		//background.setLayout(new BorderLayout());
		
		// create and add top panel of MudGUI
		this.add(createTopPanel(), BorderLayout.NORTH);
		
		// create and add center panel of MudGUI
		this.add(createCenterPanel(), BorderLayout.CENTER);
		
		// create and add bottom panel of MudGUI
		this.add(createBottomPanel(), BorderLayout.SOUTH);
	}
	
	/**
	 * 
	 * @return
	 */
	public JPanel createTopPanel()
	{
		JPanel top = new JPanel();
		
		JLabel banner = new JLabel("SAVE YO ASS", JLabel.CENTER);
		banner.setPreferredSize(new Dimension(1100, 60));
		banner.setForeground(Color.RED);
        banner.setOpaque(true);
        banner.setFont(getFont("fonts/cenobyte.ttf"));
        
        top.add(banner);
        
		return top;
	} // end of method createTopPanel
	
	/**
	 * Sets the fucking font
	 * 
	 * @param filename
	 * @return
	 */
	private static Font getFont(String filename)
	{
		Font font = null;
		
		try 
        {
			font = Font.createFont(Font.TRUETYPE_FONT, new File(filename));
		} 
        catch (Exception e) 
        {
			e.printStackTrace();
		}
		
		return font.deriveFont(50f);
	} // end of private method getFont
	
	/**
	 * 
	 * @return
	 */
	public JPanel createCenterPanel()
	{	
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1, 2, 10, 0));
		center.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		// create chat panel
		JPanel chatPanel = new JPanel();
		chatPanel.setLayout(new BorderLayout());
		chatPanel.setPreferredSize(new Dimension(550, 550));
		
		// initialize the text area and add it to the chat panel
		chatArea = new JTextArea();
		chatArea.setLineWrap(true);
		chatArea.setBackground(Color.BLACK);
		chatArea.setForeground(Color.WHITE);
		chatArea.setEditable(false);
		chatArea.setFont(getFont("fonts/trajan.ttf").deriveFont(12f));
		chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
		
		JPanel commandPanel = new JPanel();
		commandPanel.setLayout(new BorderLayout());
		commandPanel.setPreferredSize(new Dimension(550, 550));

		// initialize the text area and add it to the command panel
		commandArea = new JTextArea();
		commandArea.setLineWrap(true);
		commandArea.setBackground(Color.BLACK);
		commandArea.setForeground(Color.WHITE);
		commandArea.setEditable(false);
		commandArea.setFont(getFont("fonts/trajan.ttf").deriveFont(12f));
		commandPanel.add(new JScrollPane(commandArea), BorderLayout.CENTER);
		
		center.add(chatPanel);
		center.add(commandPanel);
		
		return center;
	} // end of method createCenterPanel
	
	/**
	 * 
	 * @return
	 */
	public JPanel createBottomPanel()
	{
		JPanel bottomPanel = new JPanel();
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(500, 40));
		textField.setBackground(Color.BLACK);
		textField.setForeground(Color.WHITE);
		textField.setFont(getFont("fonts/trajan.ttf").deriveFont(12f));
		
		enterButton = new JButton("Enter");
		enterButton.setOpaque(true);
		enterButton.setBorderPainted(false);
		//enterButton.setContentAreaFilled(false);
		enterButton.setBackground(Color.RED);
		enterButton.setForeground(Color.WHITE);
		enterButton.setFont(getFont("fonts/cenobyte.ttf").deriveFont(14f));
		enterButton.setPreferredSize(new Dimension(100, 40));
		
		// add button and field to the bottom panel
		bottomPanel.add(textField);
		bottomPanel.add(enterButton);
		
		// create a listener for writing messages to server
		ActionListener listener = new EnterListener();
		
		// attach listener to the text field and button
		textField.addActionListener(listener);
		enterButton.addActionListener(listener);
		
		return bottomPanel;
	} // end of method createBottomPanel
	
	private class EnterListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			String s = textField.getText().toUpperCase();
<<<<<<< HEAD
			String command = new String();
			String argument = new String();
			
			if (s.indexOf(" ") > 0)
			{
				String[] splitS = s.split(" ", 2);
				command = splitS[0];
				argument = splitS[1];
			}
			
			else
			{
				command = s;
=======
			Commands c;
			String argument;
			String command;
			if (s.indexOf(" ") > 0)
			{
			String[] splitS = s.split(" ", 2);
			command = splitS[0];
			argument = splitS[1];
			c = Commands.valueOf(command);
>>>>>>> 00a90af1ce64a91ccdf716eb117d4fcaf368d3ce
			}
			
			else
			{
				command = s;
				c = Commands.valueOf(command);
				argument = "";
				
			}
			
			try
			{
<<<<<<< HEAD
				switch(c)
				{
//				case MOVE:
//					output.writeObject();
//					break;
//				case LOOK:
//					output.writeObject();
//					break;
//				case GET:
//					output.writeObject();
//					break;
//				case DROP:
//					output.writeObject();
//					break;
//				case INVENTORY:
//					output.writeObject();
//					break;
//				case COMMANDS:
//					output.writeObject();
//					break;
//				case WHO:
//					output.writeObject();
//					break;
//				case SCORE:
//					output.writeObject();
//					break;
				case OOC:
					output.writeObject(new AddChatMessageCommand(clientName + ":  " + argument));
					break;
				case QUIT:
					output.writeObject(new DisconnectCommand(clientName));
					break;
//				case SHUTDOWN:
//					break;
//				default:
//					break;
				}
=======

				switch(c)
				{
				case SAY:
				case TELL:
				case OOC:
					output.writeObject(new AddChatMessageCommand(clientName + ":  " + argument));
					break;
				case COMMANDS:
				case WHO:
					output.writeObject(new PrintCommand(clientName, c));
					break;
				case SCORE:
				case GIVE:
				case GET:
				case INVENTORY:
				case DROP:
				case USE:
				case QUIT:
					output.writeObject(new DisconnectCommand(clientName));
					break;
				default:
					break;
				}

>>>>>>> 00a90af1ce64a91ccdf716eb117d4fcaf368d3ce
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			textField.setText("");
		}
	} // end of private class EnterListener
	
	/**
	 * Updates the chat log and command log. Called by UpdateClientCommands
	 * 
	 * @param chatMessages The current chat log
	 */
	public void update(List<String> chatMessages, List<String> commandMessages) 
	{
		String chat = "";
		for (String message: chatMessages)
			chat = chat + message + "\n";
		
		chatArea.setText(chat);
		chatArea.setCaretPosition(chat.length());
		
		String command = "";
		for (String message: commandMessages)
			command = command + message + "\n";
		
		commandArea.setText(command);
		commandArea.setCaretPosition(command.length());
		
		repaint();
	} // end of method update
	
	public void updateCommands(List<String> commandMessages)
	{
		String command = "";
		for (String message: commandMessages)
			command = command + message + "\n";
		
		commandArea.setText(command);
		commandArea.setCaretPosition(command.length());
		
		repaint();
	}
}
