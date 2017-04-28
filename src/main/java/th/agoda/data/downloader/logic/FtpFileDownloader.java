package th.agoda.data.downloader.logic;

import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
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
			if (validateConnection(username, password)) {
				return;
			}
			InputStream inputStream = ftpClient.retrieveFileStream(urlBean.getRemoteFileName());
			outputFileWriter.saveFile(urlBean, inputStream);
			ftpClient.logout();
		} catch (IOException e) {
			log.error("Exception at FtpFileDownloader, exception message is = {} ", e.getMessage());
			throw new RuntimeException("Not able to connect to FTP host. Please check the hostname "+hostName + ", exception : "+e.getMessage());
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					throw new RuntimeException("Exception while closing FTP connection, exception : "+e.getMessage());
				}
			}
		}
	}

	private boolean validateConnection(String username, String password) throws IOException {
		boolean isLoggedIn = ftpClient.login(username, password);
		if (!isLoggedIn) {
			log.error("FTP authentication failure for user {} ", username);
			return true;
		}
		int reply = ftpClient.getReplyCode();
		if(!FTPReply.isPositiveCompletion(reply)) {
			ftpClient.disconnect();
			log.error("FTP server refused connection.");
			throw new RuntimeException("FTP server refused connection. ");
		}
		return false;
	}
}
