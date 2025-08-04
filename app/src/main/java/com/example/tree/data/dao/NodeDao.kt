package com.example.tree.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tree.data.entity.NodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NodeDao {
    @Query("SELECT * FROM nodes WHERE parentId IS NULL LIMIT 1")
    suspend fun getRoot(): NodeEntity?

    @Insert
    suspend fun insert(node: NodeEntity)

    @Delete
    suspend fun delete(node: NodeEntity)

    @Query("SELECT * FROM nodes WHERE id = :id LIMIT 1")
    suspend fun getNode(id: String): NodeEntity?

    @Query("DELETE FROM nodes WHERE id = :id OR parentId = :id")
    suspend fun deleteNodeWithChildren(id: String)

    @Query("SELECT * FROM nodes")
    fun observeAll(): Flow<List<NodeEntity>>

}
