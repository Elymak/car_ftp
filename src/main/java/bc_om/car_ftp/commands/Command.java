package bc_om.car_ftp.commands;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Socket;

import bc_om.car_ftp.users.User;

/**
 * 
 * Classe abstraite commande
 * 
 * @author breuzon
 *
 */
/**
 * @author Serial
 *
 */
/**
 * @author Serial
 *
 */
public abstract class Command {
	
	/**
	 * commande du client ftp
	 */
	protected String command;
	
	/**
	 * utilisateur du client ftp
	 */
	protected User user;
	
	/**
	 * socket de commande
	 */
	protected Socket s;
	
	/**
	 * socket de transfert de fichiers
	 */
	protected DatagramSocket data_socket;
	
	
	/**
	 * input de la socket
	 */
	protected BufferedReader br;
	
	/**
	 * output de la socket
	 */
	protected DataOutputStream dos; 
	
	public Command (String command, User user, Socket s, DatagramSocket data_socket){
		this.command = command;
		this.user = user;
		this.s = s;
		this.data_socket = data_socket;
		
		try {
		
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.dos = new DataOutputStream(s.getOutputStream());
			
		} catch (IOException e) {
			System.out.println("[ERROR] Failed to get streams in command constructor");
			
		}
	}
	
	public abstract void execute();
}
