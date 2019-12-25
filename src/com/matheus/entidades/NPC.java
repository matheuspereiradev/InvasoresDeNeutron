package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;

public class NPC extends Entidade {

	public static BufferedImage npc_pirata_1=Jogo.spritesheet.getSprite(64, 128, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_pirata_2=Jogo.spritesheet.getSprite(80, 128, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_agente_1=Jogo.spritesheet.getSprite(64, 144, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_agente_2=Jogo.spritesheet.getSprite(80, 144, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_cabecao_1=Jogo.spritesheet.getSprite(64, 160, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_cabecao_2=Jogo.spritesheet.getSprite(80, 160, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_colorido_1=Jogo.spritesheet.getSprite(0, 128, Jogo.tamanho, Jogo.tamanho);
	public static BufferedImage npc_colorido_2=Jogo.spritesheet.getSprite(16, 128, Jogo.tamanho, Jogo.tamanho);
	
	public static BufferedImage npc[];
	int frames = 0, index = 0;

	public NPC(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.depth = 3;
	}

	public void atualizar() {
		frames++;
		if (frames == 80) {
			frames = 0;
			index++;
			if (index == 2) {
				index = 0;
			}

		}

	}

	public void renderizar(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
