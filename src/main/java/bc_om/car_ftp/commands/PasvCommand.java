package bc_om.car_ftp.commands;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import bc_om.car_ftp.server.CommandInterpreter;
import bc_om.car_ftp.users.User;

public class PasvCommand extends Command {
	
	private CommandInterpreter ci;

	public PasvCommand(String command, User user, Socket s, ServerSocket data_socket, CommandInterpreter ci) {
		super(command, user, s, data_socket);
		this.ci = ci;
	}

	@Override
	public void execute() {
		ci.setPassif(true);
		try {
			
			Inet4Address addr = (Inet4Address) InetAddress.getLocalHost();
			String Ipv4 = addr.getHostAddress().replace(".", ",");
			int port = data_socket.getLocalPort();
			
			super.dos.write(("227 Passive Mode Activated. " + Ipv4 + "," + port/256 + "," + port%256 + "\n").getBytes());
			ci.accept();
			
		} catch (IOException e) {
			System.out.println("[ERROR] Cannot set passive mode");
		}
		
		
	}

}
