package th.agoda.data.downloader.services;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.output.OutputFilenameCreator;

@Component
public class FtpFileDownloader implements FileDownloader {

	@Autowired
	private FTPClient ftpClient;

	@Autowired
	private OutputFilenameCreator outputFilenameCreator;

	@Override
	public void readFile(UrlBean urlBean) {
		String hostName = urlBean.getHostname();
		try {
			ftpClient.connect(hostName, urlBean.getPort());
			String username = urlBean.getUsername();
			String password = urlBean.getPassword();
			boolean isLoggedIn = ftpClient.login(username, password);
			if (!isLoggedIn) {
				throw new RuntimeException("Ftp connection cannot be established for user "+username);
			}

			String fileName = outputFilenameCreator.create(hostName, FilenameUtils.getName(urlBean.getRemoteFileName()));
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			boolean download = ftpClient.retrieveFile(urlBean.getRemoteFileName(), fileOutputStream);
			if (!download) {
				throw new RuntimeException("File could not be downloaded from FTP server");
			}

			fileOutputStream.close();
		} catch (IOException e) {
			//LOG.error
			throw new RuntimeException("Not able to connect to FTP host. Please check the hostname "+hostName + ", exception : "+e.getMessage());
		}
	}
}
