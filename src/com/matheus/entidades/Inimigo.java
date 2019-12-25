package com.matheus.entidades;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.matheus.aStar.Node;
import com.matheus.aStar.Vector2i;
import com.matheus.game.Jogo;
import com.matheus.game.Sons;

public class Inimigo extends Entidade {

	protected int vida;
	protected int danoFrames = 10, currentDano = 0;
	protected boolean sofrendoDano = false;
	protected boolean movendo=false;
	protected double speed;
	protected List<Node> caminho;
	protected int power;
	
	public Inimigo(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		depth=0;
	}

	public void findPath(List<Node> caminho) {
		if(caminho!=null) {
			if(caminho.size()>0) {
				Vector2i target=caminho.get(caminho.size()-1).tile;
				//int xprev;
				//int yprev;
				if(x<target.x*16) {
					x++;
					movendo=true;
				}else if(x>target.x*16) {
					x--;
					movendo=true;
				}
				
				if(y<target.y*16) {
					y++;
					movendo=true;
				}else if(y>target.y*16) {
					y--;
					movendo=true;
				}
				
				if(x==target.x*16 && y==target.y*16) {
					caminho.remove(caminho.size()-1);
				}
			}
		}
	}
	
	public static boolean colisaoComJogador(int x, int y, int mascaraX, int mascaraY, int width, int height) {
		Rectangle inimigoAtual = new Rectangle(x + mascaraX, y + mascaraY, width, height);
		Rectangle jogador = new Rectangle(Jogo.jogador.getX(), Jogo.jogador.getY(), Jogo.tamanho, Jogo.tamanho);
		return inimigoAtual.intersects(jogador);
	}

	public void verificarVida() {
		if (vida <= 0) {
			Jogo.entidades.remove(this);
			Jogo.inimigo.remove(this);
		}
	}
	
	public double calcularDistancia(int x1,int x2,int y1,int y2) {
		return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	}

	public void colisaoComBala() {
		for (int i = 0; i < Jogo.balas.size(); i++) {
			Entidade e = Jogo.balas.get(i);
			if (e instanceof AtirarMunicao) {
				if (isColidding(e, this)) {
					if (!Jogo.mute) {
						Sons.danoInimigoSong.play();
					}
					vida--;
					sofrendoDano = true;
					Jogo.balas.remove(e);
					return;
				}
			}
		}
	}
	

	public void testarAtaqueNoPlayer() {
		if (Jogo.rand.nextInt(100) < power) {
			Jogo.jogador.vida--;
			Jogo.jogador.sofrendoDano = true;
			if (!Jogo.mute) {
				Sons.danoSong.play();
			}
		}
	}

}
