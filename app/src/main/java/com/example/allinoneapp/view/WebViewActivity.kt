package com.example.allinoneapp.view

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.allinoneapp.databinding.ActivityWebViewBinding
import android.content.DialogInterface
import com.example.allinoneapp.utilClass.ConnectionStatus


class WebViewActivity : AppCompatActivity() {
    lateinit var binding: ActivityWebViewBinding
    var searchKey:String="key"
    var myWebView: WebView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.visibility=View.INVISIBLE
//        searchKey = binding.txtSearchWord.text.toString()
        binding.txtSearchWord.visibility= View.GONE
         myWebView = binding.webView
        myWebView!!.webViewClient = WebViewClient()

        var isConnected :Boolean= ConnectionStatus.call(this)

        if(isConnected) {
            myWebView!!.loadUrl("mistershoppie.com")
        }
        else{
            netAlert()

        }

//        binding.btnSearch.setOnClickListener {
//             var isConnected :Boolean=ConnectionStatus.call(this)
//            if(isConnected) {
//                binding.btnSearch.visibility=View.INVISIBLE
//                binding.webView.visibility=View.VISIBLE
//                myWebView.loadUrl("mistershoppie.com")
//            }
//            else{
//                binding.btnSearch.visibility=View.VISIBLE
//                binding.webView.visibility=View.INVISIBLE
//            }
//        }

    }

    private fun netAlert() {
        AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_dialog_alert)
            .setTitle("Internet Connection Alert")
            .setMessage("Please Check Your Internet Connection")
            .setPositiveButton("Tray Again",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    var isConnected :Boolean= ConnectionStatus.call(this)
                    if(isConnected) {
                        myWebView!!.webViewClient = WebViewClient()
                        myWebView!!.loadUrl("mistershoppie.com")

                    }else{
                        netAlert()
                    }

                }).show()
    }

}