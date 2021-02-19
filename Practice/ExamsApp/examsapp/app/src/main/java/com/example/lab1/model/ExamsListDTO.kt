package com.example.lab1.model

class ExamsListDTO(var exams: List<Exam>) {

    override fun toString(): String {
        var stringu = ""
        for (c in exams)
        {
            stringu += c.toString() + " "
        }
        return stringu
    }

    fun toList() : List<Exam>
    {
        var examsList = ArrayList<Exam>()
        examsList.toList()
        for(exam in exams)
            examsList.add(exam)

        return examsList
    }
}