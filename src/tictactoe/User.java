package tictactoe;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
 class Avatar{
       private String name,path;
       private int id;
       ResultSet rs;
       DBManager con;
       Avatar(){
           con=new DBManager();
       }
       
       public void setimgName(String Name){
           name=Name;
       }
       public String getimgNamefm(){
           return name;
       }
        public void setPath(String path){
           this.path=path;
       }
       public String getPath(){
           return path;
       }
         
       public int getID(){
           return id;
       }
       
      public int getImgId(String Name){
          rs=con.select("select id from avatar where name='"+Name+"'");
           try {
               if(rs.first()){
                   return rs.getInt("id");
               }
               } catch (SQLException ex) {
               //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
           }
           return -1;
      }
       
      public String getImgPath(String Name){
          rs=con.select("select path from avatar where name='"+Name+"'");
           try {
               if(rs.first()){
                   return rs.getString("path");
               }
               } catch (SQLException ex) {
               //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
           }
           return "";
      }
       public String getImgName(int id){
          rs=con.select("select name from avatar where id="+id+"");
           try {
               while(rs.next()){
                   return rs.getString("name");
               }
               } catch (SQLException ex) {
               //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
           }
           return "";
      }
       
   }
//public class User {
//   private String userName,password,email;
//   private int id,imgId,status,Tscore;
//   DBManager con;
//   
//   public User(){
//       con = new DBManager();
//   }
//   public User(String uname,String pass,String mail,int status,int imgId){
//       userName=uname;
//       password=pass;
//       email=mail;
//       this.imgId=imgId;
//       this.status=status;
//       con= new DBManager();
//    }
//
//    public User(String mail,String pass){
//       password=pass;
//       email=mail;
//       con= new DBManager();
//    }
//   
//    public void setID(int id){this.id=id;}
//    public void setuName(String uname){userName=uname;}
//    public void setEmail(String email){this.email=email;}
//    public void setPass(String pass){password=pass;}
//    public void setimgId(int imgId){this.imgId=imgId;}
//    public void setStatus(int status){this.status=status;}
//    
//    
//    
//    public int getID(){return id;}
//    public String getName(){return this.userName;}
//    public String getEmail(){return this.email;}
//    public String getPass(){return this.password;}
//    public int getStatus(){return this.status;}
//    public int getimgId(){return this.imgId;}
//    public int Tscore(){return this.Tscore;}
//    
//    public boolean signIn(){
//       ResultSet rs;
//       boolean flag=false;
//       rs=con.select("SELECT * FROM users where email ='"+email+ "' and password ='"+password+"';");
//       
//       try {
//           if(rs.first()){
//               flag=true;
//               try {
//                   this.id=rs.getInt("id");
//                   this.userName=rs.getString("userName");
//                   this.email=rs.getString("email");
//                   this.password=rs.getString("password");
//                   this.imgId=rs.getInt("imgId");
//                   this.status=rs.getInt("status");
//                   this.Tscore=rs.getInt("Tscore");
//                   //con.end();
//               } catch (SQLException ex) {
//                   ex.printStackTrace();
//               }
//           }
//           //}
//       } catch (SQLException ex) {
//           //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//       }
//       return flag; 
//    }
//    
//    public boolean search4Email(){
//       boolean flag=false;
//       ResultSet rs;
//       rs=con.select("SELECT * FROM users where email = '"+this.email+"'");
//       try {
//           if(rs.next()){
//               flag=true;
//           } 
//       } catch (SQLException ex) {
//           //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//       }
//       return flag;
//    }
//    
//    public boolean signUp(){
//       boolean flag;
//       flag=con.insert("insert into users (userName, email, password,imgId,status) values ('"+this.userName+"','"+this.email+"','"+this.password+"', "+this.imgId+" , NULL)");
//       this.Tscore=0;
//       //con.end();
//       return flag; 
//    }
//    
//     public int getIDByEmail(String mail){
//        int myid= -1;
//       ResultSet rs;
//       rs=con.select("SELECT * FROM users where email = '"+mail+"'");
//      
//       try {
//            if(rs.first()){
//                rs.first();
//               myid=rs.getInt("id");
//           } 
//       } catch (SQLException ex) {
//          // Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//       }
//          
//       return myid;
//    }
//    
////    public ResultSet selectOnline(){
////        ResultSet rs;
////        rs=con.select("SELECT * FROM users where status = '1'"); 
////        //System.out.println(rs);
////        //con.end();
////        return rs;    
////    }
//    
//     public void selectOnline(Vector<String> names){
//       try {
//           ResultSet rs;
//           rs=con.select("SELECT * FROM users where status = '1'");
//
//           while(rs.next()){
//               names.add(rs.getString(3));
//           }
//           //con.end();
//       } catch (SQLException ex) {
//       }
//    }
//    public void serchByEmail(String Email){
//        //System.out.println(Email);
//        ResultSet rs;
//        //User usr;
//        rs=con.select("SELECT * FROM users where email ='"+Email+"';");
//        try {
//           if(rs.first()){
//               try {
//                   this.id=rs.getInt("id");
//                   this.userName=rs.getString("userName");
//                   this.email=rs.getString("email");
//                   this.password=rs.getString("password");
//                   this.imgId=rs.getInt("imgId");
//                   this.status=rs.getInt("status");
//                   this.Tscore=rs.getInt("Tscore");
//                   //con.end();
//               } catch (SQLException ex) {
//                   ex.printStackTrace();
//               }
//           }
//           //}
//       } catch (SQLException ex) {
//           //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//       }
//       //return usr;
//    }
//
//}


