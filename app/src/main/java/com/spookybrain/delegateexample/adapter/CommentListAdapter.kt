package com.spookybrain.delegateexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.spookybrain.delegateexample.Comment
import com.spookybrain.delegateexample.R
import com.squareup.picasso.Picasso


class CommentListAdapter(
    private val shouldRequestMoreItems: () -> Unit,
    private val onGoToTopButtonPressed: () -> Unit
) :
    RecyclerView.Adapter<ViewHolder>() {

    private val comments = mutableListOf<Comment>()
    private var couldRequest = true
    private var isLoading = true
    private var lastSize = comments.size

    class CommentViewHolder(private val view: View) : ViewHolder(view) {
        private var ivAvatar: ImageView = view.findViewById(R.id.ivAvatar)
        private var tvName: TextView = view.findViewById(R.id.tvName)
        private var tvContent: TextView = view.findViewById(R.id.tvContent)
        private var tvCreated: TextView = view.findViewById(R.id.tvCreated)
        private var tvLikedText: TextView = view.findViewById(R.id.tvLikedText)
        private var tvSharedText: TextView = view.findViewById(R.id.tvSharedText)
        private var ivLikedIcon: ImageView = view.findViewById(R.id.ivLikedIcon)
        private var ivSharedIcon: ImageView = view.findViewById(R.id.ivSharedIcon)

        fun bind(comment: Comment) {
            tvName.text = comment.name
            tvContent.text = comment.content
            tvCreated.text = comment.created

            val likedEmpty = comment.liked == 0
            val sharedEmpty = comment.shared == 0
            ivLikedIcon.setBackgroundResource(getLikedIcon(likedEmpty))
            ivSharedIcon.setBackgroundResource(getSharedIcon(sharedEmpty))
            val likedVisibility = if (!likedEmpty) View.VISIBLE else View.GONE
            val sharedVisibility = if (!sharedEmpty) View.VISIBLE else View.GONE

            tvLikedText.visibility = likedVisibility
            tvLikedText.text = comment.liked.toString()
            tvSharedText.visibility = sharedVisibility
            tvSharedText.text = comment.shared.toString()



            Picasso.with(view.context).load(comment.avatar).into(ivAvatar)
        }

        private fun getLikedIcon(isZero: Boolean): Int {
            return if (isZero) R.drawable.ic_favorite_outlined else R.drawable.ic_favorite
        }

        private fun getSharedIcon(isZero: Boolean): Int {
            return if (isZero) R.drawable.ic_share_outlined else R.drawable.ic_share
        }
    }

    inner class LoadingViewHolder(view: View) : ViewHolder(view) {
        fun bind() {
            if (!isLoading) { // validates if is loading to avoid duplicated calls
                shouldRequestMoreItems()
                isLoading = true
            }
        }
    }

    inner class GoToTopViewHolder(view: View) : ViewHolder(view) {
        private val fabGoToTop: FloatingActionButton = view.findViewById(R.id.fabGoTop)

        init {
            fabGoToTop.setOnClickListener {
                onGoToTopButtonPressed()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            CommentAdapterItemType.ITEM.type -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.comment_item, parent, false)
                CommentViewHolder(view)
            }

            CommentAdapterItemType.GO_TO_TOP.type -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_go_top, parent, false)
                GoToTopViewHolder(view)
            }

            else -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_loading, parent, false)
                LoadingViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is CommentViewHolder) {
            holder.bind(comments[position])
        } else if (holder is LoadingViewHolder) {
            holder.bind()
        }
    }

    override fun getItemCount(): Int = comments.size

    override fun getItemViewType(position: Int): Int {
        return if (position < comments.size - 1) { // validates where's current focus
            CommentAdapterItemType.ITEM.type
        } else if (couldRequest) {
            CommentAdapterItemType.LOADING.type
        } else {
            CommentAdapterItemType.GO_TO_TOP.type
        }
    }

    fun addComments(newComments: Array<Comment>) {
        lastSize = comments.size
        comments.addAll(newComments)
        notifyItemInserted(lastSize)
        couldRequest = lastSize != comments.size // validates if last response was empty
        isLoading = false
    }

    fun isEmpty() = comments.isEmpty() // Allows check if adapter is empty
}

