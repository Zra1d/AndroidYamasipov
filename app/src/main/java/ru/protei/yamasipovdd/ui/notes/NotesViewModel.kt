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
            Note("Title 1", "Text 1"),
            Note("Title 2", "Text 2"),
            Note("Title 3", "Text 3")
        )
    )
    val notes: State<List<Note>> = _notes

    fun setSelected(note: Note?) {
        _selected.value = note
    }

    fun addNote(note: Note) {
        _notes.value = _notes.value + listOf(note)
    }
}
