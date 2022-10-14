package coding.withze.fileprocessing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coding.withze.fileprocessing.databinding.ActivityPdfHandleBinding

class PdfHandleActivity : AppCompatActivity() {

    lateinit var binding : ActivityPdfHandleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfHandleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonWebView.setOnClickListener {
                val intent = Intent(this@PdfHandleActivity, WebViewActivity::class.java)
                startActivity(intent)
            }

            buttonAssets.setOnClickListener {
                val intent = Intent(this@PdfHandleActivity, PdfViewActivity::class.java)
                intent.putExtra("ViewType", "assets")
                startActivity(intent)
            }

            buttonStorage.setOnClickListener {
                val intent = Intent(this@PdfHandleActivity, PdfViewActivity::class.java)
                intent.putExtra("ViewType", "storage")
                startActivity(intent)
            }

            buttonInternet.setOnClickListener {
                val intent = Intent(this@PdfHandleActivity, PdfViewActivity::class.java)
                intent.putExtra("ViewType", "internet")
                startActivity(intent)
            }
        }


    }
}