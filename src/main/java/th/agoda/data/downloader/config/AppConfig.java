package th.agoda.data.downloader.config;

import com.jcraft.jsch.JSch;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import th.agoda.data.downloader.properties.RestTemplateConnectionProperties;

@Configuration
public class AppConfig {

	@Autowired
	private RestTemplateConnectionProperties restTemplateConnectionProperties;

	@Bean
	public FTPClient ftpClient() {
		return new FTPClient();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(clientHttpRequestFactory());
	}

	@Bean
	public JSch jSch() {
		return new JSch();
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(restTemplateConnectionProperties.getReadTimeout());
		factory.setConnectTimeout(restTemplateConnectionProperties.getReadTimeout());
		return factory;
	}
}
