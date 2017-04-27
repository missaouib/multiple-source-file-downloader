package th.agoda.data.downloader.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.beans.UrlBean;

@Component
public class InputUrlFileReader {

	@Autowired
	private UrlParser urlParser;

	public List<UrlBean> getUrlBeans(String resourceName) {
		List<UrlBean> urlBeans = new ArrayList<>();
		try {
			String urlsFileName = this.getClass().getResource(resourceName).getFile();
			BufferedReader br = new BufferedReader(new FileReader(urlsFileName));
			String str;
			while ((str = br.readLine()) != null) {
				urlBeans.add(urlParser.parse(str));
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Exception while reading URI list from file. Exception : "+e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Exception while fetching the URI list from reader, exception : "+e.getMessage());
		}
		return urlBeans;
	}
}
