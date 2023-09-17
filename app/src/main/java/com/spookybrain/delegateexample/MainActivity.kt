package com.spookybrain.delegateexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CommentListAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvCommentList)

        adapter = CommentListAdapter(
            listOf(
                Comment(
                    "https://cloudflare-ipfs.com/ipfs/Qmd3W5DuhgHirLHGVixi6V76LhCkZUz6pnFt5AJBiyvHye/avatar/261.jpg",
                    "Doyle Sherwood",
                    "Quo error non omnis quisquam. Hic dolore reprehenderit debitis neque. Nobis libero cupiditate ipsa voluptas veritatis porro ab maiores. Quos rem dolores natus delectus.",
                    "2023-09-17T00:42:38.281-06:00",
                    0,
                    3
                )
            )
        )

        recyclerView.adapter = adapter
    }
}