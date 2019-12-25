package com.matheus.entidades.especiais;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.entidades.Entidade;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Tile;

public class Chave extends Entidade{
	
	private int index = 0, frames = 0, maxFrames = 25, maxIndex = 1;
	private BufferedImage[] chaves;
	
	
	public Chave(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		chaves=new BufferedImage[2];
		chaves[0]= Tile.TILE_CHAVE;
		chaves[1]=Tile.TILE_CHAVE_2;
	}
	
	public void renderizar(Graphics g) {
		g.drawImage(chaves[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
	
	public void atualizar() {
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
	}

}
