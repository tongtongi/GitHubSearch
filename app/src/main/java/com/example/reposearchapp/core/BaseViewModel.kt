package com.example.reposearchapp.core

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Hold a reference to the Disposable when it is created and call the dispose function at the right
 * moment in one of the Lifecycle hooks (i.e. onStop or onDestroy) in order to avoid memory leaks.
 */
abstract class BaseViewModel : ViewModel() {

    private val disposableBag = CompositeDisposable()

    protected fun Disposable.autoDispose() {
        disposableBag.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        disposableBag.clear()
    }
}