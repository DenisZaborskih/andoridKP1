package com.example.prilozhuha

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import java.io.File
import java.lang.Exception
import kotlin.system.exitProcess

class ClientMActivity : Activity(){
    lateinit var profButton : Button
    lateinit var schedButton: Button
    lateinit var scrollView : ScrollView
    lateinit var lineView : LinearLayout
    lateinit var textArr : Array<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_client_act)
        scrollView = findViewById(R.id.scrollClient)
        lineView = findViewById(R.id.scrollLinear)
        textArr = emptyArray()
        val login = intent.getStringExtra("LoginC")
        val file = File("/data/data/com.example.prilozhuha/files/Anketas.txt")
        try {
            for (line in file.readLines()) {
                var tempArr : Array<String> = emptyArray()
                var tempStr = ""
                for(letter in line){
                    if(letter != '.') {
                        tempStr += letter
                    }
                    else {
                        tempArr += tempStr
                        Log.d("textArr", tempStr)
                        tempStr = ""
                    }
                }
                textArr += tempArr
            }
        }
        catch(e: Exception){
            Toast.makeText(this,"Преподаватели не найдены", Toast.LENGTH_SHORT).show()
        }

        if(textArr.isNotEmpty()){
            for(str in textArr){
                val tmpTextView = TextView(this)
                val tmpString = "Должность: ${str[1]}\nСтаж: ${str[2]} \nЦена: ${str[3]}"
                tmpTextView.text = tmpString
                tmpTextView.setOnClickListener(){
                    val intentAnketa = Intent(this, TeachAnkActivity::class.java)
                    intentAnketa.putExtra("Login", str[0])
                    intentAnketa.putExtra("Client", login)
                    startActivity(intentAnketa)
                }
                tmpTextView.setTextColor(Color.BLACK)
                tmpTextView.setBackgroundColor(Color.LTGRAY)
                tmpTextView.textSize=35F
                lineView.addView(tmpTextView)
                val lineTV = TextView(this)
                lineTV.setBackgroundColor(Color.BLACK)
                lineTV.textSize = 1F
                lineTV.text = " "
                lineView.addView(lineTV)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        schedButton = findViewById(R.id.scheduleButton)
        profButton = findViewById(R.id.profileButton)
        val login = intent.getStringExtra("LoginC")

        schedButton.setOnClickListener(){
            Log.e("SchedButton","Pressed")
            val intentSched = Intent(this,scheduleActivity::class.java)
            intentSched.putExtra("Login", login)
            startActivity(intentSched)
        }

        profButton.setOnClickListener(){
            Log.e("profButton","Pressed")
            val intentProf = Intent(this, profileActivity::class.java)
            intentProf.putExtra("Login", login)
            startActivity(intentProf)
        }
    }

    override fun onDestroy() {
        moveTaskToBack(true)
        super.onDestroy()
        exitProcess(0)
    }
}