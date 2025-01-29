package fullstack.application.studentsapp.adapter

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fullstack.application.studentsapp.OnItemClickListener
import fullstack.application.studentsapp.R
import fullstack.application.studentsapp.model.Student

class StudentViewHolder(itemView: View, listener: OnItemClickListener?): RecyclerView.ViewHolder(itemView) {

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