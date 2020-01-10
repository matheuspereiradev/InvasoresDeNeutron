package com.matheus.graficos;

import java.awt.Color;
import java.awt.Graphics;

import com.matheus.game.Jogo;
import com.matheus.mundo.Tile;

public class Dialogos{
	
	public String [] dialogosAleatorios;
	public int numdialogo=0;
	
	public Dialogos() {
		dialogosAleatorios=new String []{"Oi é um prazer lhe ver por aqui", "Estou cansado, não aguento mais trabalhar"
				,"Malditos aliens, alguém tem que fazer algo", "Oi me chamo Ariel","Estou faminto", "Somos apenas escravos nesse mundo"
				, "Pensando bem, eu até gosto dos aliens", "Que mundo caótico","Pode me chamar de K\\so isso"};
		numdialogo=Jogo.rand.nextInt(dialogosAleatorios.length);
		
	}
	
	public void atualizar() {
		if(Jogo.pularCena) {
			numdialogo=Jogo.rand.nextInt(dialogosAleatorios.length);
			Jogo.estado_cena=Jogo.jogando;
			Jogo.jogador.colidindoComNPC=false;
			Jogo.conversandoComNPC=false;
			Jogo.pularCena=false;
				
		}
	}
	
	public void renderizar(Graphics g) {
		switch (Jogo.numfase) {
		case 1:
			escreverDialogo(dialogosAleatorios[numdialogo],g);
			break;
		}
		
	}

	public static void escreverDialogo(String frase, Graphics g) {
		g.drawImage(Tile.CAIXADEDIALOGO, 15, 150, null);
		g.setColor(Color.black);
		
		if(frase.contains("\\")) {
			String linha1, linha2;
			linha1=frase.substring(0, frase.indexOf("\\"));
			linha2=frase.substring(frase.indexOf("\\")+1,frase.length());
			g.drawString(linha1,42 , 165);
			g.drawString(linha2,42 , 175);
		}else {
			g.drawString(frase,42 , 165);
		}
		
	}
}
