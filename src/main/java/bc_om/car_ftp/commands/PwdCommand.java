package bc_om.car_ftp.commands;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import bc_om.car_ftp.users.User;

public class PwdCommand extends Command{

	public PwdCommand(String command, User user, Socket s,
			ServerSocket data_socket) {
		super(command, user, s, data_socket);
	}

	@Override
	public void execute() {
		try {
			super.dos.write(("257 /" + user.getCurrent_directory() + "\n").getBytes());
		} catch (IOException e) {
			System.out.println("[ERROR] Cannot send current directory to FTP Client");
		}
	}

	

}
