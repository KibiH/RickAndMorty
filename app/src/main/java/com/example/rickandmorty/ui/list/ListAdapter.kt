package com.example.rickandmorty.ui.list

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.rickandmorty.R
import com.example.rickandmorty.data.CharacterData


class ListAdapter(private val context: Activity, private val characters: List<CharacterData>)
    : ArrayAdapter<CharacterData>(context, R.layout.three_item_layout, characters) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.three_item_layout, null, true)

        val currentCharacter: CharacterData? = getItem(position)
        val nameText = rowView.findViewById(R.id.name) as TextView
        val speciesTest = rowView.findViewById(R.id.species) as TextView
        val genderText = rowView.findViewById(R.id.gender) as TextView

        nameText.text = currentCharacter?.name
        speciesTest.text = currentCharacter?.species
        genderText.text = currentCharacter?.gender

        return rowView
    }
}