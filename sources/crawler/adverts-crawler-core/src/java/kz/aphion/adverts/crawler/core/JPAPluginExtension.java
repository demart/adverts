package kz.aphion.adverts.crawler.core;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.PersistenceException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.type.Type;
import org.reflections.Reflections;

import play.Logger;
import play.Play;
import play.classloading.ApplicationClasses.ApplicationClass;
import play.db.DB;
import play.db.jpa.JPA;
import play.db.jpa.JPABase;
import play.db.jpa.JPAPlugin;
import play.db.jpa.JPQL;
import play.exceptions.JPAException;
import play.utils.Utils;

/**
 * JPA Plugin Extension.
 * Main problem in standart plugin is: Entity can be only in play project not in extarnal jar
 * 
 */
public class JPAPluginExtension extends JPAPlugin {

    public static boolean autoTxs = true;
    
    @Override
    public void onApplicationStart() {
    	if (JPA.entityManagerFactory != null) {
    		JPA.entityManagerFactory.close();
    		JPA.entityManagerFactory = null;
    	}
    	
    	
        //if (JPA.entityManagerFactory == null) {
    	List<Class> myTypes = new ArrayList<>();

            List<Class> classes = Play.classloader.getAnnotatedClasses(Entity.class);
            
            Reflections reflections = new Reflections("kz.aphion");
        	Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Entity.class);
            if (annotated != null) {
            	classes.addAll(annotated);
            	Logger.info("Found Entity classes in kz.aphion: " + annotated.size());
            }
            
            if (classes.isEmpty() && Play.configuration.getProperty("jpa.entities", "").equals("")) {
                return;
            }

            final String dataSource = Play.configuration.getProperty("hibernate.connection.datasource");
            if (StringUtils.isEmpty(dataSource) && DB.datasource == null) {
                throw new JPAException("Cannot start a JPA manager without a properly configured database", new NullPointerException("No datasource configured"));
            }

            Ejb3Configuration cfg = new Ejb3Configuration();

            if (DB.datasource != null) {
                cfg.setDataSource(DB.datasource);
            }

            if (!Play.configuration.getProperty("jpa.ddl", Play.mode.isDev() ? "update" : "none").equals("none")) {
                cfg.setProperty("hibernate.hbm2ddl.auto", Play.configuration.getProperty("jpa.ddl", "update"));
            }

            cfg.setProperty("hibernate.dialect", getDefaultDialect(Play.configuration.getProperty("db.driver")));
            cfg.setProperty("javax.persistence.transaction", "RESOURCE_LOCAL");

