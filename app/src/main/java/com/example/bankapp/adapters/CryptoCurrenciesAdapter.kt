package com.example.bankapp.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
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
            TVVolume.text = "Volume(24hrs): ${cryptoObject.d?.volume?.toDouble()?.toBillionNotation()}"
            cryptoObject.d?.priceChangePct?.apply {
                TVChange.text = "Price change(24hrs): $this"
                if(this.contains('-'))
                    TVChange.setTextColor(R.color.red)
                else
                    TVChange.setTextColor(R.color.green)

            }
            TVPrice.text = "Price: ${cryptoObject.price}"
            TVMarketCap.text = "Market Cap: ${cryptoObject.marketCap?.toDouble()?.toBillionNotation()}"
            TVCryptoCurrencySymbol.text = cryptoObject.symbol
            TVCryptoCurrencyDate.text = cryptoObject.priceDate?.dropLast(10)
            cryptoObject.logoUrl?.let{
                IVCryptoCurrencyLogo.loadSvg(it)

            }


        }
    }


    override fun getItemCount() = cryptoData.size

    inner class CryptoViewHolder(val binding: ListItemCryptoCurrencyBinding):RecyclerView.ViewHolder(binding.root)
}