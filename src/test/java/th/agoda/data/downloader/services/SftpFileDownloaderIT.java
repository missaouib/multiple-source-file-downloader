package th.agoda.data.downloader.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.sshd.SshServer;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.CommandFactory;
import org.apache.sshd.server.command.ScpCommandFactory;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.sftp.SftpSubsystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.logic.SftpFileDownloader;

//FIXME: verify this IT?
@RunWith(SpringRunner.class)
@SpringBootTest
public class SftpFileDownloaderIT {

	@Autowired
	private SftpFileDownloader sftpFileDownloader;

	private SshServer sshServer;

	@Before
	public void setUp() throws IOException {
		sshServer = SshServer.setUpDefaultServer();
		sshServer.setPort(22999);
		sshServer.setKeyPairProvider(new SimpleGeneratorHostKeyProvider("hostkey.ser"));
		sshServer.setPasswordAuthenticator((username, password, serverSession) -> true);
		CommandFactory commandFactory = command -> {
			System.out.println("Command : "+command);
			return null;
		};
		sshServer.setCommandFactory(new ScpCommandFactory(commandFactory));
		List<NamedFactory<Command>> namedFactories = new ArrayList<>();
		namedFactories.add(new SftpSubsystem.Factory());
		sshServer.setSubsystemFactories(namedFactories);
		sshServer.start();
	}

	@After
	public void tearDown() throws InterruptedException {
		sshServer.stop();
	}

	@Test
	public void testSftpDownload() {
		UrlBean urlBean = new UrlBean();
		urlBean.setHostname("localhost");
		urlBean.setPort(22999);
		urlBean.setUsername("remote-username");
		urlBean.setUsername("remote-password");
		sftpFileDownloader.readFile(urlBean);
	}

}
