package com.spookybrain.delegateexample.adapter

enum class CommentAdapterItemType {
    ITEM {
        override val type: Int = 0
    },
    LOADING {
        override val type: Int = 1
    },
    GO_TO_TOP {
        override val type: Int = 2
    };

    abstract val type: Int
}