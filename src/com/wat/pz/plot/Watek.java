package com.wat.pz.plot;

public class Watek extends Thread{

	
	public Watek(){

	}
	
	public void run(){

		while(true){


			try {
				sleep(5000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
