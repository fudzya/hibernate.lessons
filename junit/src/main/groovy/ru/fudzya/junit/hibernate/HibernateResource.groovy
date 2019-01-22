package ru.fudzya.junit.hibernate

import org.hibernate.HibernateException
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.cfgxml.internal.ConfigLoader
import org.hibernate.boot.registry.StandardServiceRegistry
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.AvailableSettings
import org.hibernate.internal.SessionFactoryRegistry
import org.hibernate.tool.schema.Action
import org.junit.rules.ExternalResource
import ru.fudzya.common.dataaccess.utils.Constants
import ru.fudzya.common.dataaccess.utils.NamesUtils

import javax.naming.InitialContext

import static ru.fudzya.junit.hibernate.HibernateResource.createServiceRegistry

class HibernateResource extends ExternalResource {

	private final static Map DEFAULT_SETTINGS = [:].with { map ->
		map.put(AvailableSettings.SHOW_SQL,                         true)
		map.put(AvailableSettings.FORMAT_SQL,                       true)
		map.put(AvailableSettings.HBM2DDL_AUTO,                     Action.CREATE)
		map.put(AvailableSettings.GENERATE_STATISTICS,              true)
		map.put(AvailableSettings.SESSION_FACTORY_NAME_IS_JNDI,     true)
		map.put(AvailableSettings.TRANSACTION_COORDINATOR_STRATEGY, 'jta')
		map.put(AvailableSettings.USE_NEW_ID_GENERATOR_MAPPINGS,    true)
	    return map
	}

	@Override
	protected void before() throws Throwable {
		hibernateConfigFilesName()?.each { name ->
			new MetadataSources().buildMetadata(createServiceRegistry(name)).buildSessionFactory()
		}
	}

	@Override
	protected void after() {
		SessionFactoryRegistry.INSTANCE.clearRegistrations()
	}

	protected Collection<String> hibernateConfigFilesName() {
		throw new UnsupportedOperationException()
	}

	private static StandardServiceRegistry createServiceRegistry(String configFileName) {

		return new StandardServiceRegistryBuilder().with { builder ->

			def configLoader = new ConfigLoader(builder.getBootstrapServiceRegistry()).loadConfigXmlResource(configFileName)
			def configValues = configLoader.getConfigurationValues()

			def factoryName = configLoader.getSessionFactoryName()
			if (factoryName) {
				configValues.put(AvailableSettings.SESSION_FACTORY_NAME, NamesUtils.toSessionFactoryName(factoryName))
			}
			else {
				def defaultName = NamesUtils.toSessionFactoryName(Constants.DEFAULT_REGISTRY_ENTRY_NAME)

				if (SessionFactoryRegistry.INSTANCE.getNamedSessionFactory(defaultName)) {
					throw new HibernateException("Назначение имени по-умолчанию${defaultName} возможно только для одной SessionFactory")
				}

				configValues.put(AvailableSettings.SESSION_FACTORY_NAME, defaultName)
			}

			def dataSource = configValues.get(AvailableSettings.DATASOURCE)
			if (dataSource) {
				configValues.put(AvailableSettings.DATASOURCE, new InitialContext().lookup((String) dataSource))
			}
			else {
				throw new NullPointerException("Для фабрики ${factoryName} не установлено свойство $AvailableSettings.DATASOURCE")
			}

			builder.applySettings(DEFAULT_SETTINGS)
			builder.configure(configLoader)
			builder.build()
		}
	}
}
