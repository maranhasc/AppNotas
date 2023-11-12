package net.azarquiel.note.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import net.azarquiel.note.R
import net.azarquiel.note.adapter.CustomAdapter
import net.azarquiel.note.model.Note
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var launcherDetail : ActivityResultLauncher<Intent>
    private lateinit var notas: ArrayList<Note>
    private var contador: Int = -1
    private lateinit var adapter: CustomAdapter
    private lateinit var notasSH: SharedPreferences
    private lateinit var contadorSH: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Registramos la vuelta de la segunda activity Detail
        // Esta nos va a devolver la nota tecleada por el usuario
        launcherDetail = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // unwrapper var (desenvolvemos la variable) susceptible de ser null
                result.data?.let {
                    // ya estamos de vuelta y comprobamos si nos regreso algo
                    val nota = it.getSerializableExtra("nota") as Note
                    nota.id = incrementarContador()
                    addNote(nota) // save SH y refresh recycler
                }
            }
        }
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { onclickfab() }

        notasSH = getSharedPreferences("notas", Context.MODE_PRIVATE)
        contadorSH = getSharedPreferences("contador", Context.MODE_PRIVATE)
        getContador()

        initRV()
        getAllNotas()
        adapter.setNotas(notas)
    }

    private fun onclickfab() {
// Abrir la segunda Activity (startActivityForResult antiguo)
        val intent = Intent(this,DetailActivity::class.java)
        launcherDetail.launch(intent)
    }

    private fun initRV() {
        val rvnotas = findViewById<RecyclerView>(R.id.rvnotas)
        adapter = CustomAdapter(this, R.layout.rownota)
        rvnotas.adapter = adapter
        rvnotas.layoutManager = LinearLayoutManager(this)
    }

    private fun getAllNotas() {
        val listaAll = notasSH.all
        notas = ArrayList<Note>()
        for ((key,value) in listaAll) {
            val jsonProducto = value.toString()
            val nota = Gson().fromJson(jsonProducto, Note::class.java)
            notas.add(nota)
        }
    }
    private fun addNote(nota: Note) {
        // save SH
        val editor = notasSH.edit()
        val notajson = Gson().toJson(nota)
        editor.putString(nota.id.toString(), notajson)
        editor.commit()
        // refresh Recycler
        notas.add(0, nota)  // ver
        adapter.setNotas(notas)
    }

    private fun incrementarContador(): Int {
        contador+=1

        val edit = contadorSH.edit()
        edit.putInt("c", contador)
        edit.commit()
        return contador
    }

    private fun getContador() {
        contador = contadorSH.getInt("c", -1)
        if (contador==-1) contador = 0
    }


}