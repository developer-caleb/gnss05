package kr.loplab.gnss05.fragments

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import kr.loplab.gnss02.FragmentBase
import kr.loplab.gnss05.R
import kr.loplab.gnss05.activities.viewmodel.MobileStationViewModel
import kr.loplab.gnss05.activities.viewmodel.PositionInformationViewModel
import kr.loplab.gnss05.databinding.FragmentPositionDetailInfoBinding

class PositionDetailInfoFragment : FragmentBase<FragmentPositionDetailInfoBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_position_detail_info
    lateinit var viewModel1: PositionInformationViewModel

    //private lateinit var _binding : FragmentPositionDetailInfoBinding
  /*  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.fragment_position_detail_info, container, false)
    }*/

    companion object {

    }

    override fun init() {
        Log.d(TAG, "init: viewbinding 성공!")
        viewModel1 = ViewModelProvider(requireActivity()).get(PositionInformationViewModel::class.java)
        viewBinding.viewModel = viewModel1
    }

    override fun initListener() {

    }

    override fun initDataBinding() {

    }
}