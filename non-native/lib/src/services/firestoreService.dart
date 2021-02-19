import 'package:bookLibrary/src/models/book.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class FirestoreService {
  FirebaseFirestore db = FirebaseFirestore.instance;

  //Read
  Stream<List<Book>> getBooks() {
    return db.collection('books').snapshots().map((snapshot) =>
        snapshot.docs.map((doc) => Book.fromJson(doc.data())).toList());
  }

  //Insert + Update = Upsert: if it does not exist, create it, if it exists, update it
  Future<void> upsertBook(Book book) {
    var options = SetOptions(merge: true);
    return db.collection('books').doc(book.id).set(book.toMap(), options);
  }

  //Delete
  Future<void> deleteBook(String bookID) {
    return db.collection('books').doc(bookID).delete();
  }
}
