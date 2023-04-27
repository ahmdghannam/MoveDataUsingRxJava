package com.example.rxdemo

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

object MyBus {
    private val publisher = BehaviorSubject.create<Any>()
    fun publish(event: Any) {
        publisher.onNext(event)
    }

    fun <T : Any> listen(eventType: Class<T>): Observable<T> {
        return publisher.ofType(eventType)
    }
}