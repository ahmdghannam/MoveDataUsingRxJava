package com.example.rxdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rxdemo.databinding.ActivityMainBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setup()
        addCallBacks()
    }

    private fun setup() {
        MyBus.listen(MoveDataEvent::class.java)
            .subscribe { }
            .add(compositeDisposable)
    }

    private fun addCallBacks() {
        Observable.create{ emitter->
            binding.btnMove.setOnClickListener {
                emitter.onNext(Unit)
            }
        }.throttleFirst(1, TimeUnit.SECONDS).subscribe{
            changeActivity()
        }.add(compositeDisposable)
    }

    private fun changeActivity() {
        val moveDataEvent=MoveDataEvent(binding.editText.text.toString())
        MyBus.publish(moveDataEvent)
        startActivity(Intent(this, SecondActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}