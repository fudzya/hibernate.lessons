package ru.fudzya.junit.h2

import bitronix.tm.TransactionManagerServices
import bitronix.tm.resource.jdbc.PoolingDataSource
import org.apache.commons.lang.RandomStringUtils
import org.junit.rules.ExternalResource
import ru.fudzya.common.dataaccess.utils.NamesUtils
import ru.fudzya.junit.db.DatabasePlatform

import javax.naming.InitialContext

class PoolingDataSourceResource extends ExternalResource {

	@Lazy
	private volatile InitialContext context = { new InitialContext() }()

	private final PoolingDataSource dSource

	PoolingDataSourceResource(final DatabasePlatform platform) {
		
		this.dSource = new PoolingDataSource().with { source ->
			source.setUniqueName(NamesUtils.toJndiDataSourceName(jndiDataSourceName()))
			source.setMinPoolSize(1)
			source.setMaxPoolSize(5)
			source.setClassName(platform.driverName())
			source.setAllowLocalTransactions(true)
			source.setDriverProperties(platform.driverProperties())
			return source
		}
	}

	protected String jndiDataSourceName() {
		throw new UnsupportedOperationException()
	}

	@Override
	protected void before() throws Throwable {
		dSource.init()

		def tmConfig = TransactionManagerServices.configuration

		tmConfig.with {
			serverId    = "vm-${RandomStringUtils.random(10, true, true)}"
			disableJmx  = true
		}
		
		context.rebind(dSource.getUniqueName(),                                  dSource)
		context.rebind(tmConfig.getJndiUserTransactionName(),                    TransactionManagerServices.getTransactionManager())
		context.rebind(tmConfig.getJndiTransactionSynchronizationRegistryName(), TransactionManagerServices.getTransactionSynchronizationRegistry())
	}

	@Override
	protected void after() {
		TransactionManagerServices.getTransactionManager().shutdown()

		dSource.close()
		context.close()
	}
}