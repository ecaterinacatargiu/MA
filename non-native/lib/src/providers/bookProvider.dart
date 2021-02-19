import 'package:bookLibrary/src/models/book.dart';
import 'package:bookLibrary/src/services/firestoreService.dart';
import 'package:flutter/cupertino.dart';
import 'package:uuid/uuid.dart';

class BookProvider with ChangeNotifier {
  final firestoreService = FirestoreService();
  String _title;
  String _author;
  String _year;
  String _description;
  String _rating;
  String _id;
  var uuid = Uuid();

  //Getters
  String get title => _title;
  String get author => _author;
  String get year => _year;
  String get description => _description;
  String get rating => _rating;

  Stream<List<Book>> get books => firestoreService.getBooks();

  //Setters
  set changeTitle(String title) {
    _title = title;
    notifyListeners();
  }

  set changeAuthor(String author) {
    _author = author;
    notifyListeners();
  }

  set changeYear(String year) {
    _year = year;
    notifyListeners();
  }

  set changeDescription(String description) {
    _description = description;
    notifyListeners();
  }

  set changeRating(String rating) {
    _rating = rating;
    notifyListeners();
  }

  //Functions
  loadAll(Book book) {
    if (book != null) {
      _id = book.id;
      _title = book.title;
      _author = book.author;
      _year = book.year;
      _description = book.description;
      _rating = book.rating;
    } else {
      _id = null;
      _title = null;
      _author = null;
      _year = null;
      _description = null;
      _rating = null;
    }
  }

  saveBook() {
    if (_id == null) {
      //add
      var newBook = Book(
          id: uuid.v1(),
          title: _title,
          author: _author,
          year: _year,
          description: _description,
          rating: _rating);
      firestoreService.upsertBook(newBook);
    } else {
      //update
      var updatedBook = Book(
          id: _id,
          title: _title,
          author: _author,
          year: _year,
          description: _description,
          rating: _rating);
      firestoreService.upsertBook(updatedBook);
    }
  }

  deleteBook(String id) {
    firestoreService.deleteBook(id);
  }
}
