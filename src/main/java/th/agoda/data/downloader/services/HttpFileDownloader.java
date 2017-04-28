package th.agoda.data.downloader.services;

import java.io.ByteArrayInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.output.OutputFileWriter;

//FIXME: Configure logging,
// FIXME: Fix timeout, huge file download etc
@Component
public class HttpFileDownloader implements FileDownloader {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OutputFileWriter outputFileWriter;

	@Override
	public void readFile(UrlBean urlBean) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<byte[]> response = restTemplate.exchange(urlBean.getUri(), HttpMethod.GET, entity, byte[].class);
		if (response.getStatusCode() == HttpStatus.OK) {
			outputFileWriter.saveFile(urlBean, new ByteArrayInputStream(response.getBody()));
		}
	}

}
