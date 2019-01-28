package com.rtybase.ipmaths;

import org.junit.Test;

import com.rtybase.ipmaths.SubnetConverter;
import com.rtybase.ipmaths.SubnetUtils;
import com.rtybase.ipmaths.SubnetValidator;

public class SubnetUtilsSpeedTest {
	private static final int TRIES = 1_000_000;

//	@Test
	public void testTiming() throws Exception {
		long start = System.currentTimeMillis();
		SubnetConverter c = SubnetUtils.cidrConverterWith(16, 1);
		for (int i = 0; i < TRIES; i++) {
			c.toCidrNotation("192.168.1.20");
		}
		long duration = System.currentTimeMillis() - start;
		System.out.println("IPv4: " + duration + "ms");
		System.out.println("IPv4: " + ((1.0 * duration) / TRIES) + "ms/op");

		start = System.currentTimeMillis();
		c = SubnetUtils.cidrConverterWith(1, 32);
		for (int i = 0; i < TRIES; i++) {
			c.toCidrNotation("2607:f818:200:201:616c:4b4e:f19e:cee5");
		}
		duration = System.currentTimeMillis() - start;
		System.out.println("IPv6 light: " + duration + "ms");
		System.out.println("IPv6 light: " + ((1.0 * duration) / TRIES) + "ms/op");

		start = System.currentTimeMillis();
		c = SubnetUtils.cidrConverterWith(1, 120);
		for (int i = 0; i < TRIES; i++) {
			c.toCidrNotation("::192.168.1.20");
		}
		duration = System.currentTimeMillis() - start;
		System.out.println("IPv6 super-slow: " + duration + "ms");
		System.out.println("IPv6 super-slow: " + ((1.0 * duration) / TRIES) + "ms/op");
		System.out.println("--------------------");
	}

//	@Test
	public void testValidation() throws Exception {
		long start = System.currentTimeMillis();
		SubnetValidator validator = SubnetUtils.subnetValidatorWith("192.168.0.0/16");
		for (int i = 0; i < TRIES; i++) {
			validator.isInSubnet("192.168.1.20");
		}
		long duration = System.currentTimeMillis() - start;
		System.out.println("IPv4: " + duration + "ms");
		System.out.println("IPv4: " + ((1.0 * duration) / TRIES) + "ms/op");

		start = System.currentTimeMillis();
		validator = SubnetUtils.subnetValidatorWith("2607:f818:0:0:0:0:0:0/32");
		for (int i = 0; i < TRIES; i++) {
			validator.isInSubnet("2607:f818:200:201:616c:4b4e:f19e:cee5");
		}
		duration = System.currentTimeMillis() - start;
		System.out.println("IPv6: " + duration + "ms");
		System.out.println("IPv6: " + ((1.0 * duration) / TRIES) + "ms/op");
		System.out.println("--------------------");
	}
}
