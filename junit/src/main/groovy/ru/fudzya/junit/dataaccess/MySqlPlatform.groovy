package ru.fudzya.junit.dataaccess

import com.mysql.cj.jdbc.MysqlXADataSource
import ru.fudzya.junit.db.DatabasePlatform
import ru.fudzya.junit.db.Product

final class MySqlPlatform implements DatabasePlatform {

	@Override
	Product product()
	{
		return Product.mysql
	}

	@Override
	String driverName() {
		return MysqlXADataSource.class.getName()
	}

	@Override
	Properties driverProperties() {
		return ([ 'URL'      : 'jdbc:mysql://localhost:3306/test?verifyServerCertificate=false&autoReconnect=true&serverTimezone=UTC',
		          'user'     : 'root',
		          'password' : 'root' ]) as Properties
	}
}
