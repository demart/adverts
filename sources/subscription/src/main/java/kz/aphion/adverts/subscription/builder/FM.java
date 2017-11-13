package kz.aphion.adverts.subscription.builder;

import java.io.File;
import java.io.IOException;

import freemarker.template.*;
/**
 * Singleton конфигурации фримаркера, нужно инициализировать один раз, чтобы кэшировать темплейты
 * @author artem.demidovich
 *
 * Created at Oct 25, 2017
 */
public class FM {

	private static Configuration _cfg;
	
	static {
		// Create your Configuration instance, and specify if up to what FreeMarker
		// version (here 2.3.25) do you want to apply the fixes that are not 100%
		// backward-compatible. See the Configuration JavaDoc for details.
		_cfg = new Configuration(Configuration.VERSION_2_3_26);

		// Specify the source where the template files come from. Here I set a
		// plain directory for it, but non-file-system sources are possible too:
		try {
			_cfg.setDirectoryForTemplateLoading(new File(FM.class.getResource("/templates").getPath()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Set the preferred charset template files are stored in. UTF-8 is
		// a good choice in most applications:
		_cfg.setDefaultEncoding("UTF-8");

		// Sets how errors will appear.
		// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
		_cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
		_cfg.setLogTemplateExceptions(false);
	}
	
	/**
	 * Конфигурация статичная, создается один раз, путь к шаблонам в папке ресурсов /templates
	 * @return
	 */
	public static Configuration getCfg() {
		return _cfg;
	}
	
}
