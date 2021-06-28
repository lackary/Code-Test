package com.lacklab.app.codetest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lacklab.app.codetest.data.MigoPass
import com.lacklab.app.codetest.databinding.ItemPassBinding

class PassAdapter : ListAdapter<MigoPass, RecyclerView.ViewHolder>(PassDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewBinding =
            ItemPassBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return PassViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            (holder as PassViewHolder).bind(item)
        }
    }

    class PassViewHolder(
        private val viewBinding: ItemPassBinding) : RecyclerView.ViewHolder(viewBinding.root) {
            init {
                viewBinding.root.setOnClickListener {

                }
            }

        fun bind(item: MigoPass) {
            viewBinding.textViewPass.text = "${item.number} ${item.passType} Pass"
            viewBinding.textViewPrice.text = "Rp %.4f".format(item.prices)
            viewBinding.btnBuy.text =
                if (item.passeStatus == "Added") "BUY" else "ACTIVATED"
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