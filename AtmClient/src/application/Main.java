package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	private TextField accNum;
	public static final ServerConnect server = new ServerConnect();
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("ATM Login");
			GridPane grid = new GridPane();
			Label accNumlbl = new Label("Account Number: ");
			Label pinlbl = new Label("Pin:");
			accNum = new TextField();
			PasswordField pin = new PasswordField();

			Button button = new Button("Login");
			Label error = new Label();
			
			grid.add(accNumlbl, 0, 0);
			grid.add(accNum, 1, 0);
			grid.add(pinlbl, 0, 1);
			grid.add(pin,1,1);
			grid.add(button, 1, 2);
			grid.add(error, 2, 2);
			grid.setPadding(new Insets(10,10,10,10));
			grid.setVgap(10);
			grid.setAlignment(Pos.CENTER);
			
			root.setCenter(grid);
			server.connect();
			
			
			
			button.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					
					String text = accNum.getText()+ " " + pin.getText();
					server.write(text);
					try {
						String msg = server.read();
						if(msg.equals("true")) {
							MenuScene menu = new MenuScene(primaryStage);
							menu.setMenuScene();

							
						}
						else {
							error.setText(msg);;
							
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println(e.toString());;
					}
					
					
					
					
					// TODO Auto-generated method stub
					
				}
				
			});
			
			
			
			
		
			       

			
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
