package th.agoda.data.downloader.services;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.output.OutputFilenameCreator;

//FIXME: Configure logging,
// FIXME: Fix timeout, huge file download etc
@Component
public class HttpFileDownloader implements FileDownloader {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private OutputFilenameCreator outputFilenameCreator;

	@Override
	public void readFile(UrlBean urlBean) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(headers);

		ResponseEntity<byte[]> response = restTemplate.exchange(urlBean.getUri(), HttpMethod.GET, entity, byte[].class);
		if (response.getStatusCode() == HttpStatus.OK) {
			String fileName = outputFilenameCreator.create(urlBean.getHostname(), getFileName(urlBean.getUri()));
			FileOutputStream fileOutputStream = null;
			InputStream inputStream = null;
			try {
				fileOutputStream = new FileOutputStream(fileName);
				inputStream = new ByteArrayInputStream(response.getBody());
				byte [] data = new byte[1024];
				int count;
				while ((count=inputStream.read(data, 0, 1024)) != -1) {
					fileOutputStream.write(data, 0, count);
				}
			} catch (IOException e) {
				throw new RuntimeException("Exception caught while saving file to disk.");
			} finally {
				try {
					if (inputStream != null) {
						inputStream.close();
					}
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
				} catch (IOException e) {
				e.printStackTrace();
				}
			}
		}
	}

	private String getFileName(String remoteFullFilePath) {
		int beginIndex = remoteFullFilePath.lastIndexOf("/") + 1;
		int length = remoteFullFilePath.length();
		return remoteFullFilePath.substring(beginIndex, length);
	}

}
