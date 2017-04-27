package th.agoda.data.downloader.services;

import th.agoda.data.downloader.beans.UrlBean;

interface FileDownloader {

	void readFile(UrlBean urlBean);

}
