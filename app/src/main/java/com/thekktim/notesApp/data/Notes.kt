package com.example.notes.data

import java.io.Serializable
import java.util.*

data class Notes(var notesTitle: String, var notesDescription: String, var notesDate: Date, var notesSelected: Boolean): Serializable