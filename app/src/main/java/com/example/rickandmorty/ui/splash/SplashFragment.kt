package com.example.rickandmorty.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.MainActivity
import com.example.rickandmorty.databinding.FragmentSplashBinding
import com.example.rickandmorty.utils.executeAsyncTask
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null

    private var uiScope = CoroutineScope(Dispatchers.Main)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val splashViewModel =
            ViewModelProvider(this).get(SplashViewModel::class.java)

        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textVersion
        splashViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // in order to continue to the main view we call the navigation form the activity
        uiScope.executeAsyncTask( onPreExecute = {
            // this is in main thread

        }, doInBackground = {
            // in background
            Thread.sleep(2000) // pause for 2 seconds
            "Result"
        }, onPostExecute = {
            // runs in main thread
            val main = activity as MainActivity
            main.moveToList()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}