public class User {
    String userName,password,email;
    int id,status,Tscore;
   DBManager con;
      Avatar avatar;

      private String imgName;

   public User(){
       con = new DBManager();
       avatar=new Avatar();
   }
   public User(String uname,String pass,String mail,int status,String imgName){
       userName=uname;
       password=pass;
       email=mail;
       this.imgName=imgName;
       this.status=status;
       con= new DBManager();
              avatar=new Avatar();

    }

    public User(String mail,String pass){
       password=pass;
       email=mail;
              avatar=new Avatar();

       con= new DBManager();
    }
   
    public void setID(int id){this.id=id;}
    public void setuName(String uname){userName=uname;}
    public void setEmail(String email){this.email=email;}
    public void setPass(String pass){password=pass;}
    public void setimgName(String imgname){this.imgName=imgname;}
    public void setStatus(int status){this.status=status;}
    
    
    
    public int getID(){return id;}
    public String getName(){return this.userName;}
    public String getEmail(){return this.email;}
    public String getPass(){return this.password;}
    public int getStatus(){return this.status;}
    public String getimgname(){return imgName;}
    public int getTscore(){return this.Tscore;}
    
    public boolean signIn(){
       ResultSet rs;
       boolean flag=false;
       rs=con.select("SELECT * FROM users where email ='"+email+ "' and password ='"+password+"';");
       
       try {
           if(rs.first()){
               flag=true;
               try {
                   this.id=rs.getInt("id");
                   this.userName=rs.getString("userName");
                   this.email=rs.getString("email");
                   this.password=rs.getString("password");
                   this.imgName=avatar.getImgName(rs.getInt("imgId"));
                   this.status=rs.getInt("status");
                   this.Tscore=rs.getInt("Tscore");
                   con.update("UPDATE users set status='1' where email ='"+email+ "' and password ='"+password+"';");
                   
//con.end();
               } catch (SQLException ex) {
                   ex.printStackTrace();
               }
           }
           //}
       } catch (SQLException ex) {
           //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
       }
       return flag; 
    }
    
    public boolean search4Email(){
       boolean flag=false;
       ResultSet rs;
       rs=con.select("SELECT * FROM users where email = '"+this.email+"'");
       try {
           if(rs.next()){
               flag=true;
           } 
       } catch (SQLException ex) {
           //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
       }
       return flag;
    }
    
    public boolean signUp(){
       boolean flag;
       flag=con.insert("insert into users (userName, email, password,imgId,status) values ('"+this.userName+"','"+this.email+"','"+this.password+"', "+avatar.getImgId(this.imgName)+" , '0')");
       this.Tscore=0;
       //con.end();
       return flag; 
    }
    
     public int getIDByEmail(String mail){
        int myid= -1;
       ResultSet rs;
       rs=con.select("SELECT * FROM users where email = '"+mail+"'");
      
       try {
            if(rs.first()){
                rs.first();
               myid=rs.getInt("id");
           } 
       } catch (SQLException ex) {
          // Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
       }
          
       return myid;
    }
   
