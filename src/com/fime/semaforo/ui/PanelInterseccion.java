package com.fime.semaforo.ui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fime.semaforo.ColorSemaforo;
import com.fime.semaforo.Interseccion;
import com.fime.semaforo.InterseccionListener;
import com.fime.semaforo.SemaforoListener;


public class PanelInterseccion extends JPanel implements InterseccionListener{

	private static final long serialVersionUID = 1L;
	private JLabel labelStreets;
	private ImageIcon streetsImage ;
	private ImageIcon carImage;
	private JLabel carLabel;
	private JLabel[][] listaCarros;
	private JPanel panelSemaforo1 ;
	private Interseccion interseccionEng;
	private JPanel panelSemaforo2;

	public PanelInterseccion()
	{
		
		
		 interseccionEng = new Interseccion();
		interseccionEng.addListener(this);
		Thread t = new Thread(interseccionEng);
		t.start();
		 createPabelInterseccion();

	}
	
	private void createPabelInterseccion()
	{
		listaCarros = new JLabel[2][6];
		streetsImage = new ImageIcon("images/calle3.png");
		labelStreets = new JLabel(streetsImage);
		panelSemaforo1 = new PanelSemaforo();
		panelSemaforo2 = new PanelSemaforo();
		
		labelStreets.setSize(new Dimension(500,500));
		panelSemaforo1.setPreferredSize(new Dimension(100,200));
		panelSemaforo2.setPreferredSize(new Dimension(100,200));
		
		this.setLayout(null);
		this.add(labelStreets);
		this.add(panelSemaforo1);
		this.add(panelSemaforo2);
		Insets insets = this.getInsets();
		panelSemaforo1.setBounds((int)(insets.right - panelSemaforo1.getSize().width),0,panelSemaforo1.getPreferredSize().width,panelSemaforo1.getPreferredSize().height);
		panelSemaforo2.setBounds(300,0,panelSemaforo2.getPreferredSize().width,panelSemaforo2.getPreferredSize().height);

		labelStreets.setBounds((int)(insets.right-insets.right/2),(int)(insets.bottom - insets.bottom/2),labelStreets.getPreferredSize().width,labelStreets.getPreferredSize().height);
		
		interseccionEng.suscribirSemaforo1((SemaforoListener) panelSemaforo1);
		interseccionEng.suscribirSemaforo2((SemaforoListener) panelSemaforo2);
		
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
		int numCarLista1 = 0;
		int numCarLista2  = 0;
		for (int i = 0; i < listaCarros[0].length ; i++)
		{
			if(listaCarros[0][i] != null) numCarLista1 ++;
			if(listaCarros[1][i] != null) numCarLista2 ++;
		}
				
		
		//SE FUE CARRO EN CARRIL HORIZONTAL
		if (carros[0] < numCarLista1)
		{
			for (JLabel carroLbl : listaCarros[0]) 
			{
				
				if (carroLbl != null)
				{
					Rectangle carrLblBound = carroLbl.getBounds();
					animacionIdaCarro(carrLblBound, new Point(0,carrLblBound.y), carroLbl);
					carroLbl.setVisible(false);
					carroLbl = null;
				}
				
			}
			
		}
		//SE FUE CARRO EN CARRIL VERTICAL
		else if (carros[1] < numCarLista2)
		{
			for (JLabel carroLbl : listaCarros[1]) 
			{
				
				if (carroLbl != null)
				{
					Rectangle carrLblBound = carroLbl.getBounds();
					animacionIdaCarro(carrLblBound, new Point(carrLblBound.x,500), carroLbl);
					carroLbl.setVisible(false);
					carroLbl = null;
					
				}
			}
		}
		
		
	}

	//ANIMACION ARRIBA - ABAJO , IZQUIERDA - DERECHA
	public void animacionIdaCarro(Rectangle carrBounds, Point endPoint, JLabel label)
	{
		//VARS FOR ANIMATION SPEED
		final int FPS = 120;
		final double SKIP_TICKS = 1000 /FPS;
		double sleepTime;
		

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
		
		double nextGameTick = System.currentTimeMillis();
		
		
		if (enX)
		{
			for (int i = 0; i <  Math.abs(dx); i++)
			{
				 nextGameTick += SKIP_TICKS;
			        sleepTime = nextGameTick - System.currentTimeMillis();
			        if( sleepTime >= 0.0 ) {
			            try {
							Thread.sleep( (long) sleepTime );
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			        
				if (derecha)
					x++;
				else x--;
				label.setBounds(x,y+100, label.getPreferredSize().width, label.getPreferredSize().height);
			}
			
		}
		else
		{
			for (int i = 0; i < Math.abs(dy); i++)
			{
				 nextGameTick += SKIP_TICKS;
			        sleepTime = nextGameTick - System.currentTimeMillis();
			        if( sleepTime >= 0.0 ) {
			            try {
							Thread.sleep( (long) sleepTime );
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
				
				if (abajo)
					y++;
				else y--;
				label.setBounds(x+100, y, label.getPreferredSize().width, label.getPreferredSize().height);
			}
			
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

	@Override
	public void cambioColorSemaforo(ColorSemaforo color) {
		// TODO Auto-generated method stub
		
		
		
	}



}
