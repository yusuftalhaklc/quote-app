package com.yusuftalhaklc.akotlinquotes.api

import com.google.gson.annotations.SerializedName

data class QuoteJson(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("authorSlug")
    val authorSlug: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("dateAdded")
    val dateAdded: String,
    @SerializedName("dateModified")
    val dateModified: String,
    @SerializedName("length")
    val length: Int,
    @SerializedName("tags")
    val tags: List<String>

)