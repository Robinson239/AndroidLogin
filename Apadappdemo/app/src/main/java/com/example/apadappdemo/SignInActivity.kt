package com.example.apadappdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val btn_register: TextView = findViewById(R.id.tv_don_t_have_an_account) as TextView
        btn_register.setOnClickListener{
            val intent= Intent(this,RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        val btn_login: Button = findViewById(R.id.btn_login) as Button
        val et_user_email: EditText = findViewById(R.id.et_login_email) as EditText
        val et_register_password: EditText = findViewById(R.id.et_login_password) as EditText

        btn_login.setOnClickListener {
            when{
                TextUtils.isEmpty(et_user_email.text.toString().trim {it <= ' '}) -> {
                    Toast.makeText( this, "Please enter email", Toast.LENGTH_SHORT).show()
                }


                TextUtils.isEmpty(et_register_password.text.toString().trim  {it <= ' '}) -> {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email: String = et_user_email.text.toString().trim {it <= ' '}
                    val password: String = et_register_password.text.toString().trim {it <= ' '}

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->

                                if(task.isSuccessful) {
                                    Toast.makeText(this,"You are logged in successfully", Toast.LENGTH_SHORT).show()

                                    val intent= Intent(this,MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra(
                                        "user_id",FirebaseAuth.getInstance().currentUser!!.uid
                                    )
                                    startActivity(intent)
                                    finish()

                                }
                                else{
                                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                }

                            }
                        )

                }
            }


        }



    }
}