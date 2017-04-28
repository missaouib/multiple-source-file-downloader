package th.agoda.data.downloader.output;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutputFilenameCreator {

	@Autowired
	private OutputDirectoryProperties outputDirectoryProperties;

	public String create(String hostName, String fileName) {
		StringBuilder stringBuilder = new StringBuilder(outputDirectoryProperties.getPath());
		stringBuilder.append(hostName);
		stringBuilder.append("_");
		stringBuilder.append(String.valueOf(System.currentTimeMillis()));
		stringBuilder.append("_");
		stringBuilder.append(fileName);
		return stringBuilder.toString();
	}
}
