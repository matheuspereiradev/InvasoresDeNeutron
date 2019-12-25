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
			
			break;

		}
	}
	
	public boolean logicaFase1() {
		//verificar impacto com a porta
		if (Jogador.up && Mundo.isDoor(Jogo.jogador.getX(), (int) (Jogo.jogador.y - Jogo.jogador.speed))) {
			//verifica se o jogador possui a chave
			if(Jogador.possuiChave) {
				Jogador.possuiChave=false;
				return true;
			}else {
				Jogo.mensagem="Você não possui a chave da porta";
				Jogo.exibirMensagem=true;
			}
		} else if (Jogador.down && Mundo.isDoor(Jogo.jogador.getX(), (int) (Jogo.jogador.y + Jogo.jogador.speed))) {
			if(Jogador.possuiChave) {
				Jogador.possuiChave=false;
				return true;
			}else {
				Jogo.mensagem="Você não possui a chave da porta";
				Jogo.exibirMensagem=true;
			}
		}else if (Jogador.left && Mundo.isDoor((int) (Jogo.jogador.x - Jogo.jogador.speed), Jogo.jogador.getY())) {
			if(Jogador.possuiChave) {
				Jogador.possuiChave=false;
				return true;
			}else {
				Jogo.mensagem="Você não possui a chave da porta";
				Jogo.exibirMensagem=true;
			}
		} else if (Jogador.right && Mundo.isDoor((int) (Jogo.jogador.x + Jogo.jogador.speed), Jogo.jogador.getY())) {
			if(Jogador.possuiChave) {
				Jogador.possuiChave=false;
				return true;
			}else {
				Jogo.mensagem="Você não possui a chave da porta";
				Jogo.exibirMensagem=true;
			}
		}
		//
		
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
