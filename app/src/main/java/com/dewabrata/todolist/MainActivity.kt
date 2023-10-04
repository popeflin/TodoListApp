package com.dewabrata.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dewabrata.todolist.apiservice.APIConfig
import com.dewabrata.todolist.apiservice.model.ResponseGetAllData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        getAllTodolist()
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
}