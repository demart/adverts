package kz.aphion.adverts.crawler.kn.jobs;

import kz.aphion.adverts.crawler.core.jobs.CrawlerApplicationStartJob;
import play.jobs.OnApplicationStart;

@OnApplicationStart(async=false)
public class KnCrawlerApplicationStartJob extends CrawlerApplicationStartJob {}
