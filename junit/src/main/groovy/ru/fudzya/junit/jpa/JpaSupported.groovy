package ru.fudzya.junit.jpa

import org.junit.Rule
import ru.fudzya.junit.MultiplyExternalResources
import ru.fudzya.junit.dataaccess.DatabasePlatformSelector
import ru.fudzya.junit.db.DatabasePlatform
import ru.fudzya.junit.h2.H2ServerResource
import ru.fudzya.junit.h2.PoolingDataSourceResource

abstract class JpaSupported {

	@Rule
	public MultiplyExternalResources resources = new MultiplyExternalResources().with { resources ->

		DatabasePlatform platform = new DatabasePlatformSelector()

		resources.add(new H2ServerResource(platform))
		resources.add(new PoolingDataSourceResource(platform) {
			@Override
			protected String jndiDataSourceName() {
				return JpaSupported.this.jndiDataSourcesName()
			}
		})

		return resources
	}

	protected abstract String jndiDataSourcesName()
}
