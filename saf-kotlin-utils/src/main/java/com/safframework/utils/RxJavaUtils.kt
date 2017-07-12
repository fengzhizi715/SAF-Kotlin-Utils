package com.safframework.utils

import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Tony Shen on 2017/7/13.
 */

fun <T> observableToMain():ObservableTransformer<T, T> {

    return ObservableTransformer{
        upstream ->
             upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> flowableToMain(): FlowableTransformer<T, T> {

    return FlowableTransformer{
        upstream ->
             upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}