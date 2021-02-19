import 'dart:core';

import 'package:flutter/material.dart';

class Book {
  final String id;
  final String title;
  final String author;
  final String year;
  final String description;
  final String rating;

  Book(
      {@required this.id,
      this.title,
      this.author,
      this.year,
      this.description,
      this.rating});

//Method used to get the data from a json file such as it takes from firebase and to buid an object from it
  factory Book.fromJson(Map<String, dynamic> json) {
    return Book(
        id: json['id'],
        title: json['title'],
        author: json['author'],
        year: json['year'],
        description: json['description'],
        rating: json['rating']);
  }

//Method used to transform an object to a json object so that we cand send it to the firebase
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'author': author,
      'year': year,
      'description': description,
      'rating': rating
    };
  }
}
