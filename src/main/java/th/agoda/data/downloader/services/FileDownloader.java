package th.agoda.data.downloader.services;

import th.agoda.data.downloader.beans.UrlBean;

public interface FileDownloader {

	void readFile(UrlBean urlBean);

}
