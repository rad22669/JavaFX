package view;

import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import controller.*;
import model.*;

/**
 * @author Matija Radomirovic
 * @version 20/02/2022
 */
public class Frame extends Stage{
	
	// Components
	private RadioButton[] wechselRichtung;
	private Label aktuellerKurs, datumVomKurs, ausgewaehlterKurs, messageBottom;
	private TextField inputAmount, outputAmount;
	private ComboBox<String> auswahlWaehrung;
	private Button calculate;
	
	// GUI
	private BorderPane borderPane;
	private GridPane gridPane;
	private HBox hBox;
	
	// ActionEvent, MouseEvent, KeyEvent
	private Event action = new Event(this);
	private Event.Mouse mouse = new Event.Mouse(this);
	private Event.Key key = new Event.Key(this);
	
	// Model
	private Waehrung waehrungen = new Waehrung();
	
	/** Initialisiert zuerst die Components und fügt sie dann der GUI hinzu.*/
	public Frame() {initComp(); initGui();}
	
	/** Initialisiert die Components.*/
	private void initComp() {
		wechselRichtung = new RadioButton[2];
		wechselRichtung[0] = new RadioButton("Euro --> Fremdwährung");
		wechselRichtung[0].setFont(new Font("Lucida Sans Unicode", 13));
		wechselRichtung[0].addEventHandler(ActionEvent.ACTION, action);
		wechselRichtung[0].addEventHandler(MouseEvent.MOUSE_ENTERED, mouse);
		wechselRichtung[0].addEventHandler(KeyEvent.KEY_PRESSED, key);
		wechselRichtung[0].setSelected(true);
		wechselRichtung[1] = new RadioButton("Fremdwährung --> Euro");
		wechselRichtung[1].setFont(new Font("Lucida Sans Unicode", 13));
		wechselRichtung[1].addEventHandler(ActionEvent.ACTION, action);
		wechselRichtung[1].addEventHandler(MouseEvent.MOUSE_ENTERED, mouse);
		wechselRichtung[1].addEventHandler(KeyEvent.KEY_PRESSED, key);
		aktuellerKurs = new Label("1€ = " + waehrungen.euroToFremd_fremdToEuro("src/model/waehrungen.txt", 0).get(waehrungen.waehrungNamen().get(0)) + " " + waehrungen.waehrungNamen().get(0));
		aktuellerKurs.setFont(new Font("Lucida Sans Unicode", 13));
		datumVomKurs = new Label("Kurse vom 18.02.2022");
		datumVomKurs.setFont(new Font("Lucida Sans Unicode", 13));
		ausgewaehlterKurs = new Label("Euro  =  ");
		ausgewaehlterKurs.setFont(new Font("Lucida Sans Unicode", 13));
		messageBottom = new Label("Betrag für die Umrechnung eingeben");
		messageBottom.setTextFill(Color.web("#808080"));
		messageBottom.setFont(new Font("Lucida Sans Unicode", 14));
		inputAmount = new TextField();
		inputAmount.setFont(new Font("Lucida Sans Unicode", 13));
		inputAmount.addEventHandler(ActionEvent.ACTION, action);
		inputAmount.addEventHandler(KeyEvent.KEY_PRESSED, key);
		inputAmount.setPromptText("Enter Amount:");
		outputAmount = new TextField();
		outputAmount.setFont(new Font("Lucida Sans Unicode", 13));
		outputAmount.setDisable(true);
		auswahlWaehrung = new ComboBox<String>(FXCollections.observableList(waehrungen.waehrungNamen()));
		auswahlWaehrung.setStyle("-fx-font: 13px \"Lucida Sans Unicode\";");
		auswahlWaehrung.getSelectionModel().selectFirst();
		auswahlWaehrung.addEventHandler(ActionEvent.ACTION, action);
		auswahlWaehrung.addEventHandler(KeyEvent.KEY_PRESSED, key);
		calculate = new Button("Berechnen");
		calculate.setFont(new Font("Lucida Sans Unicode", 13));
		calculate.addEventHandler(ActionEvent.ACTION, action);
		calculate.addEventHandler(KeyEvent.KEY_PRESSED, key);
	}
	
	/** Initialisiert die GUI.*/
	private void initGui() {
		borderPane = new BorderPane();
		gridPane = new GridPane();
		hBox = new HBox();
		hBox.setPadding(new Insets(0, 0, 5, 5));
		gridPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouse);
		gridPane.setPadding(new Insets(35, 10, 10, 25));
		gridPane.setVgap(10);
		gridPane.setHgap(15);
		gridPane.add(wechselRichtung[0], 0, 0);
		gridPane.add(wechselRichtung[1], 0, 1);
		gridPane.add(aktuellerKurs, 0, 2);
		gridPane.add(datumVomKurs, 3, 2);
		gridPane.add(inputAmount, 0, 3);
		gridPane.add(ausgewaehlterKurs, 1, 3);
		hBox.getChildren().add(messageBottom);
		gridPane.add(outputAmount, 2, 3);
		gridPane.add(auswahlWaehrung, 3, 3);
		gridPane.add(calculate, 2, 5);
		borderPane.setCenter(gridPane);
		borderPane.setBottom(hBox);
		Scene scene = new Scene(borderPane, 800, 320);
		setTitle("Währungsrechner");
		getIcons().add(new Image(getClass().getResourceAsStream("euroIcon.png")));
		setResizable(false);
		setScene(scene);
		showAndWait();
	}
	
	// Get-Methoden für alle Components.
	
	public GridPane getGridPane() {return this.gridPane;}
	public RadioButton getWechselrichtung(int richtung) {return wechselRichtung[richtung];}
	public Label getAktuellerKurs() {return aktuellerKurs;}
	public Label getDatumVomKurs() {return datumVomKurs;}
	public Label getAusgewaehlterKurs() {return ausgewaehlterKurs;}
	public Label getMessageBottom() {return messageBottom;}
	public TextField getInputAmount() {return inputAmount;}
	public TextField getOutputAmount() {return outputAmount;}
	public ComboBox<String> getAuswahlWaehrung(){return auswahlWaehrung;}
	public Button getCalculate() {return calculate;}
}