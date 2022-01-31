package com.example.hackernews.ui.base





import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment


abstract class BaseFragment : Fragment {


    protected var fragmentView: View? = null

    constructor() : super()
    constructor(@LayoutRes layout: Int) : super(layout)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fragmentView = super.onCreateView(inflater, container, savedInstanceState)
        return fragmentView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentView = null
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    goBack()
                }
            })
    }

    private  fun goBack() {
        val navHostFragment = this.parentFragment as NavHostFragment?
        /**If navHostFragment has no fragment in stack that means you reach to the top most fragment on the stack and app exist popup should be shown here.Otherwise just simple pop the immediate fragment from backstack */
        if (navHostFragment != null &&
            navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            requireActivity().finish()
        }
        else NavHostFragment.findNavController(this).navigateUp()
    }



}