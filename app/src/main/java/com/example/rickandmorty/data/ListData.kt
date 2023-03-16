package com.example.rickandmorty.data

import android.util.Log
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ListData {
    private val url: String = "https://rickandmortyapi.com/api/character/"

    var newUrl : String? = url
    private var characterList : ArrayList<CharacterData> = ArrayList()
    var mCallback : ListChangedCallback? = null
    var nextUrl : String? = null

    fun resetList() {
        newUrl = url
        characterList.clear()
    }

    fun getCharacterList() : ArrayList<CharacterData> {
        return characterList
    }
    fun setCallback(callback : ListChangedCallback) {
       mCallback = callback
    }

    fun parseJsonData(string: String?) {
        try {
            val jsonObject = JSONObject(string!!)
            val charsArray: JSONArray = jsonObject.getJSONArray("results")
            for (i in 0 until charsArray.length()) {
                val character = charsArray.getJSONObject(i)
                val charData = CharacterData(character.getString("name"), character.getString("species"), character.getString("gender"), character.getString("image"))
                characterList.add(charData)
            }

            val infoObject = jsonObject.getJSONObject("info")
            nextUrl = infoObject.getString("next")

       } catch (e: JSONException) {
            e.printStackTrace()
        }

        Log.d("RickAndMorty", "List length = " + characterList.size)
        for(character in characterList) {
            Log.d("RickAndMorty", "Name = " + character.name)
        }
        Log.d("RickAndMorty", "Next url = " + nextUrl)
        mCallback?.listAvailable(characterList, nextUrl)
    }

}