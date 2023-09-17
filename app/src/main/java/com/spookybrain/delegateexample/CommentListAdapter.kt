package com.spookybrain.delegateexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class CommentListAdapter(private val comments: List<Comment>) :
    RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }
}

