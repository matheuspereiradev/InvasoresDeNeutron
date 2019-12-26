package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;

public class InimigoAlien extends Inimigo {

	private double speed = 1.2;
	private int maskX = 8, maskY = 8, maskW = 10, maskH = 10;
	private int dir_up = 1, dir_right = 0, dir_down = 2, dir_left = 3;
	private int direcao = dir_down;
	private int index = 0, frames = 0, maxFrames = 10, maxIndex = 2, tamanhoArray = 3;
	private int distanciaDeAlcanceDoAtaque = 60;

	int sortearDirecao = Jogo.rand.nextInt(4);
	int tempoDirecao = 0, maxTempoDirecao = 40;
	boolean direcaoDisponivel = false;

	private BufferedImage[] rightAlien;
	private BufferedImage[] leftAlien;
	private BufferedImage[] upAlien;
	private BufferedImage[] downAlien;

	public InimigoAlien(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.power=10;
		this.vida=6;
		rightAlien = new BufferedImage[tamanhoArray];
		leftAlien = new BufferedImage[tamanhoArray];
		upAlien = new BufferedImage[tamanhoArray];
		downAlien = new BufferedImage[tamanhoArray];

		for (int i = 0; i < rightAlien.length; i++) {
			rightAlien[i] = Jogo.spritesheet.getSprite(64 + (Jogo.tamanho * i), 32, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < leftAlien.length; i++) {
			leftAlien[i] = Jogo.spritesheet.getSprite(16 + (Jogo.tamanho * i), 32, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < upAlien.length; i++) {
			upAlien[i] = Jogo.spritesheet.getSprite(112 + (Jogo.tamanho * i), 32, Jogo.tamanho, Jogo.tamanho);
		}
		for (int i = 0; i < downAlien.length; i++) {
			downAlien[i] = Jogo.spritesheet.getSprite(48 + (Jogo.tamanho * i), 16, Jogo.tamanho, Jogo.tamanho);
		}

	}

	public boolean isColidindo(int xnext, int ynext) {
		Rectangle alienAtual = new Rectangle(xnext + maskX, ynext + maskX, maskW, maskH);
		for (int i = 0; i < Jogo.inimigo.size(); i++) {
			Inimigo inimigo = Jogo.inimigo.get(i);
			if (inimigo == this) {
				// verifica se ele é ele proprio exemplo um aliem semore vai estar colidindo com
				// ele mesmo
				continue;
			}
			Rectangle alienTeste = new Rectangle(inimigo.getX() + maskX, inimigo.getY() + maskY, maskW, maskH);
			if (alienTeste.intersects(alienAtual)) {
				return true;
			}

		}

		return false;
	}

	public void atualizar() {
		movendo = false;
		if (calcularDistancia(this.getX(), Jogo.jogador.getX(), this.getY(),
				Jogo.jogador.getY()) < distanciaDeAlcanceDoAtaque) {
			if (!colisaoComJogador(this.getX(), this.getY(), this.maskX, this.maskY, this.maskW, this.maskH)) {

				if ((int) x < Jogo.jogador.getX() && Mundo.isFree((int) (x + speed), this.getY() )
						&& !isColidindo((int) (x + speed), this.getY()) && !(Mundo.isDoor((int) (x + speed), this.getY()))) {
					movendo = true;
					direcao = dir_right;
					x += speed;
				} else if ((int) x > Jogo.jogador.getX() && Mundo.isFree((int) (x - speed), this.getY())
						&& !isColidindo((int) (x - speed), this.getY())  && !(Mundo.isDoor((int) (x - speed), this.getY()))) {
					movendo = true;
					direcao = dir_left;
					x -= speed;
				}
				if ((int) y < Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y + speed))
						&& !isColidindo(this.getX(), (int) (y + speed)) && !(Mundo.isDoor(this.getX(), (int) (y + speed)))) {
					movendo = true;
					direcao = dir_down;
					y += speed;
				} else if ((int) y > Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y - speed))
						&& !isColidindo(this.getX(), (int) (y - speed)) && !(Mundo.isDoor(this.getX(), (int) (y - speed)))) {
					movendo = true;
					direcao = dir_up;
					y -= speed;
				}

			} else {
				testarAtaqueNoPlayer();// aqui chama o metodo e passa a probabilidade de o ataque dele acertar
			}
		} else {
			// direção maior q distanciaDeAlcanceDoAtaque

			if (tempoDirecao == maxTempoDirecao) {
				tempoDirecao = 0;
				sortearDirecao=Jogo.rand.nextInt(4);
			} else {
				tempoDirecao++;
			}

			if (sortearDirecao == 0 && Mundo.isFree((int) (x + speed), this.getY())
					&& !isColidindo((int) (x + speed), this.getY()) && !(Mundo.isDoor((int) (x + speed), this.getY()))) {
				movendo = true;
				direcao = dir_right;
				x += speed;
			} else if (sortearDirecao == 1 && Mundo.isFree((int) (x - speed), this.getY())
					&& !isColidindo((int) (x - speed), this.getY()) && !(Mundo.isDoor((int) (x - speed), this.getY()))) {
				movendo = true;
				direcao = dir_left;
				x -= speed;
			}

			else if (sortearDirecao == 2 && Mundo.isFree(this.getX(), (int) (y + speed))
					&& !isColidindo(this.getX(), (int) (y + speed)) && !(Mundo.isDoor(this.getX(), (int) (y + speed)))) {
				movendo = true;
				direcao = dir_down;
				y += speed;
			} else if (sortearDirecao == 3 && Mundo.isFree(this.getX(), (int) (y - speed))
					&& !isColidindo(this.getX(), (int) (y - speed)) && !(Mundo.isDoor(this.getX(), (int) (y - speed)))) {
				movendo = true;
				direcao = dir_up;
				y -= speed;
			}

			// fim da direçao

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
				g.drawImage(rightAlien[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (direcao == dir_left) {
				g.drawImage(leftAlien[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
			if (direcao == dir_up) {
				g.drawImage(upAlien[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (direcao == dir_down) {
				g.drawImage(downAlien[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else {
			g.drawImage(Entidade.inimigoAlienDano, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		// para vizualizar melhor oq esta acontecdo descomenta
		// super.renderizar(g);
		// g.setColor(Color.BLUE);
		// g.fillRect(this.getX()+maskX-Camera.x, this.getY()+maskY-Camera.y,
		// maskW,maskH);
	}

}
