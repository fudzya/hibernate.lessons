package ru.fudzya.junit.hibernate


import org.junit.Rule
import ru.fudzya.junit.MultiplyExternalResources
import ru.fudzya.junit.dataaccess.DatabasePlatformSelector
import ru.fudzya.junit.db.DatabasePlatform
import ru.fudzya.junit.h2.PoolingDataSourceResource
import ru.fudzya.junit.h2.H2ServerResource

abstract class HibernateSupported {

	@Rule
	public MultiplyExternalResources resources = new MultiplyExternalResources().with { resources ->

		DatabasePlatform platform = new DatabasePlatformSelector()

		resources.add(new H2ServerResource(platform))
		resources.add(new PoolingDataSourceResource(platform) {
			@Override
			protected String jndiDataSourceName() {
				return HibernateSupported.this.jndiDataSourceName()
			}
		})

		resources.add(new HibernateResource() {
			@Override
			protected Collection<String> hibernateConfigFilesName() {
				return HibernateSupported.this.hibernateConfigFilesName()
			}
		})

		return resources
	}

	protected abstract String jndiDataSourceName()

	protected abstract Collection<String> hibernateConfigFilesName()
}
