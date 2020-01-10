package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;

public class Mago extends Entidade{

	public static double speed = 0.9;
	private int maskX = 8, maskY = 8, maskW = 10, maskH = 10;
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
			cimaMago[i] = Jogo.spritesheet.getSprite(576 + (Jogo.tamanho * i), 122, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < baixoMago.length; i++) {
			baixoMago[i] = Jogo.spritesheet.getSprite(576 + (Jogo.tamanho * i), 96, Jogo.tamanho, Jogo.tamanho);
		}
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
		
	}

}
