package com.matheus.fases;

import java.awt.Graphics;

import com.matheus.entidades.Jogador;
import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;

public class Fases {
	
	public static boolean apresentacao=false;
	public static boolean colidindoComMago=false;
	
	public int statusCutScene=1,statusJogando=0;
	public int status=statusJogando;
	
	public void atualizar() {
		switch (Jogo.numfase) {
		case 1:
			Jogo.pularFase=logicaFase1();
			break;
		case 2:
			Jogo.pularFase=logicaFase2();
			break;
		case 3:
			Jogo.pularFase=logicaFase3();
			break;

		}
	}
	
	private boolean logicaFase3() {
		/* CONTADOR PROGRESSIVO
			Jogo.contadorDeSegundos++;
			if(Jogo.contadorDeSegundos==60) {
				Jogo.temporizadorS++;
				Jogo.contadorDeSegundos=0;
				if(Jogo.temporizadorS==60) {
					Jogo.temporizadorM++;
					Jogo.temporizadorS=0;
				}
			}
			
			//InimigoMorte.dano=80;
			
			if (Jogo.temporizadorM==1 && Jogo.temporizadorS==20) {
				Jogo.exibeRelogio=false;
				Jogo.noite=false;
				return true;
			}*/
		//CONTADOR REGRESSIVO
		Jogo.contadorDeSegundos--;
		if(Jogo.contadorDeSegundos==0) {
			Jogo.temporizadorS--;
			Jogo.contadorDeSegundos=60;
			if(Jogo.temporizadorS<0) {
				Jogo.temporizadorM--;
				Jogo.temporizadorS=60;
			}
		}
		System.out.println(Jogo.temporizadorM+":"+Jogo.temporizadorS+":"+Jogo.contadorDeSegundos);
		//InimigoMorte.dano=80;
		
		if (Jogo.temporizadorM<=0 && Jogo.temporizadorS<=0) {
			Jogo.jogador.vida=0;
		  
		}
		
		if(colidindoComMago) {
			Jogo.exibeRelogio=false;
			Jogo.noite=false;
			return true; 
		}
			
	return false;	
	}

	public boolean logicaFase1() {
		if(Jogador.verificaColisaoPorta()==1) {
			Jogo.mensagem="Você não possui a chave da porta";
			Jogo.exibirMensagem=true;
		}else if(Jogador.verificaColisaoPorta()==2){
			Jogador.possuiChave=false;
			return true;
		}
		//return false;
		return false;
	}	
	
	public boolean logicaFase2() {
		if (Jogo.inimigo.isEmpty()){
			return true;
		}
		
		return false;
	}

	public static void atualizarCutscene() {
		if (Jogo.estado_cena == Jogo.entrada) {
			if (Jogo.jogador.getX() < 200) {
				Jogo.jogador.x++;
				Jogo.jogador.ultimoClicado = Jogo.jogador.right_dir;
				Jogo.jogador.frames++;
				if (Jogo.jogador.frames == Jogo.jogador.maxFrames) {
					Jogo.jogador.frames = 0;
					Jogo.jogador.index++;
					if (Jogo.jogador.index > Jogo.jogador.maxIndex) {
						Jogo.jogador.index = 0;
					}
				}
				Camera.x = Camera.clamp(Jogo.jogador.getX() - (Jogo.WIDITH / 2),
						Mundo.WIDTH_WORD * Jogo.tamanho - Jogo.WIDITH, 0);
				Camera.y = Camera.clamp(Jogo.jogador.getY() - (Jogo.HEIGHT / 2),
						Mundo.HEIGHT_WORD * Jogo.tamanho - Jogo.HEIGHT, 0);
			} else {
				Jogo.estado_cena = Jogo.comecar;
			}
		 }else if (Jogo.estado_cena == Jogo.comecar) {
			
			if (Jogo.pularCena) {
				Jogo.pularCena=false;
				Jogo.estado_cena = Jogo.jogando;
			}
		}
		
	}

	public void renderizar(Graphics g) {
		if(apresentacao) {
			
		}
		
	}	
	
	

}
