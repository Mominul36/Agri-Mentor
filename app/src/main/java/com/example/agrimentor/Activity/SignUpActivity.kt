package com.example.agrimentor.Activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.agrimentor.R
import com.example.agrimentor.databinding.ActivityLoginBinding
import com.example.agrimentor.databinding.ActivitySignUpBinding
import com.example.agrimentor.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    lateinit var  binding: ActivitySignUpBinding
    var flagPassshow :Boolean = false
    var flagCpassshow :Boolean = false
    var errorMessageName : String =""
    var errorMessageEmail : String =""
    var errorMessagePhone : String=""
    var errorMessagePassword : String=""
    var errorMessageCpassword : String=""


    lateinit var database: DatabaseReference
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        database = FirebaseDatabase.getInstance().getReference("User")
        auth = FirebaseAuth.getInstance()




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



        binding.cpasscontrol.setOnClickListener{
            if(flagCpassshow){
                binding.cpassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                flagCpassshow = false
                binding.cpasscontrol.setImageResource(R.drawable.pass_hide)
                binding.cpassword.setSelection(binding.cpassword.text.length)
            }else{
                binding.cpassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                flagCpassshow = true
                binding.cpasscontrol.setImageResource(R.drawable.pass_show)
                binding.cpassword.setSelection(binding.cpassword.text.length)
            }
        }

        binding.signup.setOnClickListener{

            var name: String = binding.name.text.toString()
            var email: String = binding.email.text.toString()
            var phone: String = binding.phone.text.toString()
            var password: String = binding.password.text.toString()
            var cpassword: String = binding.cpassword.text.toString()

            if(!validationcheck(name,email,phone,password,cpassword)){
                if(errorMessageName!=""){
                    binding.name.error = errorMessageName
                    errorMessageName = ""
                }
                if(errorMessageEmail!=""){
                    binding.email.error = errorMessageEmail
                    errorMessageEmail=""
                }
                if(errorMessagePhone!=""){
                    binding.phone.error = errorMessagePhone
                    errorMessagePhone=""
                }
                if(errorMessagePassword!=""){
                    binding.password.error = errorMessagePassword
                    errorMessagePassword=""
                }
                if(errorMessageCpassword!=""){
                    binding.cpassword.error = errorMessageCpassword
                    errorMessageCpassword=""
                }
            }
            else{
                signUp(name,email,phone,password)
            }
        }




    }

    private fun signUp(name: String, email: String, phone: String, password: String) {
        val id = database.push().key
        if (id == null) {
            Toast.makeText(this, "Failed to generate user ID", Toast.LENGTH_LONG).show()
            return
        }

        var user = UserModel(id,name,email,phone,password)
        database.child(id!!).setValue(user).addOnSuccessListener {
            setAuthentication(email,password)
            Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_LONG).show()
            Log.e("SignUpActivity", "Firebase Error", exception)
        }


    }

    private fun setAuthentication(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign up successful
                   // Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                    // Redirect to login or main activity
                } else {
                    // Sign up failed
                    Toast.makeText(this, "Sign Up Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun validationcheck(name: String, email: String, phone: String, password: String, cpassword: String): Boolean {
        var flag = true
        val namePattern = Regex("^[a-zA-Z\\s]+$")
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        val phonePattern = Regex("^((\\+8801)|01)[3-9]\\d{8}\$")

        //check for name
        if(name.isEmpty()){
            errorMessageName = "Enter Your Name"
            flag = false
        }else if(!(name.matches(namePattern) && name.length >= 2)){
            errorMessageName = "Enter valid name"
            flag = false
        }

        //check for email
        if(email.isEmpty()){
            errorMessageEmail = "Enter Your Email"
            flag = false
        }else if(!email.matches(emailPattern)){
            errorMessageEmail = "Enter a valid Email"
            flag = false
        }

        //check for phone
        if(!phone.matches(phonePattern)){
            errorMessagePhone = "Enter a valid Phone Number"
            flag = false
        }

        //check for password
        if(password.isEmpty()){
            errorMessagePassword = "Enter a Password"
            flag = false
        }
        else if(password.length<8){
            errorMessagePassword = "Password Must be at least 8 characters long"
            flag = false
        }

        //check for repeated password
        if(cpassword.isEmpty()){
            errorMessageCpassword = "Enter Password Again"
            flag = false
        }
        else if(password!=cpassword){
            errorMessageCpassword = "Password Didn't Match"
            flag = false
        }

        return flag
    }






}