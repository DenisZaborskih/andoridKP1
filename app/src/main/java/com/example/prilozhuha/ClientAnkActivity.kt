package com.example.prilozhuha

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.io.File

class ClientAnkActivity : Activity() {
    lateinit var agreedButton: Button
    lateinit var surname : TextView
    lateinit var nameProf : TextView
    lateinit var fatherName : TextView
    lateinit var phone : TextView
    lateinit var textArr : Array<Array<String>>
    lateinit var fileText : File
    lateinit var avatar : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_anketa_activity)
        agreedButton = findViewById(R.id.agreedButton)
        surname = findViewById(R.id.multilineSurnameClient)
        nameProf = findViewById(R.id.multilineNameClient)
        fatherName = findViewById(R.id.multilineFatherClient)
        phone = findViewById(R.id.phoneProfClient)
        avatar = findViewById(R.id.textViewAvatarClient)
        textArr = emptyArray()
        fileText = File("/data/data/com.example.prilozhuha/files/text.txt")
        avatar.setTextColor(Color.rgb((0..255).random(),(0..255).random(),(0..255).random()))
        avatar.setBackgroundColor(Color.rgb((0..255).random(),(0..255).random(),(0..255).random()))
        val cLogin = intent.getStringExtra("Client")

        for (line in fileText.readLines()) {
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
                textArr += tempArr
            }
        }
        for(i in textArr){
            if(i.contains(cLogin)){
                surname.text = i[0]
                nameProf.text = i[1]
                fatherName.text = i[2]
                phone.text = i[3]
            }
        }
        agreedButton.setOnClickListener(){
            val intentSched = Intent(this, scheduleRegActivity::class.java)
            intentSched.putExtra("LoginT", intent.getStringExtra("Login"))
            intentSched.putExtra("LoginC", cLogin)
            startActivity(intentSched)
        }
    }
}