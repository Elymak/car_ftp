package bc_om.car_ftp.commands;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;

import bc_om.car_ftp.users.User;

public class SystCommand extends Command{

	public SystCommand(String command, User user, Socket s, DatagramSocket ds) {
		super(command, user, s, ds);
	}

	@Override
	public void execute() {
		try {
			super.dos.write("202 UNIX\n".getBytes());
		} catch (IOException e) {
			System.out.println("[ERROR] Cannot write from SystCommand");
		}
	}

}
