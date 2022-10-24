package com.ducdiep.bookmarket.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.dd.company.baseapp.extensions.ensureMainThread
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentDelegate<T : ViewBinding>(
    private val fragment: Fragment,
    private val viewBinder: (View) -> T,
    private val disposeEvents: T.() -> Unit = {}
) : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {

    private inline fun Fragment.observeLifecycleOwnerThroughLifecycleCreation(
        crossinline viewOwner: LifecycleOwner.() -> Unit
    ) {
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                viewLifecycleOwnerLiveData.observe(
                    this@observeLifecycleOwnerThroughLifecycleCreation,
                    Observer { viewLifecycleOwner ->
                        viewLifecycleOwner.viewOwner()
                    }
                )
            }
        })
    }

    private var fragmentBinding: T? = null

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        fragmentBinding?.disposeEvents()
        fragmentBinding = null
    }

    init {
        fragment.observeLifecycleOwnerThroughLifecycleCreation {
            lifecycle.addObserver(this@FragmentDelegate)
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        ensureMainThread()
        val binding = fragmentBinding
        if (binding != null) {
            return binding
        }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Fragment views are destroyed.")
        }
        return viewBinder(thisRef.requireView()).also { fragmentBinding = it }
    }
}