package fullstack.application.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import fullstack.application.studentsapp.R.id.details_activity_student_id_value_text_view
import fullstack.application.studentsapp.model.Student

class StudentDetailsActivity : AppCompatActivity() {
    var studentId: String? = null
   var studentName: String? = null
    var studentChecked: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        studentId = intent.getStringExtra("student_id")
        studentName = intent.getStringExtra("student_name")
        studentChecked = intent.getBooleanExtra("student_checked", false)

        findViewById<TextView>(details_activity_student_id_value_text_view).text = studentId
        findViewById<TextView>(R.id.details_activity_student_name_value_text_view).text = studentName
        findViewById<Button>(R.id.details_activity_student_edit_button).setOnClickListener {
            val intent = createEditStudentIntent(studentId, studentName, studentChecked)
            startActivity(intent)
        }
        val checked = findViewById<CheckBox>(R.id.details_activity_student_check_box)
        checked.isChecked = studentChecked
        checked.isEnabled = false
    }

    private fun createEditStudentIntent(id: String?, name: String?, isChecked: Boolean): Intent? {
        id?.let { name?.let { return Intent(this@StudentDetailsActivity, EditStudentActivity::class.java).apply {
            putExtra("student_id", id)
            putExtra("student_name", name)
            putExtra("student_checked", isChecked)
        } } }
        return null;
    }

    private fun createStudentsRecyclerViewIntent(id: String?, name: String?, isChecked: Boolean): Intent? {
        id?.let { name?.let { return Intent(this@StudentDetailsActivity, StudentsRecyclerViewActivity::class.java).apply {
            putExtra("student_id", id)
            putExtra("student_name", name)
            putExtra("student_checked", isChecked)
        } } }
        return null;
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(createStudentsRecyclerViewIntent(studentId, studentName, studentChecked))
    }
}