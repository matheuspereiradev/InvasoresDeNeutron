package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.entidades.especiais.Chave;
import com.matheus.game.Jogo;
import com.matheus.game.Sons;
import com.matheus.mundo.*;

public class Jogador extends Entidade {

	public static boolean left, right, up, down;
	public double speed = 1.2;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int ultimoClicado = down_dir;
	private BufferedImage[] rightplayer;
	private BufferedImage[] leftplayer;
	private BufferedImage[] upplayer;
	private BufferedImage[] downplayer;
	public int index = 0, frames = 0, maxFrames = 10, maxIndex = 2;
	public boolean movimentando;
	private BufferedImage jogadorAtingido;
	public boolean sofrendoDano = false;
	public int sofrendoDanoFrames = 0;
	public boolean colidindoComNPC=false;

	public int velDisparo = 10, ultDisparo = 0;
	public boolean podeAtirar = true;

	public int numeroDeBalas = 0;
	public double vida = 100;
	public static final int MAX_LIFE = 100;
	public boolean armado = false;
	public static boolean possuiChave=false;
	public boolean pegarItem=false;
	public boolean atirando = false;
	public static boolean colidindoComMago=false;

	public Jogador(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		depth=1;
		
		rightplayer = new BufferedImage[3];
		leftplayer = new BufferedImage[3];
		upplayer = new BufferedImage[3];
		downplayer = new BufferedImage[3];
		jogadorAtingido = Jogo.spritesheet.getSprite(64, 48, Jogo.tamanho, Jogo.tamanho);
		for (int i = 0; i < rightplayer.length; i++) {
			rightplayer[i] = Jogo.spritesheet.getSprite(48 + (i * Jogo.tamanho), 0, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < 3; i++) {
			leftplayer[i] = Jogo.spritesheet.getSprite(96 + (i * Jogo.tamanho), 0, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < 3; i++) {
			upplayer[i] = Jogo.spritesheet.getSprite(0 + (i * Jogo.tamanho), 16, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < 3; i++) {
			downplayer[i] = Jogo.spritesheet.getSprite(0 + (i * Jogo.tamanho), 0, Jogo.tamanho, Jogo.tamanho);
		}
	}

	public void atualizar() {

		if (vida <= 0 && Jogo.totalVidas==0) {
			/*
			 * Jogo.entidades.clear(); Jogo.inimigo.clear(); Jogo.lifePack.clear();
			 * Jogo.municao.clear(); Jogo.iniciarJogo();
			 */
			Jogo.jogador.vida = 0.0;
			Jogo.status = "GAME_OVER";
		}else if (vida <= 0 && Jogo.totalVidas>0) {
			Mundo.proximaFase();
			Jogo.totalVidas--;
		}

		if (!podeAtirar) {
			ultDisparo++;
			if (ultDisparo == velDisparo) {
				podeAtirar = true;
				ultDisparo=0;
			}
		}

		movimentando = false;
		if (up && Mundo.isFree(getX(), (int) (y - speed)) && verificaColisaoPorta()==0) {
			movimentando = true;
			ultimoClicado = up_dir;
			y -= speed;
		} else if (down && Mundo.isFree(getX(), (int) (y + speed)) && verificaColisaoPorta()==0) {
			movimentando = true;
			ultimoClicado = down_dir;
			y += speed;
		}
		if (left && Mundo.isFree((int) (x - speed), getY()) && verificaColisaoPorta()==0) {
			movimentando = true;
			ultimoClicado = left_dir;
			x -= speed;
		} else if (right && Mundo.isFree((int) (x + speed), getY()) && verificaColisaoPorta()==0) {
			movimentando = true;
			ultimoClicado = right_dir;
			x += speed;
		}
		// animar

		if (movimentando) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					index = 0;
				}
			}
		}

		if (sofrendoDano) {
			sofrendoDanoFrames++;
			if (sofrendoDanoFrames == 8) {
				sofrendoDanoFrames = 0;
				sofrendoDano = false;
			}
		}

		if (atirando) {
			// criar a bala
			atirando = false;
			if (podeAtirar) {
				if (armado && numeroDeBalas > 0) {
					podeAtirar=false;
					if (!Jogo.mute) {
						Sons.tiroSong.play();
					}
					numeroDeBalas--;
					int dx = 0, dy = 0, px = 0, py = 0;
					if (ultimoClicado == right_dir) {
						px = 18;
						py = 9;
						dx = 1;
					} else if (ultimoClicado == left_dir) {
						px = -6;
						py = 9;
						dx = -1;
					}
					if (ultimoClicado == up_dir) {
						px = -1;
						py = 8;
						dy = -1;
					} else if (ultimoClicado == down_dir) {
						px = 1;
						py = 13;
						dy = 1;
					}
					Jogo.balas.add(new AtirarMunicao(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy));
				}
			}
		}

		verificarColisaoComPackDeVida();
		verificarColisaoMunicao();
		verificarColisaoArma();
		verificarColisaoChave();
		verificarColisaoNPC();

		Camera.x = Camera.clamp(getX() - (Jogo.WIDITH / 2), Mundo.WIDTH_WORD * Jogo.tamanho - Jogo.WIDITH, 0);
		Camera.y = Camera.clamp(getY() - (Jogo.HEIGHT / 2), Mundo.HEIGHT_WORD * Jogo.tamanho - Jogo.HEIGHT, 0);
	}

