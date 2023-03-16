package com.example.rickandmorty.ui.details

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentDetailsBinding
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null

    private val  detailsArgs : DetailsFragmentArgs by navArgs()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val detailsViewModel =
            ViewModelProvider(this)[DetailsViewModel::class.java]

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        detailsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topToolbar.inflateMenu(R.menu.details_menu)

        binding.topToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_back-> {
                    // Navigate back to list screen
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                    true
                }
                else -> false
            }
        }

        binding.topToolbar.title = detailsArgs.characterName
        val url = detailsArgs.imageLink
        Log.d("RickAndMorty", "Link is " + url)

        val myExecutor = Executors.newSingleThreadExecutor()
        val myHandler = Handler(Looper.getMainLooper())

        var myImage: Bitmap?
        val imageView: ImageView = binding.imageDetails
        myExecutor.execute {
            myImage = loadImage(url)
            myHandler.post {
                imageView.setImageBitmap(myImage)
            }
        }
    }

    // Function to establish connection and load image
    private fun loadImage(string: String): Bitmap? {
        val url:    URL = convertStringToURL(string)!!
        val connection: HttpURLConnection?
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)
            return BitmapFactory.decodeStream(bufferedInputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("RickAndMorty", "Error downloading " + e.message)
        }
        return null
    }

    // Function to convert string to URL
    private fun convertStringToURL(string: String): URL? {
        try {
            return URL(string)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}