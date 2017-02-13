package bc_om.car_ftp.log;

public class ConsoleLogger {

	public static void log(LogType type, String message) {
		System.out.println("[" + type.toString() + "]" + message);
	}
	
}
