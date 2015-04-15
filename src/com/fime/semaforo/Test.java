package com.fime.semaforo;

public class Test {
	
	public static void main(String[] args)
	{
		
		Interseccion inter = new Interseccion();
		
		Thread t = new Thread(inter);
		t.start();
		
	}

}
