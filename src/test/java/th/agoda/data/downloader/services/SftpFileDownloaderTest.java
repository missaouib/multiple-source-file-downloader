package th.agoda.data.downloader.services;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.logic.SftpFileDownloader;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class SftpFileDownloaderTest {

	@Mock
	private JSch jSch;

	@InjectMocks
	private SftpFileDownloader sftpFileDownloader;

	@Test
	public void testReadFileWhenJschSessionfails() throws JSchException {
		Mockito.doThrow(JSchException.class).when(jSch).getSession(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt());
		UrlBean urlBean = Mockito.mock(UrlBean.class);

		try {
			sftpFileDownloader.readFile(urlBean);
			Assert.fail("RuntimeException expected");
		} catch (RuntimeException e) {
			log.error("RuntimeException at SftpFileDownloaderTest.testReadFileWhenJschSessionfails");
		} catch (Exception e) {
			Assert.fail("RuntimeException expected");
		}
	}

}
