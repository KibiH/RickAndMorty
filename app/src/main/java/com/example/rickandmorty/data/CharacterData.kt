package com.example.rickandmorty.data

data class CharacterData(
    var name: String? = null,
    var species: String? = null,
    var gender: String? = null,
    var image: String? = null) {
    }

val boring = CharacterData("Kibi", "Jew", "Male", "https://rickandmortyapi.com/api/character/avatar/1.jpeg")

