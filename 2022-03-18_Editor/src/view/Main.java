package view;
	
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Matija Radomirovic
 * @version 18/03/2022
 */
public class Main extends Application {
	
	@Override
	/** Erzeugt die GUI.*/
	public void start(Stage stage) {new Frame();}
	
	/** Startet das Programm.*/
	public static void main(String[] args) {launch(args);}
}