package com.dd.company.baseapp.sharepreference

interface DataSource<T> {
    fun getAll(): List<T>
    fun add(item: T)
    fun addAll(items: List<T>)
    fun clear()
}