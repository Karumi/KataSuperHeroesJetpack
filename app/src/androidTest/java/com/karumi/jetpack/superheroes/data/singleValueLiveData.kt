package com.karumi.jetpack.superheroes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> singleValueLiveData(value: T): LiveData<T> =
    MutableLiveData<T>().apply { postValue(value) }