package com.lacklab.app.codetest.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.lacklab.app.codetest.R
import com.lacklab.app.codetest.databinding.FragmentPassDetailBinding
import com.lacklab.app.codetest.utilities.Converters
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassDetailFragment : Fragment(){

    private lateinit var viewBinding: FragmentPassDetailBinding
    private val args: PassDetailFragmentArgs by navArgs()
    private val converters = Converters()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPassDetailBinding.inflate(inflater, container, false)
        viewBinding.textItemType.text =
            "${resources.getString(R.string.item_pass_type)} ${args.pass.passType}"
        viewBinding.textItemNumber.text =
            "${resources.getString(R.string.item_pass_number)} ${args.pass.number}"
        viewBinding.textItemPrices.text =
            "${resources.getString(R.string.item_pass_price)} Rp %.4f".format(args.pass.prices)
        viewBinding.textItemStatus.text =
            "${resources.getString(R.string.item_pass_status)} ${args.pass.passeStatus}"
        viewBinding.textItemActivation.text =
            "${resources.getString(R.string.item_pass_activation)} ${args.pass.passActivation}"
        viewBinding.textItemInsertionDate.text =
            "${resources.getString(R.string.item_pass_insertion_date)} " +
                    "${Converters.dateFormat.format(args.pass.insertionDate.time)}"
        if (args.pass.expirationTime != null) {
            viewBinding.textItemExpirationTime.text =
                "${resources.getString(R.string.item_pass_expiration_time)}" +
                        "${Converters.dateFormat.format(args.pass.expirationTime?.time)}"
        }


        return viewBinding.root
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }
}