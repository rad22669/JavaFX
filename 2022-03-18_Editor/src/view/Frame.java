package view;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import controller.*;

/**
 * @author Matija Radomirovic
 * @version 18/03/2022
 */
public class Frame extends Stage{
	
	// Components
	private Label dateiname, titel, inhalt, status;
	private TextField eingabeDateiname, eingabeTitel;
	private ToggleGroup textOderBinärGroup;
	private RadioButton[] textOderBinär;
	private Button[] ladenOderSpeichern;
	private Button refresh;
	private TextArea editieren;
	private FileChooser fileChooser;
	private KeyCombination[] keyCombs; //Shortcuts 
									   //CTRL + S -> speichern
									   //CTRL + O -> öffnen
									   //CTRL + W -> schließen
									   //CTRL + R -> Neu Laden
	
	// GUI
	private BorderPane borderPane;
	private HBox topBereich, bottomBereich;
	private VBox centerBereichTitel, centerBereichInhalt, centerBereich;
	
	// Events
	private Controller event = new Controller(this);
	
	/** Initialisiert zuerst die Components und fügt sie dann der GUI hinzu.*/
	public Frame() {initComp(); initGui();}
	
	private void initComp() {
		dateiname = new Label("Dateiname:");
		dateiname.setFont(new Font("Verdana", 13));
		titel = new Label("Titel:");
		titel.setFont(new Font("Verdana", 13));
		inhalt = new Label("Inhalt:");
		inhalt.setFont(new Font("Verdana", 13));
		status = new Label("Status: Neues Dokument");
		status.setFont(new Font("Verdana", 13));
		eingabeDateiname = new TextField("");
		eingabeDateiname.setFont(Font.font("Verdana", 13));
		eingabeDateiname.addEventHandler(KeyEvent.KEY_TYPED, event);
		eingabeDateiname.addEventHandler(KeyEvent.KEY_PRESSED, event);
		eingabeDateiname.addEventHandler(KeyEvent.KEY_RELEASED, event);
		eingabeTitel = new TextField("");
		eingabeTitel.setFont(Font.font("Verdana", 13));
		eingabeTitel.addEventHandler(KeyEvent.KEY_RELEASED, event);
		textOderBinärGroup = new ToggleGroup();
		textOderBinär = new RadioButton[2];
		textOderBinär[0] = new RadioButton("text");
		textOderBinär[0].setSelected(true);
		textOderBinär[0].setToggleGroup(textOderBinärGroup);
		textOderBinär[0].setFont(new Font("Verdana", 13));
		textOderBinär[1] = new RadioButton("binär");
		textOderBinär[1].setToggleGroup(textOderBinärGroup);
		textOderBinär[1].setFont(new Font("Verdana", 13));
		ladenOderSpeichern = new Button[2];
		ladenOderSpeichern[0] = new Button("laden");
		ladenOderSpeichern[0].setFont(new Font("Verdana", 13));
		ladenOderSpeichern[0].setPrefWidth(90);
		ladenOderSpeichern[0].setPrefHeight(27);
		ladenOderSpeichern[0].addEventHandler(ActionEvent.ACTION, event);
		ladenOderSpeichern[1] = new Button("speichern");
		ladenOderSpeichern[1].setFont(new Font("Verdana", 13));
		ladenOderSpeichern[1].setPrefWidth(90);
		ladenOderSpeichern[1].setPrefHeight(27);
		ladenOderSpeichern[1].addEventHandler(ActionEvent.ACTION, event);
		refresh = new Button();
		refresh.setFont(new Font("Verdana", 13));
		refresh.setPrefWidth(40);
		refresh.addEventHandler(ActionEvent.ACTION, event);
		ImageView imageView = new ImageView(new Image(getClass().getResource("refreshIcon.png").toExternalForm()));
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(20);
		refresh.setGraphic(imageView);
		editieren = new TextArea();
		editieren.setFont(Font.font("Verdana", 13));
		editieren.setPrefRowCount(50);
		editieren.addEventFilter(KeyEvent.KEY_RELEASED, event);
		fileChooser = new FileChooser();
		fileChooser.setTitle("Wähle Speicherort");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		keyCombs = new KeyCombination[4];
		keyCombs[0] = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
		keyCombs[1] = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
		keyCombs[2] = new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN);
		keyCombs[3] = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
	}
	
	private void initGui() {
		borderPane = new BorderPane();
		topBereich = new HBox();
		topBereich.setPadding(new Insets(15));
		topBereich.setSpacing(15);
		topBereich.setAlignment(Pos.CENTER_LEFT);
		topBereich.getChildren().addAll(dateiname, eingabeDateiname, textOderBinär[0], textOderBinär[1], ladenOderSpeichern[0], ladenOderSpeichern[1], refresh);
		borderPane.setTop(topBereich);
		centerBereichTitel = new VBox();
		centerBereichTitel.setPadding(new Insets(10, 15, 15, 15));
		centerBereichTitel.setSpacing(7);
		centerBereichTitel.getChildren().addAll(titel, eingabeTitel);
		centerBereichInhalt = new VBox();
		centerBereichInhalt.setPadding(new Insets(10, 15, 15, 15));
		centerBereichInhalt.setSpacing(7);
		centerBereichInhalt.getChildren().addAll(inhalt, editieren);
		centerBereich = new VBox();
		centerBereich.getChildren().addAll(centerBereichTitel, centerBereichInhalt);
		borderPane.setCenter(centerBereich);
		bottomBereich = new HBox();
		bottomBereich.setPadding(new Insets(0, 15, 15, 15));
		bottomBereich.setAlignment(Pos.CENTER_LEFT);
		bottomBereich.setAlignment(Pos.BOTTOM_CENTER);
		bottomBereich.getChildren().addAll(status);
		borderPane.setBottom(bottomBereich);
		borderPane.addEventHandler(MouseEvent.MOUSE_PRESSED, event);
		Scene scene = new Scene(borderPane, 700, 470);
		scene.getAccelerators().put(keyCombs[0], () -> ladenOderSpeichern[0].fire());
		scene.getAccelerators().put(keyCombs[1], () -> ladenOderSpeichern[1].fire());
		scene.getAccelerators().put(keyCombs[2], () -> close());
		scene.getAccelerators().put(keyCombs[3], () -> refresh.fire());
		setMinWidth(scene.getWidth()+20);
		setMinHeight(scene.getHeight()+50);
		setTitle("POS1 Editor");
		getIcons().add(new Image(getClass().getResourceAsStream("editIcon.png")));
		setScene(scene);
		showAndWait();
	}
	
	// Get-Methoden für alle Components.
	
	public Label getDateiname() {return this.dateiname;}
	public Label getTitel() {return this.titel;}
	public Label getInhalt() {return this.inhalt;}
	public Label getStatus() {return this.status;}
	public TextField getEingabeDateiname() {return this.eingabeDateiname;}
	public TextField getEingabeTitel() {return this.eingabeTitel;}
	public RadioButton getTextOderBinär(int zhl) {return textOderBinär[zhl];}
	public Button getLadenOderSpeichern(int zhl) {return ladenOderSpeichern[zhl];}
	public Button getRefresh() {return this.refresh;}
	public TextArea getEditieren() {return this.editieren;}
	public FileChooser getFileChooser() {return this.fileChooser;}
	public BorderPane getBorderPane() {return this.borderPane;}
}