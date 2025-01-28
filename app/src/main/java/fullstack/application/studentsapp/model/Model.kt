package fullstack.application.studentsapp.model

class Model private constructor() {

    val students: MutableList<Student> = ArrayList()

    companion object {
        val shared = Model()
    }

    init {
        for (i in 0..10) {
            val student = Student(
                name = "student number $i",
                id = i.toString(),
                isChecked = false
            )
            students.add(student)
        }
    }
}