/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;


import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author LinaMasoud
 */
public class GameUI /*extends Application*/ {
     Image image1;
     Image image2;
     Image image3;
     Image image4;
     Image image5;
     Image image;
     
    public GameUI(int player1) throws FileNotFoundException{
       image1 = new Image(new FileInputStream("D:\\ITI Cloud Platform\\TicTacToeServerConnected2\\TicTacToe-Last\\src\\tictactoe\\images"+player1+".jpg"));
       image2 = new Image(new FileInputStream("D:\\ITI Cloud Platform\\TicTacToeServerConnected2\\TicTacToe-Last\\src\\tictactoe\\images2.jpg"));
       image3 = new Image(new FileInputStream("D:\\ITI Cloud Platform\\TicTacToeServerConnected2\\TicTacToe-Last\\src\\tictactoe\\imagesgrid.png"),500, 500,true,true);
       image4 = new Image(new FileInputStream("D:\\ITI Cloud Platform\\TicTacToeServerConnected2\\TicTacToe-Last\\src\\tictactoe\\imageso.png"),70, 70,false,false);
       image5 = new Image(new FileInputStream("D:\\ITI Cloud Platform\\TicTacToeServerConnected2\\TicTacToe-Last\\src\\tictactoe\\imagesx.png"),70, 70,false,false);
       image = new Image(new FileInputStream("D:\\ITI Cloud Platform\\TicTacToeServerConnected2\\TicTacToe-Last\\src\\tictactoe\\imageswhite.png"),70, 70,false,false);
    }
    
    
    public HBox addHBox() throws FileNotFoundException {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 50, 15, 50));
        hbox.setSpacing(500);
               /////////////////////
        ImageView iv1 = new ImageView(image1);
        iv1.setFitWidth(100);
        iv1.setFitHeight(100);
        Label label1 = new Label("Player 1" , iv1);
        label1.setContentDisplay(ContentDisplay.TOP);
        
        ImageView iv2 = new ImageView(image2);
        iv2.setFitWidth(100);
        iv2.setFitHeight(100);
        Label label2 = new Label("Player 2" , iv2);
        label2.setContentDisplay(ContentDisplay.TOP);
        hbox.getChildren().addAll(label1, label2);
        
        VBox vbox = new VBox();
        Button leaveBtn = new Button();
        leaveBtn.setText("Leave The Game");
        leaveBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //primaryStage.setScene(logInScene);
            }
        });
        
        Button pauseBtn = new Button();
        pauseBtn.setText("Pause The Game");
        pauseBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //primaryStage.setScene(logInScene);
            }
        });
        vbox.getChildren().addAll(leaveBtn, pauseBtn);
        
        HBox fHbox = new HBox();
        fHbox.setPadding(new Insets(15, 30, 15, 30));
        fHbox.setSpacing(30);
        fHbox.getChildren().addAll(hbox,vbox);
        
        fHbox.setStyle("-fx-background-color:#e6b800 ;");
        return fHbox;
    }
    
    public VBox addVBox() {
    VBox vbox = new VBox();
    vbox.setStyle("-fx-background-color:#665200 ;");
    vbox.setPadding(new Insets(20));
    vbox.setSpacing(20);
    vbox.setLayoutX(0);
    vbox.setLayoutY(150);
    vbox.setPrefSize(250, 600);
    Label lbl = new Label("Chatting Window");
    lbl.setFont(Font.font("Amble CN", FontWeight.BOLD, 20));
    vbox.getChildren().add(lbl);
  
    TextArea ta = new TextArea();
    ta.setEditable(false);
    ta.setPrefRowCount(15);
    ta.setPrefColumnCount(150);
    ta.setPrefWidth(200);
    vbox.getChildren().add(ta);
    
    TextField tf = new TextField();
    vbox.getChildren().add(tf);
    
    Button send = new Button("Send Message");
    send.setPadding(new Insets(10,52,10,52));
    send.setPrefSize(210, 30);
    vbox.getChildren().add(send);

    return vbox;
    }
    
    public GridPane addGridPane() throws FileNotFoundException {
        GridPane gp = new GridPane();
        gp.setLayoutX(250);
        gp.setLayoutY(160);
        gp.setPrefSize(750, 500);
        gp.setPadding(new Insets(80,200,80,200));
        gp.setHgap(80);
        gp.setVgap(80);
        
        gp.setBackground(new Background(new BackgroundImage(image3,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT)));
        
        gp.add(new ImageView(image), 0, 0);
        gp.add(new ImageView(image), 0, 1);
        gp.add(new ImageView(image), 0, 2);
        gp.add(new ImageView(image), 1, 0);
        gp.add(new ImageView(image), 1, 1);
        gp.add(new ImageView(image), 1, 2);
        gp.add(new ImageView(image), 2, 0);
        gp.add(new ImageView(image), 2, 1);
        gp.add(new ImageView(image), 2, 2);
        
         
         gp.setOnMousePressed(mouseEvent -> {
		double x = mouseEvent.getX();
                double y = mouseEvent.getY();
           if((x >= 142) && (x < 310)){
               if((y >=3) && (y<149)){
                   gp.add(new ImageView(image4), 0, 0);
               }
               else if((y >=149) && (y<318)){
                   gp.add(new ImageView(image5), 0, 1);
               }
               else if((y >=318) && (y<460)){
                   gp.add(new ImageView(image4), 0, 2);
               }
           }
           else if((x >= 310) && (x < 472)){
               if((y >=3) && (y<149)){
                   gp.add(new ImageView(image4), 1, 0);
               }
               else if((y >=149) && (y<318)){
                   gp.add(new ImageView(image4), 1, 1);
               }
               else if((y >=318) && (y<460)){
                   gp.add(new ImageView(image4), 1, 2);
               }
           }
           else if((x >= 472) && (x < 632)){
               if((y >=3) && (y<149)){
                   gp.add(new ImageView(image4), 2, 0);
               }
               else if((y >=149) && (y<318)){
                   gp.add(new ImageView(image4), 2, 1);
               }
               else if((y >=318) && (y<460)){
                   gp.add(new ImageView(image4), 2, 2);
               }
           }
                 
	});
                
        return gp;
    }
    

    
   /* @Override
    public void start(Stage stage) throws FileNotFoundException {
        BorderPane border = new BorderPane();
        HBox hbox = addHBox();
        VBox vbox = addVBox(); 
        GridPane gp = addGridPane();
        border.setTop(hbox);
        border.setLeft(vbox);
        border.setCenter(gp);
        Group root = new Group();
        root.getChildren().addAll(vbox,hbox,gp);
        Scene scene = new Scene(root,1000,650);
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene); 
        stage.setResizable(false);
        stage.show();
    }
    */
   

    /**
     * @param args the command line arguments
     */
   // public static void main(String[] args) {
     //   launch(args);
   // }
    
}
