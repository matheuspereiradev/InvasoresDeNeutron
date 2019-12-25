package com.matheus.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.matheus.mundo.Mundo;

public class Salvar {
	
	public static boolean saveExists=false, saveGame=false;
	
	
	public static void salvarJogo(String []val1,int []val2,int encode) {
		BufferedWriter bufferedwriter=null;
		
		try {
			bufferedwriter=new BufferedWriter(new FileWriter("save.txt"));
		}catch(IOException e) {
			System.out.println(e);
		}
		for(int i=0;i<val1.length;i++) {
			String current=val1[i];
			current+=":";
			char[]value=Integer.toString(val2[i]).toCharArray();
			for(int y=0;y<value.length;y++) {
				value[y]+=encode;
				current+=value[y];
			}
			try {
				bufferedwriter.write(current);
				if(i<val1.length-1) {
					bufferedwriter.newLine();
				}
			}catch (IOException e) {
				System.out.println(e);
			}
		}
		try {
			bufferedwriter.flush();
			bufferedwriter.close();
		}catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static String carregarJogo(int encode) {
		String linha="";
		File file=new File("save.txt");
		if(file.exists()) {
			try {
				String linhaSimples=null;
				BufferedReader carrega=new BufferedReader(new FileReader("save.txt"));
				try {
					while ((linhaSimples=carrega.readLine())!=null) {
						String[] trad=linhaSimples.split(":");
						char[]valores=trad[1].toCharArray();
						trad[1]="";
						for(int i=0; i<valores.length;i++) {
							valores[i]-=encode;
							trad[1]+=valores[i];
						}
						linha+=trad[0];
						linha+=":";
						linha+=trad[1];
						linha+="/";
					}
					
				}catch (IOException e) {
					
				}
			}catch (FileNotFoundException e) {
				
			}
			
		}
		return linha;
	}
	
	public static void applySave(String string) {
		String []str=string.split("/");
		for(int i=0;i<str.length;i++) {
			String[]str2=str[i].split(":");
			switch (str2[0]) {
			case "level":
				
				Jogo.numfase=Integer.parseInt(str2[1]);
				Mundo.proximaFase();
				Jogo.status="NORMAL";
				Menu.pausa=false;
				break;
			case "vida":
				Jogo.jogador.vida=Integer.parseInt(str2[1]);
				break;
			default:
				break;
			}
		}
	}

}
