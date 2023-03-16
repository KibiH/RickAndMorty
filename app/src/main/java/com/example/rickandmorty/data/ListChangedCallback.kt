package com.example.rickandmorty.data

interface ListChangedCallback {
    fun listAvailable(charList: ArrayList<CharacterData>)
}