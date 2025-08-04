package com.example.tree.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tree.R
import com.example.tree.base.compose.AppTheme
import com.example.tree.di.LocalAppComponent
import com.example.tree.presentation.viewmodel.TreeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreeScreen() {
    val component = LocalAppComponent.current
    val treeViewModel: TreeViewModel = viewModel(factory = component.vmFactory())

    val colors = AppTheme.colors
    val red = colors.red
    val green = colors.green

    var currentNodeId by remember { mutableStateOf<String?>(null) }
    val tree by treeViewModel.tree.collectAsState()
    val nodeToShow = remember(tree, currentNodeId) {
        if (currentNodeId == null) tree else treeViewModel.findNode(tree, currentNodeId)
    }

    LaunchedEffect(tree, currentNodeId) {
        if (currentNodeId != null && nodeToShow == null) {
            currentNodeId = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = nodeToShow?.name?.take(16) ?: stringResource(R.string.tree_root),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    if (nodeToShow?.parentId != null) {
                        IconButton(onClick = { currentNodeId = nodeToShow.parentId }) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.desc_back)
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        nodeToShow?.let { node ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = green),
                        onClick = { treeViewModel.addNode(node.id) }
                    ) {
                        Text(stringResource(R.string.add_child_emoji))
                    }
                    if (node.parentId != null) {
                        Button(
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = red),
                            onClick = { treeViewModel.removeNode(node.id) }
                        ) {
                            Text(stringResource(R.string.remove_node_emoji))
                        }
                    }
                }
                HorizontalDivider(Modifier.padding(vertical = 4.dp))
                Text(
                    stringResource(R.string.child_nodes),
                    Modifier.padding(start = 24.dp, bottom = 6.dp, top = 2.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp
                )
                if (node.children.isEmpty()) {
                    Text(
                        stringResource(R.string.no_children),
                        Modifier.padding(start = 24.dp, top = 12.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                } else {
                    LazyColumn(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(node.children.size) { idx ->
                            val child = node.children[idx]
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { currentNodeId = child.id },
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                shape = MaterialTheme.shapes.large
                            ) {
                                Column(
                                    Modifier
                                        .padding(vertical = 12.dp, horizontal = 16.dp)
                                ) {
                                    Text(
                                        child.name.take(16),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        stringResource(
                                            R.string.desc_children_count,
                                            child.children.size
                                        ),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
