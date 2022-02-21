package com.example.bankapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
            TVCryptoCurrencyName.text = cryptoObject.name
            cryptoObject.d?.volume?.toDouble()?.toBillionNotation()?.let {
                TVVolume.text = span("Volume(24hrs): ", it,15, "#E8E9EA" )
            }


            cryptoObject.d?.priceChangePct?.let {
                val color = if (it.contains("-")) "#ff4040" else "#32cd32"

                TVChange.text = span("Price Change(24hrs):", it, 20, color )
                 }
            cryptoObject.price?.let {
                TVPrice.text = span("Price: $", it,7, "#E8E9EA" )

            }
            cryptoObject.marketCap?.toDouble()?.toBillionNotation()?.let {
                TVMarketCap.text = span("Market Cap:", it, 12, "#E8E9EA")

            }
            TVCryptoCurrencySymbol.text = cryptoObject.symbol
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
             color:String):SpannableString {
             val spannedString = SpannableString("$string $subString")
             spannedString.setSpan(
                 ForegroundColorSpan(Color.parseColor(color)),
                 start,
                 spannedString.length,
                 Spannable.SPAN_EXCLUSIVE_INCLUSIVE
             )
             Log.d("TAG3", subString)

             return spannedString
         }
    }