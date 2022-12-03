package com.kahin.petinthehouse.base

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class BaseFragment : Fragment(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val callback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                onBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            callback
        )
    }

    @MainThread
    inline fun <T> LiveData<EventData<T>>.observeEventDataBy(crossinline onChanged: (T) -> Unit): Observer<EventData<T>> {
        val wrappedObserver = Observer<EventData<T>> { t ->
            t.getContentIfNotHandled()?.let { it -> onChanged.invoke(it) }
        }
        observe(this@BaseFragment, wrappedObserver)
        return wrappedObserver
    }

    abstract fun onBack()
}