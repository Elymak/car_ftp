package bc_om.car_ftp;

import java.io.IOException;

import bc_om.car_ftp.server.FTPServer;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
        FTPServer server = new FTPServer();
        try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to create FTP Server");
		}
    }

}
