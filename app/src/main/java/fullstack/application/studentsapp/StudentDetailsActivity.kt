package fullstack.application.studentsapp

import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import fullstack.application.studentsapp.R.id.details_activity_student_id_value_text_view

class StudentDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val studentId = intent.getStringExtra("student_id")
        val studentName = intent.getStringExtra("student_name")
        val studentChecked = intent.getBooleanExtra("student_checked", false)

        findViewById<TextView>(details_activity_student_id_value_text_view).text = studentId
        findViewById<TextView>(R.id.details_activity_student_name_value_text_view).text = studentName
        findViewById<CheckBox>(R.id.details_activity_student_check_box).isChecked = studentChecked
    }
}