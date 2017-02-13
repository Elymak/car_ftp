package bc_om.car_ftp.commands;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import bc_om.car_ftp.server.CommandInterpreter;
import bc_om.car_ftp.users.User;
import bc_om.car_ftp.utils.DateParser;

public class ListCommand extends Command{
	
	private CommandInterpreter c;
	
	public ListCommand(String command, User user, Socket s, ServerSocket ds, CommandInterpreter c){
		super(command, user, s, ds);
		this.c = c;
	}
	
	@Override
	public void execute() {
		String file = "";
		
		if(command !=  null){
			if(!command.isEmpty() && !"LIST".equals(command))
				file = command;
		}
			
		File directory = new File("data/root/"+ user.getCurrent_directory() +"/"+ file);
		System.out.println("[INFO] Listing directory : " + directory.getAbsolutePath());
		try {
			DataOutputStream output = new DataOutputStream(c.getData_socket().getOutputStream());
			
			File[] listFiles = directory.listFiles();
			String res = "";
			for(int i = 0; i < listFiles.length ;i++){
				if(listFiles[i].isDirectory())
					res += "d";
				else
					res += "-";
				
				if(listFiles[i].canRead())
					res += "r";
				else
					res += "-";
				
				if(listFiles[i].canWrite())
					res += "w";
				else
					res += "-";
				
				if(listFiles[i].canExecute())
					res += "x";
				else
					res += "-";
				
				String date = DateParser.getDateFormat(listFiles[i].lastModified());
				
				res += "------ " + listFiles[i].length() + " " + date + " " + listFiles[i].getName() + "\r\n";
				System.out.println("[LOG] file : " + res);
			}
			
			super.dos.write("150 Here comes the directory listing\n".getBytes());
			output.write((res).getBytes());
			output.close();
			super.dos.write("226 Directory send OK.\n".getBytes());
			
		} catch (IOException e1) {
			System.out.println("[ERROR] Cannot write in command flux for listing");
		}
	}

}
