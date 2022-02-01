package com.example.hackernews.ui.main.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.example.hackernews.data.model.Post
import com.example.hackernews.data.repository.MainRepository
import com.example.hackernews.ui.main.MainActivity
import com.example.hackernews.ui.main.adapter.PostAdapter
import com.example.hackernews.utils.EventLiveData
import com.example.hackernews.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private var _posts = MutableLiveData<Resource<List<Post>?>>()

    private var _postDetails = MutableLiveData<Post>()
    var postDetails : LiveData<Post> = _postDetails

    private var _actionDetailsPost = MutableLiveData<EventLiveData<Boolean>>()
    var actionDetailsPost: LiveData<EventLiveData<Boolean>> = _actionDetailsPost

    private var _swipedToRefresh = MutableLiveData<EventLiveData<Boolean>>()
    var swipedToRefresh: LiveData<EventLiveData<Boolean>> = _swipedToRefresh


    private val postList : MutableList <Post> = arrayListOf()



    val postsAdapter = PostAdapter {
        _postDetails.value = it
        _actionDetailsPost.value = EventLiveData(true)
    }

    val posts : LiveData<Resource<List<Post>?>>
        get () = _posts

    private fun fetchPosts(){
        viewModelScope.launch {
            repository.getNewsByDate().onEach {
                Log.e("TAG",it.data.toString())
                _posts.value = it
                it.data?.let { postList -> setupPostsList(postList) }

            }.launchIn(viewModelScope)
            _swipedToRefresh.value = EventLiveData(false)
            MainActivity.mutableMainProgress.value = View.GONE
        }

    }
    init {
        fetchPosts()
    }

    fun deletePost(position : Int){
        viewModelScope.launch {
            Log.e("TAG","deleting post")
            val post = postList[position]
            repository.deletePost(post)
            postList.remove(post)
            postsAdapter.updateData(postList)

        }
    }

    fun setStateEvent(mainStateEvent: MainStateEvent){
            when (mainStateEvent){
                is MainStateEvent.GetPostsEvent,MainStateEvent.ReloadPostsEvent -> {
                    fetchPosts()
                    Log.e("TAG","Normal event")
                }
                is MainStateEvent.None -> {
                    // nothing to do
                }
            }
    }


    private fun setupPostsList(list : List<Post>){
        postList.clear()
        for (item in list)
            postList.add(item)
        postsAdapter.updateData(postList)
    }


    sealed class MainStateEvent{
        object GetPostsEvent : MainStateEvent()
        object ReloadPostsEvent : MainStateEvent()
        object None : MainStateEvent()
    }

}