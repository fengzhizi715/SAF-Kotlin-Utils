package com.safframework.utils

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Tony Shen on 2017/7/13.
 */
object RxJavaUtils {

    @JvmStatic
    fun <T> observableToMain():ObservableTransformer<T, T> {

        return ObservableTransformer{
            upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    @JvmStatic
    fun <T> flowableToMain(): FlowableTransformer<T, T> {

        return FlowableTransformer{
            upstream ->
            upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 防止重复点击的Transformer
     */
    @JvmStatic
    fun <T> preventDuplicateClicksTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.throttleFirst(1000, TimeUnit.MILLISECONDS)
        }
    }

    /**
     * 防止重复点击的Transformer
     */
    @JvmStatic
    fun <T> preventDuplicateClicksTransformer(windowDuration:Long,timeUnit: TimeUnit): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.throttleFirst(windowDuration, timeUnit)
        }
    }
}
