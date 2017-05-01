package th.agoda.data.downloader.runner;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.builders.UrlBeanBuilder;
import th.agoda.data.downloader.enums.ProtoCol;
import th.agoda.data.downloader.input.InputUrlFileReader;
import th.agoda.data.downloader.services.FileDownloaderService;

@RunWith(MockitoJUnitRunner.class)
public class FileDownloaderRunnerTest {

	@Mock
	private InputUrlFileReader inputUrlFileReader;

	@Mock
	private FileDownloaderService fileDownloaderService;

	private FileDownloaderRunner fileDownloaderRunner;

	@Before
	public void setUp() {
		fileDownloaderRunner = new FileDownloaderRunner(inputUrlFileReader, fileDownloaderService);
	}

	@Test
	public void testRunWhenInputUrlFileReaderThrowsRuntimeException() {
		Mockito.when(inputUrlFileReader.getUrlBeans(Mockito.anyString())).thenThrow(new RuntimeException("Exception while parsing the URLs from the input file"));

		String[] commandLineParams = {"cmd1", "cmd2"};
		fileDownloaderRunner.run(commandLineParams);

		Mockito.verify(fileDownloaderService, Mockito.times(0)).download(Mockito.any(ProtoCol.class), Mockito.any(UrlBean.class));
	}

	@Test
	public void testRunWhenInputUrlFileReaderParsesUrls() {
		Mockito.when(inputUrlFileReader.getUrlBeans(Mockito.anyString())).thenReturn(getUrlBeans());

		String[] commandLineParams = {"cmd1", "cmd2"};
		fileDownloaderRunner.run(commandLineParams);

		Mockito.verify(fileDownloaderService, Mockito.times(3)).download(Mockito.any(ProtoCol.class), Mockito.any(UrlBean.class));
	}

	private List<UrlBean> getUrlBeans() {
		List<UrlBean> urlBeans = new ArrayList<>();
		urlBeans.add(new UrlBeanBuilder().uri("http://test.agoda.com/remoteFile.html").hostname("test.agoda.com").port(8080).username("testUser").password("testPwd").protocol(ProtoCol.HTTP).remoteFileName("remoteFile.html").build());
		urlBeans.add(new UrlBeanBuilder().uri("ftp://someUser:somePassword@ftp.agoda.com/remoteFile.html").hostname("ftp.agoda.com").port(21).username("someUser").password("somePassword").protocol(ProtoCol.FTP).remoteFileName("remoteFile.html").build());

		urlBeans.add(new UrlBeanBuilder().uri("sftp://someUser:somePassword@sftp.agoda.com/remoteFile.html").hostname("sftp.agoda.com").port(22).username("someUser").password("somePassword").protocol(ProtoCol.SFTP).remoteFileName("remoteFile.html").build());

		return urlBeans;
	}

}
