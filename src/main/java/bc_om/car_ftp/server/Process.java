package bc_om.car_ftp.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Process extends Thread{

	private Socket s;
	
	private InputStream is;
	private InputStreamReader isr;
	private BufferedReader br;
	
	private OutputStream os;
	private DataOutputStream dos;
	
	
	public Process(Socket socket) {
		this.s = socket;
		
		try {
			this.is = s.getInputStream();
			this.isr = new InputStreamReader(is);
			this.br = new BufferedReader(isr);
			
			this.os = s.getOutputStream();
			this.dos = new DataOutputStream(os);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to get streams in process constructor");
			
		}
		
		this.start();
	}
	
	@Override
	public void run(){
		System.out.println("Connexion acceptée");
		String str = "220";
		
		//Connection established
		try {
			this.dos.writeUTF(str);
			this.dos.writeUTF("\n");
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("Failed to send 220");
		}
		
		//USER
		try {
			String cmd = this.br.readLine();
			System.out.println(cmd);
		} catch (IOException e2) {
			e2.printStackTrace();
			System.out.println("Failed to read from buffer");
		}
		
		
		try {
			this.s.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to close the socket in Process");
		}
	}
	
}
