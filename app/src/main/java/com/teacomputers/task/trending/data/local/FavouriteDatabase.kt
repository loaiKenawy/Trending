package com.teacomputers.task.trending.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.teacomputers.task.trending.data.model.MediaItem
import com.teacomputers.task.trending.util.DataConverter

@TypeConverters(value = [DataConverter::class])
@Database(
    entities = [MediaItem::class],
    version = 1,
    exportSchema = false
)
abstract class FavouriteDatabase : RoomDatabase() {

    abstract fun favDao(): FavouriteDao

    companion object {
        @Volatile
        var INSTANCE: FavouriteDatabase? = null
        fun getDatabase(context: Context): FavouriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    FavouriteDatabase::class.java,
                    " FavouriteDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}