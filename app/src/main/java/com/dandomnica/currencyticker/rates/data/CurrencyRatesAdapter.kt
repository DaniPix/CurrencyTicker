package com.dandomnica.currencyticker.rates.data

import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dandomnica.currencyticker.R
import com.dandomnica.currencyticker.rates.CurrencyListener
import com.dandomnica.currencyticker.rates.model.Rate
import com.dandomnica.currencyticker.util.countryCodes
import com.dandomnica.currencyticker.util.currencyFullNames

private const val FIRST_INDEX = 0

class CurrencyRatesAdapter(private val listener: CurrencyListener,
						   private val data: MutableList<Rate> = mutableListOf()
) : RecyclerView.Adapter<CurrencyRatesAdapter.RateViewHolder>() {

	private var selectedCurrency = "EUR"
	private val currencyInputTextWatcher: InputValueTextWatcher = InputValueTextWatcher()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
		val container = LayoutInflater.from(parent.context).inflate(R.layout.layout_currecy_rate_item, parent, false)
		return RateViewHolder(container)
	}

	override fun getItemCount(): Int {
		return data.size
	}

	override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
		val rate = data[position]

		holder.loadFlag(rate.countryCode)
		holder.currencyShortName.text = rate.currencyShortName
		holder.currencyFullName.text = rate.currencyFullName

		holder.currencyAmountInput.removeTextChangedListener(currencyInputTextWatcher)
		holder.currencyAmountInput.text = SpannableStringBuilder(String.format("%.2f", rate.rate))
		holder.currencyAmountInput.setSelection(holder.currencyAmountInput.text.length)
		holder.currencyAmountInput.addTextChangedListener(currencyInputTextWatcher)

		holder.itemView.setOnClickListener {
			listener.onCurrencyClick( data[position])
			selectedCurrency = rate.currencyShortName
			holder.currencyAmountInput.requestFocus()
		}
	}

	override fun onBindViewHolder(holder: RateViewHolder, position: Int, payloads: MutableList<Any>) {
		if (payloads.isEmpty()) {
			onBindViewHolder(holder, position)
		} else {
			payloads.filterIsInstance<Rate>()
					.forEach {
						holder.currencyAmountInput.removeTextChangedListener(currencyInputTextWatcher)
						holder.currencyAmountInput.text = SpannableStringBuilder(String.format("%.2f", it.rate))
						holder.currencyAmountInput.setSelection(holder.currencyAmountInput.text.length)
						holder.currencyAmountInput.addTextChangedListener(currencyInputTextWatcher)
					}
		}
	}

	/**
	 * Swap top currency.
	 *
	 * First currency inside newData list contains the requested currency
	 * we want to move on top of the list.
	 */
	private fun swapTopCurrency(newData: List<Rate>) {
		val position = data.indexOfFirst { it.currencyShortName == newData[0].currencyShortName }
		val rate = data[position]
		data.removeAt(position)
		data.add(FIRST_INDEX, rate)
		notifyItemMoved(position, FIRST_INDEX)
	}

	/**
	 * Update currencies with new values every second.
	 */
	private fun refreshCurrencies(newData: List<Rate>) {
		data.forEachIndexed { index, rate ->
			if (rate.currencyShortName != selectedCurrency) {
				val currentPosition = newData.indexOf(rate)
				data[index] = newData[currentPosition]
				notifyItemChanged(index, data[index])
			} else {
				val currentPosition = newData.indexOf(rate)
				data[index] = newData[currentPosition]
			}
		}
	}

	fun setData(newData: List<Rate>) {
		when {
			data.isNotEmpty() && newData.isNotEmpty() && newData[0].currencyShortName != data[0].currencyShortName ->
				swapTopCurrency(newData)
			data.isNotEmpty() ->
				refreshCurrencies(newData)
			else -> {
				data.clear()
				data.addAll(newData)
				notifyDataSetChanged()
			}
		}
	}

	inner class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val currencyCountryIcon: ImageView = itemView.findViewById(R.id.currencyCountryIcon)
		val currencyShortName: TextView = itemView.findViewById(R.id.currencyShortName)
		val currencyFullName: TextView = itemView.findViewById(R.id.currencyFullName)
		val currencyAmountInput: EditText = itemView.findViewById(R.id.currencyAmountInput)

		fun loadFlag(countryCode: String) {
			Glide.with(itemView.context)
					.load("https://www.countryflags.io/${countryCode}/flat/64.png")
					.apply(RequestOptions.centerCropTransform())
					.into(currencyCountryIcon)
		}
	}

	private inner class InputValueTextWatcher : TextWatcher {
		override fun afterTextChanged(sequence: Editable?) {}
		override fun beforeTextChanged(sequence: CharSequence?, start: Int, count: Int, after: Int) {}
		override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
			if (sequence?.isNotEmpty() == true) {
				val changedAmountValue = sequence.toString().toDoubleOrNull() ?: return

				if (changedAmountValue <= 0.0) return

				val rate = Rate(
						countryCode = countryCodes[selectedCurrency] ?: "",
						currencyShortName = selectedCurrency,
						currencyFullName = currencyFullNames[selectedCurrency] ?: "",
						rate = changedAmountValue
				)
				listener.onTopCurrencyAmountChanged(rate)
			}
		}
	}
}