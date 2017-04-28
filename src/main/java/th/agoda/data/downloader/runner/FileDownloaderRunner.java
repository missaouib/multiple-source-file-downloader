package th.agoda.data.downloader.runner;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.common.FileDownloaderFactory;
import th.agoda.data.downloader.input.InputUrlFileReader;

@Component
@Slf4j
public class FileDownloaderRunner implements CommandLineRunner {

	private InputUrlFileReader inputUrlFileReader;

	private FileDownloaderFactory fileDownloaderFactory;

	public FileDownloaderRunner(InputUrlFileReader inputUrlFileReader, FileDownloaderFactory fileDownloaderFactory) {
		this.inputUrlFileReader = inputUrlFileReader;
		this.fileDownloaderFactory = fileDownloaderFactory;
	}

	@Override
	public void run(String... strings) throws Exception {
		List<UrlBean> urlBeans = inputUrlFileReader.getUrlBeans("/inputUrlList.txt");

		for (UrlBean urlBean:urlBeans) {
			log.debug("Downloading the file from urlBean {} ", urlBean);
			fileDownloaderFactory.get(urlBean.getProtocol()).readFile(urlBean);
		}
	}
}
