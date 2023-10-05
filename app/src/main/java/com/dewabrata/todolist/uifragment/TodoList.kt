package com.dewabrata.todolist.uifragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dewabrata.todolist.R
import com.dewabrata.todolist.adapter.TodoListAdapter
import com.dewabrata.todolist.apiservice.APIConfig
import com.dewabrata.todolist.apiservice.model.ResponseGetAllData
import com.dewabrata.todolist.apiservice.model.ResponseSuccess
import com.dewabrata.todolist.apiservice.model.TodolistItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
 * Use the [TodoList.newInstance] factory method to
 * create an instance of this fragment.
 */
class TodoList : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var recyclerView: RecyclerView
    lateinit var todoListAdapter : TodoListAdapter

    lateinit var fabAddData : FloatingActionButton

    lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.lstTodo)
        progressBar = view.findViewById(R.id.progressBar)


        fabAddData = view.findViewById(R.id.fabAdd)

        fabAddData.setOnClickListener(View.OnClickListener {

            parentFragmentManager.beginTransaction()
                .addToBackStack("add form")
                .replace(R.id.frmFragmentRoot, AddTodoList.newInstance("add", TodolistItem()))
                .commit()

        })



        getAllTodolist()


    }

    override fun onResume() {
        super.onResume()
        getAllTodolist()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TodoList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TodoList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getAllTodolist(){

        val client = APIConfig.getApiService().getAllData()


        showProgressBar(true)


        client.enqueue(object : Callback<ResponseGetAllData> {
            override fun onResponse(
                call: Call<ResponseGetAllData>,
                response: Response<ResponseGetAllData>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    showProgressBar(false)

                    todoListAdapter = TodoListAdapter(responseBody.data?.todolist!!, {item ->

                        parentFragmentManager.beginTransaction()
                            .addToBackStack("add form")
                            .replace(R.id.frmFragmentRoot, AddTodoList.newInstance("update",item))
                            .commit()
                    } , { item ->
                        deleteDataTodoList(item)

                       })

                    recyclerView.layoutManager = LinearLayoutManager(context)

                    recyclerView.adapter = todoListAdapter



                }
            }

            override fun onFailure(call: Call<ResponseGetAllData>, t: Throwable) {
                showProgressBar(false)
                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun deleteDataTodoList(data : TodolistItem){
        val client = APIConfig.getApiService()
            .deleteDataTodoList(toRequestBody(data.id.toString()))

        showProgressBar(true)
        client.enqueue(object : Callback<ResponseSuccess> {
            override fun onResponse(
                call: Call<ResponseSuccess>,
                response: Response<ResponseSuccess>
            ) {

                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    Log.e("INFO", "onSuccess: ${responseBody.message}")

                    showProgressBar(false)
                    getAllTodolist()
                }
            }

            override fun onFailure(call: Call<ResponseSuccess>, t: Throwable) {
                showProgressBar(false)
                Log.e("INFO", "onFailure: ${t.message.toString()}")
            }
        })


    }



    fun toRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    fun showProgressBar(flag:Boolean){
        if (flag){
            progressBar.visibility = View.VISIBLE
            progressBar.animate()
        }else{
            progressBar.visibility = View.GONE

        }
    }
}