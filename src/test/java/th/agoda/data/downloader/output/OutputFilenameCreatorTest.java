package th.agoda.data.downloader.output;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OutputFilenameCreatorTest {

	@Autowired
	private OutputFilenameCreator outputFilenameCreator;

	@Test
	public void testCreate() {
		String outputFileName = outputFilenameCreator.create("test.agoda.com", "testFile.mp4");
		Assert.assertNotNull(outputFileName);
	}
}
