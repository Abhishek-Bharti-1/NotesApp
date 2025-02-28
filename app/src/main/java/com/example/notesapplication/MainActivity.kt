package com.example.notesapplication

import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.notesapplication.viewModels.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notesapplication.data.Note

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HomePage();
        }
    }

}

@Composable
fun HomePage(homeViewModel: HomeViewModel = viewModel()) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                }
            )
            {
                Text("+")
            }
        },

        ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            NotesList(homeViewModel.notes) { note ->
                Toast.makeText(context, "${note.title} : ${note.content}", Toast.LENGTH_LONG).show()
            }
        }

        if (showDialog) {
            AddNoteDialog(onDismiss = { showDialog = false },
                onAdd = { title , content ->
                    homeViewModel.addNote(title, content)
                    showDialog = false
                })
        }
    }
}

@Composable
fun NotesList(notes: List<Note>, onNoteClick: (Note) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        items(notes) { note ->
            Column(
                modifier = Modifier.fillMaxWidth().background(color = Color.Yellow, shape = CircleShape) .border(width = 2.dp, color = Color.Black, shape = CircleShape)
                    .padding(16.dp).clickable { onNoteClick(note) }
            ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium
            )

            }
        }
    }
}

@Composable
fun AddNoteDialog(onDismiss: () -> Unit, onAdd: (String,String) -> Unit) {
    var title by remember {
        mutableStateOf("")
    }
    var content by remember {
        mutableStateOf("")
    }
    AlertDialog(onDismissRequest = onDismiss,
        title = { Text("Add Note") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onAdd(title , content) }) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}



