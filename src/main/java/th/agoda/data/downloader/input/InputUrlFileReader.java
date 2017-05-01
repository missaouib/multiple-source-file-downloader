package th.agoda.data.downloader.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.properties.InputDirectoryProperties;

@Component
public class InputUrlFileReader {

	@Autowired
	private UrlParser urlParser;

	@Autowired
	private InputDirectoryProperties inputDirectoryProperties;

	public List<UrlBean> getUrlBeans(String resourceName) {
		List<UrlBean> urlBeans = new ArrayList<>();
		try {
			File inputFile = new File(inputDirectoryProperties.getPath()+"inputUrlList.txt");
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
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
