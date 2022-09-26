package com.mument_android.core.model

data class TagEntity(
    val tagType: String,
    val tagString: Int,
    val tagIdx: Int
) {
    companion object {
        const val TAG_IS_FIRST = "TAG_IS_FIRST"
        const val TAG_IMPRESSIVE ="TAG_IMPRESSIVE"
        const val TAG_EMOTIONAL = "TAG_EMOTIONAL"
    }
}