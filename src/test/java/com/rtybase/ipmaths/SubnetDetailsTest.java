package com.rtybase.ipmaths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.google.common.net.InetAddresses;
import com.rtybase.ipmaths.SubnetDetails;

public class SubnetDetailsTest {

	private static final int PREFIX_SIZE_IN_BITS = 24;
	private static final String IPV4_ADDRESS_PREFIX = "127.0.0.1";
	private static final String IPV6_ADDRESS_PREFIX = "::1";
	private static final String IPV4_IN_IPV6_ADDRESS_PREFIX = "::ffff:127.0.0.1";

	private SubnetDetails subnetDetails;

	@Before
	public void setUp() {
		subnetDetails = new SubnetDetails(InetAddresses.forString(IPV4_ADDRESS_PREFIX), PREFIX_SIZE_IN_BITS);
	}

	@Test
	public void testEquals() {
		assertTrue(subnetDetails.equals(subnetDetails));

		SubnetDetails otherSubnetDetails = new SubnetDetails(InetAddresses.forString(IPV4_ADDRESS_PREFIX),
				PREFIX_SIZE_IN_BITS);
		assertTrue(subnetDetails.equals(otherSubnetDetails));
		assertEquals(subnetDetails.hashCode(), otherSubnetDetails.hashCode());
	}

	@Test
	public void testIPv4inIPv6Equals() {
		SubnetDetails otherSubnetDetails = new SubnetDetails(InetAddresses.forString(IPV4_IN_IPV6_ADDRESS_PREFIX),
				PREFIX_SIZE_IN_BITS);
		assertTrue(subnetDetails.equals(otherSubnetDetails));
		assertEquals(subnetDetails.hashCode(), otherSubnetDetails.hashCode());
	}

	@Test
	public void testNotEquals() {
		assertFalse(subnetDetails.equals(null));

		SubnetDetails otherSubnetDetails = new SubnetDetails(InetAddresses.forString("127.0.0.2"), PREFIX_SIZE_IN_BITS);
		assertFalse(subnetDetails.equals(otherSubnetDetails));
		assertNotEquals(subnetDetails.hashCode(), otherSubnetDetails.hashCode());

		otherSubnetDetails = new SubnetDetails(InetAddresses.forString(IPV4_ADDRESS_PREFIX), 20);
		assertFalse(subnetDetails.equals(otherSubnetDetails));
		assertNotEquals(subnetDetails.hashCode(), otherSubnetDetails.hashCode());
	}

	@Test
	public void testIPv4AndIPv6NotEquals() {
		SubnetDetails otherSubnetDetails = new SubnetDetails(InetAddresses.forString(IPV6_ADDRESS_PREFIX),
				PREFIX_SIZE_IN_BITS);
		assertFalse(subnetDetails.equals(otherSubnetDetails));
		assertNotEquals(subnetDetails.hashCode(), otherSubnetDetails.hashCode());
	}
}
