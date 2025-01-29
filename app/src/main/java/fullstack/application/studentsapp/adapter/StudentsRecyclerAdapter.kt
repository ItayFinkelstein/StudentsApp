package fullstack.application.studentsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fullstack.application.studentsapp.OnItemClickListener
import fullstack.application.studentsapp.R
import fullstack.application.studentsapp.model.Student

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