package com.example.sampleapp.ui.main

import androidx.fragment.app.Fragment
import com.example.sampleapp.databinding.FragmentWebviewBinding
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.sampleapp.AppUtil
import com.example.sampleapp.AppUtil.AUTH_PREFIX
import com.example.sampleapp.AppUtil.BASE_URL
import com.example.sampleapp.AppUtil.CLIENT_ID
import com.example.sampleapp.AppUtil.LOGOUT_URL
import com.example.sampleapp.AppUtil.OATH_LINK
import com.example.sampleapp.AppUtil.POSTFIX
import com.example.sampleapp.AppUtil.REDIRECT_PREFIX
import com.example.sampleapp.AppUtil.REDIRECT_URI
import com.example.sampleapp.R
import com.example.sampleapp.extension.getCodeFromRedirectUrl
import java.util.logging.Level
import java.util.logging.Logger


class WebviewFragment : Fragment() {

    lateinit var binding: FragmentWebviewBinding


    private val apiViewModel: InsApiViewModel by lazy {
        ViewModelProvider(this).get(InsApiViewModel::class.java)
    }

    private fun getRequestUrl(): String {
       return BASE_URL + OATH_LINK + CLIENT_ID +
                REDIRECT_PREFIX + REDIRECT_URI + POSTFIX
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_webview, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = apiViewModel
        binding.webView.settings.javaScriptEnabled = true
        setWebViewClient()

        binding.webView.loadUrl(getRequestUrl())

        apiViewModel.navigateToMasterFragment.observe(viewLifecycleOwner, Observer {
            if (it) {
                view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_webviewFragment_to_masterFragment) }
            }
        })

        apiViewModel.errorMessage.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                binding.textViewRedirect.text = it
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setWebViewClient() {
        binding.webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                Logger.getLogger("WebviewFragment").log(Level.INFO, "shouldOverrideUrlLoading: $url")

                if (url.startsWith("redirect_url")) {
                    return true
                }
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String) {
                binding.webView.visibility = View.VISIBLE
                super.onPageFinished(view, url)
                Logger.getLogger("WebviewFragment").log(Level.INFO, "onPageFinished: $url")

                if (url.contains(AUTH_PREFIX)) {
                    val code = url.getCodeFromRedirectUrl()
                    Logger.getLogger("WebviewFragment").log(Level.INFO, "code: $code")

                    binding.webView.visibility = View.INVISIBLE
                    binding.textViewRedirect.visibility = View.VISIBLE

                    apiViewModel.getAuthToken(code)
                }

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                AppUtil.resetLoginResponse()
                binding.webView.loadUrl(LOGOUT_URL)
                view?.let { Navigation.findNavController(it).navigate(R.id.action_webviewFragment_to_loginFragment) }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }
}

