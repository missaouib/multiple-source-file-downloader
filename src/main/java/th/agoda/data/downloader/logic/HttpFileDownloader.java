package th.agoda.data.downloader.logic;

import java.io.ByteArrayInputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.output.OutputFileWriter;

@Component
@Slf4j
public class HttpFileDownloader implements FileDownloader {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OutputFileWriter outputFileWriter;

	@Override
	public void readFile(UrlBean urlBean) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<byte[]> response;
		try {
			response = restTemplate.exchange(urlBean.getUri(), HttpMethod.GET, entity, byte[].class);
		} catch (RestClientException e) {
			log.error("RestClientException thrown while fetching file from server. Exception message = {} ", e.getMessage());
			throw new RuntimeException("RestClientException thrown while fetching file from server.");
		}
		if (response == null || response.getStatusCode() == null) {
			log.error("Response from HTTP URL = {}", response);
			return;
		}
		if (response.getStatusCode() != HttpStatus.OK) {
			log.error("Invalid response status code from HTTP URL {} is {} ", urlBean.getUri(), response.getStatusCode());
			return;
		}
		outputFileWriter.saveFile(urlBean, new ByteArrayInputStream(response.getBody()));
	}

}
