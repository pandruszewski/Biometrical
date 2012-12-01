package pl.edu.wat.pz.czujnik;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Serwer {
	private ServerSocket server = null;
	private Socket socket = null;
	private Random rn = null;
	private PrintWriter out = null;

	public Serwer() {
		rn = new Random();
		try {
			server = new ServerSocket(8180);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			
			socket = server.accept();
			System.out.println("LAALLALALALA");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		if (socket != null) {
			try {
				out = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (out != null) {
int a = 0;
			//System.out.println("out");
			while(true) {
				//System.out.println("wyslane");
				//String s = String.valueOf(i);
				a = (int) ((rn.nextDouble() * 100000) - 50000) ;
				
				System.out.println(a);
				out.println(a);
				out.flush();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Serwer();
	}

}
