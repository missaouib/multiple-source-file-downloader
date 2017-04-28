package th.agoda.data.downloader.output;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.beans.UrlBean;

@Component
public class OutputFileWriter {

	@Autowired
	private OutputFilenameCreator outputFilenameCreator;

	public void saveFile(UrlBean urlBean, InputStream inputStream) {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
		String fileName = outputFilenameCreator.create(urlBean.getHostname(), FilenameUtils.getName(urlBean.getRemoteFileName()));
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(fileName);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			byte[] buffer = new byte[1024];
			int readCount;
			while ((readCount = bufferedInputStream.read(buffer)) != -1) {
				bufferedOutputStream.write(buffer, 0, readCount);
			}
			bufferedInputStream.close();
			bufferedOutputStream.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Could not create file at disk, Exception : "+e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException("Could not write file to disk, Exception : "+e.getMessage());
		}
	}
}
