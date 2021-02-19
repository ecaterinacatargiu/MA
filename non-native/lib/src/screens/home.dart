import 'package:bookLibrary/src/models/book.dart';
import 'package:bookLibrary/src/providers/bookProvider.dart';
import 'package:bookLibrary/src/screens/entry.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final bookProvider = Provider.of<BookProvider>(context);

    return Scaffold(
      appBar: AppBar(title: Text("Book Library")),
      body: Padding(
        padding: const EdgeInsets.all(50.0),
        child: StreamBuilder<List<Book>>(
            stream: bookProvider.books,
            builder: (context, snapshot) {
              return ListView.builder(
                  itemCount: snapshot.data.length,
                  itemBuilder: (context, index) {
                    return Card(
                        child: ListTile(
                      trailing: Icon(
                        Icons.edit,
                        color: Theme.of(context).accentColor,
                      ),
                      title: Text(
                        snapshot.data[index].title,
                        style: TextStyle(color: Colors.deepPurple[700]),
                      ),
                      subtitle: Text(snapshot.data[index].author),
                      onTap: () {
                        Navigator.of(context).push(MaterialPageRoute(
                            builder: (context) =>
                                EntryScreen(book: snapshot.data[index])));
                      },
                      tileColor: Colors.indigo[50],
                    ));
                  });
            }),
      ),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.add),
        onPressed: () {
          Navigator.of(context)
              .push(MaterialPageRoute(builder: (context) => EntryScreen()));
        },
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
    );
  }
}
