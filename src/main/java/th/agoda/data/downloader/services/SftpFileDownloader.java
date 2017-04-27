package th.agoda.data.downloader.services;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.output.OutputFilenameCreator;

@Component
public class SftpFileDownloader implements FileDownloader {

	@Autowired
	private JSch jSch;

	@Autowired
	private OutputFilenameCreator outputFilenameCreator;

	@Override
	public void readFile(UrlBean urlBean) {
		try {
			Hashtable<String, String> config = new Hashtable();
			config.put("StrictHostKeyChecking", "no");
			JSch.setConfig(config);

			Session session = jSch.getSession(urlBean.getUsername(), urlBean.getHostname(), urlBean.getPort());
			session.setPassword(urlBean.getPassword());
			session.setConfig(config);
			session.connect();

			Channel channel = session.openChannel("sftp");
			channel.connect();

			ChannelSftp channelSftp = (ChannelSftp) channel;
			InputStream inputStream = channelSftp.get(urlBean.getRemoteFileName());

			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			String fileName = outputFilenameCreator.create(urlBean.getHostname(), FilenameUtils.getName(urlBean.getRemoteFileName()));
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			byte[] buffer = new byte[1024];
			int readCount;
			while ( (readCount = bufferedInputStream.read(buffer)) > 0 ) {
				System.out.println("Writing file contents ===> ");
				bufferedOutputStream.write(buffer, 0, readCount);
			}
			bufferedInputStream.close();
			fileOutputStream.close();
			bufferedOutputStream.close();
		} catch (JSchException | SftpException | IOException e) {
			e.printStackTrace();
		}
	}
}
