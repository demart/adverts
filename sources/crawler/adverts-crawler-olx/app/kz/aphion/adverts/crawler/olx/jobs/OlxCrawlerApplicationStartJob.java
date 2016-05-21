package kz.aphion.adverts.crawler.olx.jobs;

import play.jobs.OnApplicationStart;
import kz.aphion.adverts.crawler.core.jobs.CrawlerApplicationStartJob;

/**
 * Класс инициализатор работы Crawler'a
 * 
 * @author artem.demidovich
 *
 */
@OnApplicationStart(async=false)
public class OlxCrawlerApplicationStartJob extends CrawlerApplicationStartJob  {

}
