package coding.withze.fileprocessing

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import coding.withze.fileprocessing.databinding.ActivityPdfViewBinding
import coding.withze.fileprocessing.utils.Utils
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import java.io.File

class PdfViewActivity : AppCompatActivity() {

    lateinit var binding : ActivityPdfViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PRDownloader.initialize(applicationContext)
        checkPdfAction(intent)
    }

    private fun checkPdfAction(intent: Intent) {
        when (intent.getStringExtra("ViewType")) {
            "assets" -> {
                // perform action to show pdf from assets
                showPdfFromAssets(Utils.getPdfNameFromAssets())
            }
            "storage" -> {
                // perform action to show pdf from storage
                selectPdfFromStorage()
            }
            "internet" -> {
//                 perform action to show pdf from the internet
                binding.progressBar.visibility = View.VISIBLE
                val fileName = "myFile.pdf"
                downloadPdfFromInternet(
                    Utils.getPdfUrl(),
                    Utils.getRootDirPath(this),
                    fileName
                )
            }
        }
    }

//    ASSETS
    fun showPdfFromAssets(pdfName : String){
        binding.pdfView.fromAsset(pdfName)
            .password(null) // if password protected, then write password
            .defaultPage(0) // set the default page to open
            .onPageError { page, _ ->
                Toast.makeText(
                    this@PdfViewActivity,
                    "Error at page: $page", Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }

//    LOCAL STORAGE

    private fun selectPdfFromStorage() {
//        Toast.makeText(this, "selectPDF", Toast.LENGTH_LONG).show()
        val browseStorage = Intent(Intent.ACTION_GET_CONTENT)
        browseStorage.type = "application/pdf"
        browseStorage.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(
            Intent.createChooser(browseStorage, "Select PDF"), 99
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 99 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedPdfFromStorage = data.data
            showPdfFromUri(selectedPdfFromStorage)
        }
    }

    private fun showPdfFromUri(uri: Uri?) {
        binding.pdfView.fromUri(uri)
            .defaultPage(0)
            .spacing(10)
            .load()
    }

//    INTERNET
    private fun downloadPdfFromInternet(url: String, dirPath: String, fileName: String) {
        PRDownloader.download(
            url,
            dirPath,
            fileName
        ).build()
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    Toast.makeText(this@PdfViewActivity, "downloadComplete", Toast.LENGTH_LONG)
                        .show()
                    val downloadedFile = File(dirPath, fileName)
                    binding.progressBar.visibility = View.GONE
                    showPdfFromFile(downloadedFile)
                }

                override fun onError(error: Error?) {
                    Toast.makeText(
                        this@PdfViewActivity,
                        "Error in downloading file : $error",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            })
    }

    private fun showPdfFromFile(file: File) {
        binding.pdfView.fromFile(file)
            .password(null)
            .defaultPage(0)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .onPageError { page, _ ->
                Toast.makeText(
                    this@PdfViewActivity,
                    "Error at page: $page", Toast.LENGTH_LONG
                ).show()
            }
            .load()
    }

//    get root path
    fun getRootDirPath(context: Context): String {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val file: File = ContextCompat.getExternalFilesDirs(
                context.applicationContext,
                null
            )[0]
            file.absolutePath
        } else {
            context.applicationContext.filesDir.absolutePath
        }
    }
}