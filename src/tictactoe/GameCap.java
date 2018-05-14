/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Vector;

/**
 *
 * @author User
 */
//public class GameCap {
//    private Game game;
//    DBManager con;
//    GameCap(){
//       game=null;
//       con=new DBManager();
//
//    }
//  
//    public boolean saveGame(){
//        String[][] cells;
//        boolean flag;
//        cells = new String[3][3];
//        for (int i = 0; i < 3 ; i++ ){
//            for(int j = 0;j < 3;j++){
//                cells[i][j]=game.getCell(i, j).toString();
//            }
//        }
//        flag=con.insert("insert into gameCap values("+game.getID()+",'"+cells[0][0]+"','"+cells[0][1]+"','"+cells[0][2]+"','"+cells[1][0]+"','"+cells[1][1]+"','"+cells[1][2]+"','"+cells[2][0]+"','"+cells[2][1]+"','"+cells[2][2]+"')");
//        con.end();
//        return flag;
//    }
//    
//   public void setGame(Game g){
//       game=g;
//   }
//}



public class GameCap {
    private Game game;
    DBManager con;
    
    GameCap(){
       game=null;
       con=new DBManager();

    }
  
    public boolean saveGame(){
        game.setGame();
        String[][] cells;
        boolean flag;
        cells = new String[3][3];
        
        for (int i = 0; i < 3 ; i++ ){
            for(int j = 0;j < 3;j++){
                cells[i][j]=game.getCell(i, j);
            }
        }
        System.out.println("ok");
        flag=con.insert("insert into gamecap values("+game.getID()+",'"+cells[0][0]+"','"+cells[0][1]+"','"+cells[0][2]+"','"+cells[1][0]+"','"+cells[1][1]+"','"+cells[1][2]+"','"+cells[2][0]+"','"+cells[2][1]+"','"+cells[2][2]+"')");
        System.out.println("ok2");
        System.out.println("insert into gamecap values("+game.getID()+",'"+cells[0][0]+"','"+cells[0][1]+"','"+cells[0][2]+"','"+cells[1][0]+"','"+cells[1][1]+"','"+cells[1][2]+"','"+cells[2][0]+"','"+cells[2][1]+"','"+cells[2][2]+"')");
        con.end();
        return flag;
    }
   
//    public boolean updateGame(){
//        String[][] cells;
//        boolean flag;
//        cells = new String[3][3];
//        
//        for (int i = 0; i < 3 ; i++ ){
//            for(int j = 0;j < 3;j++){
//                cells[i][j]=game.getCell(i, j);
//            }
//        }
//        flag=con.update("update gamecap set cell1= '"+cells[0][0]+"',cell2='"+cells[0][1]+"',cell3='"+cells[0][2]+"',cell4='"+cells[1][0]+"',cell5='"+cells[1][1]+"',cell6='"+cells[1][2]+"',cell7='"+cells[2][0]+"',cell8='"+cells[2][1]+"',cell9='"+cells[2][2]+"' where id ="+game.getID()+"");
//        System.out.println("update gamecap set cell1= '"+cells[0][0]+"',cell2='"+cells[0][1]+"',cell3='"+cells[0][2]+"',cell4='"+cells[1][0]+"',cell5='"+cells[1][1]+"',cell6='"+cells[1][2]+"',cell7='"+cells[2][0]+"',cell8='"+cells[2][1]+"',cell9='"+cells[2][2]+"' where id ="+game.getID()+"");
//        con.end();
//        return flag;
//    }
    
    public boolean updateGame(Vector<String> testGame,int id){
        String[] cells;
        boolean flag;
        cells = new String[9];
        
        for (int i = 0; i < 9 ; i++ ){
                cells[i]= testGame.elementAt(i);
                System.out.println(testGame.elementAt(i));
        }
        flag=con.update("update gamecap set cell1= '"+cells[0]+"',cell2='"+cells[1]+"',cell3='"+cells[2]+"',cell4='"+cells[3]+"',cell5='"+cells[4]+"',cell6='"+cells[5]+"',cell7='"+cells[6]+"',cell8='"+cells[7]+"',cell9='"+cells[8]+"' where id ="+id+"");
        System.out.println("update gamecap set cell1= '"+cells[0]+"',cell2='"+cells[1]+"',cell3='"+cells[2]+"',cell4='"+cells[3]+"',cell5='"+cells[4]+"',cell6='"+cells[5]+"',cell7='"+cells[6]+"',cell8='"+cells[7]+"',cell9='"+cells[8]+"' where id ="+id+"");
        con.end();
        return flag;
    }
    
    
   public void setGame(Game g){
       game=g;
   }
}