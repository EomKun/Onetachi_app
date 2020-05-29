package com.example.onetachi.QR

import android.net.http.SslError
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.example.onetachi.R
import kotlinx.android.synthetic.main.activity_web_view_q_r.*


class WebViewQR : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_q_r)

        qrWebView.apply {
            settings.javaScriptEnabled = true //자바스크립트 인식되도록 변경
//            webViewClient= WebViewClient()
        }

        //SSL 인증 우회를 위해 필요한 부분
        class SSlWebViewConnect:WebViewClient(){
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler?.proceed()
            }
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                qrWebView.loadUrl("https://10.0.2.2:3000/")
                return true
            }
        }
        qrWebView.setWebViewClient(SSlWebViewConnect())


        qrWebView.loadUrl("https://10.0.2.2:3000/")
    }
}