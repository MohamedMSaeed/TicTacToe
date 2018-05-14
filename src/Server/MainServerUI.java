/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Vector;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tictactoe.User;

/**
 *
 * @author User
 */
public class MainServerUI extends Application{

    MyServer server;
    boolean started;
    
        public void myInfo(String msg,String title,String header){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(msg);
            alert.showAndWait();
        }

            public void myAlert(String msg){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR");
            alert.setContentText(msg);
            alert.showAndWait();
        }

    
    public GridPane getMainScene(Stage primaryStage){
        started=false;
        GridPane gp=new GridPane();
      //  gp.add(new Label("Server"),0,1);
        
        Button startbtn=new Button();
        startbtn.setText("start server");
        startbtn.setOnAction((ActionEvent event) -> {
            if(!started){
                server=new MyServer();
                started=true;
                server.startServer();
           myInfo("server started","server status","server");
            }else{
                myAlert("ERROR: Server is already running");
            }
         });
        gp.add(startbtn,0,2);
        
        Button stopbtn=new Button();
        stopbtn.setText("stop server");
        stopbtn.setOnAction((ActionEvent event) -> {
            if(started){
                started=false;
                server.stopServer();
                myInfo("server stopped","server status","server");
            }else{
                 myAlert("ERROR: Server is already stpped");
            }
         });
        gp.add(stopbtn,0,3);
        
        Button dispbtn=new Button();
        dispbtn.setText("display all users");
        dispbtn.setOnAction((ActionEvent event) -> {
            getUsersScene(primaryStage);
         });
        gp.add(dispbtn,0,4);
        return gp;
    }
            
    @Override
    public void start(Stage primaryStage) {
        Scene serverScene = new Scene(getMainScene(primaryStage), 200, 200);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(serverScene);
        primaryStage.show();
    }
    
     private ObservableList<User> getUserList() {
          User user1 = new User();
          Vector<User> usr=new Vector<>();
          user1.getAllUsers(usr);
          ObservableList<User> list = FXCollections.observableArrayList();
          usr.forEach((value) -> {
              //System.out.println(value.getID());
              list.add(value);
        });
          return list;
  }
     public void getUsersScene(Stage primaryStage){  
        TableView<User> table = new TableView<>();
        TableColumn<User, Integer> IDCol= new TableColumn<>("ID");
        TableColumn<User, String> userNameCol= new TableColumn<>("User Name");
        TableColumn<User, String> emailCol= new TableColumn<>("Email");     
        //TableColumn<User, String> passCol = new TableColumn<>("Password"); 
        TableColumn<User, Integer> TscoreCol= new TableColumn<>("Total Score");
        TableColumn<User, Boolean> activeCol= new TableColumn<>("Status");
        
        userNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        //passCol.setCellValueFactory(new PropertyValueFactory<>("pass"));
        TscoreCol.setCellValueFactory(new PropertyValueFactory<>("Tscore"));
        activeCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        ObservableList<User> list = getUserList();
        
//        list.forEach((value) -> {
//              System.out.println(value.getID());
//        });
        table.setItems(list);
        
        table.getColumns().addAll(IDCol,userNameCol, emailCol, activeCol,TscoreCol);
        
        
        Button backbtn=new Button();
        backbtn.setText("back");
        backbtn.setOnAction((ActionEvent event) -> {
                    Scene mainScene = new Scene(getMainScene(primaryStage), 200, 200);
                    primaryStage.setScene(mainScene);

         });
        
        StackPane root = new StackPane();
        root.setPadding(new Insets(5));
        root.getChildren().add(table);
        root.getChildren().add(backbtn);
        Scene scene = new Scene(root, 450, 300);
        primaryStage.setScene(scene);
    }
       public static void main(String[] args) {
        launch(args);
    }
}
