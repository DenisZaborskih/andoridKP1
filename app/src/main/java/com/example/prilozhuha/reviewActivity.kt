package com.example.prilozhuha

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.*
import java.io.File

class reviewActivity : Activity() {

    lateinit var scroll : ScrollView
    lateinit var linear : LinearLayout
    lateinit var setReviewBtn : Button
    lateinit var etReview : EditText
    lateinit var reviewFile : File
    lateinit var reviewArr : Array<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_activity)

        scroll = findViewById(R.id.scrollReview)
        linear = findViewById(R.id.linearReview)
        setReviewBtn = findViewById(R.id.setReviewButton)
        etReview = findViewById(R.id.etReview)
        reviewFile = File("/data/data/com.example.prilozhuha/files/review.txt")
        reviewArr = emptyArray()
        val login = intent.getStringExtra("Login")
        try{
            reviewFile.readLines()
        }
        catch(e: Exception){
            reviewFile.writeText("")
        }

        for (line in reviewFile.readLines()) {
            var tempArr : Array<String> = emptyArray()
            var tempStr = ""
            for(letter in line){
                if(letter != '_') {
                    tempStr += letter
                }
                else {
                    tempArr += tempStr
                    Log.d("textArr", tempStr)
                    tempStr = ""
                }
            }
            reviewArr += tempArr
        }

        for(line in reviewArr){
            if(line[0] == login.toString()){
                val tmpTextView = TextView(this)
                val tmpString = line[1]
                tmpTextView.text = tmpString
                tmpTextView.setTextColor(Color.BLACK)
                tmpTextView.setBackgroundColor(Color.LTGRAY)
                tmpTextView.textSize = 20F
                linear.addView(tmpTextView)
                val lineTV = TextView(this)
                lineTV.setBackgroundColor(Color.BLACK)
                lineTV.textSize = 1F
                lineTV.text = " "
                linear.addView(lineTV)
            }
        }

        setReviewBtn.setOnClickListener(){
            if(etReview.text.toString().isEmpty()){
                Toast.makeText(this, "Отзыв не написан!", Toast.LENGTH_SHORT).show()
            }
            else if(etReview.text.toString().contains('_')){
                Toast.makeText(this, "Отзыв не написан!", Toast.LENGTH_SHORT).show()
            }
            else{
                val reviewStr = "${login}_${etReview.text}_\n"
                reviewFile.appendText(reviewStr)
                finish()
            }
        }
    }
}