package com.example.lab1.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab1.R
//import com.example.lab1.fragments.UpdateFragmentArgs
import com.example.lab1.model.Book
import com.example.lab1.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.custom_row.*
import kotlinx.android.synthetic.main.fragment_updatee.*
import kotlinx.android.synthetic.main.fragment_updatee.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var bookViewModel : BookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_updatee, container, false)

        bookViewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        view.titleInput_up.setText(args.currentBook.title)
        view.authorInput_up.setText(args.currentBook.author)
        view.yearInput_up.setText(args.currentBook.year.toString())
        view.descriptionInput_up.setText(args.currentBook.description)
        view.ratingInput_up.setText(args.currentBook.rating.toString())

        view.updateBookButton.setOnClickListener{
            updateBook()
        }
        setHasOptionsMenu(true)
        return view
    }

    private fun updateBook()
    {
        val title = titleInput_up.text.toString()
        val author = authorInput_up.text.toString()
        val year = Integer.parseInt(yearInput_up.text.toString())
        val description = descriptionInput_up.text.toString()
        val rating = Integer.parseInt(ratingInput_up.text.toString())

        if(checkInput(title, author))
        {
            val updatedBook = Book(args.currentBook.id, title, author, year, description, rating)
            bookViewModel.updateBook(updatedBook)

            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        else
        {
            Toast.makeText(requireContext(), "Oos, something went wrong..", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkInput(title: String, author: String) : Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(author))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete)
        {
            deleteBook()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteBook()
    {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _, _->
            bookViewModel.deleteBook(args.currentBook)
            Toast.makeText(requireContext(), " ${args.currentBook.title} was successfully deleted!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("I've changed my mind"){ _, _ -> }

        builder.setTitle("Delete book ${args.currentBook.title}?")
        builder.setMessage("Are you sure you want to delete the book ${args.currentBook.title}?")

        builder.create().show()
    }
}