package th.agoda.data.downloader.output;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.beans.UrlBean;

@Component
@Slf4j
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
			log.info("File saved to location {} from URL {} ", fileName, urlBean.getUri());
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Could not create file at disk, Exception : "+e.getMessage());
		} catch (IOException e) {
			log.error("IOException while saving file {} to disk. Hence deleting the file ", fileName);
			new File(fileName).delete();
			throw new RuntimeException("Could not write file to disk, Exception : "+e.getMessage());
		}
	}
}
