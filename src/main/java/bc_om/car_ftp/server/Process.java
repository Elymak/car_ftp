package bc_om.car_ftp.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import bc_om.car_ftp.csv.CSVReader;
import bc_om.car_ftp.users.User;

/**
 * Classe qui gère une connexion au serveur
 * 
 * @author breuzon
 *
 */
public class Process extends Thread{

	private Socket s;
	private DatagramSocket data_transport_socket;
	
	private BufferedReader br;
	private DataOutputStream dos;
	
	private List<User> users;
	
	
	public Process(Socket socket) {
		this.s = socket;
		try {
			this.data_transport_socket = new DatagramSocket();
		} catch (SocketException e1) {
			System.out.println("[ERROR] Failed to create data transport socket");
		}
		
		this.users = CSVReader.getUsers();
		
		try {
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.dos = new DataOutputStream(s.getOutputStream());
			
		} catch (IOException e) {
			System.out.println("[ERROR] Failed to get streams in process constructor");
			
		}
		
		//lancement du process => run()
		this.start();
	}
	
	@Override
	public void run(){
		System.out.println("[INFO] New connexion");
		//Connection established
		
		//TODO USER AND PASSWORD in CommandInterpreter
		
		try {
			this.dos.write("220 Connexion with FTP server established\n".getBytes());
		} catch (IOException e) {
			System.out.println("[ERROR] Failed to send 220");
		}
		
		//USER commande
		try {
			String user_cmd = this.br.readLine();
			System.out.println("[COMMAND] " + user_cmd);
			
			if(authentification(user_cmd)){
				//begin commande password
				User user = getUser(user_cmd.substring(5));
				System.out.println("[INFO] User found : " + user);
				
				this.dos.write("331 Username OK, need password\n".getBytes());
				
				String passWrd_cmd = this.br.readLine();
				System.out.println("[COMMAND] " + passWrd_cmd);
				
				if(checkPassword(user, passWrd_cmd)){
					//ready to interprete commands
					System.out.println("[INFO] Authentification sucessful");
					this.dos.write("230 Login sucessful\n".getBytes());
					
					CommandInterpreter ci = new CommandInterpreter(s, data_transport_socket, user);
					
					String cmd = "";
					while(cmd != "QUIT" && cmd != null){
						cmd = this.br.readLine();
						try{
							ci.interpretCommand(cmd);
						} catch(IOException e){
							System.out.println(e.getMessage());
						}
					}
					
				} else {
					this.dos.write("530 Wrong password\n".getBytes());
				}
			} else {
				//renvoi erreur user inconnu
				System.out.println("[ERROR] No User found with login '" + user_cmd.substring(5) + "'");
				this.dos.write("530 User unknown\n".getBytes());
			}
		} catch (IOException e) {
			System.out.println("[ERROR] Failed to read 'USER' from buffer");
		}
		
		
		try {
			this.s.close();
			System.out.println("[INFO] Connexion fermée");
		} catch (IOException e) {
			System.out.println("[ERROR] Failed to close the socket in Process");
		}
	}
	
	private boolean checkPassword(User user, String cmd) {
		return user.getPassword().equals(cmd.substring(5));
	}

	public boolean authentification(String cmd) throws IOException{
//		chercher si le user est dans la base
		return getUser(cmd.substring(5)) != null;
	}
	
	public User getUser(String user){
		for(User u : this.users){
			if(u.getLogin().equals(user))
				return u;
		}
		return null;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public DatagramSocket getData_transport_socket() {
		return data_transport_socket;
	}

	public void setData_transport_socket(DatagramSocket data_transport_socket) {
		this.data_transport_socket = data_transport_socket;
	}
	
}
