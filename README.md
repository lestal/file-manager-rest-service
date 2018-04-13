# file-manager-service

Minimal [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.smartvid.directory.DirectoryApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
##Usage:
###Assuming that application has access only to 'target' folder

1. GET /api/dir/{dirName} returns a list of all of the subdirectories with paths and files counts;
Examples: 
	- GET /api/dir/one ->
	```json
	[
		{"path":"D:\\Development\\directory\\target\\one\\sub_one","children":[],"filesCount":3,"type":"DIR"},
		{"path":"D:\\Development\\directory\\target\\one\\sub_three","children":[],"filesCount":0,"type":"DIR"},
		{"path":"D:\\Development\\directory\\target\\one\\sub_two","children":[],"filesCount":0,"type":"DIR"}
	]
	```
2. GET /api/files/{directory} returns a list of files in that directory;
Examples: 
	- GET /api/files/one/sub_one->
	```json
	[
		{"path":"D:\\Development\\directory\\target\\one\\sub_one\\file.json","type":"FILE"},
		{"path":"D:\\Development\\directory\\target\\one\\sub_one\\file.mp3","type":"FILE"},
		{"path":"D:\\Development\\directory\\target\\one\\sub_one\\file.txt","type":"FILE"}
	]
    ```
Notes: 	If directory does not exist then returns status 404
```xml
<errors xmlns:atom="http://www.w3.org/2005/Atom">
<error logref="error">
<message>Could not find directory: 'dirName'</message>
</error>
</errors>
```
3. GET /api/attributes/{directory} returns all of the standard file attributes for that file;
Examples: 
	- GET /api/attributes/one/sub_one/file.mp3->
	```json
	{"size":239598440,"hidden":false,"createdDate":"2018-04-12T16:35:52.493773Z","modifiedDate":"2018-04-12T16:35:52.493773Z","rights":"---x-r-w"}
    ```
Notes: 	If directory does not exist then returns status 404
```xml
<errors xmlns:atom="http://www.w3.org/2005/Atom">
<error logref="error">
<message>Could not find directory: 'dirName'</message>
</error>
</errors>
```
###Running 'mvn prepare-package' will generate all the API docs located in 'target/generated-snippets/api-guide.html'