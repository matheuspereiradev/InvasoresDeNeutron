package com.matheus.graficos;

import java.awt.Graphics;

import com.matheus.entidades.Entidade;
import com.matheus.entidades.Jogador;
import com.matheus.game.Jogo;
import com.matheus.mundo.Tile;

public class UI {


	public int quantidadeDeCoracoes = 11;
	public int CoracoesMax = 11;
	public Dialogos dialogos;

	
	public UI() {
		dialogos=new Dialogos();
	}

	public void atualizar() {
		quantidadeDeCoracoes = (int) (Jogo.jogador.vida / 10) + 1;
		if(Jogo.estado_cena==Jogo.dialogo) {
			dialogos.atualizar();
		}
		
	}
	
	public void renderizar(Graphics g) {

/*	COLOCAR BARRA DE LIFE
 * 	g.setColor(Color.RED);
	g.fillRect(12, 5, 82, 10);
	g.setColor(new Color(0, 127, 14));
	g.fillRect(12, 5, (int) ((Jogo.jogador.vida / Jogador.MAX_LIFE) * 82), 10);*/
		g.drawImage(Tile.VIDA_CORACAO_VERDE, 265, 10, null);
		
		
		if(Jogador.possuiChave) {
			g.drawImage(Tile.TILE_CHAVE_ICONE, 145, 200,null);
		}
		if(Jogo.jogador.armado) {
			g.drawImage(Tile.TILE_ARMA_ICONE, 125, 200,null);
		}
		
		if(Jogo.estado_cena==Jogo.dialogo) {
			dialogos.renderizar(g);
		}
		
		for (int i = 0; i < CoracoesMax; i ++) {
			g.drawImage(Entidade.coracao_2, 5 + (i * 10), 5, null);
		}
		for (int i = 0; i < quantidadeDeCoracoes; i ++) {
			g.drawImage(Entidade.coracao, 5 + (i * 10), 5, null);
		}
	}

	

}
