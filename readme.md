File Downloader from Multiple Sources
============

This product has the function of downloading the files from multiple hosts, like HTTP, FTP or SFTP. Even this product can be enhanced in future to handle other type of protocols too.


Development
------

### Requirements

* JDK 8
* [Lombok](https://projectlombok.org/)
* Maven 3.3.3+ (or use the included Maven wrapper via `mvnw`)


Release Notes
------

### 1.0.0
  Current version has below features.
  * Download files from HTTP, FTP and SFTP
  * Due to un-availability of valid SFTP host, SFTP downloader might need testing before releasing to host.
  
### Release procedure
* Create input directory and edit the <b>src/main/resources/inputDirectory.properties</b> to point to the input directory name
* Create output directory path and edit the <b>src/main/resources/outputDirectory.properties</b> to point to the output files location
* Run below maven goals using below command.
<i>mvn clean compile package install</i>
* We can run the downloader application using below command: <i> java -jar downloader-0.0.1-SNAPSHOT.jar </i>
  