package bc_om.car_ftp;

import java.io.IOException;

import bc_om.car_ftp.log.ConsoleLogger;
import bc_om.car_ftp.log.LogType;
import bc_om.car_ftp.server.FTPServer;

/**
 * Classe Application qui lance le serveur FTP
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
			ConsoleLogger.log(LogType.ERROR, "Failed to create FTP Server");
		}
    }

}
