package com.example.notes.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.data.Notes
import com.example.notes.R
import com.example.notes.Utils
import com.example.notes.adapters.NotesAdapter
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment(), NotesAdapter.OnCardItemClick {
    lateinit var adapter: NotesAdapter
    private val gson = Gson()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Utils.notesList.size == 0) {
            Utils.notesList.add(
                Notes(
                    "Анонс",
                    "Анонс образует простые заметки, представляющие собой превентивные сообщения о будущих всевозможных культурных мероприятиях, выставках, концертах и прочее.\n" +
                            "\nУказывается время и место будущего события. Далее сжато излагается сама его суть.",
                    Date(),
                    false
                )
            )
            Utils.notesList.add(
                Notes(
                    "Аннотация",
                    "Аннотация — это основной и первоначальный источник информации о статье или книге. И чем грамотнее и точнее вы напишите аннотацию, тем больше вероятности, что кто-нибудь захочет прочитать весь текст статьи.\n" +
                            "\n" +
                            "Вы можете потратить много времени на проведение исследований и интерпретацию результатов. Но указав в аннотации 2 — 3 общих предложения, есть риск, что статью так никто и не прочтет.\n" +
                            "Предметом отображения в данном жанре журналистики выступает определенное, уже состоявшееся информационное явление. Прежде всего, это книги и статьи.\n" +
                            "\n" +
                            "Цель заключается не столько, чтобы известить аудиторию о существовании какого-либо нового издания, сколько в том, чтобы описать кратко его качества.\n" +
                            "\n" +
                            "Пример аннотации в информационном жанре журналистики\n" +
                            "Пример заметки в виде аннотации\n" +
                            "Аннотация включает в себя характеристику основной темы, проблемы объекта, цели исследования, основные методы, результаты исследования и выводы. Здесь указывают, что нового несет в себе материал по сравнению с другими.\n" +
                            "\n" +
                            "Поэтому информация должна быть информативной, оригинальной, содержательной, структурированной и компактной. Объем как правило, от 150 до 250 слов.\n" +
                            "\n" +
                            "Важно знать, что аннотация готовится уже после написания основного текста статьи или книги.\n" +
                            "Удобнее всего писать структурированную аннотацию по структурированной статье. При этом нужно выбирать из каждого раздела статьи только самые важные сведения. Они в совокупности должны составлять полное представление об информации, изложенной в статье.\n" +
                            "\n" +
                            "В аннотацию не включают ссылки на источники из полного текста. Также не желательно использование аббревиатур и сокращений. Но если вам просто необходимо использовать какую-то аббревиатуру, тогда не забудьте раскрыть ее смысл в самой аннотации.",
                    Date(),
                    false
                )
            )
            Utils.notesList.add(
                Notes(
                    "Мини-рецензия",
                    "Мини-рецензия представляет собой оценочную, иногда рекламную заметку. Ее предметом выступает какое-то информационное явление (книга, кинофильм, пьеса и так далее).\n" +
                            "\n" +
                            "Цель публикации данного типа заключается в том, чтобы сообщить читателю о впечатлении, полученном ее автором в ходе знакомства с отображаемым предметом. При этом мнение автора не обосновывается какими-то доказательствами, а представляет собой простое изложение эмоций.\n" +
                            "\n" +
                            "Здесь не важен анализ. Здесь важны только отношения автора, его мнение и оценка. Повествование обычно идет от первого лица.",
                    Date(),
                    false
                )
            )
            Utils.notesList.add(
                Notes(
                    "Мини-история",
                    "В основе мини-истории лежит определенная интрига. Очень часто такие короткие исторические заметки публикуют женские журналы. В них рассказывают о профессиональных, любовных или семейных приключениях.",
                    Date(),
                    false
                )
            )

        }


        val recyclerView = view.findViewById<RecyclerView>(R.id.recycle)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        adapter = NotesAdapter(Utils.notesList, this)
        recyclerView.adapter = adapter
    }


    override fun onCardClick(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable(Utils.KEY, Utils.notesList[position])

        val fragment = DetailsFragment()
        fragment.arguments = bundle

        Utils.notesList[position].notesSelected = true

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()

    }

    override fun onChangeClick(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable(Utils.KEY, Utils.notesList[position])

        val fragment = EditNotesFragment()
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDeleteClick(position: Int) {
        val sharedPreference = context?.getSharedPreferences("list", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        Utils.notesList.removeAt(position)
        val userNotesString = gson.toJson(Utils.notesList)
        editor?.putString("list", userNotesString)
        editor?.apply()
        adapter.notifyDataSetChanged()
    }
}
