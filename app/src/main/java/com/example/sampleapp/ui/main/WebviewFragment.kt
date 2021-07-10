package com.example.sampleapp.ui.main

import androidx.fragment.app.Fragment
import com.example.sampleapp.databinding.FragmentWebviewBinding
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.sampleapp.AppUtil.CLIENT_ID
import com.example.sampleapp.R
import com.example.sampleapp.extension.getCodeFromRedirectUrl
import java.util.logging.Level
import java.util.logging.Logger


class WebviewFragment : Fragment() {

    lateinit var binding: FragmentWebviewBinding
    private var isContentload = false
    private lateinit var requestUrl: String
    private val authPrefix = "https://sampleapp.com/oauth?code="


    private val apiViewModel: InsApiViewModel by lazy {
        ViewModelProvider(this).get(InsApiViewModel::class.java)
    }

    private fun initRequestUrl() {
        requestUrl = context?.resources?.getString(R.string.base_url) +
                "oauth/authorize/?client_id=" + CLIENT_ID +
                "&redirect_uri=" + context?.resources?.getString(R.string.redirect_uri) +
                "&response_type=code&scope=user_profile,user_media"
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        initRequestUrl()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_webview, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = apiViewModel


        binding.webView.settings.javaScriptEnabled = true
        setWebViewClient()

        binding.webView.loadUrl(requestUrl)

        apiViewModel.navigateToMasterFragment.observe(viewLifecycleOwner, Observer {
            if (it) {
                view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_webviewFragment_to_masterFragment) }
            }
        })

        apiViewModel.errorMessage.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    private fun setWebViewClient() {
        binding?.webView?.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                Logger.getLogger("WebviewFragment").log(Level.INFO, "shouldOverrideUrlLoading")

                if (url.startsWith("redirect_url")) {
                    Logger.getLogger("WebviewFragment").log(Level.INFO, "shouldOverrideUrlLoading true")
                    return true
                }
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String) {
                binding.webView.visibility = View.VISIBLE
                isContentload = true
                super.onPageFinished(view, url)

                if (url.contains(authPrefix)) {

                    val code = url.getCodeFromRedirectUrl()
                    Logger.getLogger("WebviewFragment").log(Level.INFO, "code: $code")

                    binding.webView.visibility = View.INVISIBLE
                    binding.textViewRedirect.visibility = View.VISIBLE

                    apiViewModel.getAuthToken(code)
                }

            }
        }
    }
}

