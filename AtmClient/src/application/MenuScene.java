package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuScene {
	private Label label;
	Stage stage; 
	
	public MenuScene(Stage stage) {
		this.stage = stage; 
		
		

		
	}
	
	public void showScene(Scene scene) {
		stage.setScene(scene);
		
		stage.setTitle("Account Actions");
		stage.show();
	}
	
	private Scene enterAmount() {
		Button enter = new Button("OK");
		TextField amountText = new TextField();
		amountText.setMaxWidth(150);
		Label lbl = new Label("Enter amount");
		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(lbl, amountText, enter);
		vbox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(vbox, 400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		enter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println(amountText.getText());
				Main.server.write(amountText.getText());
				try {
					System.out.println(Main.server.read());
					MenuScene scene = new MenuScene(stage);
					scene.setMenuScene();
				}
				catch(Exception e ) {
					
				}
				// TODO Auto-generated method stub
				
			}
			
		});
		
		return scene;
		
		
	}
	
	public void setMenuScene() {
		VBox vbox = new VBox();
		Button withdraw = new Button("Withdraw");
		Button deposit = new Button("Deposit");
		Button checkBalance = new Button("Balance");
		label = new Label();
		vbox.getChildren().add(withdraw);
		vbox.getChildren().add(deposit);
		vbox.getChildren().add(checkBalance);
		vbox.getChildren().add(label);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
	
		Scene scene = new Scene(vbox,400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);

		
		
		withdraw.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Main.server.write("1");
				try {
					System.out.println(Main.server.read());
					stage.setScene(enterAmount());
					
				}
				catch(Exception e ) {
					
				}
				// TODO Auto-generated method stub
				
			}
			
		});
		
		deposit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Main.server.write("2");
				try {
					String msg = Main.server.read();
					System.out.println(msg);
					stage.setScene(enterAmount());
					
					
				}
				catch(Exception e ) {
					
				}
				// TODO Auto-generated method stub
				
			}
			
		});
		
		checkBalance.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				Main.server.write("3");
				try {
					String msg = Main.server.read();
					label.setText(msg);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				
			}
			
		});

	}
	

}
