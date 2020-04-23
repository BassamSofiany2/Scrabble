package test;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.*;

public class UITest extends Application{

	@Override
	public void start(Stage primaryStage)
	{
	    UI ui = new UI(new Board());
	    ui.setAlignment(Pos.CENTER);
	    StackPane container = new StackPane(ui);
	    Scene scene = new Scene(container);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	}
	
	public static void main(String[] args)
	{
	    launch(args);
	}

}