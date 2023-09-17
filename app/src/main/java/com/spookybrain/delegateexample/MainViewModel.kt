package com.spookybrain.delegateexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel {
    private val _state = MutableLiveData<Array<Comment>>()
    val state: LiveData<Array<Comment>>
        get() = _state

    private val service: CommentService = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3002/") // port could variate respectively with your service
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(CommentService::class.java)

    fun getComments() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            service.getComments().enqueue(object : Callback<Array<Comment>> {
                override fun onResponse(
                    call: Call<Array<Comment>>,
                    response: Response<Array<Comment>>
                ) {
                    _state.value = response.body()
                }

                override fun onFailure(call: Call<Array<Comment>>, t: Throwable) {
                    _state.value = emptyArray()
                }
            })
        }
    }
}