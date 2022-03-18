package model;

import java.io.*;
import java.util.*;


/**
 * @author Matija Radomirovic
 * @version 18/03/2022
 */
public class Datei {
	
	@SuppressWarnings("deprecation")
	public String[] createFile_loadFile(boolean create, File file, String titel, String content, boolean serialisiert) {
		if(create) {
			if(serialisiert) {
				try (ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)))){
					oos.writeBytes(titel+'\n');
					oos.writeBytes(content);
				}catch(IOException ioe) {}
			}
			else {
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
					bw.write(titel+"\n");
					bw.write(content);
				}catch(IOException ioe) {}
			}
			return null;
		}
		else {
			String args[] = new String[3];
			if(!serialisiert) {
				try (BufferedReader br = new BufferedReader(new FileReader(file))){
					args[0] = file.getName().substring(0, file.getName().toString().lastIndexOf('.'));
					args[1] = br.readLine();
					StringBuilder sb = new StringBuilder();
					String txt="";
					while((txt=br.readLine())!=null)sb.append(txt+'\n');
					args[2] = sb.toString();
				}catch(IOException ioe) {}
			}
			else {
				try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)))){
					args[0] = file.getName().substring(0, file.getName().toString().lastIndexOf('.'));
					args[1] = ois.readLine();
					String txt="";
					StringBuilder sb = new StringBuilder();
					while((txt=ois.readLine())!=null)sb.append(txt+'\n');
					args[2] = sb.toString();
				}catch(IOException ioe ) {ioe.printStackTrace();}
			}
			return args;
		}
	}
	
	public int anzahlWoerter(String text) {
		int cnt=0;
		text = text.replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s+", " ").trim();
		ArrayList<String> textAsList = new ArrayList<>(Arrays.asList(text.split(" ")));
		for(String run : textAsList) {if(run!=" "&&textAsList.toString().length()>2)++cnt;}
		return cnt;
	}
}