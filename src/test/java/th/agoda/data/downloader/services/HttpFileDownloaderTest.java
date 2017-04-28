package th.agoda.data.downloader.services;

import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.logic.HttpFileDownloader;
import th.agoda.data.downloader.output.OutputFileWriter;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class HttpFileDownloaderTest {

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private OutputFileWriter outputFileWriter;

	@InjectMocks
	private HttpFileDownloader httpFileDownloader;

	@Test
	public void testReadFileWhenRestTemplateThrowsException() {
		Mockito.doThrow(RestClientException.class).when(restTemplate).exchange(Mockito.anyString(), Mockito.anyObject(), Mockito.anyObject(), Mockito.eq(byte[].class));

		UrlBean urlBean = Mockito.mock(UrlBean.class);

		try {
			httpFileDownloader.readFile(urlBean);
		} catch (RuntimeException e) {
			log.error(" RuntimeException at HttpFileDownloaderTest.testReadFileWhenRestTemplateThrowsException");
		} catch (Exception e) {
			Assert.fail("Expected RuntimeException");
		}
	}

	@Test
	public void testReadFileWhenRestTemplateReturnsNullException() {
		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.anyObject(), Mockito.anyObject(), Mockito.eq(byte[].class))).thenReturn(null);

		UrlBean urlBean = Mockito.mock(UrlBean.class);

		httpFileDownloader.readFile(urlBean);

		Mockito.verify(outputFileWriter, Mockito.times(0)).saveFile(Mockito.any(UrlBean.class), Mockito.any(InputStream.class));
	}

	@Test
	public void testReadFileWhenRestTemplateReturnsNotOkStatusCode() {
		ResponseEntity<byte[]> responseEntity = Mockito.mock(ResponseEntity.class);
		Mockito.when(responseEntity.getStatusCode()).thenReturn(HttpStatus.BAD_GATEWAY);
		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.anyObject(), Mockito.anyObject(), Mockito.eq(byte[].class))).thenReturn(responseEntity);

		UrlBean urlBean = Mockito.mock(UrlBean.class);

		httpFileDownloader.readFile(urlBean);

		Mockito.verify(outputFileWriter, Mockito.times(0)).saveFile(Mockito.any(UrlBean.class), Mockito.any(InputStream.class));
	}

	@Test
	public void testReadFileWhenRestTemplateReturnsOkStatusCode() {
		ResponseEntity<byte[]> responseEntity = Mockito.mock(ResponseEntity.class);
		Mockito.when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
		Mockito.when(responseEntity.getBody()).thenReturn("testingHttpDownload".getBytes());
		Mockito.when(restTemplate.exchange(Mockito.anyString(), Mockito.anyObject(), Mockito.anyObject(), Mockito.eq(byte[].class))).thenReturn(responseEntity);

		UrlBean urlBean = Mockito.mock(UrlBean.class);

		httpFileDownloader.readFile(urlBean);

		Mockito.verify(outputFileWriter, Mockito.times(1)).saveFile(Mockito.any(UrlBean.class), Mockito.any(InputStream.class));
	}
}
