 package editcom.vialsoft.mvipractice.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import editcom.vialsoft.mvipractice.R

private const val TAG = "MaiActDebug"

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModel()
        inflateMainFragment()
    }

    fun initViewModel(){
        Log.d(TAG, "called: ")
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java);
    }

    fun inflateMainFragment(){
        Log.d(TAG, "inflateMainFragment: called")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment() , "MainFragment")
            .commit()
    }

}