package com.example.rickandmorty.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.data.CharacterData

class ListViewModel : ViewModel() {

    private val _list = MutableLiveData<ArrayList<CharacterData>>().apply {
//        value = "This is dashboard Fragment"
    }
    val list: LiveData<ArrayList<CharacterData>> = _list
}