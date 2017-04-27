package th.agoda.data.downloader.output;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.common.RandomNumberGenerator;

@Component
public class OutputFilenameCreator {

	@Autowired
	private OutputDirectoryProperties outputDirectoryProperties;

	@Autowired
	private RandomNumberGenerator randomNumberGenerator;

	public String create(String hostName, String fileName) {
		StringBuilder stringBuilder = new StringBuilder(outputDirectoryProperties.getPath());
		stringBuilder.append(hostName);
		stringBuilder.append("_");
		stringBuilder.append(String.valueOf(randomNumberGenerator.getRandomNumber()));
		stringBuilder.append("_");
		stringBuilder.append(fileName);
		return stringBuilder.toString();
	}
}
