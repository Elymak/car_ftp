package bc_om.car_ftp.commands;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import bc_om.car_ftp.users.User;

public class NotImplementedCommand extends Command{


	public NotImplementedCommand(String command, User user, Socket s, ServerSocket data_socket) {
		super(command, user, s, data_socket);
	}

	@Override
	public void execute() {
		try {
			super.dos.write("202 Not Implemented Yet\n".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
