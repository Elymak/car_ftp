package bc_om.car_ftp.commands;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import bc_om.car_ftp.users.User;

public class CwdCommand extends Command{

	public CwdCommand(String command, User user, Socket s, ServerSocket data_socket) {
		super(command, user, s, data_socket);
	}

	@Override
	public void execute() {
		String file_wanted = command.substring(4);
		
		String path = "data/root/" + user.getCurrent_directory();
		if(!"".equals(file_wanted)){
			path += "/" + file_wanted;
		} else {
			System.out.println("[WARNING] directory is empty or null");
			try {
				super.dos.write("(remote-directory)".getBytes());
				file_wanted = super.br.readLine();
				
				if("".equals(file_wanted)){
					super.dos.write("usage : cd remote-directory\r\n".getBytes());
				}
				
			} catch (IOException e) {
				System.out.println("[ERROR] Cannot send usage of command CWD");
			}
			path += "/" + file_wanted;
			
		}
		
		File f = new File(path);
		File user_root = new File("data/root/" + user.getLogin());
		try {
			System.out.println("[LOG] user_root : " + user_root.getCanonicalPath());
			System.out.println("[LOG] f         : " + f.getCanonicalPath());
			
			String root = user_root.getCanonicalPath();
			String fi = f.getCanonicalPath();
			if(fi.length() < root.length()){
				//error
				System.out.println("[ERROR] User does not have permissions for this directory");
				super.dos.write("550 You do not have permissions for this directory\r\n".getBytes());
				
			} else {
				if(fi.substring(0, root.length()).equals(root)){
					//OK
					System.out.println("[INFO] Ok for this directory");
					if(f.isDirectory()){
						user.setCurrent_directory(user.getCurrent_directory()+ "/" + file_wanted);
						try {
							super.dos.write("250 Directory successfully changed\r\n".getBytes());
						} catch (IOException e) {
							System.out.println("[ERROR] Cannot send 250");
						}
					} else {
						System.out.println("[ERROR] This directory doesn't exist");
						try {
							super.dos.write("550 Failed to change directory\r\n".getBytes());
						} catch (IOException e) {
							System.out.println("[ERROR] Cannot send 550");
						}
					}
					System.out.println("[INFO] Current directory is now : "  + user.getCurrent_directory());
				} else {
					//error
					System.out.println("[ERROR] User does not have permissions for this directory");
					super.dos.write("550 You do not have permissions for this directory\r\n".getBytes());
				}
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

}
