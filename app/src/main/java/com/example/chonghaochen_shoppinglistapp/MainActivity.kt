package com.example.chonghaochen_shoppinglistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListApp()
        }
    }
}

data class ShoppingItem(val name: String, val quantity: String, var isChecked: Boolean = false)

@Composable
fun ShoppingListApp() {
    var items by remember { mutableStateOf(listOf<ShoppingItem>()) }
    var newItemName by remember { mutableStateOf("") }
    var newItemQuantity by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Input fields and Add button
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // OutlinedTextFields for the item name and quantity as one other component
            OutlinedTextField(
                value = newItemName,
                onValueChange = { newItemName = it },
                label = { Text("Item Name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = newItemQuantity,
                onValueChange = { newItemQuantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newItemName.isNotBlank() && newItemQuantity.isNotBlank()) {
                        items = items + ShoppingItem(newItemName, newItemQuantity)
                        newItemName = ""
                        newItemQuantity = ""
                    }
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Shopping list using lazy column
        LazyColumn {
            items(items) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = item.isChecked,
                        onCheckedChange = { isChecked ->
                            items = items.map {
                                if (it == item) it.copy(isChecked = isChecked) else it
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${item.name} - ${item.quantity}",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}