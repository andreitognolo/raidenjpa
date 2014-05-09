package org.raidenjpa.util;

import org.junit.Assert;
import org.junit.Test;

public class UtilTest {
	
	@Test
	public void isInt() {
		
		Assert.assertTrue(Util.isInteger(Integer.toString(Integer.MAX_VALUE)));
		Assert.assertTrue(Util.isInteger("000"));
		Assert.assertTrue(Util.isInteger("314159265"));
		Assert.assertFalse(Util.isInteger(Long.toString(1l + (long)Integer.MAX_VALUE)));
		Assert.assertFalse(Util.isInteger("0x8"));
		Assert.assertFalse(Util.isInteger("100000000000000000000000000010"));
		
	}

}
