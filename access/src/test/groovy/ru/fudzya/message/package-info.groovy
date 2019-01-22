@GenericGenerator(
	name       = "MESSAGE_ID",
	strategy   = "enhanced-sequence",
	parameters = [
		@Parameter(
			name = "sequence_name",
			value = "S_MESSAGE"
		)
])
@NamedQueries([@NamedQuery(name = 'Hibernate.Message.All', query = 'select m from Message m')])
package ru.fudzya.message

import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.NamedQueries
import org.hibernate.annotations.NamedQuery
import org.hibernate.annotations.Parameter