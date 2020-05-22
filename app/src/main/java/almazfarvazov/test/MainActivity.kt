package almazfarvazov.test

import almazfarvazov.test.models.User
import almazfarvazov.test.models.ValueEventListenerAdapter
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mFirebaseHelper: FirebaseHelper
    private lateinit var mUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate")

        mAuth = FirebaseAuth.getInstance()

//        mFirebaseHelper = FirebaseHelper(this)
//        mFirebaseHelper.currentUserReference().addValueEventListener(ValueEventListenerAdapter{
//            mUser = it.getValue(User::class.java)!!
//            firstname.text = mUser.firstname
//        })

        exit_btn.setOnClickListener{
            mAuth.signOut()
        }
        mAuth.addAuthStateListener {
            if (it.currentUser == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}

class FirebaseHelper(private val activity: Activity) {
    private val mAuth = FirebaseAuth.getInstance()
    private val mDatabase = FirebaseDatabase.getInstance().reference

    fun currentUserReference(): DatabaseReference = mDatabase.child("users").child(mAuth.currentUser!!.uid)
}
