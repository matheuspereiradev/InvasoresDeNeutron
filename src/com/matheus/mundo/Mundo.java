package com.matheus.mundo;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import com.matheus.entidades.*;
import com.matheus.entidades.especiais.Chave;
import com.matheus.game.Jogo;
import com.matheus.game.Sons;
import com.matheus.graficos.Spritesheet;

public class Mundo {

	public static Tile[] tiles;
	public static List<Objetos> objetos;

	public static int WIDTH_WORD, HEIGHT_WORD;

	public Mundo(String path) {
		objetos = new ArrayList<Objetos>();
		try {
			BufferedImage mapa = ImageIO.read(getClass().getResource(path));
			WIDTH_WORD = mapa.getWidth();
			HEIGHT_WORD = mapa.getHeight();
			int[] pixels = new int[WIDTH_WORD * HEIGHT_WORD];

			tiles = new Tile[WIDTH_WORD * HEIGHT_WORD];

			mapa.getRGB(0, 0, WIDTH_WORD, HEIGHT_WORD, pixels, 0, WIDTH_WORD);
			for (int xx = 0; xx < WIDTH_WORD; xx++) {
				for (int yy = 0; yy < HEIGHT_WORD; yy++) {
					int atual = xx + (yy * WIDTH_WORD);

					if (tiles[atual] == null) {

						tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, verificarPisoPadrao());
						// padrão é ser piso de madeira

//===========================================TILES===================================================================						
						
						if (pixels[atual] == 0xFF000000) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_PISO_MADEIRA);
							// chao
						} else if (pixels[atual] == 0xFF38FF2D) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR);
							// chao
						}else if (pixels[atual] == 0xFF1EFF61) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_FLOOR_NIGHT);
							// chao
						}else if (pixels[atual] == 0xFF496629) {
							tiles[atual] = new WallTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_ARVORE_NIGHT);
							// Arvore
						}
						
									
						
						else if (pixels[atual] == 0xFFFFFFFF) {
							tiles[atual] = new WallTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_WALL);
							// parede
						}else if (pixels[atual] == 0xFF7C7F67) {
							tiles[atual] = new WallTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_ARVORE);
							// Arvore
						}						
						
//=============================================itens de enfeite=================================
						else if (pixels[atual] == 0xFFFFB1A5) {
							tiles[atual] = new WallTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_BARRIU);
							// Barriu
						}else if (pixels[atual] == 0xFFA5BAFF) {
							tiles[atual] = new WallTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_BAU);
							// bau
						}else if (pixels[atual] == 0xFFFFB55B) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_TAPETE);
							// bau
						}else if (pixels[atual] == 0xFF5B5E52) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_TEIA_ARANHA);
							// teia de aranha
						}
						else if (pixels[atual] == 0xFFFF75C7) {
							NPC npc = new NPC(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho,
									Jogo.tamanho, NPC.npc_pirata_1);
							Jogo.entidades.add(npc);
							Jogo.npc.add(npc);
							//NPC
						}
						
						
						
						
						
						else if (pixels[atual] == 0xFF008048) {
							criarTilesEmbaixoDaCasa(xx, yy, 2, 2);
							objetos.add(new Objetos(xx * Jogo.tamanho, yy * Jogo.tamanho, Objetos.casa_32X32));
						}else if (pixels[atual] == 0xFFFF63CD) {
							criarTilesEmbaixoDaCasa(xx, yy, 5, 4);
							objetos.add(new Objetos(xx * Jogo.tamanho, yy * Jogo.tamanho, Objetos.bar));
						}else if (pixels[atual] == 0xFF7F3F76) {
							criarTilesEmbaixoDaCasa(xx, yy, 4, 3);
							if (Jogo.rand.nextInt(100) < 50) {
								objetos.add(new Objetos(xx * Jogo.tamanho, yy * Jogo.tamanho, Objetos.casa_64X48_1));
							} else {
								objetos.add(new Objetos(xx * Jogo.tamanho, yy * Jogo.tamanho, Objetos.casa_64X48_2));
							}
						}
						
						
