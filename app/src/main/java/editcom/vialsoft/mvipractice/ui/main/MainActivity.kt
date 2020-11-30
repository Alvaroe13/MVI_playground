package editcom.vialsoft.mvipractice.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import editcom.vialsoft.mvipractice.R

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inflateMainFragment()

    }

    fun inflateMainFragment(){
        Log.d(TAG, "inflateMainFragment: called")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment() , "MainFragment")
            .commit()
    }

}