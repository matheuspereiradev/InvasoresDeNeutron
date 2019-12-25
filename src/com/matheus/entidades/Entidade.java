package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;

public class Entidade {

	public static BufferedImage coracaoVida = Jogo.spritesheet.getSprite(128, 16, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage municaoBalas = Jogo.spritesheet.getSprite(96, 16, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage arma = Jogo.spritesheet.getSprite(128, 48, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage inimigoCaveiraDano = Jogo.spritesheet.getSprite(16, 64, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage inimigoMorteDano = Jogo.spritesheet.getSprite(192,32, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage inimigoAlienDano = Jogo.spritesheet.getSprite(0, 64, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage inimigoGiganteDano = Jogo.spritesheet.getSprite(96, 288, 32, 32);
	public static BufferedImage armaRight = Jogo.spritesheet.getSprite(96, 48, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage armaLeft = Jogo.spritesheet.getSprite(80, 48, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage armaDown = Jogo.spritesheet.getSprite(112, 48, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage coracao = Jogo.spritesheet.getSprite(32, 64, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage coracao_2 = Jogo.spritesheet.getSprite(48, 64, Jogo.tamanho, Jogo.tamanho);
	
	
	
	protected int width, height;
	public double x, y;
	protected BufferedImage sprite;
	
	public static Comparator<Entidade> entidadeSorter = new Comparator<Entidade>() {
		@Override
		public int compare(Entidade n0, Entidade n1) {
			if (n1.depth < n0.depth)
				return +1;

			if (n1.depth > n0.depth)
				return -1;

			return 0;
		}
	};
	
	public int depth;

	protected int maskX, maskY, maskW, maskH;
	// A mascara X e Y dizem o quanto a mascara deve ser movida para baixo e lado
	// a mascara de tamanho é w e h

	public Entidade(double x, double y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;

		this.maskX = 0;
		this.maskY = 0;
		// por padrão a mascara é do tamanho inteiro do jogador passado ao criar uma
		// entidade
		this.maskW = width;
		this.maskH = height;
	}
	
	

	public void setMask(int maskX, int maskY, int maskW, int maskH) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.maskW = maskW;
		this.maskH = maskH;
	}

	public int getMaskX() {
		return maskX;
	}

	public void setMaskX(int maskX) {
		this.maskX = maskX;
	}

	public int getMaskY() {
		return maskY;
	}

	public void setMaskY(int maskY) {
		this.maskY = maskY;
	}

	public int getMaskW() {
		return maskW;
	}

	public void setMaskW(int maskW) {
		this.maskW = maskW;
	}

	public int getMaskH() {
		return maskH;
	}

	public void setMaskH(int maskH) {
		this.maskH = maskH;
	}

	public int getX() {
		return (int) this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public int getY() {
		return (int) this.y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public static boolean isColidding(Entidade e1, Entidade e2) {
		Rectangle mask1 = new Rectangle(e1.getX() + e1.maskX, e1.getY() + e1.maskY, e1.maskW, e1.maskH);
		Rectangle mask2 = new Rectangle(e2.getX() + e2.maskX, e2.getY() + e2.maskY, e2.maskW, e2.maskH);
		
			return mask1.intersects(mask2);
		
	}

	public void atualizar() {

	}

	public void renderizar(Graphics g) {
		g.drawImage(this.sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
