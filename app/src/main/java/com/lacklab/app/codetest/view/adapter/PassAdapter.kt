package com.lacklab.app.codetest.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lacklab.app.codetest.data.MigoPass
import com.lacklab.app.codetest.databinding.ItemPassBinding
import com.lacklab.app.codetest.databinding.ViewPassHeaderBinding
import com.lacklab.app.codetest.utilities.Converters
import com.lacklab.app.codetest.utilities.ONEDAYMILLIS
import com.lacklab.app.codetest.view.fragment.WalletFragmentDirections
import com.lacklab.app.codetest.viewmodel.PassViewModel
import com.lacklab.app.codetest.viewmodel.WalletViewModel
import java.util.*

class PassAdapter : ListAdapter<MigoPass, RecyclerView.ViewHolder>(PassDiffCallback()) {
    companion object {
        const val TYPE_DAY_HEADER = 0
        const val TYPE_HOUR_HEADER = 1
        const val TYPE_NORMAL = 2
    }

    private var sectionPosition: List<Int> = mutableListOf(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_DAY_HEADER || viewType == TYPE_HOUR_HEADER) {
            val viewBinding = ViewPassHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
            return HeaderViewHolder(viewBinding)
        }
        val viewBinding = ItemPassBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return PassViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == sectionPosition!![0]) {
            (holder as HeaderViewHolder).bind("DAY PASS")
        }
        else if (position == sectionPosition!![1]) {
            (holder as HeaderViewHolder).bind("HOUR PASS")
        }
        else {
            val item = if (position > sectionPosition!![1]) {
                getItem(position - 2)
            }
            else {
                getItem(position - 1)
            }
//            val item = getItem(position - 1)
            if (item != null) {
                (holder as PassViewHolder).bind(item)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == sectionPosition!![0]) {
            return TYPE_DAY_HEADER
        }
        else if (position == sectionPosition!![1]) {
            return TYPE_HOUR_HEADER
        }
        return TYPE_NORMAL

    }

    override fun getItemCount(): Int {
        return if (sectionPosition!!.size > 1)
            super.getItemCount() + 2 else super.getItemCount() + 1
//        return super.getItemCount() + 1
    }

    fun setHeaderPosition(indices: List<Int>) {
        sectionPosition = indices
    }

    class HeaderViewHolder(
            private val viewBinding: ViewPassHeaderBinding
        ) : RecyclerView.ViewHolder(viewBinding.root) {
            fun bind(headerType: String) {
                viewBinding.textViewHeader.text = headerType
            }
        }

    class PassViewHolder(
            private val viewBinding: ItemPassBinding
        ) : RecyclerView.ViewHolder(viewBinding.root) {
            private var pass: MigoPass? = null
            init {
                val converters = Converters()
                viewBinding.root.setOnClickListener {
                    navigateToPassDetail(pass!!, it)
                }

                viewBinding.btnBuy.setOnClickListener {
                    pass!!.passeStatus = "Bought"
                    pass!!.passActivation = "Activated"
                    if (pass!!.passType == "Day") {
                        // calculate the expiration date of Day
                    } else {
                        var calendar = Calendar.getInstance()
                        calendar.add(Calendar.HOUR, pass!!.number.toInt())

                        Log.i("Adapter", "expiration Date: ${Converters.dateFormat.format(calendar.time)}")
                        pass!!.expirationTime = calendar
                    }
                }
            }

        fun bind(item: MigoPass) {
            pass = item
            viewBinding.textViewPass.text = "${item.number} ${item.passType} Pass"
            viewBinding.textViewPrice.text = "Rp %.4f".format(item.prices)
            if (item.passeStatus == "Added") {
                viewBinding.btnBuy.text ="BUY"
                viewBinding.btnBuy.isClickable = true
            } else {
                if (item.passActivation == "Activated") {
                    viewBinding.btnBuy.text ="ACTIVATED"
                    viewBinding.btnBuy.isClickable = false
                } else {
                    viewBinding.btnBuy.text ="Expired"
                    viewBinding.btnBuy.isClickable = false
                }
            }
        }

        private fun navigateToPassDetail(pass:MigoPass, view: View) {
            val direction = WalletFragmentDirections.
                actionWalletFragmentToPassDetailFragment(pass)
            view.findNavController().navigate(direction)
        }

    }

}

private class PassDiffCallback : DiffUtil.ItemCallback<MigoPass> () {
    override fun areItemsTheSame(oldItem: MigoPass, newItem: MigoPass): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MigoPass, newItem: MigoPass): Boolean {
        return oldItem == newItem
    }


}