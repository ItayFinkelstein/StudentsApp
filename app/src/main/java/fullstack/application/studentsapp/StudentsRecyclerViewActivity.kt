package fullstack.application.studentsapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val recyclerView: RecyclerView = findViewById(R.id.students_list_recycler_view)
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

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

    class StudentViewHolder(
        itemView: View,
        listener: OnItemClickListener?
    ): RecyclerView.ViewHolder(itemView) {

        private var studentNameTextView: TextView? = null
        private var studentIdTextView: TextView? = null
        private var studentCheckBox: CheckBox? = null
        private var student: Student? = null

        init {
            studentNameTextView = itemView.findViewById(R.id.student_row_name_text_view)
            studentIdTextView = itemView.findViewById(R.id.student_row_id_text_view)
            studentCheckBox = itemView.findViewById(R.id.student_row_check_box)

            studentCheckBox?.apply {
                setOnClickListener {
                    (tag as? Int)?.let { tag ->
                        student?.isChecked = (it as? CheckBox)?.isChecked ?: false
                    }
                }
            }

            itemView.setOnClickListener {
                Log.d("TAG", "On click listener on item in position: $adapterPosition")
                listener?.onItemClick(adapterPosition)
                listener?.onItemClick(student)
            }
        }

        fun bind(student: Student?, position: Int) {
            this.student = student
            studentNameTextView?.text = student?.name
            studentIdTextView?.text = student?.id

            studentCheckBox?.apply {
                isChecked = student?.isChecked ?: false
                tag = position
            }
        }
    }

    class StudentsRecyclerAdapter(private val students: MutableList<Student> = mutableListOf()): RecyclerView.Adapter<StudentViewHolder>() {
        var listener: OnItemClickListener? = null

        override fun getItemCount(): Int = students.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.student_list_row,
                parent,
                false
            )
            return StudentViewHolder(itemView, listener)
        }

        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
            holder.bind(
                student = students[position],
                position = position
            )
        }
    }
}