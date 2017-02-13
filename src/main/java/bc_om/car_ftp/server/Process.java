package bc_om.car_ftp.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import bc_om.car_ftp.csv.CSVReader;
import bc_om.car_ftp.users.User;

/**
 * Classe qui gère une connexion au serveur
 * 
 * @author breuzon
 *
 */
public class Process extends Thread {

	private Socket s;
	private ServerSocket data_transport_socket;

	private BufferedReader br;
	private DataOutputStream dos;

	private List<User> users;
	private User user;

	public Process(Socket socket) {
		this.s = socket;
		try {
			this.data_transport_socket = new ServerSocket(0);
		} catch (IOException e1) {
			System.out.println("[ERROR] Cannot create ServerSocket in Process constructor");
		}
		
		this.users = CSVReader.getUsers();

		try {
			this.br = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			this.dos = new DataOutputStream(s.getOutputStream());

		} catch (IOException e) {
			System.out
					.println("[ERROR] Failed to get streams in process constructor");

		}

		// lancement du process => run()
		this.start();
	}

	@Override
	public void run() {
		System.out.println("[INFO] New connexion");
		// Connection established

		// TODO USER AND PASSWORD in CommandInterpreter

		try {
			this.dos.write("220 Connexion with FTP server established\n"
					.getBytes());
		} catch (IOException e) {
			System.out.println("[ERROR] Failed to send 220");
		}

		// USER commande

		// boucle tant qu'on est pas reconnu par le serveur
		boolean connected = false, exception = false;
		while (!connected && !exception) {
			String user_cmd;
			try {
				user_cmd = this.br.readLine();
				System.out.println("[COMMAND] " + user_cmd);
				if (authentification(user_cmd)) {
					// begin commande password
					user = getUser(user_cmd.substring(5));
					System.out.println("[INFO] User found : " + user);

					this.dos.write("331 Username OK, need password\n"
							.getBytes());

					String passWrd_cmd = this.br.readLine();
					System.out.println("[COMMAND] " + passWrd_cmd);

					if (checkPassword(user, passWrd_cmd)) {
						connected = true;
					} else {
						this.dos.write("530 Wrong password\n".getBytes());
					}
				} else {
					// renvoi erreur user inconnu
					System.out.println("[ERROR] No User found with login '"
							+ user_cmd.substring(5) + "'");
					this.dos.write("530 User unknown\n".getBytes());
				}
			} catch (IOException e) {
				e.printStackTrace();
				exception = true;
			}
		}

		if (!exception && connected) {
			// ready to interprete commands
			System.out.println("[INFO] Authentification sucessful");
			try {
				this.dos.write("230 Login sucessful\n".getBytes());
				CommandInterpreter ci = new CommandInterpreter(s,
						data_transport_socket, user);

				String cmd = "";
				while (cmd != "QUIT" && cmd != null) {
					cmd = this.br.readLine();
					try {
						ci.interpretCommand(cmd);
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
				}
			} catch (IOException e1) {
				System.out.println("[ERROR] Cannot write 230");
			}
		}

		try {
			this.s.close();
			System.out.println("[INFO] Connexion fermée");
		} catch (IOException e) {
			System.out.println("[ERROR] Failed to close the socket in Process");
		}
	}

	private boolean checkPassword(User user, String cmd) {
		return user.getPassword().equals(cmd.substring(5));
	}

	public boolean authentification(String cmd) throws IOException {
		// chercher si le user est dans la base
		return getUser(cmd.substring(5)) != null;
	}

	public User getUser(String user) {
		for (User u : this.users) {
			if (u.getLogin().equals(user))
				return u;
		}
		return null;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public ServerSocket getData_transport_socket() {
		return data_transport_socket;
	}

	public void setData_transport_socket(ServerSocket data_transport_socket) {
		this.data_transport_socket = data_transport_socket;
	}

}
