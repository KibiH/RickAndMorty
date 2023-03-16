package com.example.rickandmorty.utils

import android.R
import android.util.Log
import android.widget.ArrayAdapter
import com.android.volley.toolbox.StringRequest
import com.example.rickandmorty.data.CharacterData
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


val url: String = "https://rickandmortyapi.com/api/character/"
var request: StringRequest = StringRequest(url,
    { string -> parseJsonData(string) }, {
        Log.e("RickAndMorty", "An error occurred")
    })

fun parseJsonData(string: String?) {
    val al = ArrayList<CharacterData>()
    try {
        val jsonObject = JSONObject(string)
        val charsArray: JSONArray = jsonObject.getJSONArray("results")
        for (i in 0 until charsArray.length()) {
            val character = charsArray.getJSONObject(i)
            val charData = CharacterData(character.getString("name"), character.getString("species"), character.getString("gender"), character.getString("image"))
            al.add(charData)
        }
//        val adapter: ArrayAdapter<*> = ArrayAdapter(this, R.layout.simple_list_item_1, al)
//        fruitsList.setAdapter(adapter)
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    Log.d("RickAndMorty", "List length = " + al.size)
    for(character in al) {
        Log.d("RickAndMorty", "Name = " + character.name)
    }
}
