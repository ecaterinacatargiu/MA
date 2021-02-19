import 'package:bookLibrary/src/providers/bookProvider.dart';
import 'package:bookLibrary/src/screens/home.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:provider/provider.dart';

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
        create: (context) => BookProvider(),
        child: MaterialApp(
            home: HomeScreen(),
            theme: ThemeData(
                accentColor: Colors.deepPurple[400],
                primaryColor: Colors.deepPurple[400],
                textTheme: GoogleFonts.varelaTextTheme(),
                backgroundColor: Colors.purple[50])));
  }
}
