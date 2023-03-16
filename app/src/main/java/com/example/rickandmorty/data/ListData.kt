package com.example.rickandmorty.data

import android.util.Log
import com.android.volley.toolbox.StringRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ListData {
    val url: String = "https://rickandmortyapi.com/api/character/"
    private var characterList : ArrayList<CharacterData> = ArrayList()
    var mCallback : ListChangedCallback? = null

    public var request: StringRequest = StringRequest(url,
        { string -> parseJsonData(string) }, {
            Log.e("RickAndMorty", "An error occurred")
        })

    fun resetList() {
        characterList.clear()
    }

    fun getCharacterList() : ArrayList<CharacterData> {
        return characterList
    }
    fun setCallback(callback : ListChangedCallback) {
       mCallback = callback
    }

    private fun parseJsonData(string: String?) {
        try {
            val jsonObject = JSONObject(string)
            val charsArray: JSONArray = jsonObject.getJSONArray("results")
            for (i in 0 until charsArray.length()) {
                val character = charsArray.getJSONObject(i)
                val charData = CharacterData(character.getString("name"), character.getString("species"), character.getString("gender"), character.getString("image"))
                characterList.add(charData)
            }

       } catch (e: JSONException) {
            e.printStackTrace()
        }

        Log.d("RickAndMorty", "List length = " + characterList.size)
        for(character in characterList) {
            Log.d("RickAndMorty", "Name = " + character.name)
        }
        mCallback?.listAvailable(characterList)
    }

}