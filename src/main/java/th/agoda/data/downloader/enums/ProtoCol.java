package th.agoda.data.downloader.enums;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.util.StringUtils;

public enum ProtoCol {

	HTTP("http"), HTTPS("https"), FTP("ftp"), SFTP("sftp");

	private String protocolName;

	ProtoCol(String protocolName) {
		this.protocolName = protocolName;
	}

	public String getProtocolName() {
		return protocolName;
	}

	public static ProtoCol valueOfProtocolName(String protocolName) {
		if (StringUtils.isEmpty(protocolName)) {
			throw new IllegalArgumentException("Empty or null protocol name found ");
		}
		Optional<ProtoCol> optionalProtocol = Arrays.stream(values()).filter(protoCol -> protoCol.getProtocolName().equals(protocolName)).findFirst();
		if (!optionalProtocol.isPresent()) {
			throw new IllegalArgumentException("Invalid protocol name: " + protocolName + " found ");
		}
		return optionalProtocol.get();
	}

}
