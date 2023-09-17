package com.spookybrain.delegateexample

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spookybrain.delegateexample.adapter.CommentListAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModel()
    private var uiState = MutableLiveData<MainUiState>(MainUiState.Loading)

    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvCommentList)
        val llEmptyComments: LinearLayout = findViewById(R.id.llEmptyComments)
        val btnRefresh: FloatingActionButton = findViewById(R.id.btnRefresh)
        val pbLoading: ProgressBar = findViewById(R.id.pbLoading)

        btnRefresh.setOnClickListener {
            viewModel.getComments()
            uiState.value = MainUiState.Loading
        }

        val adapter = CommentListAdapter(::shouldRequestMoreItems, ::onGoToTopButtonPressed)

        recyclerView.adapter = adapter

        viewModel.state.observeForever { comments ->
            runOnUiThread {
                adapter.addComments(comments)
                val state = if (adapter.isEmpty()) {
                    MainUiState.EmptyList
                } else {
                    MainUiState.ShowList
                }
                uiState.value = state

            }
        }

        uiState.observeForever { state ->
            runOnUiThread {
                llEmptyComments.visibility = state.empty
                recyclerView.visibility = state.list
                pbLoading.visibility = state.loading
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getComments()
        uiState.value = MainUiState.Loading
    }

    private fun shouldRequestMoreItems() {
        viewModel.getComments()
    }

    private fun onGoToTopButtonPressed() {
        recyclerView.layoutManager?.scrollToPosition(0)
        //TODO: this scroll action could be animated
    }
}

