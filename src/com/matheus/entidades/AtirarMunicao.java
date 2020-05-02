package com.matheus.entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.matheus.game.Jogo;
import com.matheus.mundo.Camera;
import com.matheus.mundo.Mundo;

public class AtirarMunicao extends Entidade{

	private int dx;
	private int dy;
	private double speed=2;
	private int tempoDeDuracao=50, curTempoDeDuracao=0;
	
	public AtirarMunicao(double x, double y, int width, int height, BufferedImage sprite, int dx,int dy) {
		super(x, y, width, height, sprite);
		this.dx=dx;
		this.dy=dy;
	}
	
	@Override
	public void atualizar() {
      if(Mundo.isFreeDinamico((int)(x+(dx*speed)),(int)( y+(dy*speed)), 3, 3)) {
    	  x+=dx*speed;
  		  y+=dy*speed;	
		}
      else {
    	  Jogo.balas.remove(this);
    	  Mundo.gerarParticula(100,(int) x,(int) y);
			return;
		}
      curTempoDeDuracao++;
		if(curTempoDeDuracao==tempoDeDuracao) {
			Jogo.balas.remove(this);
			Mundo.gerarParticula(500,(int) x,(int) y);
			return;
	  }
		
	}
	
	@Override
	public void renderizar(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX()-Camera.x,this.getY()-Camera.y, width, height);
		
	}

	
}
