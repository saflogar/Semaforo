package com.fime.semaforo;

import java.util.ArrayList;
import java.util.List;

public class Semaforo implements Runnable {
	
	private ArrayList<InterseccionListener> listeners = new ArrayList<InterseccionListener>();
	private int numCarros;
	private ColorSemaforo colorActual;
	private List<SemaforoListener> semaforoListeners = new ArrayList<SemaforoListener>();

	//private static final int defaultCarros = 5;
	
	public Semaforo(Interseccion interse)
	{
		//interse.addListener(this);
		colorActual = ColorSemaforo.ROJO;
	}
	
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		while(true)
		{
			if (this.colorActual.equals(ColorSemaforo.VERDE) && (this.numCarros == 0))
			{
				this.colorActual = ColorSemaforo.ROJO;
				notificar();
			}
			else if (this.colorActual.equals(ColorSemaforo.VERDE) && (this.numCarros > 0))
			{
				try {
					
					Thread.sleep(1000 * numCarros);
					this.colorActual = ColorSemaforo.ROJO;
					notificar();
					System.out.print("[DEBUG] se termino el tiempo ");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
	
	public void addListener(InterseccionListener listener)
	{
		listeners.add(listener);
	}

	public void seFueCarro(int carros) {
		// TODO Auto-generated method stub
		this.numCarros--;
	}

	public void activarSemaforo(ColorSemaforo colorNuevo,int carros)
	{
			this.numCarros = carros;
			this.colorActual = ColorSemaforo.VERDE;
			notificar();
	}
	
	public void desactivarSemaforo()
	{
		this.colorActual = ColorSemaforo.ROJO;
		notificar();
		
	}
	
	public ColorSemaforo getColorActual()
	{
		return this.colorActual;

	}
	public void addListener(SemaforoListener  listener)
	{
		semaforoListeners.add(listener);
	}
	
	private void notificar()
	{
		for(SemaforoListener sem : semaforoListeners)
		{
			sem.cambioSemaforo(colorActual);
		}
	}
	
	
	
}
