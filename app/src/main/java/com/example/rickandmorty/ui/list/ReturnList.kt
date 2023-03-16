package com.example.rickandmorty.ui.list

import com.example.rickandmorty.data.CharacterData

interface ReturnList {
    fun gotList(characterList : ArrayList<CharacterData>)
}