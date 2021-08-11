package com.example.apadappdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.w3c.dom.Text

//import kotlinx.android.synthetic.main.activity_register*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btn_register: Button = findViewById(R.id.btn_register) as Button
        val et_user_email: EditText = findViewById(R.id.et_user_email) as EditText
        val et_register_password: EditText = findViewById(R.id.et_user_password) as EditText
        val go_to_login_button: TextView= findViewById(R.id.go_to_login) as TextView
        go_to_login_button.setOnClickListener{
            startActivity(Intent(this,SignInActivity::class.java))
        }

        btn_register.setOnClickListener {
            when{
                TextUtils.isEmpty(et_user_email.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText( this, "Please enter email",Toast.LENGTH_SHORT).show()
                }


            TextUtils.isEmpty(et_register_password.text.toString().trim  {it <= ' '}) -> {
                Toast.makeText(this, "Please enter password",Toast.LENGTH_SHORT).show()
        }
            else -> {
                val email: String = et_user_email.text.toString().trim {it <= ' '}
                val password: String = et_register_password.text.toString().trim {it <= ' '}

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(
                        OnCompleteListener<AuthResult> { task ->

                            if(task.isSuccessful) {
                                val firebaseUser: FirebaseUser= task.result!!.user!!
                                Toast.makeText(this, "You are registered successfully",Toast.LENGTH_SHORT).show()

                                val intent= Intent(this,MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()

                            }
                            else{
                                Toast.makeText(this, task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                            }

                        }
                    )

            }
        }


    }
}}