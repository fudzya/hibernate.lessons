package ru.fudzya.junit.db

interface DatabasePlatform {

	Product    product()
	String     driverName()
	Properties driverProperties()
}