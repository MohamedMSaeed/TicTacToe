
package tictactoe;

public class Score {
    int GID;
    int UID;
    int score;
    DBManager con;
    Score(){
        GID=-1;
        UID=-1;
        score=-1;
        con=new DBManager();
    }
    
    public boolean addNewScore(Game g,User u,int s){
        GID=g.getID();
        UID=u.getID();
        score=s;
        boolean flag;
        flag=con.insert("insert into score values("+GID+","+UID+","+score+")");
        System.out.println("insert into score values("+GID+","+UID+","+score+")");
        con.end();
        return flag;
    }
    
}
