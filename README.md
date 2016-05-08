# Adverts (Crawler project)
# 
To run project required following middleware or tools:

1. Gradle
2. Postgres (9.X version)
3. MongoDB (3.3.X version)
4. ActiveMQ (5.X version)
5. Eclipse/Idea (optional for coding)

## Start project preparations

1. Create DB name adverts in Postgres using scripts in db/full-dump-09-05-16-02-27.sql (or the latest full script)
2. Run Mongo (default port)
3. Run ActiveMQ
4. Set path to jvm /sources/gradle.properties (example org.gradle.java.home=/Library/Java/JavaVirtualMachines/jdk1.7.0_79.jdk/Contents/Home/)
4. Execute command ./gradlew clean build
5. Execute command ./gradlew eclise (or idea)

## Play projects preparations

#### Prepare crawler projects
1. Execute command: cd /sources/crawler/adverts-crawler-XXX/
2. Execute command: play eclipsify
3. Execute command: play deps

#### To run play crawlers applications
##### Krisha
Change directory to source folder
Execute gradle command (Linux): ./gradlew clean runCrawlerKrisha
Execute gradle command (Windiws): gradlew(.bat) clean runCrawlerKrisha
##### KN
Change directory to source folder
Execute gradle command: ./gradlew clean runCrawlerKn
Execute gradle command (Windiws): gradlew(.bat) clean runCrawlerKn
##### IRR
Change directory to source folder
Execute gradle command: ./gradlew clean runCrawlerIrr
Execute gradle command (Windiws): gradlew(.bat) clean runCrawlerIrr
##### OLX
Change directory to source folder
Execute gradle command: ./gradlew clean runCrawlerOlx
Execute gradle command (Windiws): gradlew(.bat) clean runCrawlerOlx

The result of execution of this command will be compile all dependecy projects and run play server
