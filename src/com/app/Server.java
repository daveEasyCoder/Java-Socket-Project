package com.app;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Server implements Runnable{

	static DataInputStream inputData;
	static DataOutputStream outputData;
	static ServerSocket serversocket;
	static Socket socket;
	
	static JTextArea area2;
	static  JTextField field2;
	
	public static void main(String[] args) {
	       JFrame frame = new JFrame("Server");
	         frame.setSize(320,500);
	         frame.setLayout(null);
	         
	         
	         JLabel label2 = new JLabel("Chat App");
	         label2.setBounds(10,15,100,30);
	         label2.setFont(new Font("Serif", Font.BOLD, 20));
	         
	         area2 = new JTextArea();
	         area2.setBounds(10,50,270,340);
	         
	         
	          field2 = new JTextField("Type Message");
	         field2.setBounds(10,400,195,30);
	         
	         JButton btn2 = new JButton("Send");
	         btn2.setBounds(209,400,70,28);
	         
	         
	         btn2.addActionListener(new ActionListener() {
	             @Override
	             public void actionPerformed(ActionEvent e) {
	                 String msg = field2.getText();
	                 try {
	                	
	                			outputData.writeUTF(msg);
	                			outputData.flush();
	    						field2.setText("");
	    						
					
					} catch (IOException e1) {
						e1.printStackTrace();
					}
	             }
	         });
	         
	         frame.add(area2);
	         frame.add(label2);
	         frame.add(field2);
	         frame.add(btn2);
	         frame.setVisible(true);
	
	         
	         try {
	        	  int port = 6880;
		          serversocket = new ServerSocket(port);
		          socket = serversocket.accept();
		         
		         inputData = new DataInputStream(socket.getInputStream());
		         outputData = new DataOutputStream(socket.getOutputStream());
		         
		         
		         Thread chat = new Thread(new Server());
		        
		         chat.start();
	         }catch(Exception e) {
	        	 e.printStackTrace();
	        
	         }
	       
	        
	       
	        
	         
	    
	         
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		 try {
			 while(true) {
				 String data = inputData.readUTF();
			     SwingUtilities.invokeLater(() -> {
			  
			
                    // Update the UI on the Event Dispatch Thread
			    	 area2.append("Dawit: " + data + "\n");
                        try {
							outputData.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                });
			 }
		 }catch(Exception e){
			 e.printStackTrace();
			 
		 }
		 
			 
		
		
	}
	   // Method to close the connection and cleanup resources


}