//===============================================Entidades========================================
						else if (pixels[atual] == 0xFF2A00FF) {
							Jogo.jogador.setX(xx * Jogo.tamanho);
							Jogo.jogador.setY(yy * Jogo.tamanho);
							// Jogo.jogador.setMask(1, 1, 15, 15);
							// Jogador
						} else if (pixels[atual] == 0xFFBC7BF2) {
							tiles[atual] = new FloorTile(xx * Jogo.tamanho, yy * Jogo.tamanho,
									Tile.TILE_FLOOR_TERRA_CENTRAL);
							// areia
						}else if(pixels[atual] ==0xFFDD9BFF) {
							Mago mago=new Mago(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho,
									Jogo.tamanho, null);
							Jogo.entidades.add(mago);
						}
						
						else if (pixels[atual] == 0xFFFF6A00) {
							InimigoMorte morte = new InimigoMorte(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho,
									Jogo.tamanho, null);
							Jogo.entidades.add(morte);
							Jogo.inimigo.add(morte);
							// inimigo morte
						} else if (pixels[atual] == 0xFF00FF21) {
							InimigoCaveira caveira = new InimigoCaveira(xx * Jogo.tamanho, yy * Jogo.tamanho,
									Jogo.tamanho, Jogo.tamanho, null);
							Jogo.entidades.add(caveira);
							Jogo.inimigo.add(caveira);
							// inimigo caveira
						} else if (pixels[atual] == 0xFF89FFFD) {
							InimigoAlien alien = new InimigoAlien(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho,
									Jogo.tamanho, null);
							Jogo.entidades.add(alien);
							Jogo.inimigo.add(alien);
						} else if (pixels[atual] == 0xFFFF0000) {
							CoracaoDeVida pack = new CoracaoDeVida(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho,
									Jogo.tamanho, Entidade.coracaoVida);
							// pack.setMask(maskX, maskY, maskW, maskH); SE QUISER COLOCAR MASCARA
							Jogo.entidades.add(pack);
							Jogo.lifePack.add(pack);
							// vida
						} else if (pixels[atual] == 0xFFFFD800) {
							Arma arma = new Arma(xx * Jogo.tamanho, yy * Jogo.tamanho, 16, 16, Entidade.arma);
							Jogo.entidades.add(arma);
							Jogo.arma.add(arma);
							// arma
						} else if (pixels[atual] == 0xFFFF00DC) {
							Municao balas = new Municao(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho,
									Jogo.tamanho, Entidade.municaoBalas);
							Jogo.entidades.add(balas);
							Jogo.municao.add(balas);
							// munição
						}else if (pixels[atual] == 0xFFF2FF6D) {
							Chave chave = new Chave(xx * Jogo.tamanho, yy * Jogo.tamanho, Jogo.tamanho,
									Jogo.tamanho, null);
							Jogo.entidades.add(chave);
							Jogo.entidadesEspeciais.add(chave);
							// chave
						}
						
//====================================itens exclusivos de fases===================================						
						if(Jogo.numfase==1) {
							if (pixels[atual] == 0xFF47FF56) {
								tiles[atual] = new Door(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_ESCADA_DIREITO);
								// ESCADA DIREITO
							}else if (pixels[atual] == 0xFFFF7FA9) {
								tiles[atual] = new Door(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_ESCADA_CENTRO);
								// ESCADA CENTRO
							}else if (pixels[atual] == 0xFFBDFFB5) {
								tiles[atual] = new Door(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_ESCADA_ESQUERDO);
								// ESCADA ESQUERDO
							}
						}else {
							if (pixels[atual] == 0xFF47FF56) {
								tiles[atual] = new WallTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_ESCADA_DIREITO);
								// ESCADA DIREITO
							}else if (pixels[atual] == 0xFFFF7FA9) {
								tiles[atual] = new WallTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_ESCADA_CENTRO);
								// ESCADA CENTRO
							}else if (pixels[atual] == 0xFFBDFFB5) {
								tiles[atual] = new WallTile(xx * Jogo.tamanho, yy * Jogo.tamanho, Tile.TILE_ESCADA_ESQUERDO);
								// ESCADA ESQUERDO
							}
						}
