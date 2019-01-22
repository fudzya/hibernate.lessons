package ru.fudzya.junit.db

/**
 * @author fudzya
 * @since  13.09.2018
 */
enum Product {
	
	mysql,
	h2

	static Product of(String name) {
		Product product = values().find { value ->
			value.name().equalsIgnoreCase(name)
		}

		if (product) {
			return product
		}

		throw new NullPointerException("Использование ${name} не поддерживается")
	}
}