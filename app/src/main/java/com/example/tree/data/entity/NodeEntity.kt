package com.example.tree.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nodes")
data class NodeEntity(
    @PrimaryKey val id: String,
    val parentId: String?
)