import 'package:bookLibrary/src/models/book.dart';
import 'package:bookLibrary/src/providers/bookProvider.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class EntryScreen extends StatefulWidget {
  final Book book;

  EntryScreen({this.book});

  @override
  _EntryScreenState createState() => _EntryScreenState();
}

class _EntryScreenState extends State<EntryScreen> {
  final bookTitleController = TextEditingController();
  final bookAuthorController = TextEditingController();
  final bookYearController = TextEditingController();
  final bookDescriptionController = TextEditingController();
  final bookRatingController = TextEditingController();

  @override
  void dispose() {
    bookTitleController.dispose();
    bookAuthorController.dispose();
    bookYearController.dispose();
    bookDescriptionController.dispose();
    bookRatingController.dispose();

    super.dispose();
  }

  @override
  void initState() {
    final bookProvider = Provider.of<BookProvider>(context, listen: false);
    if (widget.book != null) {
      //Update
      bookTitleController.text = widget.book.title;
      bookAuthorController.text = widget.book.author;
      bookYearController.text = widget.book.year;
      bookDescriptionController.text = widget.book.description;
      bookRatingController.text = widget.book.rating;

      bookProvider.loadAll(widget.book);
    } else {
      //Add
      bookProvider.loadAll(null);
    }
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final bookProvider = Provider.of<BookProvider>(context);

    return Scaffold(
        appBar: AppBar(
          title: Text("My book"),
        ),
        body: Padding(
            padding: const EdgeInsets.all(50.0),
            child: ListView(
              children: [
                TextField(
                  decoration: InputDecoration(
                    labelText: "Title: ",
                    border: InputBorder.none,
                  ),
                  onChanged: (String value) => bookProvider.changeTitle = value,
                  controller: bookTitleController,
                ),
                TextField(
                  decoration: InputDecoration(
                    labelText: "Author: ",
                    border: InputBorder.none,
                  ),
                  onChanged: (String value) =>
                      bookProvider.changeAuthor = value,
                  controller: bookAuthorController,
                ),
                TextField(
                  decoration: InputDecoration(
                    labelText: "Year: ",
                    border: InputBorder.none,
                  ),
                  onChanged: (String value) => bookProvider.changeYear = value,
                  controller: bookYearController,
                ),
                TextField(
                  decoration: InputDecoration(
                    labelText: "Description: ",
                    border: InputBorder.none,
                  ),
                  onChanged: (String value) =>
                      bookProvider.changeDescription = value,
                  controller: bookDescriptionController,
                ),
                TextField(
                  decoration: InputDecoration(
                    labelText: "Rating: ",
                    border: InputBorder.none,
                  ),
                  onChanged: (String value) =>
                      bookProvider.changeRating = value,
                  controller: bookRatingController,
                ),
                RaisedButton(
                  color: Theme.of(context).accentColor,
                  child:
                      Text("Save book", style: TextStyle(color: Colors.white)),
                  onPressed: () {
                    bookProvider.saveBook();
                    Navigator.of(context).pop();
                  },
                ),
                (widget.book != null)
                    ? RaisedButton(
                        color: Theme.of(context).accentColor,
                        child: Text("Delete book",
                            style: TextStyle(color: Colors.white)),
                        onPressed: () {
                          bookProvider.deleteBook(widget.book.id);
                          Navigator.of(context).pop();
                        },
                      )
                    : Container(),
                Image.network(
                    'https://media.giphy.com/media/26gs8ySANEoAu1u92/giphy.gif',
                    height: 200.0,
                    width: 200.0)
              ],
            )));
  }
}
