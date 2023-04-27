package com.example.rxdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rxdemo.databinding.ActivitySecondBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setup()
    }

    private fun setup() {
        MyBus.listen(MoveDataEvent::class.java)
            .subscribe { binding.tvPassedData.text=it.text }
            .add(compositeDisposable)
    }

    private fun navigateBack() { finish() }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}