package fullstack.application.studentsapp

import android.os.Bundle
import android.util.Log
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
            }

            override fun onItemClick(student: Student?) {
                Log.d("StudentsRecyclerViewActivity", "On click listener on student: ${student?.id} - ${student?.name}")
            }
        }

        recyclerView.adapter = adapter
    }
}