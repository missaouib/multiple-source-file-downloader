package th.agoda.data.downloader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.output.OutputDirectoryProperties;

@Component
public class SftpFileDownloader implements FileDownloader {
	@Autowired
	private OutputDirectoryProperties outputDirectoryProperties;

	@Override
	public void readFile(UrlBean urlBean) {

	}
}
