package com.lacklab.app.codetest.view.adapter

import android.content.Context
import android.graphics.Color
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
import com.lacklab.app.codetest.event.PassItemClickEvent
import com.lacklab.app.codetest.utilities.Converters
import com.lacklab.app.codetest.utilities.ONEDAYMILLIS
import com.lacklab.app.codetest.view.fragment.WalletFragmentDirections
import com.lacklab.app.codetest.viewmodel.WalletViewModel
import java.util.*

class PassAdapter(private val clickEvent: PassItemClickEvent) : ListAdapter<MigoPass, RecyclerView.ViewHolder>(PassDiffCallback()) {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_NORMAL = 2
    }

    private var headPositions = mutableListOf<Int>()
    private var passTypes = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
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

        if (position == headPositions[0]) {
            (holder as HeaderViewHolder).bind("${passTypes[0]} PASS")
        }
        else if (passTypes.size == 2) {
            if (position == headPositions[1]) {
                (holder as HeaderViewHolder).bind("HOUR PASS")
            } else {
                val item = if (position > headPositions[1]) {
                    getItem(position - 2)
                } else {
                    getItem(position - 1)
                }
                if (item != null) {
                    (holder as PassViewHolder).bind(item, clickEvent)
                }
            }
        } else {
            val item = getItem(position - 1)
            if (item != null) {
                (holder as PassViewHolder).bind(item, clickEvent)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == headPositions[0])  {
            return TYPE_HEADER
        }
        if (passTypes.size == 2) {
            if (position == headPositions[1]) {
                return TYPE_HEADER
            }
        }
        return TYPE_NORMAL

    }

    override fun getItemCount(): Int {
        if (passTypes.size == 1) {
            return super.getItemCount() + 1
        } else if (passTypes.size == 2) {
            return super.getItemCount() + 2
        }
        return super.getItemCount()
    }

    fun setPassTypes(types: List<String>) {
        passTypes = types as MutableList<String>
    }

    fun setHeaderPosition(indices: List<Int>) {
        headPositions = indices as MutableList<Int>
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
            private var clickEvent: PassItemClickEvent? = null
            init {
                val converters = Converters()
                viewBinding.root.setOnClickListener {
                    navigateToPassDetail(pass!!, it)
                }

                viewBinding.btnBuy.setOnClickListener {
                    calculateExpirationDate(pass!!)
//                    viewBinding.btnBuy.text ="ACTIVATED"
//                    viewBinding.btnBuy.isClickable = false
//                    viewBinding.btnBuy.setTextColor(Color.GREEN)
                    Log.i("TEST", "${pass?.expirationTime?.timeInMillis}")
                    clickEvent!!.onButtonBuyClick(pass!!)

                }
            }

        fun bind(item: MigoPass, event: PassItemClickEvent) {
            clickEvent = event
            pass = item
            Log.i("TEST","${item.number} ${item.passType} Pass\"")
            viewBinding.textViewPass.text = "${item.number} ${item.passType} Pass"
            viewBinding.textViewPrice.text = "Rp %.4f".format(item.prices)
            if (item.passeStatus == "Added") {
                viewBinding.btnBuy.text ="BUY"
                viewBinding.btnBuy.isClickable = true
            } else {
                if (item.passActivation == "Activated") {
                    viewBinding.btnBuy.text ="ACTIVATED"
                    viewBinding.btnBuy.isClickable = false
                    viewBinding.btnBuy.setTextColor(Color.GREEN)
                } else {
                    viewBinding.btnBuy.text ="EXPIRED"
                    viewBinding.btnBuy.isClickable = false
                    viewBinding.btnBuy.setTextColor(Color.RED)
                }
            }
        }

        private fun navigateToPassDetail(pass:MigoPass, view: View) {
            val direction = WalletFragmentDirections.
                actionWalletFragmentToPassDetailFragment(pass)
            view.findNavController().navigate(direction)
        }

        private fun calculateExpirationDate(pass: MigoPass) {
            pass.passeStatus = "Bought"
            pass.passActivation = "Activated"
            if (pass.passType == "Day") {
                // calculate the expiration date of Day
            } else {
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.HOUR, pass.number.toInt())

                Log.i("Adapter", "expiration Date: ${Converters.dateFormat.format(calendar.time)}")
                pass.expirationTime = calendar
            }
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