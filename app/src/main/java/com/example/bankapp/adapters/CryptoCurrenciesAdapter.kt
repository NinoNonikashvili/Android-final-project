package com.example.bankapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.example.bankapp.databinding.ListItemCryptoCurrencyBinding
import com.example.bankapp.extensions.loadSvg
import com.example.bankapp.model.CryptoDataItem

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.itemView.context
        holder.binding.apply{
            val cryptoObject = cryptoData[position]
            TVCryptoCurrencyName.text = cryptoObject.name
            TVVolume.text = "Volume: ${cryptoObject.d?.volume}"
            TVChange.text = "Price: ${cryptoObject.d?.priceChangePct}"
            TVPrice.text = "Price: ${cryptoObject.price}"
            TVMarketCap.text = "Market Cap: ${cryptoObject.marketCap}"
            TVCryptoCurrencySymbol.text = cryptoObject.symbol
            TVCryptoCurrencyDate.text = "${cryptoObject.priceDate}"
            cryptoObject.logoUrl?.let{
                IVCryptoCurrencyLogo.loadSvg(it)

            }


        }
    }


    override fun getItemCount() = cryptoData.size

    inner class CryptoViewHolder(val binding: ListItemCryptoCurrencyBinding):RecyclerView.ViewHolder(binding.root)
}