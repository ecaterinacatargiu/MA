package com.example.lab1.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.lab1.R
import com.example.lab1.model.Book
import com.example.lab1.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {

    private lateinit var bookViewModel : BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        //viewModel= ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(RecyclerActivityViewModel::class.java)
        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        view.addBookButton.setOnClickListener {
            insertBookToDataBase()
        }
        return view
    }

    private fun insertBookToDataBase() {
        var title = titleInput.text.toString()
        var author = authorInput.text.toString()
        var year = yearInput.text
        var description = descriptionInput.text.toString()
        var rating = ratingInput.text

        if(checkInput(title, author, year, description, rating))
        {
            //Crate Book object
            val book = Book(
                0,
                title,
                author,
                Integer.parseInt(year.toString()),
                description,
                Integer.parseInt(rating.toString())
            )
            bookViewModel.addBook(book)
            Toast.makeText(requireContext(), "Book was added successfully!", Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        else
        {
            Toast.makeText(requireContext(), "Invalid data, try again :)", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkInput(title: String, author: String, year: Editable, description: String, rating: Editable) : Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(author) && year.isEmpty() && TextUtils.isEmpty(description) && rating.isEmpty())
    }
}