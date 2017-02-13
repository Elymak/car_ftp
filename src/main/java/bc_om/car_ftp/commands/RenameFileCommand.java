package bc_om.car_ftp.commands;

import java.net.ServerSocket;
import java.net.Socket;

import bc_om.car_ftp.users.User;

public class RenameFileCommand extends Command{

	public RenameFileCommand(String command, User user, Socket s,
			ServerSocket data_socket) {
		super(command, user, s, data_socket);
	}

	@Override
	public void execute() {
		
//		File f = new File(command);
		
		
		
		
	}

}
