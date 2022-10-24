package com.dd.company.baseapp.widget.recyclerview

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallBack<T>(private val oldData: MutableList<T>, private val newData: MutableList<T>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition : Int , newItemPosition : Int) : Boolean {
        if (oldItemPosition in oldData.indices && newItemPosition in newData.indices) {
            return oldData[oldItemPosition] === newData[newItemPosition]
        }
        return false
    }

    override fun getOldListSize() : Int {
        return oldData.size
    }

    override fun getNewListSize() : Int {
        return newData.size
    }

    override fun areContentsTheSame(oldItemPosition : Int , newItemPosition : Int) : Boolean {
        if (oldItemPosition in oldData.indices && newItemPosition in newData.indices) {
            val oldValue = oldData[oldItemPosition]
            val newValue = newData[newItemPosition]
            return oldValue === newValue && oldValue === newValue
        }
        return false
    }
}