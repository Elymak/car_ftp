package bc_om.car_ftp.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.Socket;

import bc_om.car_ftp.commands.Command;
import bc_om.car_ftp.commands.ListCommand;
import bc_om.car_ftp.commands.PortCommand;
import bc_om.car_ftp.commands.SystCommand;
import bc_om.car_ftp.users.User;

public class CommandInterpreter {

	private Socket socket;
	private DatagramSocket data_socket;
	
	//infos pour le transfert de données
	private int port;
	private Inet4Address ipv4;
	
	private User user;
	
	private BufferedReader br;
	
	private DataOutputStream dos;
	
	private Command command;
	
	public CommandInterpreter(Socket s, DatagramSocket data_socket, User user){
		this.socket = s;
		this.data_socket = data_socket;
		
		this.user = user;
		
		try {
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.dos = new DataOutputStream(s.getOutputStream());
			
		} catch (IOException e) {
			System.out.println("[ERROR] Failed to get streams in command interpreter constructor");
			
		}
		
		
	}
	
	public void interpretCommand(String command) throws IOException{
		System.out.println("[COMMAND] " + user.getLogin() + " > " + command);
		
		if(command == null)
			throw new IOException("Commande null");
		
		String[] decomposed_command = command.split(" ");
		switch(decomposed_command[0]){
			case "ACCT":
				this.dos.write("202 Not Implemented\n".getBytes());
				break;
			case "CWD":
				this.dos.write("202 Not Implemented\n".getBytes());
				break;
			case "CDUP":
				this.dos.write("202 Not Implemented\n".getBytes());
				break;
			case "QUIT":
				this.dos.write("221 GoodBye\n".getBytes());
				break;
			case "RETR":
				this.dos.write("202 Not Implemented\n".getBytes());
				break;
			case "STOR":
				this.dos.write("202 Not Implemented\n".getBytes());
				break;
			case "DELE":
				this.dos.write("202 Not Implemented\n".getBytes());
				break;
			case "SYST":
				this.command = new SystCommand(command, user, socket, data_socket);
				break;
			case "PORT":
				this.command = new PortCommand(decomposed_command[1], user, socket, data_socket, this);
				break;
			case "LIST":
				this.command = new ListCommand(decomposed_command[1], user, socket, data_socket, this);
				break;
			case "MKD":
				this.dos.write("202 Not Implemented\n".getBytes());
				break;
			default:
				break;
		}
		this.command.execute();
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Inet4Address getIpv4() {
		return ipv4;
	}

	public void setIpv4(Inet4Address addr) {
		this.ipv4 = addr;
	}
}
