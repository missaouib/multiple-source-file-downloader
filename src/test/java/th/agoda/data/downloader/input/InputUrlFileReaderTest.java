package th.agoda.data.downloader.input;

import java.util.List;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import th.agoda.data.downloader.beans.UrlBean;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InputUrlFileReaderTest {

	@Autowired
	private InputUrlFileReader inputUrlFileReader;

	@Test
	public void testForValidInputUrlFile() {
		List<UrlBean> urlBeans = inputUrlFileReader.getUrlBeans("/testInputUrlList.txt");
		Assert.assertThat(urlBeans.size(), Is.is(3));

	}
}
