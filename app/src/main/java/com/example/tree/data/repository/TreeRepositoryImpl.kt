package com.example.tree.data.repository

import com.example.tree.data.dao.NodeDao
import com.example.tree.data.entity.NodeEntity
import com.example.tree.domain.model.Node
import com.example.tree.domain.repository.TreeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.security.MessageDigest
import javax.inject.Inject

class TreeRepositoryImpl @Inject constructor(
    private val nodeDao: NodeDao
) : TreeRepository {

    override fun getTree(): Flow<Node> =
        nodeDao.observeAll().map { allNodes ->
            val root = allNodes.firstOrNull { it.parentId == null }
                ?: NodeEntity("root", null)
            buildNode(root, allNodes)
        }

    private fun buildNode(entity: NodeEntity, all: List<NodeEntity>): Node {
        val childrenEntities = all.filter { it.parentId == entity.id }
        return Node(
            id = entity.id,
            parentId = entity.parentId,
            children = childrenEntities.map { buildNode(it, all) },
            name = hashName(entity.id)
        )
    }

    override suspend fun addNode(parentId: String) {
        val newId = System.currentTimeMillis().toString() + parentId
        nodeDao.insert(NodeEntity(newId, parentId))
    }

    override suspend fun removeNode(id: String) {
        nodeDao.deleteNodeWithChildren(id)
    }

    private fun hashName(id: String): String {
        val md = MessageDigest.getInstance("SHA-256").digest(id.toByteArray())
        return "0x" + md.takeLast(20).joinToString("") { "%02x".format(it) }
    }
}
