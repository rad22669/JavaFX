package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Matija Radomirovic
 * @version 20/02/2022
 */
public class Waehrung {
	
	/**
	 * Berechnet das Resultat.
	 * @param amount
	 * @param kurs
	 * @return amount*kurs
	 */
	public float berechne(float amount, float kurs) {return amount*kurs;}
	
	/**
	 * Speichert den Namen der Waehrung und je nach Auswahl (0) den Kurs von Euro zu Fremd oder (1) von Fremd zu Euro.
	 * @param fileName
	 * @param auswahl
	 * @return map
	 */
	public HashMap<String, Float> euroToFremd_fremdToEuro(String fileName, int auswahl){
		HashMap<String, Float> map = new HashMap<String, Float>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)))){
			String line=null;
			while((line=br.readLine())!=null) {
				String[] parts = line.split("=");
				if(auswahl==0) 
					map.put(parts[0], Float.parseFloat(String.format("%.3f", Float.parseFloat(parts[1])).replace(",", ".")));
				else map.put(parts[0], Float.parseFloat(String.format("%.3f", Float.parseFloat(parts[2])).replace(",", ".")));
			}
		}catch(IOException ioe) {ioe.printStackTrace();}
		return map;
	}
	
	/**
	 * Gibt eine Liste (ObservableList<String>) aller Waehrungen, die in waehrungen.txt vorkommen für die ComboBox zurück.
	 * @return currencies
	 */
	public ObservableList<String> waehrungNamen(){
		ObservableList<String> currencies = FXCollections.observableArrayList(euroToFremd_fremdToEuro("src/model/waehrungen.txt", 0).keySet().toString().replaceAll("\\[", "").replaceAll("\\]", "").split(", "));
		Collections.sort(currencies, new Comparator<String>() {
			@Override
			public int compare(String w1, String w2) {
				return w1.compareTo(w2);
			}
		});
		return currencies;
	}
}