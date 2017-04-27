package th.agoda.data.downloader.common;

import org.junit.Test;

public class RandomNumberGeneratorTest {

	@Test
	public void testLongRandomNumber() {
		RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();
		System.out.println(randomNumberGenerator.getRandomNumber());
	}
}
