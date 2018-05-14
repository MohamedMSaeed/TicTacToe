/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author ASUS-
 */
public class UserConnection {
    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;
    int i;
    String parentPath;
    FileWriter fw;
    PrintWriter pw;
    BufferedReader br;
	
    //public static void main(String[] args){
	//UserConnection mc = new UserConnection();
	//mc.setSize(400,500);
	//mc.setVisible(true);
    //}
	
    public UserConnection(){
            i=1;
            parentPath = "Files";
		
            try{
                mySocket = new Socket("127.0.0.1",6060);
                dis = new DataInputStream (mySocket.getInputStream());
                ps = new PrintStream(mySocket.getOutputStream());
            }
            catch(IOException ex){
		ex.printStackTrace();
            }
		
		
            new Thread (new Runnable(){
                public void run(){
                    while(true){
			try{
                            String str = dis.readLine();
			}
			catch(IOException ex){
                            ex.printStackTrace();
                            try{
                                ps.close();
                                dis.close();
                                mySocket.close();
                                System.exit(0);
                            }
                            catch(IOException ex2){
                                ex2.printStackTrace();
                            }
                            break;
                        }
                    }
		}
            }).start();
    }
}
