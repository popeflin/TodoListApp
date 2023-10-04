package com.dewabrata.todolist.uifragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.dewabrata.todolist.R
import com.dewabrata.todolist.apiservice.APIConfig
import com.dewabrata.todolist.apiservice.model.ResponseSuccess
import com.dewabrata.todolist.apiservice.model.TodolistItem
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTodoList.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTodoList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: TodolistItem? = null
    lateinit var txtTugas :EditText
    lateinit var txtDetail :EditText
    lateinit var switch : Switch
    lateinit var btnSend : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getParcelable(ARG_PARAM2,TodolistItem::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtTugas = view.findViewById(R.id.editTugas)
        txtDetail = view.findViewById(R.id.editDetail)
        switch = view.findViewById((R.id.switch1))
        btnSend = view.findViewById(R.id.btnSend)


        if(param1 == "add"){

            btnSend.setOnClickListener(View.OnClickListener {
                addDataTodoList(TodolistItem(txtTugas.text.toString(),null,txtDetail.text.toString(),if(switch.isChecked) "1" else "0"))

            })

        }else{
            txtTugas.setText( param2?.tugas)
            txtDetail.setText(param2?.detail)
            if(param2?.status == "0"){
                switch.setChecked(false)
            }else{
                switch.setChecked(true)
            }

            btnSend.setOnClickListener {

                updateDataTodoList(TodolistItem(txtTugas.text.toString(),param2?.id,txtDetail.text.toString(),if(switch.isChecked) "1" else "0"))
            }
        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddTodoList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: TodolistItem) =
            AddTodoList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putParcelable(ARG_PARAM2, param2)
                }
            }
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

                    parentFragmentManager.popBackStackImmediate()
                }
            }

            override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {

                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })


    }

    fun updateDataTodoList(data : TodolistItem){
        val client = APIConfig.getApiService()
            .updateDataTodoList(toRequestBody(data.id.toString()),toRequestBody(data.tugas.toString()),
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
                    parentFragmentManager.popBackStackImmediate()
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