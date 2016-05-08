# Adverts (Crawler project)
To run project required following middleware or tools
1. Gradle
2. Postgres (9.X version)
3. MongoDB (3.3.X version)
4. ActiveMQ (5.X version)
5. Eclipse/Idea (optional for coding)

## Start project preparations

1. Create DB name adverts in Postgres using scripts in db/dump-09-05-16-00-06.backup (or the latest full script)
2. Run Mongo (default port)
3. Run ActiveMQ
4. Execute command ./gradlew clean build
5. Execute command ./gradlew eclise (or idea)

## Play projects preparations

#### Prepare crawler projects
1. Execute command: cd /sources/crawler/adverts-crawler-XXX/
2. Execute command: play eclipsify
3. Execute command: play deps

#### To run play crawlers applications
##### Krisha
Execute gradle command: ./gradlew clean runCrawlerKrisha
##### KN
Execute gradle command: ./gradlew clean runCrawlerKn
##### IRR
Execute gradle command: ./gradlew clean runCrawlerIrr
##### OLX
Execute gradle command: ./gradlew clean runCrawlerOlx

The result of execution of this command will be compile all dependecy projects and run play server
