package bc_om.car_ftp.csv;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import bc_om.car_ftp.users.User;

public class CSVReaderTest {

	@Test
	public void testGetUsers() throws IOException {
		List<User> users = new ArrayList<User>();
		users.add(new User("admin", "admin1"));
		users.add(new User("camille", "serial"));
		users.add(new User("max", "luigi"));
		
		BufferedReader brMock = Mockito.mock(BufferedReader.class);
		Mockito.when(brMock.readLine()).thenReturn("Login,Password").thenReturn("admin,admin1").thenReturn("camille,serial").thenReturn("max,luigi").thenReturn(null);
		Mockito.doNothing().when(brMock).close();
		
		assertEquals(users, CSVReader.getUsers());
		
	}

}
