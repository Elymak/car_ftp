package bc_om.car_ftp.commands;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import bc_om.car_ftp.log.ConsoleLogger;
import bc_om.car_ftp.log.LogType;
import bc_om.car_ftp.server.CommandInterpreter;
import bc_om.car_ftp.users.User;

public class RetrCommand extends Command{

	private CommandInterpreter ci;

	public RetrCommand(String command, User user, Socket s,
			ServerSocket data_socket, CommandInterpreter ci) {
		super(command, user, s, data_socket);
		this.ci = ci;
	}

	@Override
	public void execute() {
		File file = new File("data/root" + user.getCurrent_directory() + "/" + command);
		DataOutputStream output = null;
		BufferedReader buffer = null;
		try {
			output = new DataOutputStream(ci.getData_socket().getOutputStream());
			buffer = new BufferedReader(new FileReader(file));
		} catch (IOException e) {
			ConsoleLogger.log(LogType.ERROR, "Cannot get input/output stream");
			
			try {
				super.dos.write("425 Cannot Send data\r\n".getBytes());
			} catch (IOException e1) {
				ConsoleLogger.log(LogType.ERROR, "Cannot send error 425");
			}
		}
		
		
		try {
			String s = "";
			super.dos.write("125 Starting transfert\r\n".getBytes());
			s = buffer.readLine();
			while(s != null){
				output.write((s + "\r\n").getBytes());
				s = buffer.readLine();
			}
			super.dos.write("226 File transfert completed\r\n".getBytes());
			
			output.close();
			buffer.close();
			
		} catch (IOException e) {
			ConsoleLogger.log(LogType.ERROR, "Cannot send data");
			
			try {
				super.dos.write("425 Cannot Send data\r\n".getBytes());
			} catch (IOException e1) {
				ConsoleLogger.log(LogType.ERROR, "Cannot send error 425");
			}
		}
		
		
		
	}

	

}
