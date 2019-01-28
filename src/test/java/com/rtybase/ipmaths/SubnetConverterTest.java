package com.rtybase.ipmaths;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.rtybase.ipmaths.SubnetConverter;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class SubnetConverterTest {

	private static final int NOT_USED = 1;

	@Test(expected = IllegalArgumentException.class)
	public void testWithNegativeIpv4Prefix() throws Exception {
		new SubnetConverter(-200, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithNegativeIpv6Prefix() throws Exception {
		new SubnetConverter(1, -200);
	}

	@Test
	@Parameters({
		"16, 192.168.1.20, 192.168.0.0/16",
		"24, 192.168.1.20, 192.168.1.0/24",
		"32, 192.168.1.20, 192.168.1.20/32",
		"128, 192.168.1.20, 192.168.1.20/32",
		"24, 192.168.1.255, 192.168.1.0/24",
		"24, 127.0.0.1, 127.0.0.0/24",
		"24, 255.255.255.255, 255.255.255.0/24",
		"16, 255.255.255.255, 255.255.0.0/16",
		"16, 255.0.0.0, 255.0.0.0/16",
		"32, 255.255.255.255, 255.255.255.255/32",
		"0, 255.255.255.255, 0.0.0.0/0",
		"1, 255.255.255.255, 128.0.0.0/1"
	})
	public void testIPv4SubnetConversion(int ipv4PrefixBits, String ip, String expectedResult) throws Exception {
		SubnetConverter converter = new SubnetConverter(ipv4PrefixBits, NOT_USED);
		assertEquals(converter.toCidrNotation(ip).toString(), expectedResult);
	}

	@Test
	public void testIPv4inIPv6SubnetConversion() throws Exception {
		SubnetConverter converter = new SubnetConverter(16, NOT_USED);
		assertEquals(converter.toCidrNotation("::ffff:192.168.1.20").toString(), "192.168.0.0/16");
	}

	@Test
	@Parameters({
		"32, 2607:f818:200:201:616c:4b4e:f19e:cee5, 2607:f818:0:0:0:0:0:0/32",
		"128, 2607:f818:200:201:616c:4b4e:f19e:cee5, 2607:f818:200:201:616c:4b4e:f19e:cee5/128",
		"256, 2607:f818:200:201:616c:4b4e:f19e:cee5, 2607:f818:200:201:616c:4b4e:f19e:cee5/128",
		"120, ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff, ffff:ffff:ffff:ffff:ffff:ffff:ffff:ff00/120",
		"124, ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff, ffff:ffff:ffff:ffff:ffff:ffff:ffff:fff0/124",
		"32, ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff, ffff:ffff:0:0:0:0:0:0/32",
		"120, ::1, 0:0:0:0:0:0:0:0/120",
		"80, 2001:db8::1, 2001:db8:0:0:0:0:0:0/80",
		"120, ::192.168.1.20, 0:0:0:0:0:0:c0a8:100/120"
	})
	public void testIPv6SubnetConversion(int ipv6PrefixBits, String ip, String expectedResult) throws Exception {
		SubnetConverter converter = new SubnetConverter(NOT_USED, ipv6PrefixBits);
		assertEquals(converter.toCidrNotation(ip).toString(), expectedResult);
	}
}
