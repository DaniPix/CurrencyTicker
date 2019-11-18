package com.dandomnica.remote.api.models

import com.google.gson.annotations.SerializedName

data class Rates(@SerializedName("base")
				 val baseCurrency: String,
				 @SerializedName("rates")
				 val currenciesRate: Map<String, Double>)