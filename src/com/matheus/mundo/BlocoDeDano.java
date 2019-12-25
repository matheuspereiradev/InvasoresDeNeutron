package com.matheus.mundo;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import com.matheus.entidades.Entidade;
import com.matheus.game.Jogo;
import com.matheus.game.Sons;

public class BlocoDeDano extends Tile{

	public BlocoDeDano(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}
	
	public static boolean isColiddingTileEntidade(Entidade entidade, BlocoDeDano tile) {
		Rectangle ent = new Rectangle(entidade.getX() + entidade.getMaskX(), entidade.getY() + entidade.getMaskY(), entidade.getMaskW(), entidade.getMaskH());
		Rectangle til = new Rectangle(tile.getX(), tile.getY(), Jogo.tamanho, Jogo.tamanho);
		return ent.intersects(til);
	}
	
	public static void danoNaLava() {
		if (Jogo.rand.nextInt(100) < 20) {
			Jogo.jogador.vida-=15;
			Jogo.jogador.sofrendoDano = true;
			if (!Jogo.mute) {
				Sons.lava.play();
			}
		}
	}

}
