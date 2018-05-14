package tictactoe;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 *
 * @author User
 */
public class TicTacToe extends Application {
    
    int width,height;
    Scene logInScene;
    User playerInfo;
    Alert about;
    
//    public  void myAlert(String msg){
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("ERROR");
//        alert.setHeaderText("ERROR");
//        alert.setContentText(msg);
//        alert.showAndWait();
//    }
       
    @Override
    public void start(Stage primaryStage) {
        width = 800;
        height = 600;
        playerInfo=new User();
        UserUI ui=new UserUI();
        logInScene = new Scene(ui.getSignInView( playerInfo, primaryStage), width, height);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(logInScene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent event) {
                playerInfo.logOut();
            }
        });
    }

   
    public static void main(String[] args) {
        launch(args);
    }
}
