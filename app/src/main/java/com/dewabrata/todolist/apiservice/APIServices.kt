package com.dewabrata.todolist.apiservice

import android.location.Location
import com.dewabrata.todolist.apiservice.model.ResponseGetAllData
import com.dewabrata.todolist.apiservice.model.ResponseSuccess
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface APIServices {

    @GET("todolist/all")
    fun getAllData() : Call<ResponseGetAllData>

    @Multipart
    @POST("todolist/add")
    fun addDataTodoList(@Part("tugas") tugas:RequestBody ,@Part("detail")
                         detail:RequestBody,@Part("status") status:RequestBody,@Part("location")location: RequestBody,@Part image: MultipartBody.Part) : Call<ResponseSuccess>

    @Multipart
    @POST("todolist/update")
    fun updateDataTodoList(@Part("id") id:RequestBody, @Part("tugas") tugas:RequestBody ,@Part("detail")
    detail:RequestBody,@Part("status") status:RequestBody,@Part("location")location: RequestBody,@Part("image")image:  MultipartBody.Part) : Call<ResponseSuccess>

    @Multipart
    @POST("todolist/delete")
    fun deleteDataTodoList(@Part("id") id:RequestBody) : Call<ResponseSuccess>


    @GET("todolist/all")
    fun getAllDataByFilter(@Query("filters[0][co][2][fl]") filterField : String,
                           @Query("filters[0][co][2][fl]") filterOperator : String,
                           @Query("filters[0][co][2][fl]") filterValue : String,
                           @Query("sort_order") sortorder : String

    ) : Call<ResponseGetAllData>


}