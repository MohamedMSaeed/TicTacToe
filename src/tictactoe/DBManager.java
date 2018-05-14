package tictactoe;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Vector;


public class DBManager {

    private Connection con;
       
  public DBManager()
   {
       try
       {
            con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/tecToc", "root", "root");              
       }catch(SQLException e)
       {
            System.out.println("problem with constructing a db connection !");
       }
   }

       public ResultSet select(String query){ 
            Statement stmt;
            ResultSet rs = null; 
	
           try 
              {
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE); 
                rs=stmt.executeQuery(query);

                //stmt.close();
              } catch (SQLException ex) {
              }
           
        return rs;
       }
       
        
         public boolean insert(String query)
         {
                boolean flag=false;
                  try {
                    try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
                        preparedStmt.executeUpdate();
                    }
                      flag=true;
                  } catch (SQLException ex) {
                  }
              
              return flag;
      }
         
         
         public boolean update(String query)
         {
                boolean flag=false;
                  try {
                    try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
                        preparedStmt.executeUpdate();
                    }
                      flag=true;
                  } catch (SQLException ex) {
                  }
              
              return flag;
      }
      
       public boolean end()
        {
                boolean flag=false;
                try {
                    con.close();
                    flag=true;
                } catch (SQLException ex) {
                }

              return flag;
      }
       
        public boolean delete(String query)
         {
                boolean flag=false;
                  try {
                    try (PreparedStatement preparedStmt = con.prepareStatement(query)) {
                        preparedStmt.executeUpdate();
                    }
                      flag=true;
                  } catch (SQLException ex) {
                  }
              
              return flag;
      }

}
