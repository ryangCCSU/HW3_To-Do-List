package com.example.hw3_to_do_list

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE = 123
    private val FILE_NAME = "MyList"
    private val taskList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Create instance of getSharedPreferences
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        //Retrieve data
        val tasks = sharedPreferences.getString("tasks", "") ?: ""

        //If data was retrieved
        if (tasks.isNotEmpty())
        {
            //Create instance of Gson
            val gson = Gson()
            //Create object expression and get the Java Type
            val sType = object : TypeToken<List<String>>() { }.type
            //Deserialize Json
            val savedTaskList = gson.fromJson<List<String>>(tasks, sType)

            //Iterate through adding each item to list
            for (task in savedTaskList)
            {
                taskList.add(task)
            }
        }

        //Create adapter
        val myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskList)
        //Set adapter
        task_list.adapter = myAdapter

        //Long item click event listener
        task_list.setOnItemLongClickListener { parent, view, position, id ->

            //Get item position
            val selectedItem = parent.getItemAtPosition(position).toString()

            //Remove the item
            taskList.removeAt(position)

            //Update adapter
            myAdapter.notifyDataSetChanged()

            //Display message showing what user deleted
            val toast = Toast.makeText(this, "Deleting $selectedItem", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM, 0, 400)
            toast.show()

            //If the list is now empty display task completion message
            if(taskList.isNullOrEmpty())
            {
                val toast = Toast.makeText(this, "All tasks are completed!", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.BOTTOM, 0, 400)
                toast.show()
            }

            return@setOnItemLongClickListener true
        }
    }

    fun openSecondActivity(view: View)
    {
        //Open second activity
        val myIntent = Intent(this, SecondActivity::class.java)
        startActivityForResult(myIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            //Return from second activity

            //Extract data from intent
            val tasks = data?.getStringExtra("tasks")

            //Create instance of Gson
            val gson = Gson()
            //Create object expression and get the Java Type
            val sType = object : TypeToken<List<String>>() { }.type
            //Deserialize Json
            val savedTaskList = gson.fromJson<List<String>>(tasks, sType)

            //Iterate through adding each item to list
            for (task in savedTaskList)
            {
                taskList.add(task)
            }
        }
    }

    override fun onStop() {
        super.onStop()

        //Create instance of getSharedPreferences
        val sharedPreferences = getSharedPreferences(FILE_NAME, MODE_PRIVATE)
        //Clear out anything already in the sharedPreferences
        sharedPreferences.edit().clear().apply()

        //Edit the sharedPreferences
        val editor = sharedPreferences.edit()

        //Create instance of Gson
        val gson = Gson()
        //Serializes into Json
        val taskListJson = gson.toJson(taskList)
        // Put Json into sharedPreferences
        editor.putString("tasks", taskListJson)
        // Apply the changes
        editor.apply()
    }
}
