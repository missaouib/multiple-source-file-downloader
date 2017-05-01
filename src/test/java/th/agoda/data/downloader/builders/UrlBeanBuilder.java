package th.agoda.data.downloader.builders;

import th.agoda.data.downloader.beans.UrlBean;
import th.agoda.data.downloader.enums.ProtoCol;

public class UrlBeanBuilder {

	private UrlBean urlBean;

	public UrlBeanBuilder() {
		urlBean = new UrlBean();
	}

	public UrlBeanBuilder uri(String uri) {
		this.urlBean.setUri(uri);
		return this;
	}

	public UrlBeanBuilder protocol(ProtoCol protocol) {
		this.urlBean.setProtocol(protocol);
		return this;
	}

	public UrlBeanBuilder hostname(String hostname) {
		this.urlBean.setHostname(hostname);
		return this;
	}

	public UrlBeanBuilder port(int port) {
		this.urlBean.setPort(port);
		return this;
	}

	public UrlBeanBuilder username(String username) {
		this.urlBean.setUsername(username);
		return this;
	}

	public UrlBeanBuilder password(String password) {
		this.urlBean.setPassword(password);
		return this;
	}

	public UrlBeanBuilder remoteFileName(String remoteFileName) {
		this.urlBean.setRemoteFileName(remoteFileName);
		return this;
	}

	public UrlBean build() {
		return this.urlBean;
	}
}
