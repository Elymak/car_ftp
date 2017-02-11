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
import java.net.ServerSocket;
import java.net.Socket;

import bc_om.car_ftp.commands.Command;
import bc_om.car_ftp.commands.ListCommand;
import bc_om.car_ftp.commands.NotImplementedCommand;
import bc_om.car_ftp.commands.PasvCommand;
import bc_om.car_ftp.commands.PortCommand;
import bc_om.car_ftp.commands.PwdCommand;
import bc_om.car_ftp.commands.SystCommand;
import bc_om.car_ftp.users.User;

public class CommandInterpreter {

	private Socket socket;
	private ServerSocket data_transport_socket;
	private Socket data_socket;
	
	//infos pour le transfert de données
	private int port;
	private Inet4Address ipv4;
	private boolean passif;
	
	private User user;
	
	private BufferedReader br;
	
	private DataOutputStream dos;
	
	private Command command;
	
	public CommandInterpreter(Socket s, ServerSocket data_transport_socket, User user){
		this.socket = s;
		this.data_transport_socket = data_transport_socket;
		this.passif = false;
		
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
			case "CWD":
			case "CDUP":
			case "QUIT":
			case "RETR":
			case "STOR":
			case "DELE":
			case "MKD":
				this.command = new NotImplementedCommand(command, user, socket, data_transport_socket);
				break;
			case "SYST":
				this.command = new SystCommand(command, user, socket, data_transport_socket);
				break;
			case "PORT":
				this.command = new PortCommand(decomposed_command[1], user, socket, data_transport_socket, this);
				break;
			case "LIST":
				this.command = new ListCommand(decomposed_command[0], user, socket, data_transport_socket, this);
				break;
			case "PWD":
				this.command = new PwdCommand(command, user, socket, data_transport_socket);
				break;
			case "PASV":
				this.command = new PasvCommand(command, user, socket, data_transport_socket, this);
				break;
			default:
				break;
		}
		this.command.execute();
	}
	
	public void accept(){
		try {
			this.data_socket = data_transport_socket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isPassif() {
		return passif;
	}

	public void setPassif(boolean passif) {
		this.passif = passif;
	}

	public Inet4Address getIpv4() {
		return ipv4;
	}

	public void setIpv4(Inet4Address addr) {
		this.ipv4 = addr;
	}

	public BufferedReader getBr() {
		return br;
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}

	public DataOutputStream getDos() {
		return dos;
	}

	public void setDos(DataOutputStream dos) {
		this.dos = dos;
	}
}
