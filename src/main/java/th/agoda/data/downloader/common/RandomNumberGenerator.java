package th.agoda.data.downloader.common;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomNumberGenerator {

	public long getRandomNumber() {
		Random random = new Random();
		return random.longs(0, Long.MAX_VALUE).limit(10).findFirst().getAsLong();
	}
}
