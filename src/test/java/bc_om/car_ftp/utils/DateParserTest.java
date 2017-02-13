package bc_om.car_ftp.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DateParserTest {

	@Test
	public void testGetDateFormat() {
		assertEquals("Feb 14 2017", DateParser.getDateFormat(148710000L * 10000));
	}

}
