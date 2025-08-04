package com.example.tree.domain.usecase

import com.example.tree.domain.repository.TreeRepository
import javax.inject.Inject

class RemoveNodeUseCase @Inject constructor(
    private val repo: TreeRepository
) {
    suspend operator fun invoke(id: String) = repo.removeNode(id)
}
