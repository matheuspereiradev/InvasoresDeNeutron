package com.matheus.entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;

public class Particula extends Entidade {
	 
	public int tempoVida=3, atualTempo=0, spd = 2;
	public double dx=0, dy=0;
	public Color color;
	public Particula(double x, double y, int width, int height, BufferedImage sprite, Color c) {
		super(x, y, width, height, sprite);
		
		dx=new Random().nextGaussian();
		dy=new Random().nextGaussian();
		this.color=c;
	}
	
	@Override
	public void atualizar() {
		x+=dx*spd;
		y+=dy*spd;
		
		atualTempo++;
		if (atualTempo==tempoVida) {
			Jogo.entidades.remove(this);
		}
	}
	
	@Override
	public void renderizar(Graphics g) {
		g.setColor(this.color);
		g.fillRect(this.getX()-Camera.x,this.getY()-Camera.y, width, height);
		
	}

}