//======================================================================================================
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isFree(int xprox, int yprox) {
		int x1 = xprox / Jogo.tamanho;
		int y1 = yprox / Jogo.tamanho;

		int x2 = (xprox + Jogo.tamanho - 2) / Jogo.tamanho;
		int y2 = yprox / Jogo.tamanho;

		int x3 = xprox / Jogo.tamanho;
		int y3 = (yprox + Jogo.tamanho - 2) / Jogo.tamanho;

		int x4 = (xprox + Jogo.tamanho - 2) / Jogo.tamanho;
		int y4 = (yprox + Jogo.tamanho - 2) / Jogo.tamanho;

		return !((tiles[x1 + (y1 * Mundo.WIDTH_WORD)] instanceof WallTile)
				|| (tiles[x2 + y2 * Mundo.WIDTH_WORD] instanceof WallTile)
				|| (tiles[x3 + y3 * Mundo.WIDTH_WORD] instanceof WallTile)
				|| (tiles[x4 + y4 * Mundo.WIDTH_WORD] instanceof WallTile));
	}

	public static boolean isDoor(int xprox, int yprox) {
		int x1 = xprox / Jogo.tamanho;
		int y1 = yprox / Jogo.tamanho;

		int x2 = (xprox + Jogo.tamanho - 2) / Jogo.tamanho;
		int y2 = yprox / Jogo.tamanho;

		int x3 = xprox / Jogo.tamanho;
		int y3 = (yprox + Jogo.tamanho - 2) / Jogo.tamanho;

		int x4 = (xprox + Jogo.tamanho - 2) / Jogo.tamanho;
		int y4 = (yprox + Jogo.tamanho - 2) / Jogo.tamanho;

		return ((tiles[x1 + (y1 * Mundo.WIDTH_WORD)] instanceof Door)
				|| (tiles[x2 + y2 * Mundo.WIDTH_WORD] instanceof Door)
				|| (tiles[x3 + y3 * Mundo.WIDTH_WORD] instanceof Door)
				|| (tiles[x4 + y4 * Mundo.WIDTH_WORD] instanceof Door));
	}
	

	public void renderizar(Graphics g) {
		// int xstart = Camera.x >> 4;Jogo.tamanho
		int xstart = Camera.x / Jogo.tamanho;
		int ystart = Camera.y / Jogo.tamanho;

		int xfinal = xstart + (Jogo.WIDITH / Jogo.tamanho) + 1;
		int yfinal = ystart + (Jogo.HEIGHT / Jogo.tamanho) + 1;

		for (int xx = xstart; xx <= xfinal; xx++) {
			for (int yy = ystart; yy <= yfinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH_WORD || yy >= HEIGHT_WORD) {
					continue;
				}
				Tile tile = tiles[xx + (yy * WIDTH_WORD)];
				tile.renderizar(g);
			}
		}

		renderizarObjetos(g);
	}

	
	public void criarTilesEmbaixoDaCasa(int xInic, int yInic, int xTm, int yTm) {
		for (int x = xInic; x < xInic + xTm; x++) {
			for (int y = yInic; y < yInic + yTm; y++) {
				tiles[x + (y * WIDTH_WORD)] = new WallTile(x * Jogo.tamanho, y * Jogo.tamanho, Tile.TILE_FLOOR);
			}
		}
	}

	public void renderizarObjetos(Graphics g) {
		for (int i = 0; i < objetos.size(); i++) {
			objetos.get(i).renderizar(g);
		}
	}
	
	public static void proximaFase() {
		opcoesDoMundo();
		
		switch (Jogo.numfase) {
		case 1:
			//Jogo.fase=new Fase1();
			Jogo.mundo = new Mundo("/fases/fase1.png");
			Jogo.pularFase=false;
			break;
		case 2:
			Jogo.mundo = new Mundo("/fases/fase2.png");
			Jogo.pularFase=false;
			break;
		case 3:
			Jogo.mundo = new Mundo("/fases/fase3.png");
			Jogo.contadorDeSegundos=60;
			Jogo.temporizadorM=1;
			Jogo.temporizadorS=30;
			Jogo.exibeRelogio=true;
			Jogo.noite=true;
			Jogo.pularFase=false;
			break;	
		case 4:
			Jogo.mundo = new Mundo("/fases/fase4.png");
			Jogo.pularFase=false;
			break;
		}
	}
	
	public static void opcoesDoMundo() {
		if (!Jogo.mute)
			Sons.proxFase.play();

		Jogo.entidades.clear();
		Jogo.inimigo.clear();
		Jogo.lifePack.clear();
		Jogo.municao.clear();
		Jogo.arma.clear();
		Jogo.balas.clear();
		Jogo.npc.clear();
		Jogo.morte.clear();

		Jogo.entidades = new ArrayList<Entidade>();
		Jogo.inimigo = new ArrayList<Inimigo>();
		Jogo.lifePack = new ArrayList<CoracaoDeVida>();
		Jogo.municao = new ArrayList<Municao>();
		Jogo.arma = new ArrayList<Arma>();
		Jogo.balas = new ArrayList<AtirarMunicao>();
		Jogo.npc = new ArrayList<NPC>();
		Jogo.morte = new ArrayList<InimigoMorte>();

		Jogo.spritesheet = new Spritesheet("/Spritesheet.png");
		Jogo.jogador = new Jogador(35, 29, Jogo.tamanho, Jogo.tamanho,
				Jogo.spritesheet.getSprite(0, 0, Jogo.tamanho, Jogo.tamanho));
		Jogo.entidades.add(Jogo.jogador);
	}
	
	public BufferedImage verificarPisoPadrao() {
		switch (Jogo.numfase) {
		case 1:
			return Tile.TILE_PISO_MADEIRA;
		case 2:
			return Tile.TILE_FLOOR;	
		case 3:
			return Tile.TILE_FLOOR;	
		case 4:
			return Tile.TILE_FLOOR;	
		}
		
		return null;
	}
}
