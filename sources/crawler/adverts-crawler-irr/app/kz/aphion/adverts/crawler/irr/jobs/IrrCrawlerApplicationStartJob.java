package kz.aphion.adverts.crawler.irr.jobs;

import kz.aphion.adverts.crawler.core.jobs.CrawlerApplicationStartJob;
import play.jobs.OnApplicationStart;


/**
 * Класс инициализатор работы Crawler'a
 * 
 * @author artem.demidovich
 *
 */
@OnApplicationStart(async=false)
public class IrrCrawlerApplicationStartJob extends CrawlerApplicationStartJob {}
