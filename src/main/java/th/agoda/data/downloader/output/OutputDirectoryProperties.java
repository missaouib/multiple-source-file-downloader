package th.agoda.data.downloader.output;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:/outputDirectory.properties")
@ConfigurationProperties(prefix = "output.directory")
public class OutputDirectoryProperties {

	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "OutputDirectoryProperties{" +
			   "path='" + path + '\'' +
			   '}';
	}
}
