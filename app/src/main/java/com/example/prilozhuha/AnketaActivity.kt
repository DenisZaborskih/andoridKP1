package com.example.prilozhuha

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import java.io.File
import java.lang.Exception

class AnketaActivity : Activity() {
    lateinit var price : EditText
    lateinit var stage : EditText
    lateinit var job : EditText
    lateinit var startButton : Button
    lateinit var file : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.anketa_activity)
    }

    override fun onResume() {
        super.onResume()
        price = findViewById(R.id.editTextPrice)
        stage = findViewById(R.id.editTextStage)
        job = findViewById(R.id.editTextJob)
        startButton = findViewById(R.id.buttonRegAnketa)
        file = File("/data/data/com.example.prilozhuha/files/Anketas.txt")
        var anketaString : String
        val login = intent.getStringExtra("Login")


        startButton.setOnClickListener(){
            while(true){
                if(price.text.toString().isEmpty() || stage.text.toString().isEmpty() || job.text.toString().isEmpty()){
                    Toast.makeText(this,"Что-то не заполнено. Заполните!", Toast.LENGTH_SHORT).show()
                    break
                }
                else {
                    anketaString = ("${login}.${job.text}.${stage.text}.${price.text}.\n")
                    try {
                        file.appendText(anketaString)
                    }
                    catch(e: Exception){
                        file.writeText("")
                        file.appendText(anketaString)
                    }
                    val intent = Intent(this, TeachMActivity::class.java)
                    intent.putExtra("LoginT", login)
                    startActivity(intent)
                    break
                }
            }
        }
    }

}