package th.agoda.data.downloader.config;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	@Bean
	public FTPClient ftpClient() {
		return new FTPClient();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
