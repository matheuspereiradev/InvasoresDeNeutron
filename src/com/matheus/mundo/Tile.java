package com.matheus.mundo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;

public class Tile {
	public static BufferedImage TILE_FLOOR = Jogo.spritesheet.getSprite(144, 0, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_TERRA = Jogo.spritesheet.getSprite(144, 48, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_ARVORE = Jogo.spritesheet.getSprite(144, 16, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_WALL = Jogo.spritesheet.getSprite(176, 16, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_FLOOR_2 = Jogo.spritesheet.getSprite(176, 0, Jogo.tamanho, Jogo.tamanho);
	//public static BufferedImage TILE_LAVA =Jogo.spritesheet.getSprite(208, 16, 16, 16);
	//public static BufferedImage TILE_LAVA_2 =Jogo.spritesheet.getSprite(224, 16, 16, 16);
	public static BufferedImage TILE_PISO_MADEIRA =Jogo.spritesheet.getSprite(256, 64, 16, 16);
	public static BufferedImage TILE_BAU =Jogo.spritesheet.getSprite(272, 64, 16, 16);
	public static BufferedImage TILE_FLOOR_TERRA_CENTRAL = Jogo.spritesheet.getSprite(144, 112, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage TILE_ESCADA_CENTRO=Jogo.spritesheet.getSprite(304, 64, 16, 16);
	public static BufferedImage TILE_ESCADA_ESQUERDO=Jogo.spritesheet.getSprite(288, 64, 16, 16);
	public static BufferedImage TILE_ESCADA_DIREITO=Jogo.spritesheet.getSprite(320, 64, 16, 16);
	public static BufferedImage TILE_BARRIU = Jogo.spritesheet.getSprite(336, 64, 16, 16);
	public static BufferedImage TILE_TAPETE=Jogo.spritesheet.getSprite(288, 0, 16, 16);
	public static BufferedImage TILE_TEIA_ARANHA=Jogo.spritesheet.getSprite(288, 80, 16, 16);
	public static BufferedImage TILE_CHAVE=Jogo.spritesheet.getSprite(160, 32, 16, 16);
	public static BufferedImage TILE_CHAVE_2=Jogo.spritesheet.getSprite(176, 32, 16, 16);
	public static BufferedImage TILE_CHAVE_ICONE=Jogo.spritesheet.getSprite(192, 48, 16, 16);
	public static BufferedImage TILE_ARMA_ICONE=Jogo.spritesheet.getSprite(192, 64, 16, 16);
	
	private BufferedImage sprite;
	private int x, y;

	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void renderizar(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
