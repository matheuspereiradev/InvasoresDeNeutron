package com.matheus.entidades;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.matheus.fases.Fases;
import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;

public class Mago extends Entidade{

	public static double speed = 0.7;
	public boolean movendo=false;
	
	private int dir_up = 1, dir_right = 0, dir_down = 2, dir_left = 3;
	private int direcao = dir_down;
	private int index = 0, frames = 0, maxFrames = 10, maxIndex = 2, tamanhoArray = 3;
	public static int distanciaDeAlcanceDoAtaque = 60;
	
	int sortearDirecao = Jogo.rand.nextInt(4);
	int tempoDirecao = 0, maxTempoDirecao = 40;
	boolean direcaoDisponivel = false;

	private BufferedImage[] direitaMago;
	private BufferedImage[] esquerdaMago;
	private BufferedImage[] cimaMago;
	private BufferedImage[] baixoMago;
	
	public Mago(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		direitaMago = new BufferedImage[tamanhoArray];
		esquerdaMago = new BufferedImage[tamanhoArray];
		cimaMago = new BufferedImage[tamanhoArray];
		baixoMago = new BufferedImage[tamanhoArray];

		for (int i = 0; i < direitaMago.length; i++) {
			direitaMago[i] = Jogo.spritesheet.getSprite(576 + (Jogo.tamanho * i), 128, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < esquerdaMago.length; i++) {
			esquerdaMago[i] = Jogo.spritesheet.getSprite(576 + (Jogo.tamanho * i), 144, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < cimaMago.length; i++) {
			cimaMago[i] = Jogo.spritesheet.getSprite(576 + (Jogo.tamanho * i), 112, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < baixoMago.length; i++) {
			baixoMago[i] = Jogo.spritesheet.getSprite(576 + (Jogo.tamanho * i), 96, Jogo.tamanho, Jogo.tamanho);
		}
	}
	
	public void atualizar() {
		movendo = false;
		
		//direçao
		if (tempoDirecao == maxTempoDirecao) {
			tempoDirecao = 0;
			sortearDirecao=Jogo.rand.nextInt(4);
		} else {
			tempoDirecao++;
		}

		if (sortearDirecao == 0 && Mundo.isFree((int) (x + speed), this.getY())
				&& !(Mundo.isDoor((int) (x + speed), this.getY()))) {
			movendo = true;
			direcao = dir_right;
			x += speed;
		} else if (sortearDirecao == 1 && Mundo.isFree((int) (x - speed), this.getY())
				&& !(Mundo.isDoor((int) (x - speed), this.getY()))) {
			movendo = true;
			direcao = dir_left;
			x -= speed;
		}

		else if (sortearDirecao == 2 && Mundo.isFree(this.getX(), (int) (y + speed))
				 && !(Mundo.isDoor(this.getX(), (int) (y + speed)))) {
			movendo = true;
			direcao = dir_down;
			y += speed;
		} else if (sortearDirecao == 3 && Mundo.isFree(this.getX(), (int) (y - speed))
				&& !(Mundo.isDoor(this.getX(), (int) (y - speed)))) {
			movendo = true;
			direcao = dir_up;
			y -= speed;
		}
		//fim caminhar
		if (movendo) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}
		
		if(colisaoComJogador(this.getX(), this.getY(), this.maskX, this.maskY, this.maskW, this.maskH)) {
			//Jogo.mensagem="Presione Q para falar com o mago";
			//Jogo.exibirMensagem=true;
			Fases.colidindoComMago=true;
		};
	}

	public void renderizar(Graphics g) {

			if (direcao == dir_right) {
				g.drawImage(direitaMago[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (direcao == dir_left) {
				g.drawImage(esquerdaMago[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			if (direcao == dir_up) {
				g.drawImage(cimaMago[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (direcao == dir_down) {
				g.drawImage(baixoMago[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			
			/*super.renderizar(g);
			 g.setColor(Color.BLUE);
			 g.fillRect(this.getX()+maskX-Camera.x, this.getY()+maskY-Camera.y,
			 maskW,maskH);*/
		
	}
	
	public static boolean colisaoComJogador(int x, int y, int mascaraX, int mascaraY, int width, int height) {
		Rectangle mago = new Rectangle(x + mascaraX, y + mascaraY, width, height);
		Rectangle jogador = new Rectangle(Jogo.jogador.getX(), Jogo.jogador.getY(), Jogo.tamanho, Jogo.tamanho);
		return mago.intersects(jogador);
	}

}
