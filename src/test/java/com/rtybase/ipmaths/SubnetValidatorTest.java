package com.rtybase.ipmaths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.rtybase.ipmaths.SubnetValidator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class SubnetValidatorTest {
	@Test(expected = IllegalArgumentException.class)
	public void testWithNegativeIpPrefixSize() throws Exception {
		new SubnetValidator("127.0.0.1", -24);
	}

	@Test(expected = NullPointerException.class)
	public void testWithNullIpPrefix() throws Exception {
		new SubnetValidator(null, 24);
	}

	@Test
	@Parameters({
		"192.168.0.0, 16, 192.168.1.20",
		"192.168.1.0, 24, 192.168.1.20",
		"192.168.1.20, 32, 192.168.1.20",
		"192.168.1.20, 128, 192.168.1.20",
		"192.168.1.0, 24, 192.168.1.255",
		"127.0.0.0, 24, 127.0.0.1",
		"255.255.255.0, 24, 255.255.255.255",
		"255.255.0.0, 16, 255.255.255.255",
		"255.0.0.0, 16, 255.0.0.0",
		"255.255.255.255, 32, 255.255.255.255",
		"192.168.0.0, 16, ::ffff:192.168.1.20",
		"0.0.0.0, 0, 192.168.1.20",

		"2607:f818:0:0:0:0:0:0, 32, 2607:f818:200:201:616c:4b4e:f19e:cee5",
		"2607:f818:200:201:616c:4b4e:f19e:cee5, 128, 2607:f818:200:201:616c:4b4e:f19e:cee5",
		"2607:f818:200:201:616c:4b4e:f19e:cee5, 256, 2607:f818:200:201:616c:4b4e:f19e:cee5",
		"ffff:ffff:ffff:ffff:ffff:ffff:ffff:ff00, 120, ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff",
		"ffff:ffff:ffff:ffff:ffff:ffff:ffff:fff0, 124, ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff",
		"ffff:ffff:0:0:0:0:0:0, 32, ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff",
		"0:0:0:0:0:0:0:0, 120, ::1",
		"2001:db8:0:0:0:0:0:0, 80, 2001:db8::1",
		"0:0:0:0:0:0:c0a8:100, 120, ::192.168.1.20"
	})
	public void testIPSubnetMatching(String ipPrefix, int ipPrefixBits, String ip) throws Exception {
		SubnetValidator validator = new SubnetValidator(ipPrefix, ipPrefixBits);
		assertTrue(validator.isInSubnet(ip));
	}

	@Test
	@Parameters({
		"192.168.0.0, 16, 192.169.1.20",
		"192.168.1.0, 24, 192.168.2.20",
		"192.168.1.20, 32, 192.168.1.21",
		"192.168.0.0, 16, ::ffff:192.169.1.20",

		"255.255.255.160, 27, 255.255.255.224",
		"255.255.160.0, 19, 255.255.224.0",

		"2607:f818:0:0:0:0:0:0, 32, 2607:f819:200:201:616c:4b4e:f19e:cee5",
		"ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffa0, 123, ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffe0"
	})
	public void testIPSubnetNotMatching(String ipPrefix, int ipPrefixBits, String ip) throws Exception {
		SubnetValidator validator = new SubnetValidator(ipPrefix, ipPrefixBits);
		assertFalse(validator.isInSubnet(ip));
	}
}
