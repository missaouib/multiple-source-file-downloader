package th.agoda.data.downloader.services;

import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.WindowsFakeFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.logic.FtpFileDownloader;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FtpFileDownloaderIT {

	private static final int FTP_PORT_NUMBER = 21;

	private FakeFtpServer fakeFtpServer;

	@Autowired
	private FtpFileDownloader ftpFileDownloader;

	@Before
	public void setUp() throws IOException {
		fakeFtpServer = new FakeFtpServer();
		fakeFtpServer.addUserAccount(new UserAccount("user", "password", "C:\\data"));

		FileSystem fileSystem = new WindowsFakeFileSystem();
		fileSystem.add(new DirectoryEntry("C:\\data"));
		FileEntry fileEntry = new FileEntry("C:\\data\\testData.jpg");
		InputStream inputStream = getClass().getResourceAsStream("/ftp/testData.jpg");
		fileEntry.setContents(IOUtils.toByteArray(inputStream));
		fileSystem.add(fileEntry);
		fakeFtpServer.setFileSystem(fileSystem);
		fakeFtpServer.setServerControlPort(FTP_PORT_NUMBER);

		fakeFtpServer.start();
	}

	@After
	public void tearDown() {
		fakeFtpServer.stop();
	}

	@Test
	public void testReadFileForValidFtpDownload() {
		UrlBean urlBean = new UrlBean();
		urlBean.setHostname("localhost");
		urlBean.setPort(FTP_PORT_NUMBER);
		urlBean.setRemoteFileName("C:\\data\\testData.jpg");
		urlBean.setUsername("user");
		urlBean.setPassword("password");
		ftpFileDownloader.readFile(urlBean);
	}

	@Test
	public void testReadFileForFtpConnectionException() {
		UrlBean urlBean = new UrlBean();
		urlBean.setHostname("localhost");
		urlBean.setPort(33);
		urlBean.setRemoteFileName("C:\\data\\testData.jpg");
		urlBean.setUsername("user");
		urlBean.setPassword("password");
		try {
			ftpFileDownloader.readFile(urlBean);
			Assert.fail("RuntimeException expected");
		} catch (RuntimeException e) {
			log.error("Exception caught "+e.getMessage());
		} catch(Exception e) {
			Assert.fail("RuntimeException expected");
		}
	}
}
