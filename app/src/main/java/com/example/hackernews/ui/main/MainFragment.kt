package com.example.hackernews.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hackernews.R
import com.example.hackernews.databinding.FragmentMainBinding
import com.example.hackernews.ui.base.BaseFragment
import com.example.hackernews.ui.main.adapter.PostAdapter
import com.example.hackernews.ui.main.adapter.SwipeGesture
import com.example.hackernews.ui.main.viewmodel.MainViewModel
import com.example.hackernews.utils.Commons
import com.example.hackernews.utils.Status
import com.example.hackernews.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment  : BaseFragment(){
    lateinit var binding : FragmentMainBinding
    lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]

        subscribe()
        viewModel.setStateEvent(MainViewModel.MainStateEvent.GetPostsEvent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSwipeLayout()

    }

    private fun setupSwipeLayout(){
        binding.swipeRefreshLayout.isRefreshing = false

        val refreshListener = SwipeRefreshLayout.OnRefreshListener {
            Log.e("TAG","refreshing")
            viewModel.setStateEvent(MainViewModel.MainStateEvent.ReloadPostsEvent)
        }

        binding.swipeRefreshLayout.setOnRefreshListener(refreshListener)
        binding.swipeRefreshLayout.setColorSchemeColors(Commons.getColor(R.color.purple_700))

        viewModel.swipedToRefresh.observe(this){
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
        viewModel.posts.observe(this){
            when (it.status){
                Status.SUCCESS -> {
                    MainActivity.mutableMainProgress.value = View.GONE
                    Log.e(TAG,it.data.toString())
                    setupClickObserver()
                }
                Status.LOADING -> {
                    Log.e(TAG,"Loading data")
                    MainActivity.mutableMainProgress.value = View.VISIBLE
                }
                Status.ERROR -> {
                    MainActivity.mutableMainProgress.value = View.GONE
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