     public void selectOnline(Vector<String> names){
       try {
           ResultSet rs;
           rs=con.select("SELECT * FROM users where status = '1'");
           
           while(rs.next()){
                if(!this.getEmail().equals(rs.getString(3))){
                    names.add(rs.getString(3)+":   Score is: "+rs.getInt(7));
                }
           }
           
       } catch (SQLException ex) {
       }
    }
     
     public void selectGames(Vector<String> games, int playerID){
       try {
           
           ResultSet rs1;
           ResultSet rs2;
           Vector<String> gamesID = new  Vector<String>();
           rs1 = con.select("SELECT id FROM game WHERE UXID='"+playerID+"' OR UOID='"+playerID+"'");
           while(rs1.next()){
                gamesID.add(rs1.getString(1));
           }    
            for(String game : gamesID){
                rs2 = con.select("Select * from gamecap where id='"+game+"'");
                  while(rs2.next()){
                      games.add(rs2.getString(1));
                      
                  }
            }
       } 
       catch (SQLException ex) {
       }
    }
     
    public void serchByEmail(String Email){
        //System.out.println(Email);
        ResultSet rs;
        //User usr;
        rs=con.select("SELECT * FROM users where email ='"+Email+"';");
        try {
           if(rs.first()){
               try {
                   this.id=rs.getInt("id");
                   this.userName=rs.getString("userName");
                   this.email=rs.getString("email");
                   this.password=rs.getString("password");
                   this.imgName=avatar.getImgName(rs.getInt("imgId"));
                   this.status=rs.getInt("status");
                   this.Tscore=rs.getInt("Tscore");
                   //con.end();
               } catch (SQLException ex) {
                   ex.printStackTrace();
               }
           }
           //}
       } catch (SQLException ex) {
           //Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
       }
       //return usr;
    }
    public void getAllUsers(Vector<User> names){
       try {
           ResultSet rs;
           rs=con.select("SELECT * FROM users");
           
          while(rs.next()){
               User user=new User();
               user.id=rs.getInt("id");
               user.userName=rs.getString("userName");
               user.email=rs.getString("email");
               user.password=rs.getString("password");
               //user.imgName=avatar.getImgName(rs.getInt("imgId"));
               user.status=rs.getInt("status");
               user.Tscore=rs.getInt("Tscore");
               names.add(user);
           }
           //con.end();
       } catch (SQLException ex) {
       }
    }
    
    
    public void selectGamesCap(Vector<String> gamesCap, Object gameID){
       try {
           
           ResultSet rs1;
           rs1 = con.select("SELECT * FROM gamecap WHERE id='"+gameID+"'");
           while(rs1.next()){
               for(int i=2; i<=10; i++){
                gamesCap.add(rs1.getString(i));
               }
           }    
       } 
       catch (SQLException ex) {
       }
    }
    
    public String oppoMailResume(int GameID, int playerID){
        ResultSet rs1;
        ResultSet rs2;
        String mail = "";
        rs1 = con.select("select UXID,UOID from game where id="+GameID+";");
        try{
        if(rs1.first()){
            if(rs1.getInt(1)==playerID){
                int oppoID=rs1.getInt(2);
                rs2 =con.select("select email from users where id="+oppoID+"");
                if(rs2.first()){
                    mail = rs2.getString(1);
                }        
            }else{
                int oppoID=rs1.getInt(1);
                rs2 =con.select("select email from users where id="+oppoID+"");
                if(rs2.first()){
                    mail = rs2.getString(1);
                }
            }
        }
        } catch(Exception ex){
            System.out.println("error");
        }
        return mail;
    }
            
    public void deleteGame(int gameID){         
           
           con.delete("delete FROM gamecap WHERE id='"+gameID+"'");
     
    
    }
    
     public String selectPlayerType(int gameID){
         String playerMark ="";
       try {
           ResultSet rs1;
           rs1 = con.select("SELECT UXID FROM game WHERE id="+gameID);
           if(rs1.next()){
               System.out.println(rs1.getString(1));
               if (rs1.getString(1)=="null"){
                   System.out.println("Lina");
                   playerMark="O";
               }
               else{
               playerMark = "X";
            }
           } 
       } 
       catch (SQLException ex) {
       }
       return playerMark;
    }
    
    
    
    public void logOut(){
        System.out.println("UPDATE users set status='0' where email ='"+this.email+ "';");
        con.update("UPDATE users set status='0' where email ='"+this.email+ "';");
    }
}