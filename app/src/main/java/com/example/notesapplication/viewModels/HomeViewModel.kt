package com.example.notesapplication.viewModels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.notesapplication.data.Note

class HomeViewModel : ViewModel() {

    private val _notes = mutableStateListOf(
        Note(title = "Note 1", content = "this is a note"),
        Note(title = "Note 2", content = "this is a note"),
        Note(title = "Note 3", content = "this is a note"),
    )
    val notes : List<Note> get() = _notes

    fun addNote(title: String, content: String){
        if(title.isNotBlank() && content.isNotBlank()){
            _notes.add(Note(title , content))
        }
    }
}