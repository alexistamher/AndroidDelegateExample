package com.spookybrain.delegateexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()

    private lateinit var adapter: CommentListAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvCommentList)

        adapter = CommentListAdapter(::shouldRequestMoreItems, ::onGoToTopButtonPressed)
        recyclerView.adapter = adapter

        viewModel.state.observeForever { comments ->
            runOnUiThread {
                adapter.addComments(comments)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.getComments()
    }

    private fun shouldRequestMoreItems() {
        viewModel.getComments()
    }

    private fun onGoToTopButtonPressed(){
        //TODO: recycler view should scroll to top
    }
}