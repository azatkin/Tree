package com.example.tree.domain.repository

import com.example.tree.domain.model.Node
import kotlinx.coroutines.flow.Flow

interface TreeRepository {
    fun getTree(): Flow<Node>
    suspend fun addNode(parentId: String)
    suspend fun removeNode(id: String)
}