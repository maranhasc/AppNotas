package net.azarquiel.note.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.google.gson.Gson
import net.azarquiel.note.R
import net.azarquiel.note.model.Note
import java.text.SimpleDateFormat
import java.util.Date

class DetailActivity : AppCompatActivity() {
    private lateinit var ettitulo: EditText
    private lateinit var etdescription: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ettitulo = findViewById<EditText>(R.id.ettitulo)
        etdescription = findViewById<EditText>(R.id.etdescription)
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()
            }
        })
    }

    private fun onBack() {
        if (ettitulo.text.toString().isEmpty() || etdescription.text.toString().isEmpty() ) {
            Toast.makeText(this, "No saved. All required...", Toast.LENGTH_SHORT).show()
        }
        else {
            val fecha = Date()
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
            val fechaString = sdf.format(fecha)
            val nota = Note(-1, ettitulo.text.toString(), etdescription.text.toString(), fechaString )
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("nota", nota)
            setResult(Activity.RESULT_OK,intent) //volvemos a la MainActivity
            finish() //cerramos la activity actual DetailActivity
        }


    }


}