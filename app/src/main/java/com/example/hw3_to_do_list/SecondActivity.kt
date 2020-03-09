package com.example.hw3_to_do_list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_second.*
import java.io.Serializable

class SecondActivity : AppCompatActivity()
{
    private val taskList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    fun saveAndAdd(view: View)
    {
        //If the text field is not empty
        if(task_text.text.toString() != "")
        {
            //Add the entered item to the list
            taskList.add(task_text.text.toString())

            //Go back to first activity
            val myIntent = Intent()
            //Create instance of Gson
            val gson = Gson()
            //Serialize into Json
            val taskListJson = gson.toJson(taskList)

            //Store extra data in intent
            myIntent.putExtra("tasks", taskListJson)
            //Set activity's result to ok
            setResult(Activity.RESULT_OK, myIntent)

            //Clear the text field so user can enter another value
            task_text.text.clear()

            //Display message indicating the input was saved
            val toast = Toast.makeText(this, "Saved", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 400)
            toast.show()
        }
        else
        {
            //User cannot save without entering value
            val toast = Toast.makeText(this, "You must enter a task before saving", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 400)
            toast.show()
        }
    }

    fun saveAndGoBack(view: View)
    {
        //If the text field is not empty
        if(task_text.text.toString() != "")
        {
            //Add the entered item to the list
            taskList.add(task_text.text.toString())

            //Go back to first activity
            val myIntent = Intent()
            //Create instance of Gson
            val gson = Gson()
            //Serialize into Json
            val taskListJson = gson.toJson(taskList)

            //Store extra data in intent
            myIntent.putExtra("tasks", taskListJson)
            //Set activity's result to ok
            setResult(Activity.RESULT_OK, myIntent)

            //End activity
            finish()

            //Display message indicating the input was saved
            val toast = Toast.makeText(this, "Saved", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 400)
            toast.show()
        }
        else
        {
            //User cannot save without entering value
            val toast = Toast.makeText(this, "You must enter a task before saving", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 400)
            toast.show()
        }
    }

    fun goBack(view: View)
    {
        //End activity
        finish()
    }
}
