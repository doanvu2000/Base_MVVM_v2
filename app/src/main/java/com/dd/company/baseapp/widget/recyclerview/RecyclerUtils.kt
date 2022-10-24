package com.dd.company.baseapp.widget.recyclerview

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object RecyclerUtils {

    fun setManager(mContext: Context, rlv: RecyclerView) {
        var layout = GridLayoutManager(mContext, 1)
        layout.orientation = GridLayoutManager.VERTICAL
        rlv.layoutManager = layout
    }

    fun setPeekingLayoutManager(mContext: Context, rlv: RecyclerView) {
        var layout = GridLayoutManager(mContext, 1)
        layout.orientation = GridLayoutManager.HORIZONTAL
        rlv.layoutManager = layout
    }

    fun setManager(mContext: Context, rlv: RecyclerView, number: Int, orientai: Int) {
        var layout = GridLayoutManager(mContext, number)
        layout.orientation = orientai
        rlv.layoutManager = layout
    }

    fun setGridManager(m: Context, rlv: RecyclerView) {
        val manager = GridLayoutManager(m, 1)
        manager.orientation = GridLayoutManager.VERTICAL
        rlv.layoutManager = manager
    }

    fun setGridManager(m: Context, rlv: RecyclerView, lin: Int) {
        val manager = GridLayoutManager(m, lin)
        manager.orientation = GridLayoutManager.VERTICAL
        rlv.layoutManager = manager
    }

    fun setGridManagerV(
        m: Context,
        rlv: RecyclerView,
        spanCount: Int,
        adapter: RecyclerView.Adapter<*>
    ) {
        val manager = GridLayoutManager(m, spanCount)
        manager.orientation = GridLayoutManager.VERTICAL
        rlv.layoutManager = manager
        rlv.adapter = adapter
    }

    fun setGridManager(m: Context, rlv: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        val manager = GridLayoutManager(m, 1)
        manager.orientation = GridLayoutManager.VERTICAL
        rlv.layoutManager = manager
        rlv.adapter = adapter
    }

    fun setGridManager(m: Fragment, rlv: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        val manager = GridLayoutManager(m.requireContext(), 1)
        manager.orientation = GridLayoutManager.VERTICAL
        rlv.layoutManager = manager
        rlv.adapter = adapter
    }


    fun setGridManagerTwo(m: Context, rlv: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        val manager = GridLayoutManager(m, 1)
        manager.orientation = GridLayoutManager.VERTICAL
        rlv.layoutManager = manager
        rlv.adapter = adapter
    }

    fun setGridManager(m: Context, rlv: RecyclerView, lin: Int, adapter: RecyclerView.Adapter<*>) {
        val manager = GridLayoutManager(m, lin)
        manager.orientation = GridLayoutManager.VERTICAL
        rlv.layoutManager = manager
        rlv.adapter = adapter
    }


    fun setLinearLayoutManager(
        context: Context,
        rlv: RecyclerView,
        adapter: RecyclerView.Adapter<*>
    ) {
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        rlv.layoutManager = manager
        rlv.adapter = adapter
    }
}