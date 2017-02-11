package bc_om.car_ftp.commands;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import bc_om.car_ftp.server.CommandInterpreter;
import bc_om.car_ftp.users.User;

public class PortCommand extends Command{

	private CommandInterpreter c;
	
	public PortCommand(String command, User user, Socket s,  ServerSocket ds, CommandInterpreter c) {
		super(command, user, s, ds);
		this.c  = c;
	}

	@Override
	public void execute() {
		String[] infos = super.command.split(",");
		String addr = infos[0] +"." + infos[1] + "." +infos[2] +"." + infos[3];
		int port = Integer.parseInt(infos[4]) * 256 + Integer.parseInt(infos[5]);
		
		c.setPort(port);
		try {
			c.setIpv4((Inet4Address) InetAddress.getByName(addr));
			
			try {
				super.dos.write("200 PORT command successful.\n".getBytes());
			} catch (IOException e) {
				System.out.println("[ERROR] Cannot send port command success");
			}
		} catch (UnknownHostException e) {
			System.out.println("[ERROR] Cannot set Ipv4 address");
			try {
				super.dos.write("501 Syntax Error in parameters or arguments\n".getBytes());
			} catch (IOException e1) {
				System.out.println("[ERROR] Cannot send error Ipv4 setting");
			}
		}
		
		
		
	}

}
