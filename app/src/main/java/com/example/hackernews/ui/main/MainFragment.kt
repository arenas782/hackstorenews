package com.example.hackernews.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hackernews.R
import com.example.hackernews.databinding.FragmentMainBinding
import com.example.hackernews.ui.base.BaseFragment
import com.example.hackernews.ui.main.viewmodel.MainViewModel
import com.example.hackernews.utils.Commons
import com.example.hackernews.utils.Status
import com.example.hackernews.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment  : BaseFragment(){
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel : MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.swipeRefreshLayout.isRefreshing = false

        val refreshListener = SwipeRefreshLayout.OnRefreshListener {
            Log.e("TAG","refreshing")
            viewModel.setStateEvent(MainViewModel.MainStateEvent.ReloadPostsEvent)
        }

        binding.swipeRefreshLayout.setOnRefreshListener(refreshListener)
        binding.swipeRefreshLayout.setColorSchemeColors(Commons.getColor(R.color.purple_700))

        subscribe()
        viewModel.setStateEvent(MainViewModel.MainStateEvent.GetPostsEvent)

        viewModel.swipedToRefresh.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let { isRefreshing ->
                binding.swipeRefreshLayout.isRefreshing = isRefreshing
                Log.e("TAG","refreshing changed $isRefreshing")
            }
        }
    }

    private fun setupClickObserver(){
        viewModel.actionDetailsPost.observe(this){
            it.getContentIfNotHandled()?.let { action ->
                if (action){
                    viewModel.postDetails.value.let { post ->
                        when {
                            post?.story_url != null -> goToDetails(post.story_url.toString())
                            post?.url != null -> goToDetails(post.url.toString())
                            else -> Commons.showSnackBar(title = Commons.getString(R.string.post_no_url),view = binding.recycler,type = Commons.SnackType.ERROR)
                        }
                    }
                }
            }
        }
    }
    private fun goToDetails(postUrl : String){
        findNavController().navigate( MainFragmentDirections.actionMainFragmentToDetailsFragment(postUrl))
    }

    private fun subscribe(){
        viewModel.posts.observe(viewLifecycleOwner){
            when (it.status){
                Status.SUCCESS -> {
                    Log.e(TAG,it.data.toString())

                    setupClickObserver()
                }
                Status.LOADING -> {

                    Log.e(TAG,"Loading data")
                }
                Status.ERROR -> {

                    Log.e(TAG,it.message.toString())
                    Commons.showSnackBar(title = Commons.getString(R.string.something_went_wrong),view = binding.recycler,type = Commons.SnackType.ERROR)
                }
            }
        }
    }
    companion object {
        const val TAG =  "MainFragment"
    }
}