package com.example.pagination.models

import com.example.pagination.models.Hit
import java.io.Serializable

data class ImageClass(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
):Serializable