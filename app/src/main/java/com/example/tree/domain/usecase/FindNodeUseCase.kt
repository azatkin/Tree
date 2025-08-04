package com.example.tree.domain.usecase

import com.example.tree.domain.model.Node
import javax.inject.Inject

class FindNodeUseCase @Inject constructor() {
    operator fun invoke(node: Node?, id: String?): Node? {
        if (node == null || id == null) return null
        if (node.id == id) return node
        node.children.forEach { child ->
            invoke(child, id)?.let { return it }
        }
        return null
    }
}
