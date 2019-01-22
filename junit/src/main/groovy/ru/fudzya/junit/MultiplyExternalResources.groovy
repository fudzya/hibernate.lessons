package ru.fudzya.junit


import org.junit.rules.ExternalResource
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MultiplyExternalResources extends ExternalResource {

	@Delegate
	private List<ExternalResource> resources = []

	@Override
	protected void before() throws Throwable {
		resources*.before()
	}

	@Override
	protected void after() {
		resources*.after()
	}

	@Override
	Statement apply(Statement base, Description description)
	{
		resources*.apply(base, description)
		return super.apply(base, description)
	}
}
