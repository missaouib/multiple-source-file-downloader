package th.agoda.data.downloader.runner;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.input.InputUrlFileReader;
import th.agoda.data.downloader.services.FileDownloaderService;

@Component
@Slf4j
public class FileDownloaderRunner implements CommandLineRunner {

	private InputUrlFileReader inputUrlFileReader;

	private FileDownloaderService fileDownloaderService;

	public FileDownloaderRunner(InputUrlFileReader inputUrlFileReader, FileDownloaderService fileDownloaderService) {
		this.inputUrlFileReader = inputUrlFileReader;
		this.fileDownloaderService = fileDownloaderService;
	}

	@Override
	public void run(String... strings) throws Exception {
		List<UrlBean> urlBeans;
		try {
			urlBeans = inputUrlFileReader.getUrlBeans("/inputUrlList.txt");
		} catch (RuntimeException e) {
			log.error("Exception caught while processing the URL from files {}. Cannot perform file download. ", e.getMessage());
			return;
		}

		for (UrlBean urlBean:urlBeans) {
			log.debug("Downloading the file from urlBean {} ", urlBean);
			fileDownloaderService.download(urlBean.getProtocol(), urlBean);
		}
	}
}
