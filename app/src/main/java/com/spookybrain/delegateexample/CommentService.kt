package com.spookybrain.delegateexample

import retrofit2.Call
import retrofit2.http.GET

interface CommentService {
    @GET("comments")
    fun getComments(): Call<Array<Comment>>
}