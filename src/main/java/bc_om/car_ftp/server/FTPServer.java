package bc_om.car_ftp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {
	
	private static final int FTP_PORT = 2048;

	private ServerSocket socket;
	
	public FTPServer(){
		try {
			this.socket = new ServerSocket(FTP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to instanciate socket of FTP Server in constructor");
		}
	}
	
	public void start() throws IOException{
		System.out.println("FTPServer started");
		while(true){
			System.out.println("En attente d'une connexion...");
			new Process(socket.accept());
		}
	}

	public ServerSocket getServerSocket() {
		return socket;
	}

	public void setServerSocket(ServerSocket socket) {
		this.socket = socket;
	}
	
}
