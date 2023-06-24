package com.appsolution.todoapp.view

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.appsolution.todoapp.R
import com.appsolution.todoapp.viewmodel.DetailTodoViewModel
import com.google.android.material.textfield.TextInputEditText

class EditTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        var txtJudulTodo = view.findViewById<TextView>(R.id.txtJudulTodo)
        var btnAdd = view.findViewById<Button>(R.id.btnAdd)
        txtJudulTodo.text = "Edit Todo"
        btnAdd.text = "Save Changes"
        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        btnAdd.setOnClickListener{
            var txtTitle = view.findViewById<EditText>(R.id.txtTitle)
            var txtNotes = view.findViewById<EditText>(R.id.txtNotes)
            var radioGroup = view.findViewById<RadioGroup>(R.id.radioGroupPriority)
            var radio = view.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            viewModel.update(txtTitle.text.toString(), txtNotes.text.toString(),radio.tag.toString().toInt(), uuid)
            Toast.makeText(view.context, "Todo updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
        viewModel.fetch(uuid)
        observeViewModel()

    }
    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            var txtTitle = view?.findViewById<TextInputEditText>(R.id.txtTitle)
            var txtNotes = view?.findViewById<TextInputEditText>(R.id.txtNotes)
            txtTitle?.setText(it.title)
            txtNotes?.setText(it.notes)
            when(it.priority){
                1-> view?.findViewById<RadioButton>(R.id.radioLow)?.isChecked = true
                2-> view?.findViewById<RadioButton>(R.id.radioMedium)?.isChecked = true
                else -> view?.findViewById<RadioButton>(R.id.radioHigh)?.isChecked = true
            }
        })
    }


}