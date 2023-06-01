package com.example.prilozhuha

import android.app.Activity
import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_NULL
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.io.File

class profileActivity : Activity() {
    lateinit var redButton: Button
    lateinit var applyButton: Button
    lateinit var etFather : EditText
    lateinit var etName : EditText
    lateinit var etSurname : EditText
    lateinit var etPhone : EditText
    lateinit var textArr : Array<Array<String>>
    lateinit var file : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teacher_profile)

        redButton = findViewById(R.id.teacherProfileButtonRed)
        applyButton = findViewById(R.id.teacherProfileButtonApply)
        etFather = findViewById(R.id.teacherProfileFather)
        etName = findViewById(R.id.teacherProfileName)
        etSurname = findViewById(R.id.teacherProfileSurname)
        etPhone = findViewById(R.id.teacherProfilePhone)
        textArr = emptyArray()
        file = File("/data/data/com.example.prilozhuha/files/text.txt")
        val login = intent.getStringExtra("Login")
        for (line in file.readLines()) {
            var tempArr: Array<String> = emptyArray()
            var tempStr = ""
            for (letter in line) {
                if (letter != '.') {
                    tempStr += letter
                } else {
                    tempArr += tempStr
                    Log.d("textArr", tempStr)
                    tempStr = ""
                }
            }
            textArr += tempArr
        }

        for(line in textArr){
            if(line.contains(login)){
                etSurname.text = Editable.Factory.getInstance().newEditable(line[0])
                etName.text = Editable.Factory.getInstance().newEditable(line[1])
                etFather.text = Editable.Factory.getInstance().newEditable(line[2])
                etPhone.text = Editable.Factory.getInstance().newEditable(line[3])
                break
            }
        }

        redButton.setOnClickListener {
            etSurname.isEnabled = true
            etName.isEnabled = true
            etFather.isEnabled = true
            etPhone.isEnabled = true
            redButton.isClickable = false
            applyButton.visibility = View.VISIBLE
            redButton.visibility = View.INVISIBLE
            redButton.isEnabled = false
            applyButton.isEnabled = true
        }

        applyButton.setOnClickListener(){
            file.writeText("")
            for(line in textArr){
                if(line.contains(login)){
                    line[0] = etSurname.text.toString()
                    line[1] = etName.text.toString()
                    line[2] = etFather.text.toString()
                    line[3] = etPhone.text.toString()
                }
                val replacedString = "${line[0]}.${line[1]}.${line[2]}.${line[3]}.${line[4]}.${line[5]}.${line[6]}.\n"
                file.appendText(replacedString)
            }
            etSurname.isEnabled = false
            etName.isEnabled = false
            etFather.isEnabled = false
            etPhone.isEnabled = false
            redButton.isClickable = true
            applyButton.visibility = View.INVISIBLE
            redButton.visibility = View.VISIBLE
            redButton.isEnabled = true
            applyButton.isEnabled = false
        }
    }

}