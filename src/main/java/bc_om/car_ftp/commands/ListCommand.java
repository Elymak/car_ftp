package bc_om.car_ftp.commands;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

import bc_om.car_ftp.server.CommandInterpreter;
import bc_om.car_ftp.users.User;

public class ListCommand extends Command{
	
	private CommandInterpreter c;
	
	public ListCommand(String command, User user, Socket s, ServerSocket ds, CommandInterpreter c){
		super(command, user, s, ds);
		this.c = c;
	}
	
	@Override
	public void execute() {
		File directory = new File("data/root"+ super.user.getCurrent_directory());
		System.out.println("[INFO] " + directory.getAbsolutePath());
		
		String[] listFiles = directory.list();
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < listFiles.length ;i++){
			builder.append(listFiles[i] + "\r\n");
		}

		try {
			super.dos.write("150 Here comes the directory listing\n".getBytes());
			DatagramPacket packet = new DatagramPacket(builder.toString().getBytes(), builder.toString().length(), c.getIpv4(), c.getPort());
//			data_socket.send(packet);
			super.dos.write("226 Directory send OK.\n".getBytes());
			
		} catch (IOException e) {
			System.out.println("[ERROR] Cannot write into socket from command");
		}
		
		
	}

}
