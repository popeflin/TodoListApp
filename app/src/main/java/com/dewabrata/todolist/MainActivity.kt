package com.dewabrata.todolist

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.dewabrata.todolist.apiservice.APIConfig
import com.dewabrata.todolist.apiservice.model.ResponseGetAllData
import com.dewabrata.todolist.apiservice.model.ResponseSuccess
import com.dewabrata.todolist.apiservice.model.TodolistItem
import com.dewabrata.todolist.uifragment.TodoList
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkAndRequestPermission()

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.frmFragmentRoot, TodoList.newInstance("",""))
                .commit()
        }

      //  getAllTodolist()

      //  addDataTodoList(TodolistItem(" Kerja",null,"Berangkat Kerja","0"))
      //  updateDataTodoList(TodolistItem("Kerja Paksa"," 4","Romusha","1"))
     //   deleteDataTodoList(TodolistItem(null,"4",null,null))
    }

    fun checkAndRequestPermission(){
        val permissionNotGranted = mutableListOf<String>()
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            permissionNotGranted.add(android.Manifest.permission.CAMERA)
        }

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionNotGranted.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionNotGranted.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if(permissionNotGranted.isNotEmpty()){
            val permissionArray = permissionNotGranted.toTypedArray()
            requestPermissions(permissionArray,0)
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0){
            for(i in permissions.indices){
                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.e("INFO","Permission Granted ${permissions[i]}")
                }
                else{
                    Log.e("INFO","Permission Not Granted ${permissions[i]}")
                }
            }
        }
    }


    fun deleteDataTodoList(data : TodolistItem){
        val client = APIConfig.getApiService()
            .deleteDataTodoList(toRequestBody(data.id.toString()))

        client.enqueue(object : Callback<ResponseSuccess> {
            override fun onResponse(
                call: Call<ResponseSuccess>,
                response: Response<ResponseSuccess>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e("INFO", "onSuccess: ${responseBody.message}")
                }
            }

            override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {

                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })


    }



    fun toRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}