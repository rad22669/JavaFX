package controller;

import java.io.File;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.*;
import view.*;

/**
 * @author Matija Radomirovic
 * @version 18/03/2022
 */
public class Controller implements EventHandler<Event>{
	private Frame frame; //View
	private Datei datei; //Model
	
	/**
	 * Defaul-Konstruktor.
	 * @param frame
	 */
	public Controller(Frame frame) {
		this.frame = frame;
		this.datei = new Datei();
	}
	
	@Override
	public void handle(Event event) {
		Object type = event.getEventType(), source = event.getSource();
		if(type == KeyEvent.KEY_TYPED) {
			if(source == frame.getEingabeDateiname()) {
				String input = ((KeyEvent)event).getCharacter();
				if(input.matches("[\\\\/:*?\"<>|]")) {
					frame.getEingabeDateiname().setStyle("-fx-text-box-border: #FF4C4C; -fx-focus-color: #FF4C4C; -fx-faint-focus-color: #d3524422;");
					frame.getStatus().setText("Bitte geben Sie keine \\/:*?\"<>| ein!");
					frame.getStatus().setTextFill(Color.web("#FF4C4C"));
					frame.getEingabeDateiname().setUserData("wrongChar");
				}
			}
		}
		if(type == KeyEvent.KEY_PRESSED) {
			if(source == frame.getEingabeDateiname()) {
				if(frame.getEingabeDateiname().getUserData()=="wrongChar") {
					if(!((KeyEvent)event).getCode().equals(KeyCode.BACK_SPACE)) {
						Pattern pattern = Pattern.compile("[a-zA-Z]*");
						UnaryOperator<TextFormatter.Change> filter = a -> {
				            if(pattern.matcher(a.getControlNewText()).matches()) {return a;} else return null;
				        };
				        frame.getEingabeDateiname().setTextFormatter(new TextFormatter<String>(filter));
					}
					else {
						frame.getEingabeDateiname().setTextFormatter(null);
						frame.getStatus().setText("Status: Neues Dokument");
						frame.getStatus().setTextFill(Color.web("#333333"));
						frame.getEingabeDateiname().setUserData(null);
					}
				}
			}
		}
		if(type == KeyEvent.KEY_RELEASED) {
			if(source == frame.getEingabeDateiname()) {
				if(frame.getEingabeDateiname().getText().length()<1) {
					frame.getEingabeDateiname().setStyle("-fx-focus-color: #41b6dd");
					frame.getEingabeDateiname().setUserData(null);
				}
				if(frame.getEingabeDateiname().getUserData()!="wrongChar"&&frame.getEingabeDateiname().getText().length()>0&&!frame.getEingabeDateiname().getText().toString().isBlank()) {
					frame.getEingabeDateiname().setStyle("-fx-text-box-border: #3CB371; -fx-focus-color: #3CB371; -fx-faint-focus-color: #3CB37122;");
					frame.getEingabeDateiname().setUserData("done");
				}
			}
			if(source == frame.getEingabeTitel()) {
				if(frame.getEingabeTitel().getText().length()<1) {
					frame.getEingabeTitel().setStyle("-fx-focus-color: #41b6dd");
					frame.getEingabeTitel().setUserData(null);
				}
				if(frame.getEingabeTitel().getText().length()>0) {
					frame.getEingabeTitel().setStyle("-fx-text-box-border: #3CB371; -fx-focus-color: #3CB371; -fx-faint-focus-color: #3CB37122;");
					frame.getEingabeTitel().setUserData("done");
				}
			}
			if(source == frame.getEditieren()) updateFrame();
		}
		if(type == ActionEvent.ACTION) {
			if(source == frame.getLadenOderSpeichern(1)) {
				if(frame.getEingabeDateiname().getUserData()=="done"&&frame.getEingabeTitel().getUserData()=="done"){
					frame.getStatus().setText("Status: Neues Dokument");
					frame.getStatus().setTextFill(Color.web("#333333"));
					frame.getFileChooser().setInitialFileName(frame.getEingabeDateiname().getText());
					if(frame.getTextOderBinär(0).isSelected()) {
						frame.getFileChooser().getExtensionFilters().clear();
						frame.getFileChooser().getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Textdateien", "*.txt"));
						File saveFile = frame.getFileChooser().showSaveDialog(frame);
						if(saveFile!=null) datei.createFile_loadFile(true, saveFile, frame.getEingabeTitel().getText(), frame.getEditieren().getText(), false);
					}
					if(frame.getTextOderBinär(1).isSelected()) {
						frame.getFileChooser().getExtensionFilters().clear();
						frame.getFileChooser().getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DAT-Dateien", "*.dat"));
						File saveFile = frame.getFileChooser().showSaveDialog(frame);
						if(saveFile!=null) datei.createFile_loadFile(true, saveFile, frame.getEingabeTitel().getText(), frame.getEditieren().getText(), true);
					}
				}
				else if(frame.getEingabeDateiname().getUserData()!="wrongChar"){
					frame.getStatus().setTextFill(Color.web("#FF4C4C"));
					if(frame.getEingabeDateiname().getUserData()!="done") {
						frame.getStatus().setText("Status: Bitte geben Sie einen Dateinamen ein!");
						frame.getEingabeDateiname().setStyle("-fx-text-box-border: #FF4C4C; -fx-focus-color: #FF4C4C; -fx-faint-focus-color: #d3524422;");
						frame.getEingabeTitel().setStyle(null);
					}
					else {
						frame.getStatus().setText("Status: Bitte geben Sie einen Titel ein!");
						frame.getEingabeTitel().setStyle("-fx-text-box-border: #FF4C4C; -fx-focus-color: #FF4C4C; -fx-faint-focus-color: #d3524422;");
						frame.getEingabeDateiname().setStyle(null);
					}
					
				}
			}
			if(source == frame.getLadenOderSpeichern(0)) {
				if(frame.getTextOderBinär(0).isSelected()) {
					frame.getFileChooser().getExtensionFilters().clear();
					frame.getFileChooser().getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Textdateien", "*.txt"));
				}
				else {
					frame.getFileChooser().getExtensionFilters().clear();
					frame.getFileChooser().getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DAT-Dateien", "*.dat"));
				}
				File openFile = frame.getFileChooser().showOpenDialog(frame);
					if(openFile!=null) {
						String[] args=null;
						if(frame.getTextOderBinär(0).isSelected()) args = datei.createFile_loadFile(false, openFile, null, null, false);
						else args = datei.createFile_loadFile(false, openFile, null, null, true);
						frame.getEingabeDateiname().setText(args[0]);
						frame.getEingabeTitel().setText(args[1]);
						frame.getEditieren().setText(args[2]);
						updateFrame();
					}
			}
			if(source == frame.getRefresh()) {
				frame.getEingabeDateiname().setText("");
				frame.getEingabeDateiname().setUserData(null);
				frame.getEingabeDateiname().setStyle(null);
				frame.getEingabeTitel().setText("");
				frame.getEingabeTitel().setUserData(null);
				frame.getEingabeTitel().setStyle(null);
				frame.getEditieren().setText("");
				updateFrame();
			}
		}
		if(type == MouseEvent.MOUSE_PRESSED) {if(source == frame.getBorderPane()) frame.getBorderPane().requestFocus();}
	}
	
	private void updateFrame() {
		final KeyFrame updateStatus = new KeyFrame(Duration.seconds(1), e -> frame.getStatus().setText(datei.anzahlWoerter(frame.getEditieren().getText().toString())!=0?"Status: Neues Dokument - "+datei.anzahlWoerter(frame.getEditieren().getText().toString())+(datei.anzahlWoerter(frame.getEditieren().getText().toString())==1?" Wort":" Wörter"):"Status: Neues Dokument"));
		final KeyFrame updateStatusColor = new KeyFrame(Duration.seconds(1.05), e -> frame.getStatus().setTextFill(Color.web("#333333")));
		final Timeline timeline = new Timeline(updateStatus, updateStatusColor);
		Platform.runLater(timeline::play);
		frame.getEingabeDateiname().setUserData((frame.getEingabeDateiname().getUserData()=="wrongChar"||frame.getEingabeDateiname().getText().isBlank())?(frame.getEingabeDateiname().getUserData()=="wrongChar"?"wrongChar":null):"done");
		frame.getEingabeTitel().setUserData(frame.getEingabeTitel().getText().isBlank()?null:"done");
	}
}