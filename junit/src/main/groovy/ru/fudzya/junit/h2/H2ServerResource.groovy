package ru.fudzya.junit.h2

import org.h2.tools.Server
import org.hibernate.dialect.Database
import org.junit.rules.ExternalResource
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.fudzya.junit.dataaccess.DatabasePlatformSelector
import ru.fudzya.junit.db.DatabasePlatform
import ru.fudzya.junit.db.Product

class H2ServerResource extends ExternalResource {

	private final DatabasePlatform databasePlatform

	H2ServerResource(DatabasePlatform databasePlatform) {
		this.databasePlatform = databasePlatform
	}
	
	@Lazy
	protected volatile Server server = {
		Server.createTcpServer('-tcpAllowOthers', '-trace')
	}()

	@Override
	protected void before() {
		server.start()
	}

	@Override
	protected void after() {
		server.shutdown()
	}

	@Override
	Statement apply(Statement base, Description description)
	{
		if (databasePlatform.product() == Product.h2) {
			return super.apply(base, description)
		}

		return [:] as Statement
	}
}
