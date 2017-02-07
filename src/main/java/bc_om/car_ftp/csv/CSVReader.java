package bc_om.car_ftp.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bc_om.car_ftp.users.User;

public class CSVReader {

	
	private static final String CSV_FILE = "ressources/users.csv";
	private static BufferedReader BR = null;
	private static final String COMMA = ",";
	
	public static List<User> getUsers(){
        String line = "";
        List<User> users = new ArrayList<User>();
        String[] user;

        try {

            BR = new BufferedReader(new FileReader(CSV_FILE));
            BR.readLine();
            while ((line = BR.readLine()) != null) {
            	user = line.split(COMMA); // CSV SEPARATOR
                users.add(new User(user[0], user[1]));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (BR != null) {
                try {
                    BR.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
	}
}
