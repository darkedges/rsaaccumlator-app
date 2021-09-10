package org.starcoin.rsa;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import kotlin.random.Random;

public class RSAJavaTest {
	@Test
	public void testRSAAccumulator() {
		RSAAccumulator accumulator = new RSAAccumulator();
		BigInteger x0 = new BigInteger(Random.Default.nextBytes(128));
		BigInteger commit1 = accumulator.add(x0);
		TwoValue<BigInteger> proof0 = accumulator.proveMembership(x0);

		Assert.assertEquals(accumulator.getSize(), 1);
		Assert.assertEquals(accumulator.getA0(), proof0.getFirst());
		Assert.assertTrue(RSAAccumulator.verifyMembership(commit1, proof0));
	}
}
