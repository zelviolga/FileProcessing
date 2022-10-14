package coding.withze.fileprocessing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import coding.withze.fileprocessing.databinding.ActivityWebViewBinding
import coding.withze.fileprocessing.utils.Utils

class WebViewActivity : AppCompatActivity() {

    lateinit var binding : ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            pdfWebView.webViewClient = WebViewClient()
            pdfWebView.settings.setSupportZoom(true)
            pdfWebView.settings.javaScriptEnabled = true
            val url = Utils.getPdfUrl()
            pdfWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=$url")
        }


    }
}