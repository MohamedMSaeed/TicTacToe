package Server;

//package server;

import java.net.Socket ;
import java.io.IOException ;
import java.io.PrintStream ;
import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Vector;
import java.lang.Thread ;
import javafx.scene.control.Button;
import tictactoe.User;

public class ChatHandler extends Thread{
    public static int coun = 0;
	DataInputStream dis;
	PrintStream ps;
        User player = new User();
        ObjectInputStream ois;
        ObjectOutputStream ous;
	static Vector<ChatHandler> clientsVector = new Vector<ChatHandler>();
	public ChatHandler(Socket so){
		try{
			dis = new DataInputStream(so.getInputStream());
			ps = new PrintStream(so.getOutputStream());
                        //ois = new ObjectInputStream(so.getInputStream());
                        //ous = new ObjectOutputStream(so.getOutputStream());
                        //player = p;
			clientsVector.add(this);
			start();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		
	}
	public void run(){
		while(true){
			try{
                            //User player = new User();
				String msg = dis.readLine();
                                //System.out.println("server: "+msg);
                                String[] check = msg.split(":");
                                if(check[0].equals("connect")){
                                    player.serchByEmail(check[1]);
				//sendMessageToAll();
                                } else if (check[0].equals("request")){
                                   searchForOppenent(check[1],check[2],check[3]);
                                   //ps.println(check[1]);
                                }else if (check[0].equals("move")){
                                    sendMoveToOppenent(check[1],check[2],check[3]);
                                }else if (check[0].equals("moveRes")){
                                    sendMoveToOppenentRes(check[1],check[2],check[3]);
                                }else if (check[0].equals("play")){
                                    if (check[1].equals("ok")){
                                        sendOkToOppenent(check[3],check[2]);
                                    } else if (check[1].equals("cancel")){
                                        
                                    } 
                                }else if (check[0].equals("left")){
                                    oppoLeft(check[2],check[1]);
                                }else if (check[0].equals("resume")){
                                    oppoResume(check[1],check[2],check[3],check[4]);
                                }
                                
			}
			catch(IOException ex){
				try{
					ps.close();
					dis.close();
					clientsVector.remove(this);
				}
				catch(IOException ex2){
					ex2.printStackTrace();
				}
				ex.printStackTrace();
				break;
                                
			}
		}
	}
	 void searchForOppenent(String msg, String me ,String XO){
           
		for(ChatHandler ch : clientsVector){
                    if(ch.player.getEmail().equals(msg))
			ch.ps.println("playerRequest:"+msg+":"+me+":"+XO);
		}
	}
        void sendMoveToOppenent(String msg, String me ,String button){
           
		for(ChatHandler ch : clientsVector){
                    if(ch.player.getEmail().equals(msg))
			ch.ps.println("moveIt:"+msg+":"+me+":"+button);
		}
	}
        void sendMoveToOppenentRes(String msg, String me ,String button){
           
		for(ChatHandler ch : clientsVector){
                    if(ch.player.getEmail().equals(msg))
			ch.ps.println("moveItRes:"+msg+":"+me+":"+button);
		}
	}
        void sendOkToOppenent(String msg, String me){
           
		for(ChatHandler ch : clientsVector){
                    if(ch.player.getEmail().equals(msg))
			ch.ps.println("ok:"+me);
		}
	}
        void oppoResume(String msg,String me,String GID,String XO){
		for(ChatHandler ch : clientsVector){
                    if(ch.player.getEmail().equals(msg))
			ch.ps.println("resume:"+me+":"+GID+":"+XO);
		}
	}
        void oppoLeft(String msg,String me){
		for(ChatHandler ch : clientsVector){
                    if(ch.player.getEmail().equals(msg))
			ch.ps.println("left:"+me);
		}
	}
        
		
}