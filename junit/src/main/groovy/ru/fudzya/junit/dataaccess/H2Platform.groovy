package ru.fudzya.junit.dataaccess

import org.h2.jdbcx.JdbcDataSource
import ru.fudzya.junit.db.DatabasePlatform
import ru.fudzya.junit.db.Product

final class H2Platform implements DatabasePlatform {

	@Override
	Product product() {
		return Product.h2
	}

	@Override
	String driverName() {
		return JdbcDataSource.class.getName()
	}

	@Override
	Properties driverProperties() {
		return ['URL' : 'jdbc:h2:mem:default'] as Properties
	}
}
