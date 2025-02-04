package fullstack.application.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import fullstack.application.studentsapp.R.id.edit_student_activity_check_box
import fullstack.application.studentsapp.R.id.edit_student_activity_id_value_text_view
import fullstack.application.studentsapp.R.id.edit_student_activity_name_value_text_view
import fullstack.application.studentsapp.model.Model
import fullstack.application.studentsapp.model.Student

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var studentId = intent.getStringExtra("student_id")
        var studentName = intent.getStringExtra("student_name")
        val studentIsChecked = intent.getBooleanExtra("student_checked", false)
        val position = intent.getIntExtra("student_position", -1)

        findViewById<EditText>(edit_student_activity_id_value_text_view).setText(studentId)
        findViewById<TextView>(edit_student_activity_name_value_text_view).setText(studentName)
        findViewById<Button>(R.id.edit_student_activity_student_cancel_button).setOnClickListener {
            val intent = createEditStudentIntent(studentId, studentName, studentIsChecked)
            startActivity(intent)
            finish()
        }

        findViewById<Button>(R.id.edit_student_activity_student_delete_button).setOnClickListener {
            Model.shared.students.removeAt(position)
            startActivity(Intent(this@EditStudentActivity, StudentsRecyclerViewActivity::class.java))
            finish()
        }
        val studentIsCheckedButton = findViewById<CheckBox>(edit_student_activity_check_box)
        findViewById<Button>(R.id.edit_student_activity_save_button).setOnClickListener {
            studentId = findViewById<EditText>(edit_student_activity_id_value_text_view).text.toString()
            studentName = findViewById<EditText>(edit_student_activity_name_value_text_view).text.toString()
            studentIsCheckedButton.isChecked
            studentId?.let { id ->
                    studentName?.let { name ->
                            Model.shared.students[position] =
                                Student(id, name, studentIsChecked)
                        }
                }
            val intent = createEditStudentIntent(studentId, studentName, studentIsChecked)
            startActivity(intent)
            finish()
        }
        studentIsCheckedButton.isChecked = studentIsChecked
    }

    private fun createEditStudentIntent(id: String?, name: String?, isChecked: Boolean): Intent? {
        id?.let { name?.let { return Intent(this@EditStudentActivity, StudentDetailsActivity::class.java).apply {
            putExtra("student_id", id)
            putExtra("student_name", name)
            putExtra("student_checked", isChecked)
        } } }
        return null;
    }
}