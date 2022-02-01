package com.example.hackernews.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.hackernews.R
import com.example.hackernews.databinding.FragmentPostDetailsBinding
import com.example.hackernews.ui.base.BaseFragment
import com.example.hackernews.utils.viewBinding

class DetailsFragment : BaseFragment() {
    private val args : DetailsFragmentArgs by navArgs()

    private val binding by viewBinding(FragmentPostDetailsBinding::bind)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        binding.webview.apply {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url != null) {
                        view?.loadUrl(url)
                    }
                    return true
                }
            }
            settings.javaScriptEnabled = true
            loadUrl(args.postUrl.toString())
        }
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}