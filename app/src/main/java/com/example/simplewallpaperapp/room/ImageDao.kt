package com.example.simplewallpaperapp.room
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pagination.models.Hit
@Dao
interface ImageDao {
    @Insert
    fun addImage(hit: Hit)
    @Query("SELECT * FROM image")
    fun getAllImages():List<Hit>
    @Delete
    fun removeImage(hit: Hit)

}