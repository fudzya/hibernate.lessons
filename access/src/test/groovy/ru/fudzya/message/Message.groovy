package ru.fudzya.message


import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
class Message implements Serializable {

	@Id
	@GeneratedValue(generator = 'MESSAGE_ID')
	Long id

	@NotNull
	String text

	Long getId() {
		return id
	}

	void setText(String message) {
		this.text = message
	}

	String getText() {
		return text
	}
}
