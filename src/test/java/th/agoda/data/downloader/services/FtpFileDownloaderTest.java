package th.agoda.data.downloader.services;

import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.logic.FtpFileDownloader;
import th.agoda.data.downloader.output.OutputFileWriter;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class FtpFileDownloaderTest {

	@Mock
	private FTPClient ftpClient;

	@Mock
	private OutputFileWriter outputFileWriter;

	@InjectMocks
	private FtpFileDownloader ftpFileDownloader;

	@Test
	public void testReadFileForConnectionException() throws IOException {
		Mockito.doThrow(IOException.class).when(ftpClient).connect(Mockito.anyString(), Mockito.anyInt());
		try {
			UrlBean urlBean = Mockito.mock(UrlBean.class);
			ftpFileDownloader.readFile(urlBean);
			Assert.fail("RuntimeException expected");
		} catch (RuntimeException e) {
			log.error("RuntimeException at FtpFileDownloaderTest.testReadFileForConnectionException ");
		} catch (Exception e) {
			Assert.fail("RuntimeException expected");
		}
	}

	@Test
	public void testReadFileForNotLoggedIn() throws IOException {
		Mockito.doNothing().when(ftpClient).connect(Mockito.anyString(), Mockito.anyInt());
		Mockito.when(ftpClient.login(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
		UrlBean urlBean = Mockito.mock(UrlBean.class);

		ftpFileDownloader.readFile(urlBean);

		Mockito.verify(ftpClient, Mockito.times(0)).retrieveFileStream(Mockito.anyString());
		Mockito.verify(outputFileWriter, Mockito.times(0)).saveFile(Mockito.any(UrlBean.class), Mockito.any(InputStream.class));
	}

	@Test
	public void testReadFileForFtpRetrieveException() throws IOException {
		Mockito.doNothing().when(ftpClient).connect(Mockito.anyString(), Mockito.anyInt());
		Mockito.when(ftpClient.login(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		Mockito.doThrow(IOException.class).when(ftpClient).retrieveFileStream(Mockito.anyString());

		UrlBean urlBean = Mockito.mock(UrlBean.class);

		try {
			ftpFileDownloader.readFile(urlBean);
			Assert.fail("RuntimeException Expected");
		} catch (RuntimeException e) {
			log.error("RuntimeException at FtpFileDownloaderTest.testReadFileForFtpRetrieveException ");
			Mockito.verify(outputFileWriter, Mockito.times(0)).saveFile(Mockito.any(UrlBean.class), Mockito.any(InputStream.class));
		} catch (Exception e) {
			Assert.fail("RuntimeException Expected");
		}
	}

	@Test
	public void testReadFileWhenSavingOutputFile() throws IOException {
		Mockito.doNothing().when(ftpClient).connect(Mockito.anyString(), Mockito.anyInt());
		Mockito.when(ftpClient.login(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		Mockito.when(ftpClient.retrieveFileStream(Mockito.anyString())).thenReturn(getClass().getResourceAsStream("/ftp/testData.jpg"));
		Mockito.doThrow(IOException.class).when(outputFileWriter).saveFile(Mockito.any(UrlBean.class), Mockito.any(InputStream.class));

		UrlBean urlBean = Mockito.mock(UrlBean.class);

		try {
			ftpFileDownloader.readFile(urlBean);
			Assert.fail("RuntimeException Expected");
		} catch (RuntimeException e) {
			log.error("RuntimeException at FtpFileDownloaderTest.testReadFileWhenSavingOutputFile ");
		} catch (Exception e) {
			Assert.fail("RuntimeException Expected");
		}
	}
}
