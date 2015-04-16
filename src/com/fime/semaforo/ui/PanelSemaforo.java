package com.fime.semaforo.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.fime.semaforo.ColorSemaforo;
import com.fime.semaforo.Semaforo;
import com.fime.semaforo.SemaforoListener;






public class PanelSemaforo extends JPanel implements SemaforoListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private com.fime.semaforo.ColorSemaforo colorActual;
	
	public PanelSemaforo()
	{
		super();
		this.colorActual = ColorSemaforo.VERDE;
		

	}
	@Override
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		
		super.paintComponent(g);
		int x = 0;
		int y = 0;
		int width = 180 ;
		int height = 60;
		g.setColor(Color.WHITE);
		g.drawRect(x, y, height, width);
		g.setColor(Color.BLACK);
		g.fillRect(x, y, height, width);
		
		switch (colorActual) {
		case VERDE:
			g.setColor(Color.green);
			g.fillOval(0, 0, height, height);
			g.setColor(Color.WHITE);
			g.fillOval(0, 60,height, height);
			g.fillOval(0, 120, height, height);
			break;
		case AMARILLO:
			g.setColor(Color.WHITE);
			g.fillOval(0, 0, height, height);
			g.fillOval(0, 120, height, height);

			g.setColor(Color.YELLOW);
			g.fillOval(0, 60,height, height);
			break;
		
		case ROJO:
			g.setColor(Color.WHITE);
			g.fillOval(0, 0, height, height);
			g.fillOval(0, 60,height, height);

			g.setColor(Color.RED);
			g.fillOval(0, 120, height, height);

			
			break;

		default:
			break;
		}
	

		
		g.setColor(Color.black);
	}	
	
	public void cambiarColor()
	{
		
		switch (colorActual)
		{
		case VERDE:
			colorActual = com.fime.semaforo.ColorSemaforo.AMARILLO;
			break;
			
		case AMARILLO:
			colorActual = com.fime.semaforo.ColorSemaforo.ROJO;
			break;

		case ROJO:
			colorActual = com.fime.semaforo.ColorSemaforo.VERDE;
			break;
		
		default:
			break;
		}
		System.out.print("[DEBUG] semaforo"+ this.toString()+ "a" + colorActual);
	}

	@Override
	public void cambioSemaforo(ColorSemaforo colorSemaforo) {
		// TODO Auto-generated method stub
		this.colorActual = colorSemaforo;
		repaint();
		
	}
	

}
