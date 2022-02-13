package com.example.bankapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bankapp.databinding.CurrencyListItemBinding
import com.example.bankapp.extensions.roundDecimal
import com.example.bankapp.model.Rate


class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private val coursesListCallBack = object : DiffUtil.ItemCallback<Rate>() {

        override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean {
            return oldItem == newItem
        }
    }

    val differ: AsyncListDiffer<Rate> = AsyncListDiffer(this, coursesListCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            CurrencyListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rate = differ.currentList[position]

        holder.onBind(rate)
    }


    override fun getItemCount() = differ.currentList.size


    inner class ViewHolder(private val binding: CurrencyListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(rate: Rate?) {

            rate?.let {
                binding.IVCurrencyIcon.text = it.comm.currency
                binding.TVOfficialRate.text = it.off.value.roundDecimal(3)
                binding.TVSellRate.text = it.comm.sell.roundDecimal(4)
                binding.TVBuyRate.text = it.comm.buy.roundDecimal(4)
            }
        }


    }

}