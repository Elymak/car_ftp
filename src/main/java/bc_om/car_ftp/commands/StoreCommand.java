package bc_om.car_ftp.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.objenesis.instantiator.basic.NewInstanceInstantiator;

import bc_om.car_ftp.log.ConsoleLogger;
import bc_om.car_ftp.log.LogType;
import bc_om.car_ftp.server.CommandInterpreter;
import bc_om.car_ftp.users.User;

public class StoreCommand extends Command{

	private CommandInterpreter ci;

	public StoreCommand(String command, User user, Socket s, ServerSocket data_socket, CommandInterpreter ci) {
		super(command, user, s, data_socket);
		this.ci = ci;
	}

	@Override
	public void execute() {
		/*
		 * STOR
 			125, 150
 			(110)
 			226, 250
 			425, 426, 451, 551, 552
 			532, 450, 452, 553
 			500, 501, 421, 530
		 * 
		 */
		
		ConsoleLogger.log(LogType.INFO, "STOR command : " + command);
		ConsoleLogger.log(LogType.INFO, "STORE : data/root" + user.getCurrent_directory() + "/"+  command);
		
		/* création du fichier */
		File f = new File("data/root" + user.getCurrent_directory() + "/" + command);
				
		/* pour écrire dans le fichier */
		PrintWriter pw = null;
		try {
			pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			
			/* récupération du fluc d'entrée de la socket */
			BufferedReader br = new BufferedReader(new InputStreamReader(ci.getData_socket().getInputStream()));
			
			/* pret à recevoir */
			super.dos.write("125 \r\n".getBytes());
			
			/* on recoit la premiere ligne */
			String s = br.readLine();
			
			/* tant qu'on recoit */
			while(s != null){
				ConsoleLogger.log(LogType.INFO, "string received : " + s);
				
				/* on écrit */
				pw.println(s);
				s = br.readLine();
			}
			/* on ferme le fichier */
			
			pw.close();
			
			/* transfert terminé */
			super.dos.write("250 File Transfert complete\r\n".getBytes());
			
		} catch (IOException e) {
			ConsoleLogger.log(LogType.ERROR, "Cannot send 125");
		}
		
		
		
	}

}
