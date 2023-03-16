package com.example.rickandmorty

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.rickandmorty.data.CharacterData
import com.example.rickandmorty.data.ListChangedCallback
import com.example.rickandmorty.data.ListData
import com.example.rickandmorty.databinding.ActivityMainBinding
import com.example.rickandmorty.ui.list.ListFragmentDirections
import com.example.rickandmorty.ui.list.ReturnList
import com.example.rickandmorty.ui.splash.SplashFragmentDirections

class MainActivity : ListChangedCallback, AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mNavController: NavController

    private var listData : ListData? = null
    private var returnList : ReturnList?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listData = ListData()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)
                    as NavHostFragment

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mNavController = navHostFragment.navController
    }

    fun moveToList() {
        val action = SplashFragmentDirections.actionNavigationSplashToNavigationList()
        mNavController.navigate(action)
    }

    fun moveToDetails(character : CharacterData) {
        val action = ListFragmentDirections.actionNavigationListToNavigationDetails(character.image!!, character.name!!)
        mNavController.navigate(action)
    }

    fun updateList(returnListInt : ReturnList) {
        if (listData?.newUrl.isNullOrEmpty()) {
            Log.d("RickAndMorty", "End of the list!!")
            if (mNavController.currentDestination?.id == R.id.navigation_list) {
                // update the list
                returnList?.gotList(null)
            }
            return
        }
        returnList = returnListInt
        listData?.setCallback(this)
        val rQueue = Volley.newRequestQueue(this@MainActivity)
        Log.d("RickAndMorty", "About to pull from " + listData?.newUrl)

        val request = StringRequest(listData?.newUrl,
            { string -> listData?.parseJsonData(string) }, {
                Log.e("RickAndMorty", "An error occurred")
            })

        rQueue.add(request)
    }

    fun clearList() {
        listData?.resetList()
    }

    override fun listAvailable(charList: ArrayList<CharacterData>, nextUrl: String?) {
        // we got back a list, now update the list view
        Log.d("RickAndMorty", "current id = " + mNavController.currentDestination?.id)
        Log.d("RickAndMorty", "My fragment nav id = " + R.id.navigation_list)

        if (mNavController.currentDestination?.id == R.id.navigation_list) {
            // update the list
            returnList?.gotList(charList)
        }
        listData?.newUrl = nextUrl

    }
}