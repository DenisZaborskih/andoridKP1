package com.example.prilozhuha

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import java.io.File

class scheduleActivity : Activity() {
    lateinit var accountFile : File
    lateinit var schedFile: File
    lateinit var schedArr : Array<Array<String>>
    lateinit var clientArr : Array<Array<String>>
    lateinit var teacherArr : Array<Array<String>>
    lateinit var scrollSchedule : ScrollView
    lateinit var linearSchedule : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_activity)

        scrollSchedule = findViewById(R.id.scrollSchedule)
        linearSchedule = findViewById(R.id.linearSchedule)
        schedArr = emptyArray()
        clientArr = emptyArray()
        teacherArr = emptyArray()
        accountFile = File("/data/data/com.example.prilozhuha/files/text.txt")
        schedFile = File("/data/data/com.example.prilozhuha/files/schedule.txt")
        val login = intent.getStringExtra("Login")
        try {
            for (line in schedFile.readLines()) {
                if(line.contains(login.toString())){
                    var tmpStrArr : Array<String> = emptyArray()
                    var tmpStr = ""
                    for(let in line){
                        if(let != ' '){
                            tmpStr += let
                        }
                        else{
                            Log.e("tmpStrArr", tmpStr)
                            tmpStrArr += tmpStr
                            tmpStr = ""
                        }
                    }
                    schedArr += tmpStrArr

                }
            }
        }
        catch(e : Exception){
            Toast.makeText(this, "Занятий не существует", Toast.LENGTH_SHORT).show()
            finish()
        }

        for(line in accountFile.readLines()){
            for(str in schedArr) {
                if (line.contains(str[0])) {
                    var tmpTeacherArr : Array<String> = emptyArray()
                    var tmpStr = ""
                    for (let in line) {
                        if (let != '.') {
                            tmpStr += let
                        }
                        else {
                            Log.e("teacherArr", tmpStr)
                            tmpTeacherArr += tmpStr
                            tmpStr = ""
                        }
                    }
                    teacherArr += tmpTeacherArr
                } else if (line.contains(str[1])) {
                    var tmpClientArr : Array<String> = emptyArray()
                    var tmpStr = ""
                    for (let in line) {
                        if (let != '.') {
                            tmpStr += let
                        } else {
                            Log.e("clientArr", tmpStr)
                            tmpClientArr += tmpStr
                            tmpStr = ""
                        }
                    }
                    clientArr += tmpClientArr
                }
            }
        }

        for(line in schedArr){
            for(str in teacherArr){
                if(str.contains(line[0])){
                    line[0] = "Преподаватель: ${str[0]} ${str[1].first()}.${str[2].first()}.\n"
                }
            }
            for(str in clientArr){
                if(str.contains(line[1])){
                    line[1] = "Студент:${str[0]} ${str[1].first()}.${str[2].first()}.\n"
                }
            }
        }

        for(line in schedArr){
            val tmpTV = TextView(this)
            val strSched = line[0] +
                    line[1] +
                    "Дата и время: ${line[2]} ${line[3]}\n" +
                    "Длительность: ${line[4]} минут\n "
            tmpTV.text = strSched
            tmpTV.setTextColor(Color.BLACK)
            tmpTV.setBackgroundColor(Color.LTGRAY)
            tmpTV.textSize = 25F
            linearSchedule.addView(tmpTV)
            val lineTV = TextView(this)
            lineTV.setBackgroundColor(Color.BLACK)
            lineTV.textSize = 1F
            lineTV.text = " "
            linearSchedule.addView(lineTV)
        }
    }
}