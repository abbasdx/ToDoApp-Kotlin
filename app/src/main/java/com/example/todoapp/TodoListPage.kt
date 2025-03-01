package com.example.todoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListPage(viewModel: TodoViewModel) {

    val todoList by viewModel.todoList.observeAsState()
    var inputText by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFEFEF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    cursorColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Black
                ),
                label = { Text("Task Name") },
                placeholder = { Text("Enter your task here...") },
                value = inputText, onValueChange = {
                    inputText = it
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_task_24),
                        contentDescription = "add todo",
                        modifier = Modifier.clickable(
                            onClick = {
                                viewModel.addTodo(inputText)
                                inputText = ""
                            })
                    )
                }
            )

            Text(
                text = "All ToDos",
                fontSize = 40.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                textAlign = TextAlign.Start
            )
            todoList?.let {
                LazyColumn(
                    content = {
                        itemsIndexed(it) { index: Int, item: Todo ->
                            TodoItem(item = item, onDelete = {
                                viewModel.deleteTodo(item.id)
                            })
                        }
                    }
                )
            } ?: Text(
                text = "No Items Yet!",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TodoItem(item: Todo, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(Color.White)
            .padding(14.dp)

    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = SimpleDateFormat(
                    "HH:mm:ss, dd/MM/yyyy",
                    Locale.ENGLISH
                ).format(item.createdAt),
                fontSize = 12.sp,
                color = Color.Black
            )
            Text(
                text = item.title,
                fontSize = 20.sp,
                color = Color.Black
            )

        }
        IconButton(onClick = onDelete) {
            Icon(
                modifier = Modifier,
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = "delete",
                tint = Color.Red
            )
        }
    }
}