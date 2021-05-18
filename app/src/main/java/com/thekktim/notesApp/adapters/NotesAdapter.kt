package com.example.notes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.data.Notes
import com.example.notes.R

class NotesAdapter(private var listNotes : List<Notes>, var listener : OnCardItemClick) : RecyclerView.Adapter<NotesAdapter.Holder>() {


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        private var btnEdit = itemView.findViewById<ImageView>(R.id.btnEdit)
        private var btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)
        private var notesCard = itemView.findViewById<CardView>(R.id.notesCard)
        val noteTitle = itemView.findViewById<TextView>(R.id.noteTitle)!!
        val noteDescription = itemView.findViewById<TextView>(R.id.noteDescription)!!
       // val checkBox: CheckBox = itemView.findViewById<CheckBox>(R.id.checkBox)

        init{
            btnEdit.setOnClickListener(this)
            btnDelete.setOnClickListener(this)
            notesCard.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            when(v?.id) {
                R.id.btnEdit -> listener.onChangeClick(adapterPosition)
                R.id.btnDelete -> listener.onDeleteClick(adapterPosition)
                R.id.notesCard -> listener.onCardClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_cardview, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentNote: Notes = listNotes[position]
        holder.noteTitle.text = currentNote.notesTitle
        holder.noteDescription.text = currentNote.notesDescription
    }
    override fun getItemCount(): Int {
        return listNotes.size
    }

interface OnCardItemClick{
    fun onCardClick(position: Int)
    fun onChangeClick(position: Int)
    fun onDeleteClick(position: Int)
}
}