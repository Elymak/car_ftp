package bc_om.car_ftp.server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Classe FTP qui gère les créations de Thread pour chaque connexion
 * @author breuzon
 *
 */
public class FTPServer {
	
	private static final int FTP_PORT = 2048; //totalement arbitraire
	private ServerSocket socket;
	
	public FTPServer(){
		try {
			this.socket = new ServerSocket(FTP_PORT);
		} catch (IOException e) {
			System.out.println("[ERROR] Failed to instanciate socket of FTP Server in constructor");
		}
	}
	
	
	/**
	 * lancement du serveur
	 * @throws IOException
	 */
	public void start() throws IOException{
		System.out.println("[INFO] FTPServer started");
		while(true){
			System.out.println("[INFO] En attente d'une connexion...");
			//connexion reçue = nouveau processus
			new Process(socket.accept());
		}
	}

	/*
	 * GETTERS AND SETTERS
	 * 
	 */
	
	public ServerSocket getServerSocket() {
		return socket;
	}

	public void setServerSocket(ServerSocket socket) {
		this.socket = socket;
	}
	
}
