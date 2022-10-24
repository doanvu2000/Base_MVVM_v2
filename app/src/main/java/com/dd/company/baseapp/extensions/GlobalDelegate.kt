package com.ducdiep.bookmarket.extensions

import android.view.View
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class GlobalDelegate<T : ViewBinding>(val viewBinder: (View) -> T) :
    ReadOnlyProperty<View, T> {

    override fun getValue(thisRef: View, property: KProperty<*>): T {
        return viewBinder(thisRef)
    }
}