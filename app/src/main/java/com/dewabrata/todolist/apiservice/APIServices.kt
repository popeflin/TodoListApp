package com.dewabrata.todolist.apiservice

import com.dewabrata.todolist.apiservice.model.ResponseGetAllData
import com.dewabrata.todolist.apiservice.model.ResponseSuccess
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface APIServices {

    @GET("todolist/all")
    fun getAllData() : Call<ResponseGetAllData>

    @Multipart
    @POST("todolist/add")
    fun addDataTodoList(@Part("tugas") tugas:RequestBody ,@Part("detail")
                         detail:RequestBody,@Part("status") status:RequestBody) : Call<ResponseSuccess>

    @Multipart
    @POST("todolist/update")
    fun updateDataTodoList(@Part("id") id:RequestBody, @Part("tugas") tugas:RequestBody ,@Part("detail")
    detail:RequestBody,@Part("status") status:RequestBody) : Call<ResponseSuccess>

    @Multipart
    @POST("todolist/delete")
    fun deleteDataTodoList(@Part("id") id:RequestBody) : Call<ResponseSuccess>




}