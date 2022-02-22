package com.example.bankapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.example.bankapp.R
import com.example.bankapp.databinding.ListItemCryptoCurrencyBinding
import com.example.bankapp.extensions.loadSvg
import com.example.bankapp.extensions.toBillionNotation
import com.example.bankapp.model.CryptoDataItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CryptoCurrenciesAdapter():
    RecyclerView.Adapter<CryptoCurrenciesAdapter.CryptoViewHolder>()
     {
    private val cryptoListCallBack = object: DiffUtil.ItemCallback<CryptoDataItem>(){

        override fun areItemsTheSame(oldItem: CryptoDataItem, newItem: CryptoDataItem): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: CryptoDataItem, newItem: CryptoDataItem): Boolean {
            return oldItem==newItem

        }

    }
     private val async = AsyncListDiffer(this, cryptoListCallBack)
     var cryptoData:List<CryptoDataItem>
         get() = async.currentList
         set(value){async.submitList(value)}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CryptoCurrenciesAdapter.CryptoViewHolder {
        val binding = ListItemCryptoCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoViewHolder(binding)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.itemView.context
        holder.binding.apply{
            val cryptoObject = cryptoData[position]
            TVCryptoCurrencyName.text = "${cryptoObject.name}(${cryptoObject.symbol})"
            cryptoObject.d?.volume?.toDouble()?.toBillionNotation()?.let {
                TVVolume.text = span("Vol.(24hrs): ", it,0,12, "#4f4f4f" )

            }

            cryptoObject.d?.priceChangePct?.let {
                val color = if (it.contains("-")) "#ff4040" else "#32cd32"

                TVChange.text = span("", it, 0, it.length+1, color )

                 }
            cryptoObject.price?.let {
                TVPrice.text = "$${cryptoObject.price}"

            }
            cryptoObject.marketCap?.toDouble()?.toBillionNotation()?.let {
                TVMarketCap.text = span("Market Cap:", it, 0, 11, "#4f4f4f")

            }
            TVCryptoCurrencyDate.text = cryptoObject.priceDate?.dropLast(10)
            cryptoObject.logoUrl?.let{
                IVCryptoCurrencyLogo.loadSvg(it)

            }


        }
    }


    override fun getItemCount() = cryptoData.size

    inner class CryptoViewHolder(val binding: ListItemCryptoCurrencyBinding):RecyclerView.ViewHolder(binding.root)

         private fun span(
             string:String,
             subString:String,
             start:Int,
             end:Int,
             color:String):SpannableString {
             val spannedString = SpannableString("$string $subString")
             spannedString.setSpan(
                 ForegroundColorSpan(Color.parseColor(color)),
                 start,
                 end,
                 Spannable.SPAN_EXCLUSIVE_INCLUSIVE
             )


             return spannedString
         }
    }