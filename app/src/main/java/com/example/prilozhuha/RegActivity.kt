package com.example.prilozhuha

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.*
import java.io.File
import java.lang.Exception

class RegActivity : Activity() {
    lateinit var surnameEt : EditText
    lateinit var nameEt : EditText
    lateinit var fatherEt : EditText
    lateinit var phoneEt : EditText
    lateinit var mailEt : EditText
    lateinit var regButton: Button
    lateinit var checkStudent : CheckBox
    lateinit var checkTeacher : CheckBox
    lateinit var file : File
    lateinit var regText : String
    lateinit var passText : EditText
    lateinit var textArr : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reg_activity)
    }

    @SuppressLint("CommitPrefEdits")
    override fun onResume() {
        super.onResume()
        surnameEt = findViewById(R.id.lastName)
        nameEt = findViewById(R.id.name)
        fatherEt = findViewById(R.id.fatherName)
        phoneEt = findViewById(R.id.editTextPhone)
        mailEt = findViewById(R.id.editTextTextEmailAddress)
        regButton = findViewById(R.id.regButton)
        checkStudent = findViewById(R.id.checkBoxStudent)
        checkTeacher = findViewById(R.id.checkBoxTeacher)
        passText = findViewById(R.id.editTextPassword)
        file = File("/data/data/com.example.prilozhuha/files/text.txt")
        textArr = emptyArray()
        val banArr = arrayOf(' ', '$', '%', '/', '{', '}', '&')


        try {
            for (line in file.readLines()) {
                textArr += line
            }
        }
        catch(e:Exception){
            file.writeText("")
        }
        regText = ""
        var flag = true

        regButton.setOnClickListener(){
            while(true) {
                when (surnameEt.text.toString().isNotEmpty()) {
                    true -> regText += (surnameEt.text.toString() + ".")
                    else -> {
                        Toast.makeText(this, "Фамилия не заполнена!", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
                when (nameEt.text.toString().isNotEmpty()) {
                    true -> regText += (nameEt.text.toString() + ".")
                    else -> {
                        Toast.makeText(this, "Имя не заполнено!", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
                when (fatherEt.text.toString().isNotEmpty()) {
                    true -> regText += (fatherEt.text.toString() + ".")
                    else -> {
                        Toast.makeText(this, "Отчество не заполнено!", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
                when (phoneEt.text.toString().isNotEmpty()) {
                    true -> {
                        for(line in textArr){
                            if(line.contains(phoneEt.text.toString())){
                                Toast.makeText(this, "Номер телефона занят", Toast.LENGTH_SHORT).show()
                                flag = false
                                break
                            }
                        }
                        regText += (phoneEt.text.toString() + ".")
                    }
                    else -> {
                        Toast.makeText(this, "Номер телефона не заполнен!", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
                when (mailEt.text.toString().isNotEmpty()) {
                    true -> {
                        for(line in textArr){
                            if(line.contains(mailEt.text.toString())){
                                flag = false
                                Toast.makeText(this, "Адрес эл. почты занят", Toast.LENGTH_SHORT).show()
                                break
                            }
                        }
                        regText += (mailEt.text.toString() + ".")
                    }
                    else -> {
                        flag = false
                        Toast.makeText(this, "Адрес эл. почты не заполнен!", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
                when (passText.text.toString().isNotEmpty()) {
                    true -> regText += (passText.text.toString() + ".")
                    else -> {
                        flag = false
                        Toast.makeText(this, "Пароль не заполнен!", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
                when(checkStudent.isChecked || checkTeacher.isChecked){

                    true ->{
                        if(checkStudent.isChecked && checkTeacher.isChecked){
                            flag = false
                            Toast.makeText(this, "Выбрано слишком много должностей!", Toast.LENGTH_SHORT).show()
                            break
                        }
                        regText += when (checkStudent.isChecked){
                            true -> ("Студент.")
                            else -> ("Преподаватель.")
                        }
                    }
                    else ->{
                        flag = false
                        Toast.makeText(this, "Должность не выбрана!", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
                if(flag){
                    if(regText.filter { it == '.' }.count() > 7){
                        Toast.makeText(this, "Поля содержат недопустипый символ", Toast.LENGTH_SHORT).show()
                        regText = ""
                        break
                    }
                    for(i in banArr){
                        if(regText.contains(i)){
                            Toast.makeText(this, "Поля содержат недопустипый символ", Toast.LENGTH_SHORT).show()
                            regText = ""
                            break
                        }
                    }
                    file.appendText(regText + "\n")
                    finish()
                    break
                }
                else break
            }
        }
    }
}