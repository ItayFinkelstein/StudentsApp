package fullstack.application.studentsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import fullstack.application.studentsapp.R.id.add_student_activity_check_box
import fullstack.application.studentsapp.R.id.add_student_activity_id_value_text_view
import fullstack.application.studentsapp.R.id.add_student_activity_name_value_text_view
import fullstack.application.studentsapp.model.Model
import fullstack.application.studentsapp.model.Student

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.add_student_activity_student_cancel_button).setOnClickListener {
                startActivity(Intent(
                        this@AddStudentActivity,
                        StudentsRecyclerViewActivity::class.java
                    )
                )
            finish()
        }

        val studentIsChecked = findViewById<CheckBox>(add_student_activity_check_box)
        findViewById<Button>(R.id.add_student_activity_save_button).setOnClickListener {
            val studentId = findViewById<EditText>(add_student_activity_id_value_text_view).text.toString()
            val studentName = findViewById<EditText>(add_student_activity_name_value_text_view).text.toString()
            studentId.let {
                studentName.let {
                    Model.shared.students.add(Student(studentId, studentName, studentIsChecked.isChecked))
                }
            }
            val intent = Intent(this@AddStudentActivity, StudentsRecyclerViewActivity::class.java)
            startActivity(intent)
            finish()
        }
        studentIsChecked.isEnabled = true
    }
}