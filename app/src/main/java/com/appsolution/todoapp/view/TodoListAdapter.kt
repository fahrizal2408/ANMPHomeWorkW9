package com.appsolution.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.appsolution.todoapp.R
import com.appsolution.todoapp.model.Todo

class TodoListAdapter(val todos:ArrayList<Todo>,val checkTodo:(Todo)->Unit):
    RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {
    class TodoListViewHolder(var view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todo_item_layout, parent, false)

        return TodoListViewHolder(view)

    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        var checktask = holder.view.findViewById<CheckBox>(R.id.checkTask)
        checktask.text = todos[position].title
        checktask.isChecked=false
        checktask.setOnCheckedChangeListener{compoundButton,isChecked->
            if(isChecked){
                checkTodo(todos[position])
            }
        }
        val imgEdit = holder.view.findViewById<ImageView>(R.id.imgEdit)
        imgEdit.setOnClickListener{
            val action=TodoListFragmentDirections.actionEditTodo(todos[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
    }
    fun updateTodoList(newTodoList: List<Todo>) {
        todos.clear()
        todos.addAll(newTodoList)
        notifyDataSetChanged()
    }

}