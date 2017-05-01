package th.agoda.data.downloader.beans;

import lombok.Data;
import th.agoda.data.downloader.enums.ProtoCol;

@Data
public class UrlBean {

	private String uri;

	private ProtoCol protocol;

	private String hostname;

	private int port;

	private String username;

	private String password;

	private String remoteFileName;

}
