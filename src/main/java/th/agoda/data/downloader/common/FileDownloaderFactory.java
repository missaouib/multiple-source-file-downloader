package th.agoda.data.downloader.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.enums.ProtoCol;
import th.agoda.data.downloader.services.FileDownloader;
import th.agoda.data.downloader.services.FtpFileDownloader;
import th.agoda.data.downloader.services.HttpFileDownloader;
import th.agoda.data.downloader.services.SftpFileDownloader;

@Component
public class FileDownloaderFactory {

	@Autowired
	private HttpFileDownloader httpFileDownloader;

	@Autowired
	private FtpFileDownloader ftpFileDownloader;

	@Autowired
	private SftpFileDownloader sftpFileDownloader;

	public FileDownloader get(ProtoCol protoCol) {
		switch (protoCol) {
			case HTTP:
			case HTTPS:
				return httpFileDownloader;
			case FTP:
				return ftpFileDownloader;
			case SFTP:
				return sftpFileDownloader;
			default: //log error
				throw new RuntimeException("Not matching donwloader types found");
		}


	}
}
