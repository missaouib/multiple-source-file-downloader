package th.agoda.data.downloader.logic;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.InputStream;
import java.util.Hashtable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.output.OutputFileWriter;

//TODO: This need to be tested with realtime SFTP server.
@Component
public class SftpFileDownloader implements FileDownloader {

	@Autowired
	private JSch jSch;

	@Autowired
	private OutputFileWriter outputFileWriter;

	@Override
	public void readFile(UrlBean urlBean) {
		try {
			Hashtable<String, String> config = new Hashtable();
			config.put("StrictHostKeyChecking", "no");
			JSch.setConfig(config);

			Session session = jSch.getSession(urlBean.getUsername(), urlBean.getHostname(), urlBean.getPort());
			session.setPassword(urlBean.getPassword());
			session.connect();

			Channel channel = session.openChannel("sftp");
			channel.connect();

			ChannelSftp channelSftp = (ChannelSftp) channel;
			InputStream inputStream = channelSftp.get(urlBean.getRemoteFileName());

			outputFileWriter.saveFile(urlBean, inputStream);

			if (channel.isConnected()) {
				channel.disconnect();
			}
			if (session.isConnected()) {
				session.disconnect();
			}
		} catch (JSchException e) {
			throw new RuntimeException("JSchException caught at SftpFileDownloader. Exception message: "+e.getMessage());
		} catch (SftpException e) {
			throw new RuntimeException("SftpException caught at SftpFileDownloader. Exception message: "+e.getMessage());
		}
	}
}
