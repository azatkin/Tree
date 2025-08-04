package com.example.tree.domain.usecase

import com.example.tree.domain.model.Node
import com.example.tree.domain.repository.TreeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTreeUseCase @Inject constructor(
    private val repo: TreeRepository
) {
    operator fun invoke(): Flow<Node> = repo.getTree()
}
