package ru.protei.yamasipovdd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.protei.yamasipovdd.domain.Note
import androidx.activity.viewModels
import ru.protei.yamasipovdd.ui.notes.NotesViewModel
import ru.protei.yamasipovdd.ui.theme.YamasipovDDTheme

class MainActivity : ComponentActivity() {
    private val notesViewModel: NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Создаем тестовый список Note
        val noteList = listOf(
            Note("Лабы", "БД8, БД9"),
            Note("Андроид", "Сделать спринт 2"),
            Note("Заметка", "Заметка заметка")
        )

        setContent {
            YamasipovDDTheme {
                // Отображаем список Note с использованием LazyColumn
                NoteList(noteList)
            }
        }
    }
}

/*@Composable
fun NoteList(notes: List<Note>) {
    // Используем LazyColumn для создания вертикального списка
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Отображаем каждую заметку в отдельной ячейке списка
        items(notes.size) { index ->
            val note = notes[index]
            NoteItem(note)
            Spacer(modifier = Modifier.height(16.dp)) // Добавляем промежуток между заметками
        }
    }
}*/
@Composable
fun NoteList(notes: List<Note>) {
    // Используем LazyColumn для создания вертикального списка
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(6.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Отображаем каждую заметку в отдельной ячейке списка
        items(notes.size) { index ->
            val note = notes[index]
            NoteItem(note)
            Spacer(modifier = Modifier.height(16.dp)) // Добавляем промежуток между заметками
        }
    }
}

@Composable
fun NoteItem(note: Note) {
    // Отображаем заголовок и текст каждой заметки
    Card(
        colors =CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(all = 8.dp)
            .clickable { notesViewModel.setSelected(note) }

    ) {
        Column {
            Text(text = "${note.title}", fontSize = 30.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${note.text}", fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteListPreview() {
    val noteList = listOf(
        Note("Preview Title 1", "Preview Text 1"),
        Note("Preview Title 2", "Preview Text 2"),
        Note("Preview Title 3", "Preview Text 3")
    )
    NoteList(noteList)
}


