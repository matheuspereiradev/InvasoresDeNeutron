package com.matheus.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;

public class InimigoCaveira extends Inimigo {

	private double speed = 1.2;

	private int index = 0, frames = 0, maxFrames = 10, maxIndex = 2;
	private BufferedImage[] inimigoCaveira;

	public InimigoCaveira(double x, double y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		this.power = 5;
		this.vida=3;

		inimigoCaveira = new BufferedImage[3];
		inimigoCaveira[0] = Jogo.spritesheet.getSprite(16, 48, Jogo.tamanho, Jogo.tamanho);
		inimigoCaveira[1] = Jogo.spritesheet.getSprite(32, 48, Jogo.tamanho, Jogo.tamanho);
		inimigoCaveira[2] = Jogo.spritesheet.getSprite(48, 48, Jogo.tamanho, Jogo.tamanho);
		

	}

	public void atualizar() {
		// movimentação

		if (!Inimigo.colisaoComJogador(this.getX(), this.getY(), this.maskX, this.maskY, this.maskW, this.maskH)) {
			if (calcularDistancia(this.getX(), Jogo.jogador.getX(), this.getY(), Jogo.jogador.getY()) < 50) {
				if (Jogo.rand.nextInt(100) < 75) {

					if (((int) x < Jogo.jogador.getX()) && (Mundo.isFree((int) (x + speed), this.getY()))) {
						x += speed;
					} else if ((int) x > Jogo.jogador.getX() && Mundo.isFree((int) (x - speed), this.getY())) {
						x -= speed;
					}

					if ((int) y < Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y + speed))) {
						y += speed;
					} else if ((int) y > Jogo.jogador.getY() && Mundo.isFree(this.getX(), (int) (y - speed))) {
						y -= speed;
					}
				}
			}
		} else {
			testarAtaqueNoPlayer();// aqui chama o metodo e passa a probabilidade de o ataque dele acertar
		}

		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
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
			g.drawImage(inimigoCaveira[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else {
			g.drawImage(inimigoCaveiraDano, this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

	}

}
