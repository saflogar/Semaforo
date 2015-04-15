package com.fime.semaforo.ui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fime.semaforo.Interseccion;
import com.fime.semaforo.InterseccionListener;


public class PanelInterseccion extends JPanel implements InterseccionListener{

	private static final long serialVersionUID = 1L;
	private JLabel labelStreets;
	private ImageIcon streetsImage ;
	private ImageIcon carImage;
	private JLabel carLabel;
	private JLabel[][] listaCarros;

	public PanelInterseccion()
	{
		listaCarros = new JLabel[2][6];
		createPabelInterseccion();
		Interseccion interseccionEng = new Interseccion();
		interseccionEng.addListener(this);
		Thread t = new Thread(interseccionEng);
		t.start();
	}
	
	private void createPabelInterseccion()
	{
		streetsImage = new ImageIcon("images/calle3.png");
		labelStreets = new JLabel(streetsImage);
		labelStreets.setSize(new Dimension(500,500));
		this.setLayout(null);
		this.add(labelStreets);
		Insets insets = this.getInsets();
		labelStreets.setBounds((int)(insets.right-insets.right/2),(int)(insets.bottom - insets.bottom/2),labelStreets.getPreferredSize().width,labelStreets.getPreferredSize().height);
	}
	
	public void llegoCarro(int[] carro) {
		
		/*llegoCarro(int[] c) se llama cada vez que llega un carro*/
		// TODO Auto-generated method stub
		System.out.println("Carril A = "+carro[0]+"Carril B = "+carro[1]);
		
		//Obtenemos los datos del contenedor	
		Insets insets = this.getInsets();
		animacionLlegada(carro, insets, 150);
		
	}
	
	public void seFueCarro(int[] carros) {
		// TODO Auto-generated method stub
		
	}
	public void animacionIda()
	{
		
	}
	
	public void animacionIdaCarro(Rectangle carrBounds, Point endPoint, JLabel label)
	{
		/*Animacion de Arriba - Abajo, Izquierda - Derecha*/
		int x0 = carrBounds.x;
		int y0 = carrBounds.y;
		int x1 = endPoint.x;
		int y1 = endPoint.y;
		int dx = x1 - x0;
		int dy = y1 - y0;
		boolean enX = (x1 == x0) ? false:true;
		boolean derecha = (dx > 0) ? true:false;
		boolean abajo = (dy > 0) ? true : false;
		
		int x = x0;
		int y = y0;
		if (enX)
		{
			for (int i = 0; i <  Math.abs(dx); i++)
			{
				if (derecha)
					x++;
				else x--;
			}
			label.setBounds(x, y, label.getPreferredSize().width, label.getPreferredSize().height);
		}
		else
		{
			for (int i = 0; i < Math.abs(dy); i++)
			{
				if (abajo)
					y++;
				else y--;
			}
			label.setBounds(x, y, label.getPreferredSize().width, label.getPreferredSize().height);
		}
		
	}
	
	
	public void animacionLlegadacarro(Insets insets, int x, int y, int j, boolean vertical)
	{
		if (vertical)
		{
			carLabel.setBounds(j, insets.top + 380+y, carLabel.getPreferredSize().height+100, carLabel.getPreferredSize().width+100);	 
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			carLabel.setBounds(insets.left + 275-x, j, carLabel.getPreferredSize().height+100, carLabel.getPreferredSize().width+100);	 
			System.out.println("[DEBUG] x = "+carLabel.getBounds().x+" y= "+carLabel.getBounds().y);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	}
	
	public void animacionLlegadaCarros(int carros,Insets insets, int sepEntreCarriles,int sepEntreCarros, int x , int y,boolean vertical)
	{
		for (int i = 0; i < carros; i++)
		{
			if (i == 3)
			{
				/*
				 * A partir del cuarto carro se dibujan los carros 
				 * en el segundo carril
				 */
				if (vertical)
				{
					y = sepEntreCarriles;//separacion entre un carril y otro
					x = 0;
				}
				else
				{
					y = 0;//separacion entre un carril y otro
					x = sepEntreCarriles;
					
				}
				
			}
			
			int temIndice = (vertical) ? 0:1;
			
			if(listaCarros[temIndice][i] == null)
			{
				
				carImage = vertical ? new ImageIcon("images/image10.png"): new ImageIcon("images/image11.png");
				carLabel = new JLabel(carImage);
				listaCarros[temIndice][i] = carLabel;
				System.out.println("[DEBUG] Dibujando carro "+ i +"en carril A ");
				this.add(carLabel);
				if (vertical)
					for (int j= insets.left+800 ; j >= (insets.left + 350+x); j-=20)
						animacionLlegadacarro(insets, x, y, j,vertical);
				else
					for (int j = insets.top ; j <= (insets.top + 300 - y); j+=20 )
						animacionLlegadacarro(insets, x, y, j,vertical);
			}
			//se recorre una distancia para que no se empalmen los carros
			if (vertical)
				x += sepEntreCarros;
			else
				y += sepEntreCarros;
				
		}
		
	}
	
	public void animacionLlegada(int[] carriles, Insets insets, int sepEntreCarros)
	{
		
		int sepEntreCarriles = 80;//Separación entre un carril y otro
		int conteo=0;
		boolean vertical;
		for (int carros : carriles)
		{
			
			int x=0;
			int y=0;
			vertical = (conteo == 0) ? true :false;
			if (carros > 0)
			{
				animacionLlegadaCarros(carros, insets, sepEntreCarriles, sepEntreCarros, x, y, vertical);
			}
			conteo ++;
			
		}			
	}



}
