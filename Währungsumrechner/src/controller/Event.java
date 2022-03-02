package controller;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.*;
import view.*;

/**
 * @author Matija Radomirovic
 * @version 20/02/2022
 */
public class Event implements EventHandler<ActionEvent> {
	private Frame frame; //View
	private Waehrung waehrung; //Model
	
	/**
	 * Defaul-Konstruktor.
	 * @param frame
	 */
	public Event(Frame frame) {
		this.frame = frame;
		this.waehrung = new Waehrung();
	}

	@Override
	/**
	 * Alle Components, die ein ActionEvent haben, werden hier behandelt (Class: Event).
	 */
	public void handle(ActionEvent aEvent) {
		Object source = aEvent.getSource(); // --> wo wurde die Aktion ausgelöst (Auslöser finden -> dann reagieren).
		if(source == frame.getWechselrichtung(0)) {
			frame.getWechselrichtung(0).setSelected(true);
			frame.getWechselrichtung(1).setSelected(false);
			frame.getAusgewaehlterKurs().setText("Euro  =  ");
			frame.getAktuellerKurs().setText("1€ = " + waehrung.euroToFremd_fremdToEuro("src/model/waehrungen.txt", 0).get(frame.getAuswahlWaehrung().getValue()) + " " + frame.getAuswahlWaehrung().getValue());
		}
		if(source == frame.getWechselrichtung(1)) {
			frame.getWechselrichtung(1).setSelected(true);
			frame.getWechselrichtung(0).setSelected(false);
			frame.getAusgewaehlterKurs().setText(frame.getAuswahlWaehrung().getValue()+" = ");
			frame.getAktuellerKurs().setText("1 " + frame.getAuswahlWaehrung().getValue() + " = " + waehrung.euroToFremd_fremdToEuro("src/model/waehrungen.txt", 1).get(frame.getAuswahlWaehrung().getValue()) + "€");
		}
		if(source == frame.getInputAmount()) {frame.getOutputAmount().requestFocus();}
		if(source == frame.getOutputAmount()) {frame.getAuswahlWaehrung().requestFocus();}
		if(source == frame.getAuswahlWaehrung()) {
			if(frame.getWechselrichtung(0).isSelected()) {
				frame.getAusgewaehlterKurs().setText("Euro  =  ");
				frame.getAktuellerKurs().setText("1€ = " + waehrung.euroToFremd_fremdToEuro("src/model/waehrungen.txt", 0).get(frame.getAuswahlWaehrung().getValue()) + " " + frame.getAuswahlWaehrung().getValue());
			}
			else {
				frame.getAusgewaehlterKurs().setText(frame.getAuswahlWaehrung().getValue()+" = ");
				frame.getAktuellerKurs().setText("1 " + frame.getAuswahlWaehrung().getValue() + " = " + waehrung.euroToFremd_fremdToEuro("src/model/waehrungen.txt", 1).get(frame.getAuswahlWaehrung().getValue()) + "€");
			}
		}
		if(source == frame.getCalculate()) {
			if(frame.getInputAmount().getText().trim().isEmpty()) {
				frame.getOutputAmount().setText("?");
				frame.getMessageBottom().setText("Bitte geben Sie einen Betrag ein!");
				frame.getMessageBottom().setTextFill(Color.web("#FF4C4C"));
			}
			else {
				try {
					float inAmount = Float.parseFloat(frame.getInputAmount().getText().trim().replaceAll(",", ".").replaceAll(" ", ""));
					float res=0;
					if(frame.getWechselrichtung(0).isSelected()) 
						res = waehrung.berechne(inAmount, waehrung.euroToFremd_fremdToEuro("src/model/waehrungen.txt", 0).get(frame.getAuswahlWaehrung().getValue()));
					else res = waehrung.berechne(inAmount, waehrung.euroToFremd_fremdToEuro("src/model/waehrungen.txt", 1).get(frame.getAuswahlWaehrung().getValue()));
					res = Float.parseFloat(String.format("%.2f", res).replace(",", "."));
					frame.getOutputAmount().setText(String.valueOf(res));
					frame.getMessageBottom().setTextFill(Color.web("#3CB371"));
					frame.getMessageBottom().setText("Fertig!");
					
				}catch(NumberFormatException nfe) {
					frame.getMessageBottom().setTextFill(Color.web("#FF4C4C"));
					frame.getMessageBottom().setText("Fehler: "+'\u0022'+frame.getInputAmount().getText()+'\u0022'+" ist keine Zahl!");
					frame.getOutputAmount().setText("?");
				}
			}
		}
		
	}
	/**
	 * Unterklasse von Event (Event.Mouse)
	 * @author Matija Radomirovic
	 * @version 20/02/2022
	 */
	public static class Mouse implements EventHandler<MouseEvent>{
		private Frame frame; // View
		
		/**
		 * Default-Konstruktor für die Unterklasse Event.Mouse (MouseEvent).
		 * @param frame
		 */
		public Mouse(Frame frame) {this.frame = frame;}

