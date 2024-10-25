package com.example.agrimentor.Activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.agrimentor.MainActivity
import com.example.agrimentor.R
import com.example.agrimentor.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    lateinit var  binding: ActivityLoginBinding
    var flagPassshow :Boolean = false
    var errorMessageEmail : String =""
    var errorMessagePassword : String=""

    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }


        binding.passcontrol.setOnClickListener{
            if(flagPassshow){
                binding.password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                flagPassshow = false
                binding.passcontrol.setImageResource(R.drawable.pass_hide)
                binding.password.setSelection(binding.password.text.length)
            }else{
                binding.password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                flagPassshow = true
                binding.passcontrol.setImageResource(R.drawable.pass_show)
                binding.password.setSelection(binding.password.text.length)
            }
        }

        binding.create.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))
        }

        binding.login.setOnClickListener{
            var email: String = binding.email.text.toString()
            var password: String = binding.password.text.toString()
            if(!validationcheck(email,password)){
                if(errorMessageEmail!=""){
                    binding.email.error = errorMessageEmail
                    errorMessageEmail=""
                }
                if(errorMessagePassword!=""){
                    binding.password.error = errorMessagePassword
                    errorMessagePassword=""
                }
 //
            }else{
                login(email,password)



            }


        }







    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successful
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                    // Redirect to main activity
                } else {
                    // Login failed
                    Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }


    }

    private fun validationcheck(email: String, password: String): Boolean {
        var flag = true
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")

        if(email.isEmpty()){
            errorMessageEmail = "Enter Your Email"
            flag = false
        }

        if(password.isEmpty()){
            errorMessagePassword = "Enter Password"
            flag = false
        }

        return flag
    }
}