package ru.fudzya.common.dataaccess.utils

final class NamesUtils {

	public static final String SESSION_FACTORY_NAME_PREFIX = 'hibernate/session-factory'
	public static final String PERSISTENCE_UNIT_PREFIX     = 'jpa/persistence-unit'
	public static final String DATASOURCE_NAME_PREFIX      = 'dataSource'

	private NamesUtils() {
	}

	static String toSessionFactoryName(String name = SESSION_FACTORY_NAME_PREFIX) {

		if (!name) {
			throw new IllegalArgumentException('Argument must be set')
		}

		return isSessionFactoryName(name) ? name : "$SESSION_FACTORY_NAME_PREFIX/$name"
	}

	static String toJndiDataSourceName(String name) {
		
		if (!name) {
			throw new IllegalArgumentException('Argument must be set')
		}

		if (name == DATASOURCE_NAME_PREFIX) {
			throw new IllegalArgumentException("$name является некорректным для использования в качестве jndi имени dataSource")
		}

		return isDataSourceName(name) ? name : "$DATASOURCE_NAME_PREFIX/$name"
	}

	static String toPersistenceUnitName(String name) {
		
		if (!name) {
			throw new IllegalArgumentException('Argument must be set')
		}

		return isPersistenceUnitName(name) ? name : "$PERSISTENCE_UNIT_PREFIX/$name"
	}

	static boolean isDataSourceName(String name) {
		name.startsWith(DATASOURCE_NAME_PREFIX)
	}

	static boolean isSessionFactoryName(String name) {
		name.startsWith(SESSION_FACTORY_NAME_PREFIX)
	}

	static boolean isPersistenceUnitName(String name) {
		name.startsWith(PERSISTENCE_UNIT_PREFIX)
	}
}