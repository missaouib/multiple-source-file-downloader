package th.agoda.data.downloader.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.enums.ProtoCol;
import th.agoda.data.downloader.logic.FileDownloader;
import th.agoda.data.downloader.logic.FtpFileDownloader;
import th.agoda.data.downloader.logic.HttpFileDownloader;
import th.agoda.data.downloader.logic.SftpFileDownloader;

@Component
@Slf4j
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
				log.error("Not matching protocol found. Protocol is {} ", protoCol);
				throw new RuntimeException("Not matching downloader types found");
		}


	}
}