	public void renderizar(Graphics g) {
		if (!sofrendoDano) {
			if (ultimoClicado == right_dir) {
				g.drawImage(rightplayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (armado) {
					g.drawImage(Entidade.armaRight, (this.getX() - Camera.x) + 8, (this.getY() - Camera.y) + 3, null);
				}

			} else if (ultimoClicado == left_dir) {
				g.drawImage(leftplayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (armado) {
					g.drawImage(Entidade.armaLeft, (this.getX() - Camera.x) - 8, (this.getY() - Camera.y) + 3, null);
				}
			}
			if (ultimoClicado == up_dir) {
				g.drawImage(upplayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (ultimoClicado == down_dir) {
				g.drawImage(downplayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
				if (armado) {
					g.drawImage(Entidade.armaDown, (this.getX() - Camera.x) - 4, (this.getY() - Camera.y) + 6, null);
				}
			}
		} else {
			g.drawImage(jogadorAtingido, this.getX() - Camera.x, this.getY() - Camera.y, null);
			if (armado) {
				g.drawImage(Entidade.armaDown, (this.getX() - Camera.x) - 4, (this.getY() - Camera.y) + 6, null);
			}
		}
		/*
		 * super.renderizar(g); g.setColor(Color.BLUE);
		 * g.fillRect(this.getX()+maskX-Camera.x, this.getY()+maskY-Camera.y,
		 * maskW,maskH);
		 */
	}

	public void verificarColisaoComPackDeVida() {
		for (int i = 0; i < Jogo.lifePack.size(); i++) {// depois melhor criar uma lista somente para life pack
			Entidade atual = Jogo.lifePack.get(i);
			if (atual instanceof CoracaoDeVida) {
				if (Entidade.isColidding(this, atual)) {
					if (Jogo.jogador.vida <= 90) {
						Jogo.jogador.vida += 10;
						Jogo.entidades.remove(atual);
						Jogo.lifePack.remove(atual);
						if (!Jogo.mute) {
							Sons.vidaSong.play();
						}
					} else if (Jogo.jogador.vida < 100) {
						Jogo.jogador.vida = 100;
						Jogo.entidades.remove(atual);
						Jogo.lifePack.remove(atual);
						if (!Jogo.mute) {
							Sons.vidaSong.play();
						}
					}
				}
			}
		}
	}

	public void verificarColisaoMunicao() {
		for (int i = 0; i < Jogo.municao.size(); i++) {// depois melhor criar uma lista somente para life pack
			Entidade atual = Jogo.municao.get(i);
			if (atual instanceof Municao) {
				if (Entidade.isColidding(this, atual)) {
					Jogo.jogador.numeroDeBalas += 500;
					Jogo.entidades.remove(atual);
					Jogo.municao.remove(atual);
				}
			}
		}
	}

	public void verificarColisaoArma() {
		for (int i = 0; i < Jogo.arma.size(); i++) {// depois melhor criar uma lista somente para life pack
			Entidade atual = Jogo.arma.get(i);
			if (atual instanceof Arma) {
				if (Entidade.isColidding(this, atual)) {
					if (Jogo.jogador.armado) {
						Jogo.jogador.numeroDeBalas += 5;
					} else {
						Jogo.jogador.armado = true;
					}
					Jogo.entidades.remove(atual);
					Jogo.arma.remove(atual);
				}
			}
		}
	}

	public void verificarColisaoLava() {
		for (int i = 0; i < Jogo.lava.size(); i++) {// depois melhor criar uma lista somente para life pack
			BlocoDeDano atual = Jogo.lava.get(i);
			if (atual instanceof BlocoDeDano) {
				if (BlocoDeDano.isColiddingTileEntidade(Jogo.jogador, atual)) {
					BlocoDeDano.danoNaLava();

				}
			}
		}
	}
	
	public void verificarColisaoChave() {
		for (int i = 0; i < Jogo.entidadesEspeciais.size(); i++) {// depois melhor criar uma lista somente para life pack
			Entidade atual = Jogo.entidadesEspeciais.get(i);
			if (atual instanceof Chave) {
				if (Entidade.isColidding(this, atual)) {
					if (pegarItem) {
						Jogador.possuiChave=true;
						Jogo.entidades.remove(atual);
						Jogo.entidadesEspeciais.remove(atual);
						pegarItem=false;
					}else {
						Jogo.mensagem="Presione P para pegar a chave";
						Jogo.exibirMensagem = true;
					}
					
				}
			}
		}
	}
	
	public static int verificaColisaoPorta() {
		//verificar impacto com a porta
				if (Jogador.up && Mundo.isDoor(Jogo.jogador.getX(), (int) (Jogo.jogador.y - Jogo.jogador.speed))) {
					//verifica se o jogador possui a chave
					if(Jogador.possuiChave) {
						return 2;
					}else {
						return 1;
					}
				} else if (Jogador.down && Mundo.isDoor(Jogo.jogador.getX(), (int) (Jogo.jogador.y + Jogo.jogador.speed))) {
					if(Jogador.possuiChave) {
						return 2;
					}else {
						return 1;
					}
				}else if (Jogador.left && Mundo.isDoor((int) (Jogo.jogador.x - Jogo.jogador.speed), Jogo.jogador.getY())) {
					if(Jogador.possuiChave) {
						return 2;
					}else {
						return 1;
					}
				} else if (Jogador.right && Mundo.isDoor((int) (Jogo.jogador.x + Jogo.jogador.speed), Jogo.jogador.getY())) {
					if(Jogador.possuiChave) {
						return 2;
					}else {
						return 1;
					}
				}
				
				return 0;

	}
	
	public void verificarColisaoNPC() {
		for (int i = 0; i < Jogo.npc.size(); i++) {
			Entidade atual = Jogo.npc.get(i);
			if (atual instanceof NPC) {
				if (Entidade.isColidding(this, atual)) {
					colidindoComNPC=true;
					if (!Jogo.conversandoComNPC) {
						Jogo.mensagem="Presione Q para conversar";
						Jogo.exibirMensagem = true;
					}
					
				} else {
					colidindoComNPC=false;
				}
				
			}
		}
	}

}
