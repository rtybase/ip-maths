package com.rtybase.ipmaths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.net.InetAddresses;
import com.rtybase.ipmaths.SubnetUtils;
import com.rtybase.ipmaths.SubnetValidator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class SubnetUtilsTest {

	@Test
	@Parameters({
		"::ffff:192.168.1.20, 0:0:0:0:0:ffff:c0a8:114",
		"192.168.1.20, 0:0:0:0:0:ffff:c0a8:114",
		"::192.168.1.20, 0:0:0:0:0:0:c0a8:114",
		"::ffff:127.0.0.1, 0:0:0:0:0:ffff:7f00:1"
	})
	public void testConvertIPv4ToIPv6(String inputIp, String expectedIpv6) throws Exception {
		assertEquals(SubnetUtils.convertIpv4ToIpv6(InetAddresses.forString(inputIp)).getHostAddress(), expectedIpv6);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructValidatorWithNullArgument() throws Exception {
		SubnetUtils.subnetValidatorWith(null);
	}

	@Test(expected = IllegalArgumentException.class)
	@Parameters({
		"127.0.0.1",
		"0/0/0"
	})
	public void testConstructValidatorWithWrongArgument(String wrongArgument) throws Exception {
		SubnetUtils.subnetValidatorWith(wrongArgument);
	}

	@Test
	public void testConstructValidator() throws Exception {
		SubnetValidator validator = SubnetUtils.subnetValidatorWith("192.168.0.0/16");
		assertTrue(validator.isInSubnet("192.168.1.20"));
	}
}
