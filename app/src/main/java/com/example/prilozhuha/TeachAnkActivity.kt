package com.example.prilozhuha

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.File

class TeachAnkActivity : Activity() {
    lateinit var showNum : Button
    lateinit var surname : TextView
    lateinit var nameProf : TextView
    lateinit var fatherName : TextView
    lateinit var phone : TextView
    lateinit var stage : TextView
    lateinit var price : TextView
    lateinit var job : TextView
    lateinit var anketArr : Array<Array<String>>
    lateinit var textArr : Array<Array<String>>
    lateinit var fileAnk : File
    lateinit var fileText : File
    lateinit var questFile : File
    lateinit var questArr : Array<Array<String>>
    lateinit var avatar : TextView
    lateinit var reviewBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.teach_anketa_activity)

        fileText = File("/data/data/com.example.prilozhuha/files/text.txt")
        fileAnk = File("/data/data/com.example.prilozhuha/files/Anketas.txt")
        questFile = File("/data/data/com.example.prilozhuha/files/quest.txt")
        showNum = findViewById(R.id.showNumberButton)
        surname = findViewById(R.id.multilineSurname)
        nameProf = findViewById(R.id.multilineName)
        fatherName = findViewById(R.id.multilineFather)
        phone = findViewById(R.id.phoneProf)
        stage = findViewById(R.id.editTextStageProf)
        price = findViewById(R.id.editTextPriceProf)
        job = findViewById(R.id.editTextJobProf)
        avatar = findViewById(R.id.textViewAvatar)
        reviewBtn = findViewById(R.id.reviewButtonCreate)
        textArr = emptyArray()
        anketArr = emptyArray()
        questArr = emptyArray()
        avatar.setTextColor(Color.rgb((0..255).random(),(0..255).random(),(0..255).random()))
        avatar.setBackgroundColor(Color.rgb((0..255).random(),(0..255).random(),(0..255).random()))
        try{
            questFile.readText()
        }
        catch(e : Exception){
            questFile.writeText("")
        }

        val client = intent.getStringExtra("Client")
        val login = intent.getStringExtra("Login")

        for (line in fileAnk.readLines()) {
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
            anketArr += tempArr
        }

        for (line in fileText.readLines()) {
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

        for (line in questFile.readLines()) {
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
            questArr += tempArr
        }

        for(i in textArr){
            if(i.contains(login)){
                surname.text = i[0]
                nameProf.text = i[1]
                fatherName.text = i[2]
                phone.text = i[3]
            }
        }
        for(i in anketArr){
            if(i.contains(login)){
                job.text = i[1]
                stage.text = i[2]
                price.text = i[3]
            }
        }

        var flag = false

        showNum.setOnClickListener(){
            phone.visibility = View.VISIBLE
            val string = "$login.$client.\n"
            for(i in questArr){
                if(i.contains(login) && i.contains(client)){
                    flag = true
                }
            }
            if(!flag){
                questFile.appendText(string)
            }
        }

        reviewBtn.setOnClickListener(){
            val intentReview = Intent(this, reviewActivity::class.java)
            intentReview.putExtra("Login", login)
            startActivity(intentReview)
        }

    }
}