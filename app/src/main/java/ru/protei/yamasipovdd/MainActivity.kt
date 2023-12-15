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
import ru.protei.yamasipovdd.ui.theme.YamasipovDDTheme
import androidx.compose.runtime.*
import androidx.compose.foundation.text.*
import androidx.compose.foundation.lazy.items
import ru.protei.yamasipovdd.NotesViewModel
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize

class MainActivity : ComponentActivity() {
    private val notesViewModel: NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            YamasipovDDTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Используйте notesViewModel.notes.value для получения списка заметок
                    NoteList(notesViewModel.notes.value, notesViewModel)

                    // Кнопка для добавления новой заметки
                    Button(
                        onClick = {
                            notesViewModel.onAddNoteClicked()
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Добавить заметку")
                    }


                }
            }
        }
    }
}

@Composable
fun EditNote(
    note: Note,
    onEditComplete: () -> Unit,
    notesViewModel: NotesViewModel
) {
    var editedTitle by remember { mutableStateOf(note.title) }
    var editedText by remember { mutableStateOf(note.text) }

    // Элементы управления для редактирования заметки
    BasicTextField(
        value = editedTitle,
        onValueChange = { editedTitle = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )

    BasicTextField(
        value = editedText,
        onValueChange = { editedText = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )

    // Кнопка для завершения редактирования
    Button(
        onClick = {
            // Обновляем выбранную заметку
            notesViewModel.onNoteChange(editedTitle, editedText)
            onEditComplete()
        },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Завершить редактирование")
    }
}
@Composable
fun NoteList(notes: List<Note>, notesViewModel: NotesViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(notes.size) { index ->
            val note = notes[index]
            NoteItem(
                note,
                onNoteClick = {
                    println("Click on note from NoteList: $note") // Добавляем вывод в консоль
                    notesViewModel.onNoteSelected(note)
                },
                isExpanded = note == notesViewModel.selected.value,
                notesViewModel
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun NoteItem(
    note: Note,
    onNoteClick: () -> Unit,
    isExpanded: Boolean,
    notesViewModel: NotesViewModel
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(all = 20.dp)
            .clickable {
                println("Click on note: $note") // Добавляем вывод в консоль
                onNoteClick()
            }
    ) {
        Column {
            Text(text = "${note.title}", fontSize = 30.sp)
            Spacer(modifier = Modifier.height(8.dp))

            var isNoteExpanded by remember { mutableStateOf(isExpanded) }
            val surfaceColor by animateColorAsState(
                if (isNoteExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            )

            Column(
                modifier = Modifier.clickable {
                    println("Click on note: $note") // Добавляем вывод в консоль
                    onNoteClick() // Используем onNoteClick без условий
                    isNoteExpanded = !isNoteExpanded
                }
            ) {
                Text(
                    text = note.text,
                    modifier = Modifier
                        .padding(all = 4.dp)
                        .animateContentSize(),
                    maxLines = if (isNoteExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
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
    val notesViewModel = NotesViewModel() // Создаем экземпляр NotesViewModel
    NoteList(noteList, notesViewModel)
}

