package com.dandomnica.currencyticker.util

import com.dandomnica.currencyticker.base.round

val countryCodes by lazy {
	mutableMapOf<String, String>().apply {
		put("AUD", "AU")
		put("BGN", "BG")
		put("BRL", "BR")
		put("CAD", "CA")
		put("CHF", "CH")
		put("CNY", "CN")
		put("CZK", "CZ")
		put("DKK", "DK")
		put("EUR", "EU")
		put("GBP", "GB")
		put("HKD", "HK")
		put("HRK", "HR")
		put("HUF", "HU")
		put("IDR", "ID")
		put("ILS", "IL")
		put("INR", "IN")
		put("ISK", "IS")
		put("JPY", "JP")
		put("KRW", "KR")
		put("MXN", "MX")
		put("MYR", "MY")
		put("NOK", "NO")
		put("NZD", "NZ")
		put("PHP", "PH")
		put("PLN", "PL")
		put("RON", "RO")
		put("RUB", "RU")
		put("SEK", "SE")
		put("SGD", "SG")
		put("THB", "TH")
		put("TRY", "TR")
		put("USD", "US")
		put("ZAR", "ZA")
	}
}

val currencyFullNames by lazy {
	mutableMapOf<String, String>().apply {
		put("AUD", "Australian Dollar")
		put("BGN", "Bulgarian Lev")
		put("BRL", "Brazilian Real")
		put("CAD", "Canadian Dollar")
		put("CHF", "Swiss Franc")
		put("CNY", "Chinese Yuan")
		put("CZK", "Czech Koruna")
		put("DKK", "Danish Krona")
		put("EUR", "Euro")
		put("GBP", "British Pound")
		put("HKD", "Hong Kong Dollar")
		put("HRK", "Croatian Kuna")
		put("HUF", "Hungarian Forint")
		put("IDR", "Indonesian Rupiah")
		put("ILS", "Israeli Shekels")
		put("INR", "Indian Rupee")
		put("ISK", "Iceland Krona")
		put("JPY", "Japanese Yen")
		put("KRW", "Korean Won")
		put("MXN", "Mexican Peso")
		put("MYR", "Malaysian Ringgit")
		put("NOK", "Norwegian Krona")
		put("NZD", "New Zeeland Dollar")
		put("PHP", "Philipine Peso")
		put("PLN", "Poland złoty")
		put("RON", "Romanian Leu")
		put("RUB", "Russian Ruble")
		put("SEK", "Swedish Krona")
		put("SGD", "Singapore Dollar")
		put("THB", "Thai Baht")
		put("TRY", "Turkish Lira")
		put("USD", "US Dollar")
		put("ZAR", "South African Rand")
	}
}

/**
 * Calculate the rate for a non default amount of currency value against another currency.
 * Round up to two decimals.
 */
fun calculateRate(amount: Double, rate: Double): Double {
	return (amount * rate).round(2)
}
