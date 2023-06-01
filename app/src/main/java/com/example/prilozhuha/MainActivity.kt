package com.example.prilozhuha

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.File
import kotlin.Exception

class MainActivity : AppCompatActivity() {
    lateinit var file : File
    lateinit var textArr : Array<Array<String>>
    lateinit var fileAnk : File
    lateinit var anketArr : Array<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        val login: EditText = findViewById(R.id.editLogin)
        val pass: EditText = findViewById(R.id.editPassword)
        val enterButton = findViewById<Button>(R.id.buttonEntry)
        val regButton = findViewById<Button>(R.id.buttonReg)
        file = File("/data/data/com.example.prilozhuha/files/text.txt")
        fileAnk = File("/data/data/com.example.prilozhuha/files/Anketas.txt")
        textArr = emptyArray()
        anketArr = emptyArray()
        var flag: Boolean
        var anketaFlag = false
        try {
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
        } catch (e: Exception) {
            file.writeText("ФАМИЛИЯ.ИМЯ.ОТЧЕСТВО.ТЕЛЕФОН.ПОЧТА.ПАРОЛЬ.РОЛЬ.\n")
        }

        try {
            for (line in fileAnk.readLines()) {
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
                anketArr += tempArr
            }
        }
        catch(e : Exception){

        }
        enterButton.setOnClickListener(){
            flag = true
            while (flag) {
                if (login.text.toString().isEmpty()) {
                    Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show()
                    break
                }
                if (pass.text.toString().isEmpty()) {
                    Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
                    break
                }
                for (line in textArr) {
                    if (line[4] == login.text.toString() && line[5] == pass.text.toString()) {
                        if (line.contains("Студент")) {
                            val intentClient = Intent(this,ClientMActivity::class.java )
                            intentClient.putExtra("LoginC", line[4])
                            startActivity(intentClient)
                            flag = false
                            break
                        }
                        else {
                            if(anketArr.isNotEmpty()) {
                                for (i in anketArr) {
                                    if (i.contains(login.text.toString())) {
                                        val intentTeacher = Intent(this, TeachMActivity::class.java)
                                        intentTeacher.putExtra("LoginT", line[4])
                                        startActivity(intentTeacher)
                                        anketaFlag = true
                                        flag = false
                                        break
                                    }
                                }
                                if (!anketaFlag){
                                    val intentTeacherAnk = Intent(this, AnketaActivity::class.java)
                                    intentTeacherAnk.putExtra("Login", line[4])
                                    startActivity(intentTeacherAnk)
                                    flag = false
                                    break
                                }
                            }
                            else {
                                val intentTeacherAnk = Intent(this, AnketaActivity::class.java)
                                intentTeacherAnk.putExtra("Login", line[4])
                                startActivity(intentTeacherAnk)
                                flag = false
                                break
                            }
                        }
                        break
                    }
                    else if (line.contentEquals(textArr.last())){
                        Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
                        flag = false
                        break
                    }
                }
            }
        }
        regButton.setOnClickListener(){
            val intentReg = Intent(this,RegActivity::class.java )
            startActivity(intentReg)
        }
        login.setOnClickListener(){
            Toast.makeText(this, "Адрес электронной почты", Toast.LENGTH_SHORT).show()
        }
    }
}
