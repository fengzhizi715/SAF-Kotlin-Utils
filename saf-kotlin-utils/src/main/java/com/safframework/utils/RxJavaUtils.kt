package com.safframework.utils

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Tony Shen on 2017/7/13.
 */
object RxJavaUtils {

    @JvmStatic
    fun <T> observableToMain():ObservableTransformer<T, T> {

        return ObservableTransformer{ upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    @JvmStatic
    fun <T> flowableToMain(): FlowableTransformer<T, T> {

        return FlowableTransformer{ upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    @JvmStatic
    fun <T> singleToMain(): SingleTransformer<T, T> {

        return SingleTransformer{ upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    @JvmStatic
    fun completableToMain(): CompletableTransformer {

        return CompletableTransformer{ upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    @JvmStatic
    fun <T> maybeToMain(): MaybeTransformer<T, T> {

        return MaybeTransformer{ upstream ->
            upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 防止重复点击的Transformer
     */
    @JvmOverloads
    @JvmStatic
    fun <T> preventDuplicateClicksTransformer(windowDuration:Long=1000,timeUnit: TimeUnit=TimeUnit.MILLISECONDS): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.throttleFirst(windowDuration, timeUnit)
        }
    }
}
