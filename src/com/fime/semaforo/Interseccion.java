package com.fime.semaforo;

import java.util.ArrayList;
import java.util.List;

public class Interseccion  implements Runnable {

	//private ArrayList <CalleListener> listeners ;
	private int[] streets ;
	private Semaforo[] semaforos ;
	private int carrilNueCarro;
	private Semaforo semaforoAct;
	private List<InterseccionListener> listeners;
	
	public Interseccion()
	{
		
		super();
		listeners = new ArrayList<InterseccionListener>();
		semaforos = new Semaforo[2];
		semaforos[0] = new Semaforo(this);
		semaforos[1] = new Semaforo(this);
		streets = new int[2];
	//	listeners = new ArrayList<CalleListener>();
		semaforoAct = semaforos[0];
		semaforoAct.activarSemaforo(ColorSemaforo.VERDE, 0);
		
		Thread t = new Thread(semaforos[0]);
		Thread t2 = new Thread(semaforos[1]);
		
		t.start();
		t2.start();
		
		//Iniciamos el semaforo 0 en verde.
	}

	
	public void run()
	{
		while (true)
		{
			
				carrilNueCarro = (int)((Math.random() * 10) %2);
				
				quitarCarro();
				
				if (streets[carrilNueCarro] < 6)
				{
					streets[carrilNueCarro] ++; 
					notificarLLegada();
				try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
		}
	}
	
	private void quitarCarro()
	{
		int i = (semaforoAct == semaforos[0]) ? 0 :1;
		
		if (semaforoAct == semaforos[i])
		{
			//verificar que el semaforo aun este en verde
			if (semaforoAct.getColorActual().equals(ColorSemaforo.VERDE))
			{
				if (streets[i] > 0)
				{
					streets[i]--;
					notificarRetirada();

				}else if (streets[i] <= 0 )
				{
					semaforoAct.desactivarSemaforo();
					semaforoAct = semaforos[(i == 0) ? 1 : 0];
				
					semaforoAct.activarSemaforo(ColorSemaforo.VERDE,streets[i]);
				}
			}else if (semaforoAct.getColorActual().equals(ColorSemaforo.ROJO))
			{
				semaforoAct.activarSemaforo(ColorSemaforo.VERDE,streets[i]);

			}
		} 
		
	}
	
	public void addListener(InterseccionListener listener)
	{
		listeners.add(listener);
	}
	
	private void notificarLLegada()
	{
		if (listeners != null)
		for (InterseccionListener listener: listeners)
			listener.llegoCarro(streets);
	}
	
	private void notificarRetirada()
	{
		if (semaforoAct.equals(semaforos[0]))
		{
			semaforoAct.seFueCarro(streets[0]);

		}else if (semaforoAct.equals(semaforos[1]))
		{
			semaforoAct.seFueCarro(streets[1]);
		}
		
		if(listeners != null)
		for (InterseccionListener listener : listeners)
		{
			listener.seFueCarro(streets);
		}
		
	}
}
