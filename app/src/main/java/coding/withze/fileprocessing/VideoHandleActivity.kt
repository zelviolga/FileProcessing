package coding.withze.fileprocessing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coding.withze.fileprocessing.databinding.ActivityVideoHandleBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

class VideoHandleActivity : AppCompatActivity() {

    private var mPlayer: ExoPlayer? = null
    private val videoURL =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    lateinit var binding : ActivityVideoHandleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoHandleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initExoPlayer()
    }

    private fun initExoPlayer() {
        // Create a player instance.
        mPlayer = ExoPlayer.Builder(this).build()

        // Bind the player to the view.
        binding.playerView.player = mPlayer

        //setting exoplayer when it is ready.
        mPlayer!!.playWhenReady = true

        // Set the media source to be played.
        mPlayer!!.setMediaSource(buildMediaSource())

        // Prepare the player.
        mPlayer!!.prepare()
    }

    private fun releasePlayer() {
        if (mPlayer == null) {
            return
        }
        //release player when done
        mPlayer!!.release()
        mPlayer = null
    }

    //creating mediaSource
    private fun buildMediaSource(): MediaSource {
        // Create a data source factory.
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

        // Create a progressive media source pointing to a stream uri.
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoURL))

        return mediaSource
    }
}