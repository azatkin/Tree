package com.example.tree.domain.usecase

import com.example.tree.domain.repository.TreeRepository
import javax.inject.Inject

class AddNodeUseCase @Inject constructor(
    private val repo: TreeRepository
) {
    suspend operator fun invoke(parentId: String) = repo.addNode(parentId)
}
