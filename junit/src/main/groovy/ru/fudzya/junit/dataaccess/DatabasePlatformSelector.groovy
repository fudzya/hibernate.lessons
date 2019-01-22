package ru.fudzya.junit.dataaccess

import ru.fudzya.junit.db.DatabasePlatform
import ru.fudzya.junit.db.Product

class DatabasePlatformSelector implements DatabasePlatform {

	@Delegate
	private static DatabasePlatform currentPlatform

	static {

		def selected = System.getProperty('application.db.type')

		currentPlatform = ({ Product product ->

			if (product == Product.h2) {
				return new H2Platform()
			}

			if (product == Product.mysql) {
				return new MySqlPlatform()
			}

		})(selected ? Product.of(selected) : Product.h2)
	}
}