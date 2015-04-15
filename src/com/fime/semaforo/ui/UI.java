package com.fime.semaforo.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;

public class UI {
	public UI()
	{
		new Frame();
	}
	
	public class Frame extends JFrame
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private PanelInterseccion panelInter;
		
		public Frame() {
			// TODO Auto-generated constructor stub
			super();
			panelInter = new PanelInterseccion();
			panelInter.setPreferredSize(new Dimension(800,800));
			this.setSize(800,800);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.getContentPane().setLayout(new FlowLayout());
			this.getContentPane().add(panelInter);
		}
		
	}
	
	public static void main(String[] args)
	{
		UI f = new UI();
	}

}
