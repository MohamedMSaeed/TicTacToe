/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author User
 */

//public class Game {
//    private int id;
//    private  User Xplayer;
//    private User Oplayer;
//    public enum Value{X,O,NULL};
//    DBManager con;
//    public String value;
//    
//    class Cell{
//        private Value myval;
//        Cell(Value val){myval = val;}
//        Cell(){}
//        public void setMyVal(Value val){myval=val;}
//        public Value getMyVal(){return myval;}
//    }
//  
//    private   Cell[][] cells ;
//
//    Game(){
//        Xplayer=null;
//        Oplayer=null;
//        cells= new Cell[3][3];
//        for(int i=0;i<3;i++){
//            for(int j=0;j<3;j++){
//                cells[i][j]=new Cell(Value.NULL);
//            }
//        }
//        con=new DBManager();
//    }
//    
//    Game(User x, User o){
//        Xplayer=x;
//        Oplayer=o;
//        cells= new Cell[3][3];
//        for(int i=0;i<3;i++){
//            for(int j=0;j<3;j++){
//                cells[i][j]=new Cell(Value.NULL);
//            }
//        }
//        con=new DBManager();
//    }
//   
//    public void setGame(){
//        ResultSet rs ;
//        con.insert("insert into game (UXID,UOID) values ("+Xplayer.getID()+","+Oplayer.getID()+")");
//        rs=con.select("select * from game");
//        try {
//            rs.last();
//            id=rs.getInt("id");
//        } catch (SQLException ex) {
//            //Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        con.end();
//    }
//    
//    public void setCellValue(Value value,int xpos,int ypos){cells[xpos][ypos].setMyVal(value); }
//    public void setXplayer(User x){Xplayer=x;}
//    public void setOplayer(User o){Oplayer=o;}
//    
//    public int getID(){return id;}
//    public User getXplayer(){return Xplayer;}
//    public User getOplayer(){return Oplayer;}
//    public Value getCell(int xpos,int ypos){return cells[xpos][ypos].getMyVal();}
//    
// }


public class Game {
    private int id;
    private  User Xplayer;
    private User Oplayer;
    public String value;
    DBManager con;
    
    
    class Cell{
        private String myval;
        Cell(String val){myval = val;}
        Cell(){}
        public void setMyVal(String val){myval=val;}
        public String getMyVal(){return myval;}
    }
  
    private   Cell[][] cells ;

    Game(){
        Xplayer=null;
        Oplayer=null;
        cells= new Cell[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                cells[i][j]=new Cell("NULL");
            }
        }
        con=new DBManager();
    }
    
    Game(User x, User o){
        Xplayer=x;
        Oplayer=o;
        cells= new Cell[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                cells[i][j]=new Cell("NULL");
            }
        }
        con=new DBManager();
    }
   
    public void setGame(){
        ResultSet rs ;
        //if(Xplayer == null){
        //boolean x = con.insert("insert into game (UXID,UOID) values ('"+Xplayer.getID()+"','"+Oplayer.getID()+"')");
        boolean x = con.insert("insert into game (UXID,UOID) values ('"+Xplayer.getID()+"','"+Oplayer.getID()+"')");
        System.out.println(x);
        System.out.println("insert into game (UXID,UOID) values ('"+Xplayer.getID()+"','"+Oplayer.getID()+"')");
        //}
//        else if(Oplayer == null){
//        boolean x = con.insert("insert into game (UXID,UOID) values ('"+Xplayer.getID()+"', NULL)");
//        System.out.println(x);
//        }
        rs=con.select("select * from game");
        try {
            rs.last();
            id=rs.getInt("id");
        } catch (SQLException ex) {
            //Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        con.end();
    }
  
    
    public void setCellValue(String value,int xpos,int ypos){cells[xpos][ypos].setMyVal(value);}
    public void setXplayer(User x){Xplayer=x;}
    public void setOplayer(User o){Oplayer=o;}
    
    public int getID(){return id;}
    public User getXplayer(){return Xplayer;}
    public User getOplayer(){return Oplayer;}
    public String getCell(int xpos,int ypos){return cells[xpos][ypos].getMyVal();}
    
 }

