/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;

public class UserUI {
    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;
    int i;
    String parentPath;
    FileWriter fw;
    PrintWriter pw;
    BufferedReader br;
    Alert about;
    static volatile String msgPlayer = "";
    private String playerMark;
    private String adversaryMark;
    private int activeButtons = 9;
    String mymsg;
    boolean msgDis;
    User player;
    TicTacToe prim;
    User myPlay;
    User opponent;
    int width = 800;
    int height = 600;
    int play1Flag = 0;
    int play2Flag = 0;
    int startFlag = 0;
    Game g;
    GameCap gc;
    int ID;
    Score sc;
    boolean leave;
    String oppoResMail;
    Vector<String> gameCap;
    Vector<String> games;
    
    
    public void setPS(String msg){
        ps.println(msg);
    }
     
    UserUI(){
        mymsg=new String("");
        leave = false;
    }
    
    public void setMsg(String msg){
        mymsg=msg;
    }
    
    public String getMsg(){
        return mymsg;
    }
    
    void readMSGs(){
        try {
            msgPlayer = dis.readLine();
            if(!msgPlayer.equals("")){
                String[] check = msgPlayer.split(":");
                if (check[0].equals("requestPlayer")){
                    msgPlayer = "about:"+check[1];
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }               
    }
     
    void about(String oppoName){
        about = new Alert(Alert.AlertType.CONFIRMATION);
        about.setTitle("Would you play with me?");
        about.setHeaderText(null);
        about.setContentText("Tel3ab ya Raye2? Ana esmy: "+oppoName);
    }
    
    public void myAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("ERROR");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    public void myInfo(String msg,String title,String header){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }
        
    
    public void makeConnection(User usr, Stage primaryStage){
        try{
            mySocket = new Socket("127.0.0.1",6060);
            dis = new DataInputStream (mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());
            ps.println("connect:"+usr.getEmail());
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        
        new Thread (new Runnable(){
            
            public void run(){
                while(true){
                    try{
                        String msg = dis.readLine();
                        String[] check = msg.split(":");
                        if(check[0].equals("playerRequest")){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    about(check[2]);
                                    Optional<ButtonType> result = about.showAndWait();
                                    if (result.get() == ButtonType.OK){
                                        try {
                                            ps.println("play:ok:"+check[1]+":"+check[2]);
                                            playerMark=check[3];
                                            if(playerMark.equals("X")){
                                                adversaryMark="O";
                                            }else if(playerMark.equals("O")){
                                                adversaryMark="X";
                                            }
                                            opponent = new User();
                                            opponent.serchByEmail(check[2]);
                                            primaryStage.setScene(new Scene(getMultiPlayerScene(opponent,myPlay,primaryStage), width, height));
                                        } catch (FileNotFoundException ex) {
                                            ex.printStackTrace();
                                        }
                                    } else {
                                        ps.println("play:cancel:"+check[1]+":"+check[2]);
                                    }
                                }
                            });
                        }else if(check[0].equals("moveIt")){
                            opponent = new User();
                            opponent.serchByEmail(check[2]);
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    if(check[3].equals("but00")){
                                        insertOpp(but00);
                                    }else if(check[3].equals("but01")){
                                        insertOpp(but01);
                                    }else if(check[3].equals("but02")){
                                        insertOpp(but02);
                                    }else if(check[3].equals("but10")){
                                        insertOpp(but10);
                                    }else if(check[3].equals("but11")){
                                        insertOpp(but11);
                                    }else if(check[3].equals("but12")){
                                        insertOpp(but12);
                                    }else if(check[3].equals("but20")){
                                        insertOpp(but20);
                                    }else if(check[3].equals("but21")){
                                        insertOpp(but21);
                                    }else if(check[3].equals("but22")){
                                        insertOpp(but22);
                                    }
                                }
                            });
                        }else if(check[0].equals("moveItRes")){
                            opponent = new User();
                            opponent.serchByEmail(check[2]);
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    if(check[3].equals("but00")){
                                        insertOppRes(but00);
                                    }else if(check[3].equals("but01")){
                                        insertOppRes(but01);
                                    }else if(check[3].equals("but02")){
                                        insertOppRes(but02);
                                    }else if(check[3].equals("but10")){
                                        insertOppRes(but10);
                                    }else if(check[3].equals("but11")){
                                        insertOppRes(but11);
                                    }else if(check[3].equals("but12")){
                                        insertOppRes(but12);
                                    }else if(check[3].equals("but20")){
                                        insertOppRes(but20);
                                    }else if(check[3].equals("but21")){
                                        insertOppRes(but21);
                                    }else if(check[3].equals("but22")){
                                        insertOppRes(but22);
                                    }
                                }
                            });
                        }else if(check[0].equals("ok")){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    try {
                                        opponent = new User();
                                        opponent.serchByEmail(check[1]);
                                        primaryStage.setScene(new Scene(getMultiPlayerScene(myPlay,opponent,primaryStage), width, height));
                                    } catch (FileNotFoundException ex) {
                                        Logger.getLogger(UserUI.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                
                            });
                        }else if(check[0].equals("left")){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    try {
                                        //System.out.println(check[1]);
                                        opponent = new User();
                                        opponent.serchByEmail(check[1]);
                                        myAlert("Oppo Left!! He is coward");
                                        primaryStage.setScene(new Scene(getOnlineScene(myPlay,primaryStage), width, height));
                                    } catch (Exception ex) {
                                        Logger.getLogger(UserUI.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                
                            });
                        }else if(check[0].equals("resume")){
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    try {
                                        //System.out.println("resME: "+check[1]);
                                        playerMark=check[3];
                                        //System.out.println(msg);
                                        
                                        opponent = new User();
                                        opponent.serchByEmail(check[1]);
                                        opponent.selectGamesCap(gameCap,games.elementAt(Integer.parseInt(check[2])));
                                        myAlert("We Will resume game!!");
                                        //Vector<String> gameCap1 = new Vector<String>();
                                        //System.out.println(Integer.parseInt(check[2]));
                                        if(playerMark.equals("X")){
                                            playerMark="O";
                                            adversaryMark="X";
                                        }else if(playerMark.equals("O")){
                                            playerMark="X";
                                            adversaryMark="O";
                                        }
                                        
                                        primaryStage.setScene(new Scene(getMultiResumeScene(opponent,myPlay,primaryStage,gameCap.elementAt(0),gameCap.elementAt(1),gameCap.elementAt(2),gameCap.elementAt(3),gameCap.elementAt(4),gameCap.elementAt(5),gameCap.elementAt(6),gameCap.elementAt(7),gameCap.elementAt(8)), width, height));                                    } catch (Exception ex) {
                                        Logger.getLogger(UserUI.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                
                            });
                        }
                    }catch(IOException ex){
                        //ex.printStackTrace();
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
    
    public GridPane getSignInView(User playerInfo,Stage primaryStage){
        int width = 800;
        int height = 600;
        GridPane logIn=new GridPane();
        logIn.setAlignment(Pos.CENTER);
        logIn.setPadding(new Insets(25, 25, 25, 25));
        logIn.setVgap(50);
        logIn.setHgap(50);
        
        logIn.add(new Label("Email"),0,1);
        TextField userName = new TextField();
        userName.setPromptText("Email");
        logIn.add(userName,1,1);
        logIn.add(new Label("Password"),0,2);        
        TextField passWord = new TextField();
        passWord.setPromptText("password");
        logIn.add(passWord,1,2);        
        Button logInBtn = new Button();
        logInBtn.setText("LogIn");
        logInBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(userName.getText());
                if(userName.getText().isEmpty()){
                    myAlert("USER NAME FIELD IS REQUIRED");
                }else if(!matcher.matches()||userName.getText().length()>50){
                    myAlert("PLZ, ENTER  VALID EMAIL");
                }else if(passWord.getText().isEmpty()){
                    myAlert("PASSWORD FIELD IS REQUIRED");
                }else{
                    boolean flag;
                    String pass = passWord.getText();
                    //check email formate
                    String mail = userName.getText();
                    //User player;
                    player=new User(mail,pass);
                    myPlay = player;
                    flag=player.signIn();
                    if(flag){
                        playerInfo.setEmail(mail);
                        playerInfo.setPass(pass);
                        playerInfo.setID(playerInfo.getIDByEmail(mail));
                        makeConnection(player, primaryStage);
                        primaryStage.setScene(new Scene(getUserScene(playerInfo,primaryStage),width,height));
                    }else{
                        //alert
                        myAlert("ERROR ...NO SUCH CREDENTIALS");
                    }
                }
            }
        });
        
        
        Button signUpBtn = new Button();
        signUpBtn.setText("SignUp");
        signUpBtn.setOnAction((ActionEvent event) -> {
            primaryStage.setScene(new Scene(getSignUpScene(playerInfo,primaryStage), width, height));
        });

        logIn.add(logInBtn,0,3);
        logIn.add(signUpBtn,1,3);
        return logIn;
     }
     
    public GridPane getSignUpScene(User playerInfo,Stage primaryStage){
        GridPane signUp=new GridPane();
        int width = 800;
        int height = 600;
        signUp.setAlignment(Pos.CENTER);
        signUp.setPadding(new Insets(25, 25, 25, 25));
        signUp.setVgap(50);
        signUp.setHgap(50);
        
        signUp.add(new Label("User Name:"), 0,1);
        TextField newUserName = new TextField();
        newUserName.setPromptText("user name");
        signUp.add(newUserName,1,1);
        signUp.add(new Label("Password:"), 0,2);        
        TextField newPassWord = new TextField();
        newPassWord.setPromptText("password");
        signUp.add(newPassWord,1,2);
        signUp.add(new Label("Confirm Password:"), 0,3);                
        TextField confirmPassWord = new TextField();
        confirmPassWord.setPromptText("confirm password");
        signUp.add(confirmPassWord,1,3);        
        signUp.add(new Label("Email:"), 0,4);                
        TextField newEmail = new TextField();
        newEmail.setPromptText("email");
        signUp.add(newEmail,1,4);         
        
        final ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton();
        rb1.setSelected(true);
        rb1.setUserData("1");
        rb1.setGraphic(new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("avatars/avatar1.jpg"))));
        rb1.setToggleGroup(group);
        RadioButton rb2 = new RadioButton();
        rb2.setUserData("2");
        rb2.setGraphic(new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("avatars/avatar2.jpg"))));
        rb2.setToggleGroup(group);
        RadioButton rb3 = new RadioButton();
        rb3.setUserData("3");
        rb3.setGraphic(new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("avatars/avatar3.jpg"))));
        rb3.setToggleGroup(group);
        RadioButton rb4 = new RadioButton();
        rb4.setUserData("4");
        rb4.setGraphic(new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream("avatars/avatar4.jpg"))));
        rb4.setToggleGroup(group);
        
        HBox hbox = new HBox();

        hbox.getChildren().add(rb1);
        hbox.getChildren().add(rb2);
        hbox.getChildren().add(rb3);
        hbox.getChildren().add(rb4);
        hbox.setSpacing(30);
    
        Button submitBtn = new Button();
        submitBtn.setText("Submit");
        submitBtn.setOnAction((ActionEvent event) -> {
            String uName = newUserName.getText();
            String uPass = newPassWord.getText();
            String uMail = newEmail.getText();
           int imgId =Integer.parseInt( group.getSelectedToggle().getUserData().toString());
            String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(uMail);
             String avatar="avatar"+imgId;
            User user=new User(uName,uPass,uMail,0,avatar);
            if(uName.isEmpty()){
                myAlert("You Must Enter User Name!");
            }else if(uPass.isEmpty()){
                myAlert("You Must Enter Password!");
            }else if(uMail.isEmpty()){
                myAlert("YOU MUST ENTER YOUR EMAIL!");
            }else if(uName.length()>50){
                myAlert("USER NAME MUST BE 50 CHARACTERS OR LESS");
            }
            else if(!matcher.matches()||uMail.length()>50){
                myAlert("PLZ,ENTER VALID EMAIL");
            }
            else if(uPass.length()>15){
                myAlert("PASSWORD MUST BE 15 CHARACTERS OR LESS");
            }else{
                boolean flag;
                flag=user.search4Email();
                if(flag){
                    myAlert("ERROR...EMAIL IS ALREADY IN USE");
                }else{
                    boolean flag2;
                    flag2=user.signUp();
                    if(flag2){
                        myInfo("YOU'VE SUCCESSFULLY SIGNED UP","REGISTERATION","SIGN UP");
                        playerInfo.setEmail(uMail);
                        playerInfo.setuName(uName);
                        playerInfo.setPass(uPass);
                        playerInfo.setID(playerInfo.getIDByEmail(uMail));
                        primaryStage.setScene(new Scene(getSignInView(playerInfo,primaryStage),width,height));
                    }else{
                        myAlert("ERROR...FAILD ATTEMPT TO SIGN UP");
                    }
                }
            }
        });

       
        Button backBtn = new Button();
        backBtn.setText("Back To LogIn");
        backBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(new Scene(getSignInView(playerInfo,primaryStage),width,height));
            }
        });

        signUp.add(hbox,1,5);
        signUp.add(backBtn,1,6);
        signUp.add(submitBtn,2,6);
        return signUp;
     }
     
    public VBox getUserScene(User playerInfo,Stage primaryStage){
        int width = 800;
        int height = 600;
        User usr = new User();
        games=new Vector<>();
        ListView listView = new ListView();
        
        
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
	vbox.setPadding(new Insets(50, 50, 50, 50));
        Button singlePlayerBtn = new Button("Play With The Computer");
	singlePlayerBtn.setFont(Font.font("Verdana", 20));
        singlePlayerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(new Scene(getSinglePlayerScene(playerInfo,primaryStage),width,height));
            }
        });
        Button multiPlayerBtn = new Button("Play With A Friend");
	multiPlayerBtn.setFont(Font.font("Verdana", 20));    
        multiPlayerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                primaryStage.setScene(new Scene(getOnlineScene(playerInfo,primaryStage), width, height));
            }
        });
        Button logOut = new Button("Sign Out");
	logOut.setFont(Font.font("Verdana", 20));    
        logOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                playerInfo.logOut();
                primaryStage.setScene(new Scene(getSignInView(playerInfo,primaryStage),width,height));
            }
        });
        
        usr.selectGames(games,playerInfo.getID());
        for(String n : games){
            listView.getItems().add(n);
        }
        
        
        
        HBox hbox = new HBox();
	hbox.setAlignment(Pos.TOP_CENTER);
	hbox.getChildren().add(singlePlayerBtn);
	hbox.getChildren().add(multiPlayerBtn);
        hbox.getChildren().add(logOut);
	hbox.setMinHeight(70);
	hbox.setMinWidth(70);
        HBox hbox1 = new HBox();
	hbox1.setAlignment(Pos.TOP_CENTER);
	hbox1.getChildren().add(listView);
	hbox1.setMinHeight(70);
	hbox1.setMinWidth(70);
	singlePlayerBtn.setMinWidth(160);
	singlePlayerBtn.setMinHeight(hbox.getMinHeight());
	multiPlayerBtn.setMinWidth(160);
	multiPlayerBtn.setMinHeight(hbox.getMinHeight());
        logOut.setMinWidth(160);
	logOut.setMinHeight(hbox.getMinHeight());
        
        gameCap = new Vector<String>(); 
        
        Button button = new Button("Resume a Game");
            button.setOnAction(event -> { ObservableList selectedIndices = listView.getSelectionModel().getSelectedIndices();
            for(Object o : selectedIndices){
                try {
                    int i = (Integer)o;
                    //System.out.println(o);
                    ID = Integer.parseInt(games.elementAt(i));
                    usr.selectGamesCap(gameCap,games.elementAt(i));
                    oppoResMail = usr.oppoMailResume(ID, playerInfo.getID());
                    opponent = new User();
                    opponent.serchByEmail(oppoResMail);
                    
                    //System.out.println(gameCap);
                    
                    
                    playerMark= usr.selectPlayerType(Integer.parseInt(games.elementAt(i)));
                    if(playerMark.equals("X")){adversaryMark = "O";}
                    else{adversaryMark ="X";}
                    //System.out.println(playerMark);
                    ps.println("resume:"+oppoResMail+":"+playerInfo.getEmail()+":"+i+":"+playerMark);
                    primaryStage.setScene(new Scene(getMultiResumeScene(playerInfo,opponent,primaryStage,gameCap.elementAt(0),gameCap.elementAt(1),gameCap.elementAt(2),gameCap.elementAt(3),gameCap.elementAt(4),gameCap.elementAt(5),gameCap.elementAt(6),gameCap.elementAt(7),gameCap.elementAt(8)), width, height));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(UserUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
	vbox.getChildren().add(hbox);
        vbox.getChildren().add(hbox1);
        vbox.getChildren().add(button);
        return vbox;
     }
      
    public VBox getOnlineScene(User playerInfo,Stage primaryStage){
        int width = 800;
        int height = 600;
        User usr = new User();
        usr = myPlay;
        Vector<String> names=new Vector<>();
        ListView listView = new ListView();
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        usr.selectOnline(names);
        for(String n : names){
            String[] check = n.split(":");
            //if(!myPlay.getEmail().equals(check[0])){
                listView.getItems().add(n);
            //}
        }
            
        Button xBtn = new Button("Play With X");
        Button oBtn = new Button("Play With O");
        Button refresh = new Button("Refresh The List");
        
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(new Scene(getOnlineScene(playerInfo,primaryStage), width, height));
            }
        });
        
        Button back = new Button("Back To Home");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(new Scene(getUserScene(playerInfo,primaryStage),width,height));
            }
        });
        
        Button button = new Button("Send a Request");
        button.setOnAction(event -> {
            ObservableList selectedIndices = listView.getSelectionModel().getSelectedIndices();
            
            for(Object o : selectedIndices){
                try {
                    String[] check = names.get(Integer.parseInt(o.toString())).split(":");
                    ps.println("request:"+check[0]+":"+player.getEmail()+":"+adversaryMark);
                } catch (Exception ex) {
                    Logger.getLogger(UserUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
            
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().add(xBtn);
        hbox.getChildren().add(oBtn);
        hbox.getChildren().add(refresh);
        hbox.getChildren().add(back);
        
        hbox.setMinHeight(70);
        hbox.setMinWidth(70);
            
        xBtn.setOnAction(e -> xBtn());
        oBtn.setOnAction(e -> oBtn());
                
        vbox.getChildren().add(listView);
        vbox.getChildren().add(hbox);
        vbox.getChildren().add(button);
            
        return vbox;
    } 
     
    public void xBtn() {
	this.playerMark = "X";
	this.adversaryMark = "O";
    }
    
    public void oBtn() {
	this.playerMark = "O";
	this.adversaryMark = "X";
    }
    
    public VBox getSinglePlayerScene(User playerInfo,Stage primaryStage){
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
	vbox.setPadding(new Insets(20, 20, 20, 20));
        Button labelBut = new Button("TIC TAC TOE");
        labelBut.setFont(Font.font("Verdana", 20));
        Button xbut = new Button("X");
        Button obut = new Button("O");
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().add(xbut);
        hbox.getChildren().add(labelBut);
        hbox.getChildren().add(obut);
        hbox.setMinHeight(70);
        hbox.setMinWidth(70);
        xbut.setMinWidth(hbox.getMinWidth());
        xbut.setMinHeight(hbox.getMinHeight());
        obut.setMinWidth(hbox.getMinWidth());
        obut.setMinHeight(hbox.getMinHeight());
        labelBut.setMinHeight(hbox.getMinHeight());
        labelBut.setMinWidth(160);

        Button LeaveBut = new Button("Leave Game");
        labelBut.setFont(Font.font("Verdana", 20));
//        Button PauseBut = new Button("Pause Game");
        labelBut.setFont(Font.font("Verdana", 20));
        HBox hboxMenu = new HBox();
        hboxMenu.setAlignment(Pos.TOP_CENTER);
        hboxMenu.getChildren().add(LeaveBut);
//        hboxMenu.getChildren().add(PauseBut);
        hboxMenu.setMinHeight(70);
        hboxMenu.setMinWidth(70);
        LeaveBut.setMinHeight(hboxMenu.getMinHeight());
        LeaveBut.setMinWidth(160);
//        PauseBut.setMinHeight(hboxMenu.getMinHeight());
//        PauseBut.setMinWidth(160);
                
        LeaveBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.setScene(new Scene(getUserScene(playerInfo,primaryStage),width,height));
            }
        });
                
//        PauseBut.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                gc = new GameCap();
//                gc.setGame(g);
//                System.out.print(g.getCell(0, 0));
//                g.setCellValue((but00.getText().equals("")? "NULL":but00.getText()), 0, 0);
//                g.setCellValue((but01.getText().equals("")? "NULL":but01.getText()), 0, 1);
//                g.setCellValue((but02.getText().equals("")? "NULL":but02.getText()), 0, 2);
//                g.setCellValue((but10.getText().equals("")? "NULL":but10.getText()), 1, 0);
//                g.setCellValue((but11.getText().equals("")? "NULL":but11.getText()), 1, 1);
//                g.setCellValue((but12.getText().equals("")? "NULL":but12.getText()), 1, 2);
//                g.setCellValue((but20.getText().equals("")? "NULL":but20.getText()), 2, 0);
//                g.setCellValue((but21.getText().equals("")? "NULL":but21.getText()), 2, 1);
//                g.setCellValue((but22.getText().equals("")? "NULL":but22.getText()), 2, 2);
//                gc.saveGame();
//                primaryStage.setScene(new Scene(getUserScene(playerInfo,primaryStage),width,height));
//            }
//        });
                
        int val = 100;

        GridPane gridpaneC = new GridPane();
        gridpaneC.setAlignment(Pos.TOP_CENTER);
        gridpaneC.setPrefHeight(val);
        gridpaneC.setPrefWidth(val);
        gridpaneC.getColumnConstraints().add(new ColumnConstraints(val));
        gridpaneC.getColumnConstraints().add(new ColumnConstraints(val));
        gridpaneC.getColumnConstraints().add(new ColumnConstraints(val));
        gridpaneC.getRowConstraints().add(new RowConstraints(val));
        gridpaneC.getRowConstraints().add(new RowConstraints(val));
        gridpaneC.getRowConstraints().add(new RowConstraints(val));

        Button butc00 = new Button();
        butc00.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        butc00.setMinHeight(val);
        butc00.setMinWidth(val);
        Button butc01 = new Button();
        butc01.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        butc01.setMinHeight(val);
        butc01.setMinWidth(val);
        Button butc02 = new Button();
        butc02.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        butc02.setMinHeight(val);
        butc02.setMinWidth(val);
        Button butc10 = new Button();
        butc10.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        butc10.setMinHeight(val);
        butc10.setMinWidth(val);
        Button butc11 = new Button();
        butc11.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        butc11.setMinHeight(val);
        butc11.setMinWidth(val);
        Button butc12 = new Button();
        butc12.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        butc12.setMinHeight(val);
        butc12.setMinWidth(val);
        Button butc20 = new Button();
        butc20.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        butc20.setMinHeight(val);
        butc20.setMinWidth(val);
        Button butc21 = new Button();
        butc21.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        butc21.setMinHeight(val);
        butc21.setMinWidth(val);
        Button butc22 = new Button();
        butc22.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        butc22.setMinHeight(val);
        butc22.setMinWidth(val);

        butc00.setOnAction(
                        e -> gridButMethod(butc00, gridpaneC, butc00, butc01, butc02, butc10, butc11, butc12, butc20, butc21, butc22));
        butc01.setOnAction(
                        e -> gridButMethod(butc01, gridpaneC, butc00, butc01, butc02, butc10, butc11, butc12, butc20, butc21, butc22));
        butc02.setOnAction(
                        e -> gridButMethod(butc02, gridpaneC, butc00, butc01, butc02, butc10, butc11, butc12, butc20, butc21, butc22));
        butc10.setOnAction(
                        e -> gridButMethod(butc10, gridpaneC, butc00, butc01, butc02, butc10, butc11, butc12, butc20, butc21, butc22));
        butc11.setOnAction(
                        e -> gridButMethod(butc11, gridpaneC, butc00, butc01, butc02, butc10, butc11, butc12, butc20, butc21, butc22));
        butc12.setOnAction(
                        e -> gridButMethod(butc12, gridpaneC, butc00, butc01, butc02, butc10, butc11, butc12, butc20, butc21, butc22));
        butc20.setOnAction(
                        e -> gridButMethod(butc20, gridpaneC, butc00, butc01, butc02, butc10, butc11, butc12, butc20, butc21, butc22));
        butc21.setOnAction(
                        e -> gridButMethod(butc21, gridpaneC, butc00, butc01, butc02, butc10, butc11, butc12, butc20, butc21, butc22));
        butc22.setOnAction(
                        e -> gridButMethod(butc22, gridpaneC, butc00, butc01, butc02, butc10, butc11, butc12, butc20, butc21, butc22));

        gridpaneC.add(butc00, 0, 0);
        gridpaneC.add(butc01, 0, 1);
        gridpaneC.add(butc02, 0, 2);
        gridpaneC.add(butc10, 1, 0);
        gridpaneC.add(butc11, 1, 1);
        gridpaneC.add(butc12, 1, 2);
        gridpaneC.add(butc20, 2, 0);
        gridpaneC.add(butc21, 2, 1);
        gridpaneC.add(butc22, 2, 2);

        ObservableList<Node> children = gridpaneC.getChildren();
        for (Node child : children) {
            if (child instanceof Button) {
                child.setDisable(true);
            }
        }
        
        labelBut.setOnAction(e -> labelButMethod(labelBut, xbut, obut, butc00, butc01, butc02, butc10, butc11, butc12, butc20, butc21, butc22));
        xbut.setOnAction(e -> xbutMethod(xbut, obut, butc00, butc01, butc02, butc10, butc11, butc12, butc20, butc21, butc22));
        obut.setOnAction(e -> obutMethod(xbut, obut, butc00, butc01, butc02, butc10, butc11, butc12, butc20, butc21, butc22));

        vbox.getChildren().add(hboxMenu);
        vbox.getChildren().add(hbox);
        vbox.getChildren().add(gridpaneC);

        return vbox;    
    }
    
    Button but00;
    Button but01;
    Button but02;
    Button but10;
    Button but11;
    Button but12;
    Button but20;
    Button but21;
    Button but22;
    GridPane gridpane;
    
    public VBox getMultiPlayerScene(User playerInfo1,User playerInfo2, Stage primaryStage) throws FileNotFoundException{
        //int FirstPlayerScore=playerInfo1.getTscore();
        //int SecondPlayerScore=playerInfo2.getTscore();
        Avatar avatar=new Avatar();
        ImageView iv1 = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream(avatar.getImgPath(playerInfo1.getimgname()))));
        ImageView iv2 = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream(avatar.getImgPath(playerInfo2.getimgname()))));
        //System.out.println(iv2);
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        Button labelBut = new Button("TecTacToe");
        labelBut.setFont(Font.font("Verdana", 20));      
        Button player1 = new Button(playerInfo1.getEmail(), iv1);
        Button player2 = new Button(playerInfo2.getEmail(), iv2);
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().add(player1);
        hbox.getChildren().add(labelBut);
        hbox.getChildren().add(player2);

        hbox.setMinHeight(70);
        hbox.setMinWidth(70);

        Button LeaveBut = new Button("Leave Game");
        labelBut.setFont(Font.font("Verdana", 20));
        Button PauseBut = new Button("Pause Game");
        labelBut.setFont(Font.font("Verdana", 20));
        HBox hboxMenu = new HBox();
        hboxMenu.setAlignment(Pos.TOP_CENTER);
        hboxMenu.getChildren().add(LeaveBut);
        hboxMenu.getChildren().add(PauseBut);
        hboxMenu.setMinHeight(70);
        hboxMenu.setMinWidth(70);
        LeaveBut.setMinHeight(hboxMenu.getMinHeight());
        LeaveBut.setMinWidth(160);
        PauseBut.setMinHeight(hboxMenu.getMinHeight());
        PauseBut.setMinWidth(160);
                
        LeaveBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                activeButtons=9;
                play1Flag = 0;
                play2Flag = 0;
                startFlag = 0;
                ps.println("left:"+player.getEmail()+":"+opponent.getEmail());
                primaryStage.setScene(new Scene(getOnlineScene(playerInfo1,primaryStage), width, height));
            }
        });
                
        PauseBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                g = new Game(myPlay,opponent);
                g.setCellValue((but00.getText().equals("")? "NULL":but00.getText()), 0, 0);
                g.setCellValue((but01.getText().equals("")? "NULL":but01.getText()), 0, 1);
                g.setCellValue((but02.getText().equals("")? "NULL":but02.getText()), 0, 2);
                g.setCellValue((but10.getText().equals("")? "NULL":but10.getText()), 1, 0);
                g.setCellValue((but11.getText().equals("")? "NULL":but11.getText()), 1, 1);
                g.setCellValue((but12.getText().equals("")? "NULL":but12.getText()), 1, 2);
                g.setCellValue((but20.getText().equals("")? "NULL":but20.getText()), 2, 0);
                g.setCellValue((but21.getText().equals("")? "NULL":but21.getText()), 2, 1);
                g.setCellValue((but22.getText().equals("")? "NULL":but22.getText()), 2, 2);
                gc = new GameCap();
                gc.setGame(g);
                gc.saveGame();
                primaryStage.setScene(new Scene(getUserScene(playerInfo1,primaryStage),width,height));
            }
        });
               
        player1.setMinWidth(hbox.getMinWidth());
        player1.setMinHeight(hbox.getMinHeight());
        player2.setMinWidth(hbox.getMinWidth());
        player2.setMinHeight(hbox.getMinHeight());

        labelBut.setMinHeight(hbox.getMinHeight());
        labelBut.setMinWidth(160);

        int val = 100;
        gridpane = new GridPane();
        gridpane.setAlignment(Pos.TOP_CENTER);
        gridpane.setPrefHeight(val);
        gridpane.setPrefWidth(val);
        gridpane.getColumnConstraints().add(new ColumnConstraints(val));
        gridpane.getColumnConstraints().add(new ColumnConstraints(val));
        gridpane.getColumnConstraints().add(new ColumnConstraints(val));
        gridpane.getRowConstraints().add(new RowConstraints(val));
        gridpane.getRowConstraints().add(new RowConstraints(val));
        gridpane.getRowConstraints().add(new RowConstraints(val));
        but00 = new Button();
        but00.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but00.setMinHeight(val);
        but00.setMinWidth(val);
        but01 = new Button();
        but01.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but01.setMinHeight(val);
        but01.setMinWidth(val);
        but02 = new Button();
        but02.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but02.setMinHeight(val);
        but02.setMinWidth(val);
        but10 = new Button();
        but10.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but10.setMinHeight(val);
        but10.setMinWidth(val);
        but11 = new Button();
        but11.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but11.setMinHeight(val);
        but11.setMinWidth(val);
        but12 = new Button();
        but12.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but12.setMinHeight(val);
        but12.setMinWidth(val);
        but20 = new Button();
        but20.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but20.setMinHeight(val);
        but20.setMinWidth(val);
        but21 = new Button();
        but21.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but21.setMinHeight(val);
        but21.setMinWidth(val);
        but22 = new Button();
        but22.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but22.setMinHeight(val);
        but22.setMinWidth(val);

        but00.setOnAction(e -> InsertMethod(but00, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but00"));
        but01.setOnAction(e -> InsertMethod(but01, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but01"));
        but02.setOnAction(e -> InsertMethod(but02, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but02"));
        but10.setOnAction(e -> InsertMethod(but10, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but10"));
        but11.setOnAction(e -> InsertMethod(but11, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but11"));
        but12.setOnAction(e -> InsertMethod(but12, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but12"));
        but20.setOnAction(e -> InsertMethod(but20, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but20"));
        but21.setOnAction(e -> InsertMethod(but21, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but21"));
        but22.setOnAction(e -> InsertMethod(but22, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but22"));

        gridpane.add(but00, 0, 0);
        gridpane.add(but01, 0, 1);
        gridpane.add(but02, 0, 2);
        gridpane.add(but10, 1, 0);
        gridpane.add(but11, 1, 1);
        gridpane.add(but12, 1, 2);
        gridpane.add(but20, 2, 0);
        gridpane.add(but21, 2, 1);
        gridpane.add(but22, 2, 2);

        vbox.getChildren().add(hboxMenu);
        vbox.getChildren().add(hbox);
        vbox.getChildren().add(gridpane);

        return vbox;
    }
   
    
    
    public VBox getMultiResumeScene(User playerInfo1,User playerInfo2, Stage primaryStage,String btn00,String btn01,String btn02,String btn10,String btn11,String btn12,String btn20,String btn21,String btn22) throws FileNotFoundException{
        if(btn00.equalsIgnoreCase("NULL")){btn00="";};
        if(btn01.equalsIgnoreCase("NULL")){btn01="";};
        if(btn02.equalsIgnoreCase("NULL")){btn02="";};
        if(btn10.equalsIgnoreCase("NULL")){btn10="";};
        if(btn11.equalsIgnoreCase("NULL")){btn11="";};
        if(btn12.equalsIgnoreCase("NULL")){btn12="";};
        if(btn20.equalsIgnoreCase("NULL")){btn20="";};
        if(btn21.equalsIgnoreCase("NULL")){btn21="";};
        if(btn22.equalsIgnoreCase("NULL")){btn22="";};
        
        Avatar avatar=new Avatar();
        ImageView iv1 = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream(avatar.getImgPath(playerInfo1.getimgname()))));
        ImageView iv2 = new ImageView(new javafx.scene.image.Image(getClass().getResourceAsStream(avatar.getImgPath(playerInfo2.getimgname()))));
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        Button labelBut = new Button("TecTacToe");
        labelBut.setFont(Font.font("Verdana", 20));      
        Button player1 = new Button(playerInfo1.getEmail(), iv1);
        Button player2 = new Button(playerInfo2.getEmail(), iv2);
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.TOP_CENTER);
        hbox.getChildren().add(player1);
        hbox.getChildren().add(labelBut);
        hbox.getChildren().add(player2);

        hbox.setMinHeight(70);
        hbox.setMinWidth(70);

        Button LeaveBut = new Button("Leave Game");
        labelBut.setFont(Font.font("Verdana", 20));
        Button PauseBut = new Button("Pause Game");
        labelBut.setFont(Font.font("Verdana", 20));
        HBox hboxMenu = new HBox();
        hboxMenu.setAlignment(Pos.TOP_CENTER);
        hboxMenu.getChildren().add(LeaveBut);
        hboxMenu.getChildren().add(PauseBut);
        hboxMenu.setMinHeight(70);
        hboxMenu.setMinWidth(70);
        LeaveBut.setMinHeight(hboxMenu.getMinHeight());
        LeaveBut.setMinWidth(160);
        PauseBut.setMinHeight(hboxMenu.getMinHeight());
        PauseBut.setMinWidth(160);
                
        LeaveBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                activeButtons=9;
                play1Flag = 0;
                play2Flag = 0;
                startFlag = 0;
                ps.println("left:"+player.getEmail()+":"+opponent.getEmail());
                primaryStage.setScene(new Scene(getOnlineScene(playerInfo1,primaryStage), width, height));
            }
        });
                
        PauseBut.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                
                        Vector<String> gameTest = new Vector<String>();
                        
                        gameTest.add(but00.getText().equals("")? "NULL":but00.getText());
                        gameTest.add(but01.getText().equals("")? "NULL":but01.getText());
                        gameTest.add(but02.getText().equals("")? "NULL":but02.getText());
                        gameTest.add(but10.getText().equals("")? "NULL":but10.getText());
                        gameTest.add(but11.getText().equals("")? "NULL":but11.getText());
                        gameTest.add(but12.getText().equals("")? "NULL":but12.getText());
                        gameTest.add(but20.getText().equals("")? "NULL":but20.getText());
                        gameTest.add(but21.getText().equals("")? "NULL":but21.getText());
                        gameTest.add(but22.getText().equals("")? "NULL":but22.getText());
                        gc = new GameCap();
                        gc.updateGame(gameTest,ID);
                        
                        primaryStage.setScene(new Scene(getUserScene(playerInfo1,primaryStage),width,height));
                    }
                });
               
        player1.setMinWidth(hbox.getMinWidth());
        player1.setMinHeight(hbox.getMinHeight());
        player2.setMinWidth(hbox.getMinWidth());
        player2.setMinHeight(hbox.getMinHeight());

        labelBut.setMinHeight(hbox.getMinHeight());
        labelBut.setMinWidth(160);

        int val = 100;
        gridpane = new GridPane();
        gridpane.setAlignment(Pos.TOP_CENTER);
        gridpane.setPrefHeight(val);
        gridpane.setPrefWidth(val);
        gridpane.getColumnConstraints().add(new ColumnConstraints(val));
        gridpane.getColumnConstraints().add(new ColumnConstraints(val));
        gridpane.getColumnConstraints().add(new ColumnConstraints(val));
        gridpane.getRowConstraints().add(new RowConstraints(val));
        gridpane.getRowConstraints().add(new RowConstraints(val));
        gridpane.getRowConstraints().add(new RowConstraints(val));
        but00 = new Button(btn00);
        but00.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but00.setMinHeight(val);
        but00.setMinWidth(val);
        but01 = new Button(btn01);
        but01.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but01.setMinHeight(val);
        but01.setMinWidth(val);
        but02 = new Button(btn02);
        but02.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but02.setMinHeight(val);
        but02.setMinWidth(val);
        but10 = new Button(btn10);
        but10.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but10.setMinHeight(val);
        but10.setMinWidth(val);
        but11 = new Button(btn11);
        but11.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but11.setMinHeight(val);
        but11.setMinWidth(val);
        but12 = new Button(btn12);
        but12.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but12.setMinHeight(val);
        but12.setMinWidth(val);
        but20 = new Button(btn20);
        but20.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but20.setMinHeight(val);
        but20.setMinWidth(val);
        but21 = new Button(btn21);
        but21.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but21.setMinHeight(val);
        but21.setMinWidth(val);
        but22 = new Button(btn22);
        but22.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
        but22.setMinHeight(val);
        but22.setMinWidth(val);

                but00.setOnAction(e -> InsertMethod(but00, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but00"));
		but01.setOnAction(e -> InsertMethod(but01, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but01"));
		but02.setOnAction(e -> InsertMethod(but02, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but02"));
		but10.setOnAction(e -> InsertMethod(but10, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but10"));
		but11.setOnAction(e -> InsertMethod(but11, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but11"));
		but12.setOnAction(e -> InsertMethod(but12, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but12"));
		but20.setOnAction(e -> InsertMethod(but20, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but20"));
		but21.setOnAction(e -> InsertMethod(but21, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but21"));
		but22.setOnAction(e -> InsertMethod(but22, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but22"));

        gridpane.add(but00, 0, 0);
        gridpane.add(but01, 0, 1);
        gridpane.add(but02, 0, 2);
        gridpane.add(but10, 1, 0);
        gridpane.add(but11, 1, 1);
        gridpane.add(but12, 1, 2);
        gridpane.add(but20, 2, 0);
        gridpane.add(but21, 2, 1);
        gridpane.add(but22, 2, 2);
        ObservableList<Node> children = gridpane.getChildren();
		for (Node child : children) {
			if (child instanceof Button) {
                                if(!((Button) child).getText().equals("")){
                                    child.setDisable(true);
                                }
			}
		}
                
        vbox.getChildren().add(hboxMenu);
        vbox.getChildren().add(hbox);
        vbox.getChildren().add(gridpane);

        return vbox;
    }
    
    
    public void xbutMethod(Button xbut, Button obut, Button but00, Button but01, Button but02, Button but10,Button but11, Button but12, Button but20, Button but21, Button but22) {
        this.playerMark = "X";
        this.adversaryMark = "O";

        xbut.setStyle("-fx-background-color: #5F9EA0");
        obut.setStyle("-fx-background-color: #F08080");

        xbut.setDisable(true);
        obut.setDisable(true);

        but00.setDisable(false);
        but01.setDisable(false);
        but02.setDisable(false);
        but10.setDisable(false);
        but11.setDisable(false);
        but12.setDisable(false);
        but20.setDisable(false);
        but21.setDisable(false);
        but22.setDisable(false);
    }

    public void obutMethod(Button xbut, Button obut, Button but00, Button but01, Button but02, Button but10,Button but11, Button but12, Button but20, Button but21, Button but22) {
        this.playerMark = "O";
        this.adversaryMark = "X";
        xbut.setDisable(true);
        obut.setDisable(true);

        obut.setStyle("-fx-background-color: #5F9EA0");
        xbut.setStyle("-fx-background-color: #F08080");

        but00.setDisable(false);
        but01.setDisable(false);
        but02.setDisable(false);
        but10.setDisable(false);
        but11.setDisable(false);
        but12.setDisable(false);
        but20.setDisable(false);
        but21.setDisable(false);
        but22.setDisable(false);

    }

    public void labelButMethod(Button labelBut, Button xbut, Button obut, Button but00, Button but01, Button but02,Button but10, Button but11, Button but12, Button but20, Button but21, Button but22) {

        xbut.setDisable(false);
        obut.setDisable(false);

        obut.setStyle("-fx-background-color: transparent");
        xbut.setStyle("-fx-background-color: transparent");

        this.adversaryMark = "";
        this.playerMark = "";
        this.activeButtons = 9;

        but00.setDisable(true);
        but00.setText("");
        but00.setStyle("-fx-backgroud-color: transparent");
        but01.setDisable(true);
        but01.setText("");
        but01.setStyle("-fx-backgroud-color: transparent");
        but02.setDisable(true);
        but02.setText("");
        but02.setStyle("-fx-backgroud-color: transparent");
        but10.setDisable(true);
        but10.setText("");
        but10.setStyle("-fx-backgroud-color: transparent");
        but11.setDisable(true);
        but11.setText("");
        but11.setStyle("-fx-backgroud-color: transparent");
        but12.setDisable(true);
        but12.setText("");
        but12.setStyle("-fx-backgroud-color: transparent");
        but20.setDisable(true);
        but20.setText("");
        but20.setStyle("-fx-backgroud-color: transparent");
        but21.setDisable(true);
        but21.setText("");
        but21.setStyle("-fx-backgroud-color: transparent");
        but22.setDisable(true);
        but22.setText("");
        but22.setStyle("-fx-backgroud-color: transparent");

        if (labelBut.getText() == "TIC TAC TOE") {
                labelBut.setText("TIC");
                labelBut.setFont(Font.font("Verdana", 20));
        } else if (labelBut.getText() == "TIC") {
                labelBut.setText("TAC");
                labelBut.setFont(Font.font("Verdana", 20));
        } else if (labelBut.getText() == "TAC") {
                labelBut.setText("TOE");
                labelBut.setFont(Font.font("Verdana", 20));
        } else if (labelBut.getText() == "TOE") {
                labelBut.setText("TIC TAC TOE");
                labelBut.setFont(Font.font("Verdana", 20));
        }
    }

    public void gridButMethod(Button button, GridPane gridpane, Button but00, Button but01, Button but02, Button but10,Button but11, Button but12, Button but20, Button but21, Button but22) {

        button.setText(this.playerMark);
        button.setDisable(true);

        this.activeButtons = this.activeButtons - 1;
        if (this.activeButtons == 0) {
                but00.setDisable(true);
                but01.setDisable(true);
                but02.setDisable(true);
                but10.setDisable(true);
                but11.setDisable(true);
                but12.setDisable(true);
                but20.setDisable(true);
                but21.setDisable(true);
                but22.setDisable(true);
        }
        checkWin(gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22);
    }

    public void checkWin(GridPane gridpane, Button but00, Button but01, Button but02, Button but10, Button but11, Button but12, Button but20, Button but21, Button but22) {

		if ((but00.getText().equals(this.playerMark)) && (but01.getText().equals(this.playerMark))
				&& (but02.getText().equals(this.playerMark))) {
			but00.setStyle("-fx-background-color: #00ff00");
			but01.setStyle("-fx-background-color: #00ff00");
			but02.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}
			}

		} else if ((but10.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but12.getText().equals(this.playerMark))) {
			but10.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but12.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but20.getText().equals(this.playerMark)) && (but21.getText().equals(this.playerMark))
				&& (but22.getText().equals(this.playerMark))) {
			but20.setStyle("-fx-background-color: #00ff00");
			but21.setStyle("-fx-background-color: #00ff00");
			but22.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but00.getText().equals(this.playerMark)) && (but10.getText().equals(this.playerMark))
				&& (but20.getText().equals(this.playerMark))) {
			but00.setStyle("-fx-background-color: #00ff00");
			but10.setStyle("-fx-background-color: #00ff00");
			but20.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}
			}

		} else if ((but01.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but21.getText().equals(this.playerMark))) {
			but01.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but21.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but02.getText().equals(this.playerMark)) && (but12.getText().equals(this.playerMark))
				&& (but22.getText().equals(this.playerMark))) {
			but02.setStyle("-fx-background-color: #00ff00");
			but12.setStyle("-fx-background-color: #00ff00");
			but22.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but00.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but22.getText().equals(this.playerMark))) {
			but00.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but22.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but02.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but20.getText().equals(this.playerMark))) {
			but02.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but20.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}
			}

		} else {

			if (this.activeButtons > 0) {

				List<Button> butList = Arrays.asList(but00, but01, but02, but10, but11, but12, but20, but21, but22);
				Random random = new Random();

				while (true) {
					int index = random.nextInt(butList.size());
					Button button = butList.get(index);

					if (button.isDisable() != true) {
						button.setText(this.adversaryMark);
						button.setDisable(true);

						this.activeButtons = this.activeButtons - 1;

						checkWinA(gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22);
						break;

					}

				}

			}
		}
	}

    public void checkWinA(GridPane gridpane, Button but00, Button but01, Button but02, Button but10, Button but11,
			Button but12, Button but20, Button but21, Button but22) {

		if ((but00.getText().equals(this.adversaryMark)) && (but01.getText().equals(this.adversaryMark))
				&& (but02.getText().equals(this.adversaryMark))) {
			but00.setStyle("-fx-background-color: #CD5C5C");
			but01.setStyle("-fx-background-color: #CD5C5C");
			but02.setStyle("-fx-background-color: #CD5C5C");

                        ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but10.getText().equals(this.adversaryMark)) && (but11.getText().equals(this.adversaryMark))
				&& (but12.getText().equals(this.adversaryMark))) {
			but10.setStyle("-fx-background-color: #CD5C5C");
			but11.setStyle("-fx-background-color: #CD5C5C");
			but12.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but20.getText().equals(this.adversaryMark)) && (but21.getText().equals(this.adversaryMark))
				&& (but22.getText().equals(this.adversaryMark))) {
			but20.setStyle("-fx-background-color: #CD5C5C");
			but21.setStyle("-fx-background-color: #CD5C5C");
			but22.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but00.getText().equals(this.adversaryMark)) && (but10.getText().equals(this.adversaryMark))
				&& (but20.getText().equals(this.adversaryMark))) {
			but00.setStyle("-fx-background-color: #CD5C5C");
			but10.setStyle("-fx-background-color: #CD5C5C");
			but20.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but01.getText().equals(this.adversaryMark)) && (but11.getText().equals(this.adversaryMark))
				&& (but21.getText().equals(this.adversaryMark))) {
			but01.setStyle("-fx-background-color: #CD5C5C");
			but11.setStyle("-fx-background-color: #CD5C5C");
			but21.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but02.getText().equals(this.adversaryMark)) && (but12.getText().equals(this.adversaryMark))
				&& (but22.getText().equals(this.adversaryMark))) {
			but02.setStyle("-fx-background-color: #CD5C5C");
			but12.setStyle("-fx-background-color: #CD5C5C");
			but22.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but00.getText().equals(this.adversaryMark)) && (but11.getText().equals(this.adversaryMark))
				&& (but22.getText().equals(this.adversaryMark))) {
			but00.setStyle("-fx-background-color: #CD5C5C");
			but11.setStyle("-fx-background-color: #CD5C5C");
			but22.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but02.getText().equals(this.adversaryMark)) && (but11.getText().equals(this.adversaryMark))
				&& (but20.getText().equals(this.adversaryMark))) {
			but02.setStyle("-fx-background-color: #CD5C5C");
			but11.setStyle("-fx-background-color: #CD5C5C");
			but20.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		}
        }
         
    public void insertOpp(Button btn){
        if(startFlag == 0){
            btn.setText(adversaryMark);
            btn.setDisable(true);
            startFlag++;
        }
        if(play2Flag%2 == 1){
            btn.setText(adversaryMark);
            btn.setDisable(true);
            checkIfWin(gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22);
            checkWinA(gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22);
            play2Flag++;
            play1Flag++;
        }
    }
    
    public void insertOppRes(Button btn){
            btn.setText(adversaryMark);
            btn.setDisable(true);
            checkIfWin(gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22);
            checkWinA(gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22);
    }
    
    public void InsertMethod(Button button, GridPane gridpane, Button but00, Button but01, Button but02, Button but10,
			Button but11, Button but12, Button but20, Button but21, Button but22, String btnName) {
                
        
        if(play1Flag%2==0){
		button.setText(this.playerMark);
                ps.println("move:"+opponent.getEmail()+":"+player.getEmail()+":"+btnName);
		button.setDisable(true);
		this.activeButtons = this.activeButtons - 1;
		if (this.activeButtons == 0) {
			but00.setDisable(true);
			but01.setDisable(true);
			but02.setDisable(true);
			but10.setDisable(true);
			but11.setDisable(true);
			but12.setDisable(true);
			but20.setDisable(true);
			but21.setDisable(true);
			but22.setDisable(true);
		}

		checkIfWin(gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22);
                checkWinA(gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22);
                play1Flag++;
                play2Flag++;
        }
    }
    
     public void checkIfWin(GridPane gridpane, Button but00, Button but01, Button but02, Button but10, Button but11,
			Button but12, Button but20, Button but21, Button but22) {

		if ((but00.getText().equals(this.playerMark)) && (but01.getText().equals(this.playerMark))
				&& (but02.getText().equals(this.playerMark))) {
			but00.setStyle("-fx-background-color: #00ff00");
			but01.setStyle("-fx-background-color: #00ff00");
			but02.setStyle("-fx-background-color: #00ff00");
                        g = new Game(myPlay,opponent);
                        g.setGame();
                        sc = new Score();
                        sc.addNewScore(g, myPlay, 10);

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but10.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but12.getText().equals(this.playerMark))) {
			but10.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but12.setStyle("-fx-background-color: #00ff00");
                        g = new Game(myPlay,opponent);
                        g.setGame();
                        sc = new Score();
                        sc.addNewScore(g, myPlay, 10);

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but20.getText().equals(this.playerMark)) && (but21.getText().equals(this.playerMark))
				&& (but22.getText().equals(this.playerMark))) {
			but20.setStyle("-fx-background-color: #00ff00");
			but21.setStyle("-fx-background-color: #00ff00");
			but22.setStyle("-fx-background-color: #00ff00");
                        g = new Game(myPlay,opponent);
                        g.setGame();
                        sc = new Score();
                        sc.addNewScore(g, myPlay, 10);

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but00.getText().equals(this.playerMark)) && (but10.getText().equals(this.playerMark))
				&& (but20.getText().equals(this.playerMark))) {
			but00.setStyle("-fx-background-color: #00ff00");
			but10.setStyle("-fx-background-color: #00ff00");
			but20.setStyle("-fx-background-color: #00ff00");
                        g = new Game(myPlay,opponent);
                        g.setGame();
                        sc = new Score();
                        sc.addNewScore(g, myPlay, 10);

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but01.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but21.getText().equals(this.playerMark))) {
			but01.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but21.setStyle("-fx-background-color: #00ff00");
                        g = new Game(myPlay,opponent);
                        g.setGame();
                        sc = new Score();
                        sc.addNewScore(g, myPlay, 10);

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but02.getText().equals(this.playerMark)) && (but12.getText().equals(this.playerMark))
				&& (but22.getText().equals(this.playerMark))) {
			but02.setStyle("-fx-background-color: #00ff00");
			but12.setStyle("-fx-background-color: #00ff00");
			but22.setStyle("-fx-background-color: #00ff00");
                        g = new Game(myPlay,opponent);
                        g.setGame();
                        sc = new Score();
                        sc.addNewScore(g, myPlay, 10);

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but00.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but22.getText().equals(this.playerMark))) {
			but00.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but22.setStyle("-fx-background-color: #00ff00");
                        g = new Game(myPlay,opponent);
                        g.setGame();
                        sc = new Score();
                        sc.addNewScore(g, myPlay, 10);

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		} else if ((but02.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but20.getText().equals(this.playerMark))) {
			but02.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but20.setStyle("-fx-background-color: #00ff00");
                        g = new Game(myPlay,opponent);
                        g.setGame();
                        sc = new Score();
                        sc.addNewScore(g, myPlay, 10);

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
		}
	}
     
     public VBox getResumeGameScene(User playerInfo,Stage primaryStage,String btn00,String btn01,String btn02,String btn10,String btn11,String btn12,String btn20,String btn21,String btn22){
        int width = 800;
        int height = 600;
        
        if(btn00.equalsIgnoreCase("NULL")){btn00="";};
        if(btn01.equalsIgnoreCase("NULL")){btn01="";};
        if(btn02.equalsIgnoreCase("NULL")){btn02="";};
        if(btn10.equalsIgnoreCase("NULL")){btn10="";};
        if(btn11.equalsIgnoreCase("NULL")){btn11="";};
        if(btn12.equalsIgnoreCase("NULL")){btn12="";};
        if(btn20.equalsIgnoreCase("NULL")){btn20="";};
        if(btn21.equalsIgnoreCase("NULL")){btn21="";};
        if(btn22.equalsIgnoreCase("NULL")){btn22="";};
                VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.setPadding(new Insets(20, 20, 20, 20));
		Button labelBut = new Button("TIC TAC TOE");
		labelBut.setFont(Font.font("Verdana", 20));
		Button xbut = new Button("X");
		Button obut = new Button("O");
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.TOP_CENTER);
		hbox.getChildren().add(xbut);
		hbox.getChildren().add(labelBut);
		hbox.getChildren().add(obut);
		hbox.setMinHeight(70);
		hbox.setMinWidth(70);
		xbut.setMinWidth(hbox.getMinWidth());
		xbut.setMinHeight(hbox.getMinHeight());
		obut.setMinWidth(hbox.getMinWidth());
		obut.setMinHeight(hbox.getMinHeight());
		labelBut.setMinHeight(hbox.getMinHeight());
		labelBut.setMinWidth(160);
                
                Button LeaveBut = new Button("Leave Game");
		labelBut.setFont(Font.font("Verdana", 20));
                //Button PauseBut = new Button("Pause & Quit Game");
		labelBut.setFont(Font.font("Verdana", 20));
                HBox hboxMenu = new HBox();
                hboxMenu.setAlignment(Pos.TOP_CENTER);
                hboxMenu.getChildren().add(LeaveBut);
		//hboxMenu.getChildren().add(PauseBut);
                hboxMenu.setMinHeight(70);
		hboxMenu.setMinWidth(70);
                LeaveBut.setMinHeight(hboxMenu.getMinHeight());
		LeaveBut.setMinWidth(160);
                //PauseBut.setMinHeight(hboxMenu.getMinHeight());
		//PauseBut.setMinWidth(160);
                
                int val = 100;
                
                gridpane = new GridPane();
		gridpane.setAlignment(Pos.TOP_CENTER);
		gridpane.setPrefHeight(val);
		gridpane.setPrefWidth(val);
		gridpane.getColumnConstraints().add(new ColumnConstraints(val));
		gridpane.getColumnConstraints().add(new ColumnConstraints(val));
		gridpane.getColumnConstraints().add(new ColumnConstraints(val));
		gridpane.getRowConstraints().add(new RowConstraints(val));
		gridpane.getRowConstraints().add(new RowConstraints(val));
		gridpane.getRowConstraints().add(new RowConstraints(val));
                
                but00 = new Button(btn00);
		but00.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
		but00.setMinHeight(val);
		but00.setMinWidth(val);
		but01 = new Button(btn01);
		but01.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
		but01.setMinHeight(val);
		but01.setMinWidth(val);
		but02 = new Button(btn02);
		but02.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
		but02.setMinHeight(val);
		but02.setMinWidth(val);
		but10 = new Button(btn10);
		but10.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
		but10.setMinHeight(val);
		but10.setMinWidth(val);
		but11 = new Button(btn11);
		but11.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
		but11.setMinHeight(val);
		but11.setMinWidth(val);
		but12 = new Button(btn12);
		but12.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
		but12.setMinHeight(val);
		but12.setMinWidth(val);
		but20 = new Button(btn20);
		but20.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
		but20.setMinHeight(val);
		but20.setMinWidth(val);
		but21 = new Button(btn21);
		but21.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
		but21.setMinHeight(val);
		but21.setMinWidth(val);
		but22 = new Button(btn22);
		but22.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 25));
		but22.setMinHeight(val);
		but22.setMinWidth(val);
                g = new Game();
                
                LeaveBut.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        primaryStage.setScene(new Scene(getUserScene(playerInfo,primaryStage),width,height));
                    }
                });
                
                but00.setOnAction(e -> InsertMethodResumed(but00, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but00"));
		but01.setOnAction(e -> InsertMethodResumed(but01, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but01"));
		but02.setOnAction(e -> InsertMethodResumed(but02, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but02"));
		but10.setOnAction(e -> InsertMethodResumed(but10, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but10"));
		but11.setOnAction(e -> InsertMethodResumed(but11, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but11"));
		but12.setOnAction(e -> InsertMethodResumed(but12, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but12"));
		but20.setOnAction(e -> InsertMethodResumed(but20, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but20"));
		but21.setOnAction(e -> InsertMethodResumed(but21, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but21"));
		but22.setOnAction(e -> InsertMethodResumed(but22, gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22, "but22"));
                
                gridpane.add(but00, 0, 0);
		gridpane.add(but01, 0, 1);
		gridpane.add(but02, 0, 2);
		gridpane.add(but10, 1, 0);
		gridpane.add(but11, 1, 1);
		gridpane.add(but12, 1, 2);
		gridpane.add(but20, 2, 0);
		gridpane.add(but21, 2, 1);
		gridpane.add(but22, 2, 2);
                
                ObservableList<Node> children = gridpane.getChildren();
		for (Node child : children) {
			if (child instanceof Button) {
                                if(!((Button) child).getText().equals("")){
                                    child.setDisable(true);
                                }
			}
		}

		labelBut.setOnAction(e -> labelButMethod(labelBut, xbut, obut, but00, but01, but02, but10, but11, but12, but20,but21, but22));
		//xbut.setOnAction(e -> xbutMethod(playerInfo,xbut, obut, but00, but01, but02, but10, but11, but12, but20, but21, but22));
		//obut.setOnAction(e -> obutMethod(playerInfo,xbut, obut, but00, but01, but02, but10, but11, but12, but20, but21, but22));
                
                vbox.getChildren().add(hboxMenu);
                vbox.getChildren().add(hbox);
		vbox.getChildren().add(gridpane);
             
                return vbox;    
    }
     
     public void gridButMethodResumed(Button button, GridPane gridpane, Button but00, Button but01, Button but02, Button but10,
			Button but11, Button but12, Button but20, Button but21, Button but22) {

		button.setText(this.playerMark);
		button.setDisable(true);

		this.activeButtons = this.activeButtons - 1;
		if (this.activeButtons == 0) {
			but00.setDisable(true);
			but01.setDisable(true);
			but02.setDisable(true);
			but10.setDisable(true);
			but11.setDisable(true);
			but12.setDisable(true);
			but20.setDisable(true);
			but21.setDisable(true);
			but22.setDisable(true);
		}

		checkWinResumed(gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22);
	}
     
     public void InsertMethodResumed(Button button, GridPane gridpane, Button but00, Button but01, Button but02, Button but10,
			Button but11, Button but12, Button but20, Button but21, Button but22 ,String btnName) {
                
         
         
//         if(play1Flag%2==0){
//                play1Flag++;
//                play2Flag++;
//        }
		button.setText(this.playerMark);
                opponent = new User();
                opponent.serchByEmail(oppoResMail);
                ps.println("moveRes:"+opponent.getEmail()+":"+player.getEmail()+":"+btnName);
		button.setDisable(true);
		this.activeButtons = this.activeButtons - 1;
		if (this.activeButtons == 0) {
			but00.setDisable(true);
			but01.setDisable(true);
			but02.setDisable(true);
			but10.setDisable(true);
			but11.setDisable(true);
			but12.setDisable(true);
			but20.setDisable(true);
			but21.setDisable(true);
			but22.setDisable(true);
		}
                
		checkIfWinResumed(gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22);
                checkWinAResumed(gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22);
	}
     
     public void checkWinResumed(GridPane gridpane, Button but00, Button but01, Button but02, Button but10, Button but11,
			Button but12, Button but20, Button but21, Button but22) {
                        User usr = new User();
		if ((but00.getText().equals(this.playerMark)) && (but01.getText().equals(this.playerMark))
				&& (but02.getText().equals(this.playerMark))) {
			but00.setStyle("-fx-background-color: #00ff00");
			but01.setStyle("-fx-background-color: #00ff00");
			but02.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}
                                
			}
                    usr.deleteGame(ID);
                        
		} else if ((but10.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but12.getText().equals(this.playerMark))) {
			but10.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but12.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                    usr.deleteGame(ID);
		} else if ((but20.getText().equals(this.playerMark)) && (but21.getText().equals(this.playerMark))
				&& (but22.getText().equals(this.playerMark))) {
			but20.setStyle("-fx-background-color: #00ff00");
			but21.setStyle("-fx-background-color: #00ff00");
			but22.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                usr.deleteGame(ID);
		} else if ((but00.getText().equals(this.playerMark)) && (but10.getText().equals(this.playerMark))
				&& (but20.getText().equals(this.playerMark))) {
			but00.setStyle("-fx-background-color: #00ff00");
			but10.setStyle("-fx-background-color: #00ff00");
			but20.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                        usr.deleteGame(ID);

		} else if ((but01.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but21.getText().equals(this.playerMark))) {
			but01.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but21.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                        usr.deleteGame(ID);

		} else if ((but02.getText().equals(this.playerMark)) && (but12.getText().equals(this.playerMark))
				&& (but22.getText().equals(this.playerMark))) {
			but02.setStyle("-fx-background-color: #00ff00");
			but12.setStyle("-fx-background-color: #00ff00");
			but22.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                        usr.deleteGame(ID);

		} else if ((but00.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but22.getText().equals(this.playerMark))) {
			but00.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but22.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                        usr.deleteGame(ID);

		} else if ((but02.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but20.getText().equals(this.playerMark))) {
			but02.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but20.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                        usr.deleteGame(ID);
		} else {

			if (this.activeButtons > 0) {

				List<Button> butList = Arrays.asList(but00, but01, but02, but10, but11, but12, but20, but21, but22);
				Random random = new Random();

				while (true) {
					int index = random.nextInt(butList.size());
					Button button = butList.get(index);

					if (button.isDisable() != true) {
						button.setText(this.adversaryMark);
						button.setDisable(true);

						this.activeButtons = this.activeButtons - 1;

						checkWinAResumed(gridpane, but00, but01, but02, but10, but11, but12, but20, but21, but22);
						break;

					}

				}

			}
		}
                
                
	}
     
     public void checkWinAResumed(GridPane gridpane, Button but00, Button but01, Button but02, Button but10, Button but11,
			Button but12, Button but20, Button but21, Button but22) {
            User usr = new User();
		if ((but00.getText().equals(this.adversaryMark)) && (but01.getText().equals(this.adversaryMark))
				&& (but02.getText().equals(this.adversaryMark))) {
			but00.setStyle("-fx-background-color: #CD5C5C");
			but01.setStyle("-fx-background-color: #CD5C5C");
			but02.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                usr.deleteGame(ID);
		} else if ((but10.getText().equals(this.adversaryMark)) && (but11.getText().equals(this.adversaryMark))
				&& (but12.getText().equals(this.adversaryMark))) {
			but10.setStyle("-fx-background-color: #CD5C5C");
			but11.setStyle("-fx-background-color: #CD5C5C");
			but12.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                        usr.deleteGame(ID);

		} else if ((but20.getText().equals(this.adversaryMark)) && (but21.getText().equals(this.adversaryMark))
				&& (but22.getText().equals(this.adversaryMark))) {
			but20.setStyle("-fx-background-color: #CD5C5C");
			but21.setStyle("-fx-background-color: #CD5C5C");
			but22.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                        usr.deleteGame(ID);

		} else if ((but00.getText().equals(this.adversaryMark)) && (but10.getText().equals(this.adversaryMark))
				&& (but20.getText().equals(this.adversaryMark))) {
			but00.setStyle("-fx-background-color: #CD5C5C");
			but10.setStyle("-fx-background-color: #CD5C5C");
			but20.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                        usr.deleteGame(ID);

		} else if ((but01.getText().equals(this.adversaryMark)) && (but11.getText().equals(this.adversaryMark))
				&& (but21.getText().equals(this.adversaryMark))) {
			but01.setStyle("-fx-background-color: #CD5C5C");
			but11.setStyle("-fx-background-color: #CD5C5C");
			but21.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                        usr.deleteGame(ID);

		} else if ((but02.getText().equals(this.adversaryMark)) && (but12.getText().equals(this.adversaryMark))
				&& (but22.getText().equals(this.adversaryMark))) {
			but02.setStyle("-fx-background-color: #CD5C5C");
			but12.setStyle("-fx-background-color: #CD5C5C");
			but22.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
usr.deleteGame(ID);
		} else if ((but00.getText().equals(this.adversaryMark)) && (but11.getText().equals(this.adversaryMark))
				&& (but22.getText().equals(this.adversaryMark))) {
			but00.setStyle("-fx-background-color: #CD5C5C");
			but11.setStyle("-fx-background-color: #CD5C5C");
			but22.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
usr.deleteGame(ID);
		} else if ((but02.getText().equals(this.adversaryMark)) && (but11.getText().equals(this.adversaryMark))
				&& (but20.getText().equals(this.adversaryMark))) {
			but02.setStyle("-fx-background-color: #CD5C5C");
			but11.setStyle("-fx-background-color: #CD5C5C");
			but20.setStyle("-fx-background-color: #CD5C5C");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}

		}
                usr.deleteGame(ID);
        }
     
     
     public void checkIfWinResumed(GridPane gridpane, Button but00, Button but01, Button but02, Button but10, Button but11,
			Button but12, Button but20, Button but21, Button but22) {
              User usr = new User();
		if ((but00.getText().equals(this.playerMark)) && (but01.getText().equals(this.playerMark))
				&& (but02.getText().equals(this.playerMark))) {
			but00.setStyle("-fx-background-color: #00ff00");
			but01.setStyle("-fx-background-color: #00ff00");
			but02.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
usr.deleteGame(ID);
		} else if ((but10.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but12.getText().equals(this.playerMark))) {
			but10.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but12.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
usr.deleteGame(ID);
		} else if ((but20.getText().equals(this.playerMark)) && (but21.getText().equals(this.playerMark))
				&& (but22.getText().equals(this.playerMark))) {
			but20.setStyle("-fx-background-color: #00ff00");
			but21.setStyle("-fx-background-color: #00ff00");
			but22.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                usr.deleteGame(ID);
		} else if ((but00.getText().equals(this.playerMark)) && (but10.getText().equals(this.playerMark))
				&& (but20.getText().equals(this.playerMark))) {
			but00.setStyle("-fx-background-color: #00ff00");
			but10.setStyle("-fx-background-color: #00ff00");
			but20.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                usr.deleteGame(ID);
		} else if ((but01.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but21.getText().equals(this.playerMark))) {
			but01.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but21.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                usr.deleteGame(ID);
		} else if ((but02.getText().equals(this.playerMark)) && (but12.getText().equals(this.playerMark))
				&& (but22.getText().equals(this.playerMark))) {
			but02.setStyle("-fx-background-color: #00ff00");
			but12.setStyle("-fx-background-color: #00ff00");
			but22.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
usr.deleteGame(ID);
		} else if ((but00.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but22.getText().equals(this.playerMark))) {
			but00.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but22.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
usr.deleteGame(ID);
		} else if ((but02.getText().equals(this.playerMark)) && (but11.getText().equals(this.playerMark))
				&& (but20.getText().equals(this.playerMark))) {
			but02.setStyle("-fx-background-color: #00ff00");
			but11.setStyle("-fx-background-color: #00ff00");
			but20.setStyle("-fx-background-color: #00ff00");

			ObservableList<Node> children = gridpane.getChildren();
			for (Node child : children) {
				if (child instanceof Button) {
					child.setDisable(true);
				}

			}
                         usr.deleteGame(ID);
                }
                
               
	}

}