            // Explicit SAVE for JPABase is implemented here
            // ~~~~~~
            // We've hacked the org.hibernate.event.def.AbstractFlushingEventListener line 271, to flush collection update,remove,recreation
            // only if the owner will be saved or if the targeted entity will be saved (avoid the org.hibernate.HibernateException: Found two representations of same collection)
            // As is:
            // if (session.getInterceptor().onCollectionUpdate(coll, ce.getLoadedKey())) {
            //      actionQueue.addAction(...);
            // }
            //
            // This is really hacky. We should move to something better than Hibernate like EBEAN
            cfg.setInterceptor(new EmptyInterceptor() {

                @Override
                public int[] findDirty(Object o, Serializable id, Object[] arg2, Object[] arg3, String[] arg4, Type[] arg5) {
                    if (o instanceof JPABase && !((JPABase) o).willBeSaved) {
                        return new int[0];
                    }
                    return null;
                }

                @Override
                public boolean onCollectionUpdate(Object collection, Serializable key) throws CallbackException {
                    if (collection instanceof PersistentCollection) {
                        Object o = ((PersistentCollection) collection).getOwner();
                       	if (o instanceof JPABase) {
							if (entities.get() != null) {
	                           	return ((JPABase) o).willBeSaved || ((JPABase) entities.get()).willBeSaved;
							} else {
								return ((JPABase) o).willBeSaved;
							}
	                    }
                    } else {
                        System.out.println("HOO: Case not handled !!!");
                    }
                    return super.onCollectionUpdate(collection, key);
                }

                @Override
                public boolean onCollectionRecreate(Object collection, Serializable key) throws CallbackException {
                    if (collection instanceof PersistentCollection) {
                        Object o = ((PersistentCollection) collection).getOwner();
  		           	 	if (o instanceof JPABase) {
							if (entities.get() != null) {
	                           	return ((JPABase) o).willBeSaved || ((JPABase) entities.get()).willBeSaved;
							} else {
								return ((JPABase) o).willBeSaved;
							}
	                     } 
	 				} else {
			           	System.out.println("HOO: Case not handled !!!");
			        }
                    
                    return super.onCollectionRecreate(collection, key);
                }

                @Override
                public boolean onCollectionRemove(Object collection, Serializable key) throws CallbackException {
				 	if (collection instanceof PersistentCollection) {
                        Object o = ((PersistentCollection) collection).getOwner();
			            if (o instanceof JPABase) {
							if (entities.get() != null) {
                            	return ((JPABase) o).willBeSaved || ((JPABase) entities.get()).willBeSaved;
							} else {
								return ((JPABase) o).willBeSaved;
							}
                        }
                    } else {
                        System.out.println("HOO: Case not handled !!!");
                    }
                    return super.onCollectionRemove(collection, key);
                }

				protected ThreadLocal<Object> entities = new ThreadLocal<Object>();
				
				@Override
			 	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)  {
					entities.set(entity);
					return super.onSave(entity, id, state, propertyNames, types);
				}
						
				@Override
				public void afterTransactionCompletion(org.hibernate.Transaction tx) {
					entities.remove();
				}
				
			});
            if (Play.configuration.getProperty("jpa.debugSQL", "false").equals("true")) {
                org.apache.log4j.Logger.getLogger("org.hibernate.SQL").setLevel(Level.ALL);
            } else {
                org.apache.log4j.Logger.getLogger("org.hibernate.SQL").setLevel(Level.OFF);
            }
            // inject additional  hibernate.* settings declared in Play! configuration
            cfg.addProperties((Properties) Utils.Maps.filterMap(Play.configuration, "^hibernate\\..*"));

            try {
                Field field = cfg.getClass().getDeclaredField("overridenClassLoader");
                field.setAccessible(true);
                field.set(cfg, Play.classloader);
            } catch (Exception e) {
                Logger.error(e, "Error trying to override the hibernate classLoader (new hibernate version ???)");
            }
            for (Class<?> clazz : classes) {
                if (clazz.isAnnotationPresent(Entity.class)) {
                    cfg.addAnnotatedClass(clazz);
                    if (Logger.isTraceEnabled()) {
                        Logger.trace("JPA Model : %s", clazz);
                    }
                }
            }
            String[] moreEntities = Play.configuration.getProperty("jpa.entities", "").split(", ");
            for (String entity : moreEntities) {
                if (entity.trim().equals("")) {
                    continue;
                }
                try {
                    cfg.addAnnotatedClass(Play.classloader.loadClass(entity));
                } catch (Exception e) {
                    Logger.warn("JPA -> Entity not found: %s", entity);
                }
            }
            for (ApplicationClass applicationClass : Play.classes.all()) {
                if (applicationClass.isClass() || applicationClass.javaPackage == null) {
                    continue;
                }
                Package p = applicationClass.javaPackage;
                Logger.info("JPA -> Adding package: %s", p.getName());
                cfg.addPackage(p.getName());
            }
            String mappingFile = Play.configuration.getProperty("jpa.mapping-file", "");
            if (mappingFile != null && mappingFile.length() > 0) {
                cfg.addResource(mappingFile);
            }
            if (Logger.isTraceEnabled()) {
                Logger.trace("Initializing JPA ...");
            }
            try {
                JPA.entityManagerFactory = cfg.buildEntityManagerFactory();
            } catch (PersistenceException e) {
                throw new JPAException(e.getMessage(), e.getCause() != null ? e.getCause() : e);
            }
            JPQL.instance = new JPQL();
        //}
    }
    
    static String getDefaultDialect(String driver) {
        String dialect = Play.configuration.getProperty("jpa.dialect");
        if (dialect != null) {
            return dialect;
        } else if ("org.h2.Driver".equals(driver)) {
            return "org.hibernate.dialect.H2Dialect";
        } else if ("org.hsqldb.jdbcDriver".equals(driver)) {
            return "org.hibernate.dialect.HSQLDialect";
        } else if ("com.mysql.jdbc.Driver".equals(driver)) {
            return "play.db.jpa.MySQLDialect";
        } else if ("org.postgresql.Driver".equals(driver)) {
            return "org.hibernate.dialect.PostgreSQLDialect";
        } else if ("com.ibm.db2.jdbc.app.DB2Driver".equals(driver)) {
            return "org.hibernate.dialect.DB2Dialect";
        } else if ("com.ibm.as400.access.AS400JDBCDriver".equals(driver)) {
            return "org.hibernate.dialect.DB2400Dialect";
        } else if ("com.ibm.as400.access.AS390JDBCDriver".equals(driver)) {
            return "org.hibernate.dialect.DB2390Dialect";
        } else if ("oracle.jdbc.OracleDriver".equals(driver)) {
            return "org.hibernate.dialect.Oracle10gDialect";
        } else if ("com.sybase.jdbc2.jdbc.SybDriver".equals(driver)) {
            return "org.hibernate.dialect.SybaseAnywhereDialect";
        } else if ("com.microsoft.jdbc.sqlserver.SQLServerDriver".equals(driver)) {
            return "org.hibernate.dialect.SQLServerDialect";
        } else if ("com.sap.dbtech.jdbc.DriverSapDB".equals(driver)) {
            return "org.hibernate.dialect.SAPDBDialect";
        } else if ("com.informix.jdbc.IfxDriver".equals(driver)) {
            return "org.hibernate.dialect.InformixDialect";
        } else if ("com.ingres.jdbc.IngresDriver".equals(driver)) {
            return "org.hibernate.dialect.IngresDialect";
        } else if ("progress.sql.jdbc.JdbcProgressDriver".equals(driver)) {
            return "org.hibernate.dialect.ProgressDialect";
        } else if ("com.mckoi.JDBCDriver".equals(driver)) {
            return "org.hibernate.dialect.MckoiDialect";
        } else if ("InterBase.interclient.Driver".equals(driver)) {
            return "org.hibernate.dialect.InterbaseDialect";
        } else if ("com.pointbase.jdbc.jdbcUniversalDriver".equals(driver)) {
            return "org.hibernate.dialect.PointbaseDialect";
        } else if ("com.frontbase.jdbc.FBJDriver".equals(driver)) {
            return "org.hibernate.dialect.FrontbaseDialect";
        } else if ("org.firebirdsql.jdbc.FBDriver".equals(driver)) {
            return "org.hibernate.dialect.FirebirdDialect";
        } else {
            throw new UnsupportedOperationException("I do not know which hibernate dialect to use with "
                    + driver + " and I cannot guess it, use the property jpa.dialect in config file");
        }
    }
}
