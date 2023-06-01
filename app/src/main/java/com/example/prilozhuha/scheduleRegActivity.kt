package com.example.prilozhuha

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import java.io.File
import java.util.*
import java.text.SimpleDateFormat

class scheduleRegActivity : Activity() {
    lateinit var tvDate : TextView
    lateinit var btnDate : Button
    lateinit var tvTime : TextView
    lateinit var btnTime : Button
    lateinit var btnAccept : Button
    lateinit var schedFile : File
    lateinit var etLength : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_activity_create)

        tvDate = findViewById(R.id.tvDatePicker)
        btnDate = findViewById(R.id.buttonDatePicker)
        tvTime = findViewById(R.id.tvTimePicker)
        btnTime = findViewById(R.id.buttonTimePicker)
        btnAccept = findViewById(R.id.buttonSchedAccept)
        etLength = findViewById(R.id.etLength)
        var clicks = 0
        val tLogin = intent.getStringExtra("LoginT")
        val cLogin = intent.getStringExtra("LoginC")

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateDP(myCalendar)
        }

        btnDate.setOnClickListener(){
        if(clicks >= 1 && !btnAccept.isEnabled){
                btnAccept.isEnabled = true
                btnAccept.visibility = View.VISIBLE

            }
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            clicks++
        }

        btnTime.setOnClickListener(){
            if(clicks >= 1 && !btnAccept.isEnabled){
                btnAccept.isEnabled = true
                btnAccept.visibility = View.VISIBLE

            }
            val curTime = Calendar.getInstance()
            val startHour = curTime.get(Calendar.HOUR_OF_DAY)
            val startMin = curTime.get(Calendar.MINUTE)
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                tvTime.text = "$hour:$minute"
            }, startHour, startMin, true).show()
            clicks++
        }


        btnAccept.setOnClickListener(){
            schedFile = File("/data/data/com.example.prilozhuha/files/schedule.txt")
            try{
                var flag = false
                val dateTime = "${tvDate.text} ${tvTime. text}"
                for(line in schedFile.readLines()){
                    if((line.contains(tLogin.toString()) || line.contains(cLogin.toString()))&& line.contains(dateTime)){
                        Toast.makeText(this, "Дата и время уже заняты!", Toast.LENGTH_SHORT).show()
                        flag = true
                        break
                    }
                }
                if(etLength.text.isEmpty()){
                    Toast.makeText(this, "Длительность не заполнена!", Toast.LENGTH_SHORT).show()
                }

                if(!flag && etLength.text.isNotEmpty()){
                    schedFile.appendText("$tLogin $cLogin $dateTime ${etLength.text} \n")
                    finish()
                }
            }
            catch (e : Exception){
                if(etLength.text.isEmpty()){
                    Toast.makeText(this, "Длительность не заполнена!", Toast.LENGTH_SHORT).show()
                }
                else {
                    schedFile.writeText("$tLogin $cLogin ${tvDate.text} ${tvTime.text} ${etLength.text} \n")
                    finish()
                }
            }
        }

    }

    private fun updateDP(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        tvDate.text = sdf.format(myCalendar.time)
    }
}