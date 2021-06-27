package com.lacklab.app.codetest.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.lacklab.app.codetest.data.MigoPass
import com.lacklab.app.codetest.databinding.FragmentWalletBinding
import com.lacklab.app.codetest.view.adapter.PassAdapter
import com.lacklab.app.codetest.viewmodel.WalletViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WalletFragment : Fragment() {

    private val passItem: List<String> = listOf("test1", "test1", "test1", "test1")
    private val passTypes: List<String> = arrayListOf("day", "hour")
    private val passAdapter = PassAdapter()
    private lateinit var viewBinding: FragmentWalletBinding

    private val viewModel: WalletViewModel by viewModels()
    private lateinit var passesDay: List<MigoPass>
    private lateinit var passesHour: List<MigoPass>

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
        passAdapter.submitList(passItem)

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
        viewModel.passesDay.observe(viewLifecycleOwner) {
            passesDay = it
        }
//        viewModel.passesHour.observe(viewLifecycleOwner) {
//            passesHour = it
//        }
    }
}