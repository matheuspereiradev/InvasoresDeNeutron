package com.matheus.graficos;

import java.awt.Color;
import java.awt.Graphics;

import com.matheus.game.Jogo;
import com.matheus.mundo.Tile;

public class Dialogos{
	
	public String [] dialogosFase1=new String[3];
	public int numdialogo=0;
	
	public Dialogos() {
		dialogosFase1[0]="OI me chamo roberto";
		dialogosFase1[1]="Prazer roberto";
		dialogosFase1[2]="Você sabe o que está acontecendo";
		
		
	}
	
	public void atualizar() {
		if(Jogo.pularCena) {
			numdialogo++;
			if(numdialogo>=dialogosFase1.length) {
				Jogo.estado_cena=Jogo.jogando;
				Jogo.jogador.colidindoComNPC=false;
				Jogo.conversar=false;
				Jogo.pularCena=false;
				numdialogo=0;
			}
			Jogo.pularCena=false;
		}
	}
	
	public void renderizar(Graphics g) {
		switch (Jogo.numfase) {
		case 1:
			g.drawImage(Tile.CAIXADEDIALOGO, 15, 150, null);
			g.setColor(Color.black);
			g.drawString(dialogosFase1[numdialogo],40 , 160);
			break;
		}
		
	}
}