		@Override
		/**
		 * Alle Components, die ein MouseEvent haben, werden hier behandelt (Class: Event.Mouse).
		 */
		public void handle(MouseEvent mEvent) {
			Object source = mEvent.getSource(); // woher kam der Aufruf...
			if(source == frame.getWechselrichtung(0)) frame.getWechselrichtung(0).borderProperty();
			if(source == frame.getWechselrichtung(1)) frame.getWechselrichtung(1).borderProperty();
			if(source == frame.getGridPane()) frame.getGridPane().requestFocus();
		}
	}
	
	/**
	 * Unterklasse von Event (Event.Key)
	 * @author Matija Radomirovic
	 * @version 20/02/2022
	 */
	public static class Key implements EventHandler<KeyEvent>{
		private Frame frame; // View
		private Waehrung waehrung; // Model
		
		/**
		 * Default-Konstruktor für die Unterklasse Event.Key (KeyEvent).
		 * @param frame
		 */
		public Key(Frame frame) {
			this.frame = frame;
			this.waehrung = new Waehrung();
		}

		@Override
		/**
		 * Alle Components, die ein KeyEvent haben, werden hier behandelt (Class: Event.Key).
		 */
		public void handle(KeyEvent kEvent) {
			Object source = kEvent.getSource();
			if(source == frame.getWechselrichtung(0)) {
				if(kEvent.getCode().equals(KeyCode.ENTER)) {
					frame.getAusgewaehlterKurs().setText("Euro  =  ");
					frame.getAktuellerKurs().setText("1€ = " + waehrung.euroToFremd_fremdToEuro("src/model/waehrungen.txt", 0).get(frame.getAuswahlWaehrung().getValue()) + " " + frame.getAuswahlWaehrung().getValue());
					frame.getWechselrichtung(0).setSelected(true);
					frame.getWechselrichtung(1).setSelected(false);
					frame.getInputAmount().requestFocus();
				}
				if(kEvent.getCode().equals(KeyCode.DOWN))
					frame.getWechselrichtung(1).requestFocus();
			}
			if(source == frame.getWechselrichtung(1)) {
				if(kEvent.getCode().equals(KeyCode.ENTER)) {
					frame.getAusgewaehlterKurs().setText(frame.getAuswahlWaehrung().getValue()+" = ");
					frame.getAktuellerKurs().setText("1 " + frame.getAuswahlWaehrung().getValue() + " = " + waehrung.euroToFremd_fremdToEuro("src/model/waehrungen.txt", 1).get(frame.getAuswahlWaehrung().getValue()) + "€");
					frame.getWechselrichtung(0).setSelected(false);
					frame.getWechselrichtung(1).setSelected(true);
					frame.getInputAmount().requestFocus();
				}
				if(kEvent.getCode().equals(KeyCode.UP))
					frame.getWechselrichtung(0).requestFocus();
				if(kEvent.getCode().equals(KeyCode.DOWN))
					frame.getInputAmount().requestFocus();
			}
			if(source == frame.getInputAmount()) {
				if(kEvent.getCode().equals(KeyCode.ENTER)) {
					frame.getAuswahlWaehrung().requestFocus();
				}
				if(kEvent.getCode().equals(KeyCode.UP))
					frame.getWechselrichtung(1).requestFocus();
				if(kEvent.getCode().equals(KeyCode.RIGHT))
					frame.getAuswahlWaehrung().requestFocus();
			}
			if(source == frame.getAuswahlWaehrung()) {
				if(kEvent.getCode().equals(KeyCode.ENTER))
					frame.getCalculate().requestFocus();
			}
			if(source == frame.getCalculate()) {
				if(frame.getInputAmount().getText().trim().isEmpty()) {
					frame.getOutputAmount().setText("?");
					frame.getMessageBottom().setText("Bitte geben Sie einen Betrag ein!");
					frame.getMessageBottom().setTextFill(Color.web("#FF4C4C"));
				}
				else {
					try {
						float inAmount = Float.parseFloat(frame.getInputAmount().getText().trim().replaceAll(",", ".").replaceAll(" ", ""));
						float res=0;
						if(frame.getWechselrichtung(0).isSelected()) 
							res = waehrung.berechne(inAmount, waehrung.euroToFremd_fremdToEuro("src/model/waehrungen.txt", 0).get(frame.getAuswahlWaehrung().getValue()));
						else res = waehrung.berechne(inAmount, waehrung.euroToFremd_fremdToEuro("src/model/waehrungen.txt", 1).get(frame.getAuswahlWaehrung().getValue()));
						res = Float.parseFloat(String.format("%.2f", res).replace(",", "."));						frame.getOutputAmount().setText(String.valueOf(res));
						frame.getMessageBottom().setTextFill(Color.web("#3CB371"));
						frame.getMessageBottom().setText("Fertig!");
					}catch(NumberFormatException nfe) {
						frame.getMessageBottom().setTextFill(Color.web("#FF4C4C"));
						frame.getMessageBottom().setText("Fehler: "+'\u0022'+frame.getInputAmount().getText()+'\u0022'+" ist keine Zahl!");
						frame.getOutputAmount().setText("?");
					}
				}
			}
		}
	}
}