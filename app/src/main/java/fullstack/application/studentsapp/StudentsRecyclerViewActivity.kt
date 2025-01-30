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
}

var shownStudentPosition: Int? = -1

class StudentsRecyclerViewActivity : AppCompatActivity() {


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
            val intent = Intent(this, EditStudentActivity::class.java).apply {
            }
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
        Model.shared.students
        val adapter = StudentsRecyclerAdapter(Model.shared.students)
        adapter.listener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("StudentsRecyclerViewActivity", "On click listener on item in position: $position")
                val student = Model.shared.students.get(position)
                shownStudentPosition = position
                val intent = createStudentDetailsIntent(student)
                startActivity(intent)
            }

            private fun createStudentDetailsIntent(student: Student?): Intent {
                return Intent(this@StudentsRecyclerViewActivity, StudentDetailsActivity::class.java).apply {
                    putExtra("student_id", student?.id)
                    putExtra("student_name", student?.name)
                    putExtra("student_checked", student?.isChecked)
                    putExtra("student_position", shownStudentPosition)
                }
            }
        }

        recyclerView.adapter = adapter
    }
}