package com.example.tree.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tree.data.dao.NodeDao
import com.example.tree.data.entity.NodeEntity

@Database(entities = [NodeEntity::class], version = 1)
abstract class TreeDatabase : RoomDatabase() {
    abstract fun nodeDao(): NodeDao
}
