package com.safframework.utils

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import org.reactivestreams.Publisher
import java.util.concurrent.TimeUnit

/**
 * 重试机制 跟retryWhen操作符搭配使用
 * Created by tony on 2017/11/6.
 */

class RetryWithDelay(private val maxRetries: Int, private val retryDelayMillis: Int) : Function<Flowable<out Throwable>, Publisher<*>> {

    private var retryCount: Int = 0

    init {
        this.retryCount = 0
    }

    @Throws(Exception::class)
    override fun apply(@NonNull attempts: Flowable<out Throwable>): Publisher<*> {

        return attempts.flatMap { throwable ->
            if (++retryCount <= maxRetries) {

                Log.i("RetryWithDelay", "get error, it will try after " + retryDelayMillis
                        + " millisecond, retry count " + retryCount)
                // When this Observable calls onNext, the original
                // Observable will be retried (i.e. re-subscribed).
                Flowable.timer(retryDelayMillis.toLong(), TimeUnit.MILLISECONDS)

            } else {

                // Max retries hit. Just pass the error along.
                Flowable.error<Any>(throwable)
            }
        }
    }
}