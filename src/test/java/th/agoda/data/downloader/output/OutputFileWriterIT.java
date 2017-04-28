package th.agoda.data.downloader.output;

import java.io.InputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import th.agoda.data.downloader.beans.UrlBean;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OutputFileWriterIT {

	@Autowired
	private OutputFileWriter outputFileWriter;

	@Test
	public void testSaveFile() {
		UrlBean urlBean = new UrlBean();
		urlBean.setHostname("test.agoda.com");
		urlBean.setRemoteFileName("testFile.jpg");
		urlBean.setUri("http://test.agoda.com/testFile.jpg");

		InputStream inputStream = getClass().getResourceAsStream("/ftp/testData.jpg");
		outputFileWriter.saveFile(urlBean, inputStream);
	}

}
