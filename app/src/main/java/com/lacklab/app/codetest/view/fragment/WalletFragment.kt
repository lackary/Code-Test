package com.lacklab.app.codetest.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lacklab.app.codetest.R
import com.lacklab.app.codetest.data.MigoPass
import com.lacklab.app.codetest.databinding.FragmentWalletBinding
import com.lacklab.app.codetest.view.adapter.PassAdapter
import com.lacklab.app.codetest.viewmodel.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.merge

@AndroidEntryPoint
class WalletFragment : Fragment() {

    private val passAdapter = PassAdapter()
    private lateinit var viewBinding: FragmentWalletBinding

    private val viewModel: WalletViewModel by viewModels()
    private lateinit var passesDay: List<MigoPass>
    private lateinit var passesHour: List<MigoPass>
    private var currentPassType: String = "Day"
    private var currentPrice: Double = 0.0000
    private var currentPassNumber: Long = 0

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
        viewBinding = FragmentWalletBinding.inflate(inflater, container, false)

        viewBinding.recyclerViewWallet.adapter = passAdapter
//        passAdapter.submitList(passItem)

        // set bottom sheet behavior
        val bottomSheetBehavior = BottomSheetBehavior.from(
            viewBinding.includeBottomSheet.layoutBottomSheet)
        viewBinding.fabAdd.setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        // set spinner
        val passTypeSpinner = viewBinding.includeBottomSheet.spinnerPassType
        this.context?.let { it ->
            ArrayAdapter.createFromResource(
                it,
                R.array.pass_type_array,
                R.layout.support_simple_spinner_dropdown_item).also { adapter ->
                    adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                    passTypeSpinner.adapter = adapter
            }
        }
        passTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentPassType = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        // set EditText
        viewBinding.includeBottomSheet.textEditNumber.text
        viewBinding.includeBottomSheet.textEditNumber.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                currentPassNumber =
                    viewBinding.includeBottomSheet.textEditNumber.text.toString().toLong()
                currentPrice = currentPassNumber * 2.0000
                viewBinding.includeBottomSheet.textPrice.text = "Rp $currentPrice"
                true
            } else {
                false
            }
        }

        //set Button apply
        viewBinding.includeBottomSheet.btnApply.setOnClickListener {
              val migoPass =  MigoPass(
                  passType = currentPassType,
                  number = currentPassNumber,
                  prices = currentPrice,
                  passeStatus = "Added"
              )
            viewModel.insertPass(migoPass)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        return viewBinding.root
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        updateUi()
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

    private fun updateUi() {
        viewModel.passesDay.observe(viewLifecycleOwner) { it ->
            passesDay = it
            viewModel.passesHour.observe(viewLifecycleOwner) {
                passesHour = it
                val passTypes = mutableListOf<String>()
                val headPositions = mutableListOf<Int>()
                if (passesDay.isNotEmpty()) {
                    passTypes.add("DAY")
                }
                if (passesHour.isNotEmpty()) {
                    passTypes.add("HOUR")
                }
                val allList = passesDay + passesHour
                passAdapter.setPassTypes(passTypes)
                if (passTypes.size == 1) {
                    passAdapter.setHeaderPosition(arrayListOf(0))
                } else if (passTypes.size == 2) {
                    passAdapter.setHeaderPosition(arrayListOf(0, passesDay.size + 1))
                }

                passAdapter.submitList(allList)
            }
        }

    }
}