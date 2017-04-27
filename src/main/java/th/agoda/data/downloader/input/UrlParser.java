package th.agoda.data.downloader.input;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.enums.ProtoCol;

@Component
public class UrlParser {

	private static final int DEFAULT_FTP_PORT_NUMBER = 21;

	private static final int DEFAULT_SFTP_PORT_NUMBER = 22;

	UrlBean parse(String urlString) {
		UrlBean urlBean = new UrlBean();
		if (StringUtils.isBlank(urlString)) {
			throw new RuntimeException("Empty URL. Cannot parse urlString :" + urlString);
		}
		try {
			URI url = new URI(urlString);
			urlBean.setUri(urlString);
			urlBean.setProtocol(ProtoCol.valueOfProtocolName(url.getScheme()));
			urlBean.setHostname(url.getHost());
			if (ProtoCol.FTP.equals(urlBean.getProtocol())) {
				urlBean.setPort(url.getPort() == -1 ? DEFAULT_FTP_PORT_NUMBER : url.getPort());
			}
			if (ProtoCol.SFTP.equals(urlBean.getProtocol())) {
				urlBean.setPort(url.getPort() == -1 ? DEFAULT_SFTP_PORT_NUMBER : url.getPort());
			}
			urlBean.setRemoteFileName(url.getPath());
			if (url.getUserInfo() != null) {
				String userInfo = url.getUserInfo();
				if (userInfo.contains(":")) {
					String[] userInfos = userInfo.split(":");
					urlBean.setUsername(userInfos[0]);
					urlBean.setPassword(userInfos[1]);
				}
			}
		} catch (URISyntaxException e) {
			throw new RuntimeException("Exception while parsing the URL. Exception message : "+e.getMessage());
		}
		return urlBean;
	}
}
