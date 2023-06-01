package com.example.prilozhuha

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.android.material.shape.MaterialShapeDrawable
import java.io.File
import kotlin.system.exitProcess

class TeachMActivity : Activity() {
    lateinit var profButton : Button
    lateinit var schedButton: Button
    lateinit var scrollView : ScrollView
    lateinit var lineView : LinearLayout
    lateinit var questArr : Array<Array<String>>
    lateinit var userArr : Array<Array<String>>
    lateinit var questFile : File
    lateinit var userFile : File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_teach_act)
        scrollView = findViewById(R.id.scrollTeacher)
        lineView = findViewById(R.id.scrollTeacherLinear)
        questArr = emptyArray()
        userArr = emptyArray()
        val login = intent.getStringExtra("LoginT")
        questFile = File("/data/data/com.example.prilozhuha/files/quest.txt")
        userFile = File("/data/data/com.example.prilozhuha/files/text.txt")


        for (line in userFile.readLines()) {
            var tempArr : Array<String> = emptyArray()
            var tempStr = ""
            if(line.contains("Студент")) {
                for (letter in line) {
                    if (letter != '.') {
                        tempStr += letter
                    } else {
                        tempArr += tempStr
                        Log.d("userArr", tempStr)
                        tempStr = ""
                    }
                }
                userArr += tempArr
            }
        }

        try{
            for (line in questFile.readLines()) {
                var tempArr : Array<String> = emptyArray()
                var tempStr = ""
                for(letter in line){
                    if(letter != '.') {
                        tempStr += letter
                    }
                    else {
                        tempArr += tempStr
                        Log.d("questArr", tempStr)
                        tempStr = ""
                    }
                }
                questArr += tempArr
            }
        }
        catch(e : Exception){
            Toast.makeText(this,"Работы нет", Toast.LENGTH_SHORT).show()
        }

        if(questArr.isNotEmpty()){
            for(str in questArr){
                if(str.contains(login)) {
                    for(line in userArr){
                        if(line.contains(str[1])){
                            val tmpTextView = TextView(this)
                            val tmpString = "Студент: ${line[0]} ${line[1].first()}. ${line[2].first()}.\n"
                            tmpTextView.text = tmpString
                            tmpTextView.setOnClickListener() {
                                val intentAnketa = Intent(this, ClientAnkActivity::class.java)
                                intentAnketa.putExtra("Login", str[0])
                                intentAnketa.putExtra("Client", str[1])
                                startActivity(intentAnketa)
                            }
                            tmpTextView.setTextColor(Color.BLACK)
                            tmpTextView.setBackgroundColor(Color.LTGRAY)
                            tmpTextView.textSize = 35F
                            lineView.addView(tmpTextView)
                            val lineTV = TextView(this)
                            lineTV.setBackgroundColor(Color.BLACK)
                            lineTV.textSize = 1F
                            lineTV.text = " "
                            lineView.addView(lineTV)
                        }
                    }
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        schedButton = findViewById(R.id.scheduleButtonTeacher)
        profButton = findViewById(R.id.profileButtonTeacher)
        val login = intent.getStringExtra("LoginT")

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