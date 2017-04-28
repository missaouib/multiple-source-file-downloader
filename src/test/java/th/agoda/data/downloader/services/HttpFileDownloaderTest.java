package th.agoda.data.downloader.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.enums.ProtoCol;
import th.agoda.data.downloader.logic.HttpFileDownloader;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HttpFileDownloaderTest {

	@Autowired
	public HttpFileDownloader httpFileDownloader;

	//FIXME: Add Assert and check error scenarios
	@Test
	public void testForValidHttptUri() {
		UrlBean urlBean = new UrlBean();
		urlBean.setProtocol(ProtoCol.HTTPS);
		urlBean.setHostname("www.google.co.in");
		urlBean.setUri("https://www.google.co.in/logos/doodles/2017/cassini-spacecraft-dives-between-saturn-and-its-rings-5717425520640000-law.gif");

		httpFileDownloader.readFile(urlBean);
	}
}
