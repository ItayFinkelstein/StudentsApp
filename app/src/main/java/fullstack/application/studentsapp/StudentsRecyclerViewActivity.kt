package fullstack.application.studentsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fullstack.application.studentsapp.adapter.StudentsRecyclerAdapter
import fullstack.application.studentsapp.model.Model
import fullstack.application.studentsapp.model.Student


interface OnItemClickListener {
    fun onItemClick(position: Int)
    fun onItemClick(student: Student?)
}

var shownStudentPosition: Int? = -1

class StudentsRecyclerViewActivity : AppCompatActivity() {

    var students: MutableList<Student>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students_recycler_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()

        val addStudentButton: Button = findViewById(R.id.activity_students_add_button)
        addStudentButton.setOnClickListener {
            val intent = Intent(this, StudentDetailsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.students_list_recycler_view)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        setupAdapter(recyclerView)
    }

    private fun setupAdapter(recyclerView: RecyclerView) {
        students = Model.shared.students
        val adapter = StudentsRecyclerAdapter(students ?: mutableListOf())
        adapter.listener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("StudentsRecyclerViewActivity", "On click listener on item in position: $position")
                val student = students?.get(position)
                shownStudentPosition = position
                val intent = createStudentDetailsIntent(student)
                startActivity(intent)
            }

            override fun onItemClick(student: Student?) {
                Log.d("StudentsRecyclerViewActivity", "On click listener on student: ${student?.id} - ${student?.name}")
                shownStudentPosition = 0
                val intent = createStudentDetailsIntent(student)
                startActivity(intent)
            }

            private fun createStudentDetailsIntent(student: Student?): Intent {
                return Intent(this@StudentsRecyclerViewActivity, StudentDetailsActivity::class.java).apply {
                    putExtra("student_id", student?.id)
                    putExtra("student_name", student?.name)
                    putExtra("student_checked", student?.isChecked)
                }
            }
        }

        recyclerView.adapter = adapter
    }

    override fun onStart() {

        var studentId = intent.getStringExtra("student_id")
        studentId?.let {
            var studentName = intent.getStringExtra("student_name")
            var studentChecked = intent.getBooleanExtra("student_checked", false)
            if (shownStudentPosition !== -1 && studentName !== null && shownStudentPosition != null && shownStudentPosition in students?.indices ?: emptyList()) {
                students?.set(shownStudentPosition!!, Student(studentId, studentName, studentChecked))
            } else {
                // Handle the case when position or newStudent is null or out of bounds
                println("Invalid position or student data")
            }
        }

        super.onStart()
    }
}