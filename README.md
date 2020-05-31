# Web Automation Framework using Selenium
    Team : B Postive
    Contributors: Mohideen M, Bharathi V, Karthik J 

# Introduction:
This Framework is created using Java + Selenium Web Driver + TestNG which can be used across different web based applications. In this approach, the endeavor is to build a lot of applications independent reusable keyword components so that they can directly used for another web application without spending any extra effort. With this framework in place, we need not to start from scratch, but use the application independent keyword components to the extent and create application specific components on specific needs.

## Prerequisites:
- Java - jdk-1.8 or Higher
- Maven
- Selenium 3.x 
- Testng 
- Extent Reports
- Mongo(Optional)

# Execution
- Clone the repository.
- Open command prompt and go to directory
- To run on local use ..... - mvn clean test -PChrome
- To run on grid use  ..... - mvn clean test -PGrid

### Logging:
 By default Extentreport takes care of logging. We have included the webdriver event listener also. it logs all the event/action in the browser on need basis.

# Screenshot:
 - In case of failure, Screenshots are captured and saved inside target/screenshots folder. 
 - We have enabled the feasiblity of capturing the screenshot of any event in the browser. This can be achieved by enabling the captureEvents flag.

# Reporting:
This framework generates html reports which will be stored under ExtentReports folder. We can also enable the live report dashboard with the following configuration:
`Download Klov jar from` [GitHub](https://github.com/extent-framework/klov-server)
` run jar as java -jar klov-version.jar`
` Install and run the mongo server` [Guide](https://docs.mongodb.com/guides/server/install/)
> configure the params (Mongo Host, Port, KLOV Server URL) and reportEnabled as true report.yaml
> Now run the suite and hit the Klov server URL to see the status of current execution. 
Example: http://localhost:8082/build/list (If you are runing in local)