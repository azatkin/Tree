package com.example.tree.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tree.domain.model.Node
import com.example.tree.domain.usecase.AddNodeUseCase
import com.example.tree.domain.usecase.FindNodeUseCase
import com.example.tree.domain.usecase.GetTreeUseCase
import com.example.tree.domain.usecase.RemoveNodeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TreeViewModel @Inject constructor(
    private val getTreeUseCase: GetTreeUseCase,
    private val addNodeUseCase: AddNodeUseCase,
    private val removeNodeUseCase: RemoveNodeUseCase,
    private val findNodeUseCase: FindNodeUseCase,
) : ViewModel() {

    private val _tree = MutableStateFlow<Node?>(null)
    val tree: StateFlow<Node?> = _tree

    init {
        viewModelScope.launch {
            getTreeUseCase().collectLatest { _tree.value = it }
        }
    }

    fun addNode(parentId: String) {
        viewModelScope.launch { addNodeUseCase(parentId) }
    }

    fun removeNode(id: String) {
        viewModelScope.launch { removeNodeUseCase(id) }
    }

    fun findNode(node: Node?, id: String?): Node? = findNodeUseCase(node, id)
}

