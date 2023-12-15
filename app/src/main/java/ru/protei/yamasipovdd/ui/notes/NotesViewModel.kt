package ru.protei.yamasipovdd

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.protei.yamasipovdd.domain.Note

class NotesViewModel : ViewModel() {
    private val _selected = mutableStateOf<Note?>(null)
    val selected: State<Note?> = _selected

    private val _notes = mutableStateOf<List<Note>>(
        listOf(
            Note("Заголовок 1", "Текст 1"),
            Note("Заголовок 2", "Текст 2"),
            Note("Заголовок 3", "Текст 3")
        )
    )
    val notes: State<List<Note>> = _notes

    fun setSelected(note: Note?) {
        _selected.value = note
    }

    fun addNote(note: Note) {
        _notes.value = _notes.value + listOf(note)
        _selected.value = note
    }

    fun onNoteChange(title: String, text: String) {
        _selected.value?.let { editedNote ->
            _selected.value = editedNote.copy(title = title, text = text)
        }
    }

    fun onNoteSelected(note: Note) {
        println("onNoteSelected: $note")
        _selected.value = note
    }

    fun onAddNoteClicked() {
        val newNote = Note("Новый Заголовок", "Новый Текст")
        addNote(newNote)
    }
}
