package com.mument_android.detail.util

enum class SortTypeEnum(val sort: String, val tag: String) {
    SORT_LIKE_COUNT("좋아요순", "Y"),
    SORT_LATEST("최신순", "N");

    companion object {
        fun findSortTypeEnum(sort: String): SortTypeEnum {
            return values().find { it.sort == sort }
                ?: throw IllegalArgumentException("Cannot found Sort Type Enum..")
        }

        fun findSortTypeTag(sort: String): String {
            return values().find { it.sort == sort }?.tag
                ?: throw IllegalArgumentException("Cannot found Sort Tag..")
        }
    }
}
