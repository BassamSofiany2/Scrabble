
package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Scrabble extends Application
{
	@Override
	public void start(Stage primaryStage)
	{
		Player p1 = new Player("Player 1");
		Player p2 = new Player("Player 2");
		Pool pool = new Pool();
		Board board = new Board();
		Dictionary dic = new Dictionary();
		Player[] players = {p1, p2};
	    UI ui = new UI(board, players, pool, dic);
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
