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
		/* dossier rentré en paramètre */
		String directory_wanted = command.substring(4);
		
		/* chemin depuis le root du serveur */
		String path = "data/root/" + user.getCurrent_directory() + "/";
		
		/* si le parametre n'est pas "", alors il s'ajoute au path du server_root */
		if(!"".equals(directory_wanted)){
			path += directory_wanted;
		} else {
			/* sinon on demande à l'utilisateur de rentrer son ré&pertoire */
			System.out.println("[WARNING] directory is empty or null");
			try {
				super.dos.write("(remote-directory)".getBytes());
				directory_wanted = super.br.readLine();
				
				/* si encore vide, alors ce sera le répertoire home de l'utilisateur */
				if("".equals(directory_wanted)){
					super.dos.write("usage : cd remote-directory\r\n".getBytes());
				}
				
			} catch (IOException e) {
				
				/* log de l'erreur */				
				System.out.println("[ERROR] Cannot send usage of command CWD");
			}
			path += directory_wanted;
			
		}
		
		File f = new File(path);									/* dossier recherché */
		File user_file = new File("data/root/" + user.getLogin()); 	/* dossier utilisateur */
		File server_file = new File("data/root");					/* dossier du serveur */
		try {
			
			/* récupération des chemins absolu des fichiers créées ci-dessus */
			String user_root = user_file.getCanonicalPath();
			String server_root = server_file.getCanonicalPath();
			String f_name = f.getCanonicalPath();
			
			System.out.println("[INFO] Test for non absolute path...");
			if(f_name.length() < server_root.length()){
				/* test si le chemin est moins grand que le chemin de l'utilisateur */
				System.out.println("[ERROR] User does not have permissions for this directory");
				super.dos.write("550 You do not have permissions for this directory\r\n".getBytes());
				
			} else {
				
				/* test si le chemin est rentré par rapport au dossier courant de l'utilisateur */
				if(f_name.substring(0, user_root.length()).equals(user_root)){
					
					/* si le chemin demandé correspond bien à l'arborescence du serveur
					 * pour éviter de faire un cwd /../../../../../../etc/ etc... par exemple
					 */
					
					/* test di le fichier existe et est un répertoire */
					if(f.exists() && f.isDirectory()){
						System.out.println("[INFO] Ok for this directory");
						
						/* on set le nouveau current directory */
						user.setCurrent_directory(f.getCanonicalPath().substring(server_root.length()).replace("\\", "/"));
						try {
							
							/* on envoi la réponse au client */
							super.dos.write("250 Directory successfully changed\r\n".getBytes());
							
						} catch (IOException e) {
							
							/* log de l'erreur */
							System.out.println("[ERROR] Cannot send 250");
						}
						
					} else {
						/* test si le chemin est demandé par rapport au chemin absolu du server root */
						path = "data/root" + directory_wanted;
						f = new File(path);
						f_name = f.getCanonicalPath();
						
						System.out.println("[INFO] Le fichier n'exist pas ou n'est pas un répertoire\n"
										 + "[INFO] Test for absolute path...");
						
						if(f_name.length() < server_root.length()){
							/* test si le chemin est moins grand que le chemin du server root */
							System.out.println("[ERROR] User does not have permissions for this directory");
							super.dos.write("550 You do not have permissions for this directory\r\n".getBytes());
							
						}else {
							/* test si le chemin respecte bien l'arborescence du l'utilisateur */
							if(f_name.substring(0, user_root.length()).equals(user_root)){
								
								/* si le fichier exist et est un repertoire */
								if(f.exists() && f.isDirectory()){
									System.out.println("[INFO] Ok for this directory");
									
									/* on set le nouveau current directory */
									user.setCurrent_directory(f.getCanonicalPath().substring(server_root.length()).replace("\\", "/"));
									
									try {
										
										/* on envoi la réponse au client */
										super.dos.write("250 Directory successfully changed\r\n".getBytes());
									} catch (IOException e) {
										/* log de l'erreur */
										System.out.println("[ERROR] Cannot send 250");
									}
								}
							} else {
								
								/* sinon on envoi une errur, fichier inaccessible */
								System.out.println("[ERROR] Cannot access to this directory");
								try {
									super.dos.write("550 Failed to change directory\r\n".getBytes());
								} catch (IOException e) {
									System.out.println("[ERROR] Cannot send 550");
								}
							}
						}
					}
					
					/* log repetoire courant */
					System.out.println("[INFO] Current directory is now : "  + user.getCurrent_directory());
				} else {
					/* log erreur permission fichier */
					System.out.println("[ERROR] User does not have permissions for this directory");
					super.dos.write("550 You do not have permissions for this directory\r\n".getBytes());
				}
			}
			
		} catch (IOException e1) {
			/* log fichier inexsitant exception */
			System.out.println("[ERROR] Cannot read this file");
		} catch (StringIndexOutOfBoundsException e2){
			/* log si une exception est levée lors de la vérification des chemins absolu par rapport au root
			 * si on a uen excpetion, alors l'utilisateur tente d'accéder au dossier du serveur
			 */
			
			System.out.println("[LOG] No permssions for this directory");
			
			try {
				super.dos.write("550 You have no permissions access to this directory\r\n".getBytes());
			} catch (IOException e) {
				System.out.println("[ERROR] Cannot send error");
			}
		}
		
	}

}
