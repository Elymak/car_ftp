package bc_om.car_ftp.commands;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

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
		// TODO Auto-generated method stub
		ci.setPassif(true);
		try {
			
			Inet4Address addr = (Inet4Address) InetAddress.getLocalHost();
			String Ipv4 = addr.getCanonicalHostName().replace(".", ",");
			int port = data_socket.getLocalPort();
			System.out.println("[INFO] ipv4 = " + Ipv4 + "," + port/256 + "," + port%256);
			
			super.dos.write(("227 Passive Mode Activated. " + Ipv4 + "," + port/256 + "," + port%256).getBytes());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
