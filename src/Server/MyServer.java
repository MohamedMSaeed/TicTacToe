package Server;

//package server;

//import server.*;
import java.net.ServerSocket;
import java.net.Socket ;
import java.io.IOException ;
import java.util.logging.Level;
import java.util.logging.Logger;
import tictactoe.User;

 

public class MyServer extends Thread{
    ServerSocket serverSocket;
    boolean started,resumed;
    public MyServer(){
        try {
            serverSocket = new ServerSocket(6060);
        } catch (IOException ex) {
            //Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        started=false;
    }
  
    
    public boolean startServer(){
        if(!started){
        start();
        started=true;
        return true;
        }else if(!resumed){
            resumed=true;
            this.resume();
            return true;
        }else{
            return false;
        }
    }
    
    public void run(){
		try{
             while(true){
		Socket s = serverSocket.accept();
                started=true;
		new ChatHandler(s);
            }       
	}
	catch(IOException ex){
            started=false;
                    try {
                        serverSocket.close();
                    } catch (IOException ex1) {
                        //Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex1);
                    }
	}
	}
    
    public boolean stopServer(){
        //if(resumed){
        System.out.println(this);
        System.out.println(this.getState());
        try {
            //this.suspend();
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        stop();
        System.out.println(this.getState());
        return true;
        //}else{
           // return false;
       // }
        
    }
}
	