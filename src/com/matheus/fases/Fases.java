package com.matheus.fases;

import com.matheus.entidades.Jogador;
import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;

public class Fases {
	
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
			
			if (Jogo.temporizadorM==1 && Jogo.temporizadorS==0) {
				Jogo.exibeRelogio=false;
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
	
	

}
