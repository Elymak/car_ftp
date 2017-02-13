package bc_om.car_ftp.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

	private User user;
	
	@Before
	public void init() {
		user = new User("max", "luigi");
	}
	
	@Test
	public void testGetLogin() {
		assertEquals("max", this.user.getLogin());
	}
	
	@Test
	public void testSetLogin() {
		assertEquals("max", user.getLogin());
		user.setLogin("camille");
		assertEquals("camille", user.getLogin());
	}
	
	@Test
	public void testGetPassword() {
		assertEquals("luigi", this.user.getPassword());
	}
	
	@Test
	public void testSetPassword() {
		assertEquals("luigi", this.user.getPassword());
		this.user.setPassword("serial");
		assertEquals("serial", this.user.getPassword());
	}
	
	@Test
	public void testGetCurrentDirectory() {
		assertEquals("max", this.user.getCurrent_directory());
	}
	
	@Test
	public void testSetCurrentDirectory() {
		assertEquals("max", this.user.getCurrent_directory());
		this.user.setCurrent_directory("camille");
		assertEquals("camille", this.user.getCurrent_directory());
	}
	
	@Test
	public void testToString() {
		assertEquals("{max, luigi}", this.user.toString());
	}
	
	@Test
	public void testCompareTo() {
		User user = new User("max", "luigi");
		assertEquals(0, this.user.compareTo(user));
		user = new User("z", "z");
		assertTrue(this.user.compareTo(user) < 0);
		user = new User("a", "a");
		assertTrue(this.user.compareTo(user) > 0);
	}
}
