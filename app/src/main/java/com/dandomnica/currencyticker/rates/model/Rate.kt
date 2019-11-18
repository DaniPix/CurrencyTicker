package com.dandomnica.currencyticker.rates.model

data class Rate(val countryCode: String,
				val currencyShortName: String,
				val currencyFullName: String,
				val rate: Double) {

	override fun equals(other: Any?): Boolean {
		return other is Rate && currencyShortName == other.currencyShortName
	}

	override fun hashCode(): Int {
		var result = countryCode.hashCode()
		result = 31 * result + currencyShortName.hashCode()
		result = 31 * result + currencyFullName.hashCode()
		result = 31 * result + rate.hashCode()
		return result
	}
}