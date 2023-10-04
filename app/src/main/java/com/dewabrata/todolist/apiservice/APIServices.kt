package com.dewabrata.todolist.apiservice

import com.dewabrata.todolist.apiservice.model.ResponseGetAllData
import retrofit2.Call
import retrofit2.http.GET

interface APIServices {

    @GET("todolist/all")
    fun getAllData() : Call<ResponseGetAllData>


}