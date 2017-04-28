package th.agoda.data.downloader.common;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import th.agoda.data.downloader.enums.ProtoCol;
import th.agoda.data.downloader.logic.FileDownloader;
import th.agoda.data.downloader.logic.FtpFileDownloader;
import th.agoda.data.downloader.logic.HttpFileDownloader;
import th.agoda.data.downloader.logic.SftpFileDownloader;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileDownloaderFactoryTest {

	@Autowired
	private FileDownloaderFactory fileDownloaderFactory;

	@Test
	public void testGetForHTTPProtocol() {
		FileDownloader fileDownloader = fileDownloaderFactory.get(ProtoCol.HTTP);

		Assert.assertEquals(HttpFileDownloader.class, fileDownloader.getClass());
	}

	@Test
	public void testGetForHTTPSProtocol() {
		FileDownloader fileDownloader = fileDownloaderFactory.get(ProtoCol.HTTPS);

		Assert.assertEquals(HttpFileDownloader.class, fileDownloader.getClass());
	}

	@Test
	public void testGetForFTPProtocol() {
		FileDownloader fileDownloader = fileDownloaderFactory.get(ProtoCol.FTP);

		Assert.assertEquals(FtpFileDownloader.class, fileDownloader.getClass());
	}

	@Test
	public void testGetForSFTPProtocol() {
		FileDownloader fileDownloader = fileDownloaderFactory.get(ProtoCol.SFTP);

		Assert.assertEquals(SftpFileDownloader.class, fileDownloader.getClass());
	}
}
