package th.agoda.data.downloader.beans;

import th.agoda.data.downloader.enums.ProtoCol;

public class UrlBean {

	private String uri;

	private ProtoCol protocol;

	private String hostname;

	private int port;

	private String username;

	private String password;

	private String remoteFileName;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public ProtoCol getProtocol() {
		return protocol;
	}

	public void setProtocol(ProtoCol protocol) {
		this.protocol = protocol;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getRemoteFileName() {
		return remoteFileName;
	}

	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UrlBean{" +
			   "uri='" + uri + '\'' +
			   ", protocol=" + protocol +
			   ", hostname='" + hostname + '\'' +
			   ", port='" + port + '\'' +
			   ", username='" + username + '\'' +
			   ", password='" + password + '\'' +
			   ", remoteFileName='" + remoteFileName + '\'' +
			   '}';
	}
}
