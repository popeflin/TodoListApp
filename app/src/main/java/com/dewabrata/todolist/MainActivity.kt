package com.dewabrata.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dewabrata.todolist.apiservice.APIConfig
import com.dewabrata.todolist.apiservice.model.ResponseGetAllData
import com.dewabrata.todolist.apiservice.model.ResponseSuccess
import com.dewabrata.todolist.apiservice.model.TodolistItem
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

      //  getAllTodolist()

        addDataTodoList(TodolistItem(" Kerja",null,"Berangkat Kerja","0"))
    }


    fun getAllTodolist(){

        val client = APIConfig.getApiService().getAllData()

        client.enqueue(object : Callback<ResponseGetAllData> {
            override fun onResponse(
                call: Call<ResponseGetAllData>,
                response: Response<ResponseGetAllData>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e("INFO", "onSuccess: ${responseBody.data?.todolist}")
                }
            }

            override fun onFailure(call: Call<ResponseGetAllData>, t: Throwable) {

                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun addDataTodoList(data : TodolistItem){
        val client = APIConfig.getApiService()
            .addDataTodoList(toRequestBody(data.tugas.toString()),
                toRequestBody(data.detail.toString()),
                toRequestBody(data.status.toString()))

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