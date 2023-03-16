package com.example.rickandmorty.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <R> CoroutineScope.executeAsyncTask(
    onPreExecute: () -> Unit,
    doInBackground: () -> R,
    onPostExecute: (R) -> Unit
) = launch {
    onPreExecute() //runs in main thread
    val result = withContext(Dispatchers.IO) {
        doInBackground() // runs in background
    }
    onPostExecute(result)
}
