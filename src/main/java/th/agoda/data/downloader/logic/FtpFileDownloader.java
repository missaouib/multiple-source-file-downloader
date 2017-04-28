package th.agoda.data.downloader.logic;

import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.output.OutputFileWriter;

@Component
@Slf4j
public class FtpFileDownloader implements FileDownloader {

	@Autowired
	private FTPClient ftpClient;

	@Autowired
	private OutputFileWriter outputFileWriter;

	@Override
	public void readFile(UrlBean urlBean) {
		String hostName = urlBean.getHostname();
		try {
			ftpClient.connect(hostName, urlBean.getPort());
			String username = urlBean.getUsername();
			String password = urlBean.getPassword();
			boolean isLoggedIn = ftpClient.login(username, password);
			if (!isLoggedIn) {
				log.error("FTP authentication failure for user {} ", username);
				return;
			}
			InputStream inputStream = ftpClient.retrieveFileStream(urlBean.getRemoteFileName());
			outputFileWriter.saveFile(urlBean, inputStream);
		} catch (IOException e) {
			//LOG.error
			throw new RuntimeException("Not able to connect to FTP host. Please check the hostname "+hostName + ", exception : "+e.getMessage());
		}
	}
}
