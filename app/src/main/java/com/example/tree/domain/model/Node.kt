package com.example.tree.domain.model

data class Node(
    val id: String,
    val parentId: String?,
    val children: List<Node> = emptyList(),
    val name: String
)