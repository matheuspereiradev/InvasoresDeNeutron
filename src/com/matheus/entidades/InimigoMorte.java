package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.matheus.aStar.AStar;
import com.matheus.aStar.Node;
import com.matheus.aStar.Vector2i;
import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;

public class InimigoMorte extends Inimigo {

	private int index = 0, frames = 0, maxFrames = 10, maxIndex = 2;
	private BufferedImage[] inimigoMorteLeft, inimigoMorteRight, inimigoMorteUp, inimigoMorteDown;
	private int dir_up = 1, dir_right = 0, dir_down = 2, dir_left = 3;
	private int direcao = dir_down;

	public InimigoMorte(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		super.speed = 1;
		this.power = 15;
		this.vida=12;
		inimigoMorteLeft = new BufferedImage[3];
		inimigoMorteRight = new BufferedImage[3];
		inimigoMorteUp = new BufferedImage[3];
		inimigoMorteDown = new BufferedImage[3];
		for (int i = 0; i < inimigoMorteLeft.length; i++) {
			inimigoMorteLeft[i] = Jogo.spritesheet.getSprite(208 + (Jogo.tamanho * i), 48, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < inimigoMorteRight.length; i++) {
			inimigoMorteRight[i] = Jogo.spritesheet.getSprite(208 + (Jogo.tamanho * i), 32, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < inimigoMorteUp.length; i++) {
			inimigoMorteUp[i] = Jogo.spritesheet.getSprite(208 + (Jogo.tamanho * i), 64, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < inimigoMorteDown.length; i++) {
			inimigoMorteDown[i] = Jogo.spritesheet.getSprite(208 + (Jogo.tamanho * i), 80, Jogo.tamanho, Jogo.tamanho);
		}
	}

	public void atualizar() {
		movendo = false;
		if (!colisaoComJogador(this.getX(), this.getY(), this.maskX, this.maskY, this.maskW, this.maskH)) {
			if (caminho == null || caminho.size() == 0) {
				Vector2i start = new Vector2i((int) (x / 16), (int) (y / 16));
				Vector2i end = new Vector2i((int) (Jogo.jogador.x / 16), (int) (Jogo.jogador.y / 16));
				caminho = AStar.acharCaminho(Jogo.mundo, start, end);
			}

			this.findPath(caminho);
			
		} else {
			testarAtaqueNoPlayer();
		}
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
		colisaoComBala();
		verificarVida();
		if (sofrendoDano) {
			currentDano++;
			if (currentDano == danoFrames) {
				currentDano = 0;
				sofrendoDano = false;
			}
		}
	}

	public void renderizar(Graphics g) {
		if (!sofrendoDano) {
			if (direcao == dir_right) {
				g.drawImage(inimigoMorteRight[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (direcao == dir_left) {
				g.drawImage(inimigoMorteLeft[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			if (direcao == dir_up) {
				g.drawImage(inimigoMorteUp[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (direcao == dir_down) {
				g.drawImage(inimigoMorteDown[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else {
			g.drawImage(inimigoMorteDano, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

	}

	@Override
	public void findPath(List<Node> caminho) {
		if (caminho != null) {
			if (caminho.size() > 0) {
				Vector2i target = caminho.get(caminho.size() - 1).tile;

				if (x < target.x * 16) {
					x += speed;
					movendo = true;
					direcao = dir_right;

				} else if (x > target.x * 16) {
					x -= speed;
					movendo = true;
					direcao = dir_left;

				}

				if (y < target.y * 16) {
					y += speed;
					movendo = true;
					direcao = dir_down;
				} else if (y > target.y * 16) {
					y -= speed;
					movendo = true;
					direcao = dir_up;

				}

				if (x == target.x * 16 && y == target.y * 16) {
					caminho.remove(caminho.size() - 1);
				}
			}
		}
	}

	public boolean estaColidindo(int xnext, int ynext) {
		Rectangle inimigoAtual = new Rectangle(xnext + maskX, ynext + maskX, maskW, maskH);
		for (int i = 0; i < Jogo.inimigo.size(); i++) {
			Inimigo inimigo = Jogo.inimigo.get(i);
			if (inimigo == this) {
				// verifica se ele é ele proprio exemplo um aliem semore vai estar colidindo com
				// ele mesmo
				continue;
			}
			Rectangle inimigoTeste = new Rectangle(inimigo.getX() + maskX, inimigo.getY() + maskY, maskW, maskH);
			if (inimigoTeste.intersects(inimigoAtual)) {
				return true;
			}
		}
		return false;
	}

}
