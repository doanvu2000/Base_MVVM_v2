package com.dd.company.baseapp.widget.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.dd.company.baseapp.pref.BindingReflex


abstract class BaseRecyclerViewAdapter<T, VB : ViewBinding>
@JvmOverloads constructor(dataList: MutableList<T>? = null) :
    RecyclerView.Adapter<BaseViewHolder<VB>>() {

    private var binding: VB? = null

    /******************************* RecyclerView Method ****************************************/

    private var mRecyclerView: RecyclerView? = null

    var recyclerView: RecyclerView
        set(value) {
            mRecyclerView = value
        }
        get() {
            checkNotNull(mRecyclerView) {
                "Please get it after onAttachedToRecyclerView()"
            }
            return mRecyclerView!!
        }

    val context: Context
        get() {
            return recyclerView.context
        }

    var dataList: MutableList<T> = dataList ?: arrayListOf()
        internal set

    var setOnClickItemListener: ((T, position: Int) -> Unit)? = null

    private var onLongClickItemRecyclerViewAdapter: ((T, position: Int) -> Unit)? = null

    fun setOnLongClickItemRecyclerView(listener: (T, position: Int) -> Unit) {
        onLongClickItemRecyclerViewAdapter = listener
    }

    fun setOnClickItemRecyclerView(listener: (T, position: Int) -> Unit) {
        setOnClickItemListener = listener
    }

    companion object {
        const val DURATION_CLICK = 100L
    }

    var timeClick: Long = 0

    val isDoubleClick: Boolean
        get() {
            if (System.currentTimeMillis() - timeClick > 500) {
                timeClick = System.currentTimeMillis()
                return false
            }
            return true
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        return BaseViewHolder(reflexViewBinding(parent)).apply {
            bindViewClickListener(this, viewType)
        }
    }

    private fun reflexViewBinding(parent: ViewGroup): VB {
        return BindingReflex.reflexViewBinding(javaClass, LayoutInflater.from(parent.context), parent, false)
    }

    fun getLayoutInflater(context: Context): LayoutInflater {
        return LayoutInflater.from(context)
    }

    /**
     * @param viewType -> check viewType handle click header...
     */
    open fun bindViewClickListener(viewHolder: BaseViewHolder<VB>, _viewType: Int) {
        viewHolder.itemView.setOnClickListener { _ ->
            if (!isCheckClickItem()) return@setOnClickListener
            val position = viewHolder.adapterPosition
            if (position == RecyclerView.NO_POSITION) {
                return@setOnClickListener
            }
            getItem(position)?.let {
                if (!isDoubleClick) {
                    setOnClickItemListener?.invoke(it, position)
                }
            }
        }
        viewHolder.itemView.setOnLongClickListener {
            val position = viewHolder.adapterPosition
            getItem(position)?.let {
                onLongClickItemRecyclerViewAdapter?.invoke(it, position)
            }
            true
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        bindData(holder.binding, getItem(position), position)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<VB>,
        position: Int,
        payloads: MutableList<Any>,
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }
        bindData(holder.binding, getItem(position), position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    protected abstract fun bindData(binding: VB, item: T, position: Int)

    open fun getItem(@IntRange(from = 0) position: Int): T {
        return dataList[position]
    }

    open fun getItemOrNull(@IntRange(from = 0) position: Int): T? {
        return dataList.getOrNull(position)
    }

    open fun getItemPosition(item: T?): Int {
        return if (item != null && dataList.isNotEmpty()) dataList.indexOf(item) else -1
    }

    /**
     * change data
     * Change a location data
     */
    open fun setData(@IntRange(from = 0) index: Int, data: T) {
        if (index >= this.dataList.size) {
            return
        }
        this.dataList[index] = data
        notifyItemChanged(index)
    }


    /**
     * add one new data in to certain location
     * @param position
     */
    open fun addData(@IntRange(from = 0) position: Int, data: T) {
        this.dataList.add(position, data)
        notifyItemInserted(position)
        compatibilityDataSizeChanged(1)
    }

    /**
     * add one new data
     */
    open fun addData(data: T) {
        this.dataList.add(data)
        notifyItemInserted(this.dataList.size)
        compatibilityDataSizeChanged(1)
    }

    /**
     * add new data in to certain location
     * @param position the insert position
     * @param newData  the new data collection
     */
    open fun addData(@IntRange(from = 0) position: Int, newData: Collection<T>) {
        this.dataList.addAll(position, newData)
        notifyItemRangeInserted(position, newData.size)
        compatibilityDataSizeChanged(newData.size)
    }

    open fun addMoreData(@NonNull newData: Collection<T>) {
        this.dataList.addAll(newData)
        notifyItemRangeInserted(this.dataList.size - newData.size, newData.size)
        compatibilityDataSizeChanged(newData.size)
    }

    open fun setList(list: Collection<T>?) {
        if (list !== this.dataList) {
            this.dataList.clear()
            if (!list.isNullOrEmpty()) {
                this.dataList.addAll(list)
            }
        } else {
            if (!list.isNullOrEmpty()) {
                val newList = ArrayList(list)
                this.dataList.clear()
                this.dataList.addAll(newList)
            } else {
                this.dataList.clear()
            }
        }
        notifyDataSetChanged()
    }

    open fun addData(data: Collection<T>) {
        val newData: MutableList<T> = mutableListOf()
        newData.addAll(data)
        val diffResult = DiffUtil.calculateDiff(DiffUtilCallBack(dataList, newData))
        diffResult.dispatchUpdatesTo(this)
        dataList.clear()
        dataList.addAll(newData)
        notifyItemRangeInserted(this.dataList.size - newData.size, newData.size)
        compatibilityDataSizeChanged(newData.size)
    }

    /**
     * remove the item associated with the specified position of adapter
     *
     * @param position
     */
    @Deprecated("Please use removeAt()", replaceWith = ReplaceWith("removeAt(position)"))
    open fun remove(@IntRange(from = 0) position: Int) {
        removeAt(position)
    }

    /**
     * remove the item associated with the specified position of adapter
     *
     * @param position
     */
    open fun removeAt(@IntRange(from = 0) position: Int) {
        if (position >= dataList.size) {
            return
        }
        this.dataList.removeAt(position)
        notifyItemRemoved(position)
        compatibilityDataSizeChanged(0)
        notifyItemRangeChanged(position, this.dataList.size - position)
    }

    open fun remove(data: T) {
        val index = this.dataList.indexOf(data)
        if (index == -1) {
            return
        }
        removeAt(index)
    }

    open fun clearData() {
        dataList.clear()
        notifyDataSetChanged()
    }

    /**
     * compatible getEmptyViewCount may change
     *
     * @param size Need compatible data size
     */
    protected fun compatibilityDataSizeChanged(size: Int) {
        if (this.dataList.size == size) {
            notifyDataSetChanged()
        }
    }

    private var timeClickItem = 0L

    protected fun isCheckClickItem(): Boolean {
        if (System.currentTimeMillis() - timeClickItem > DURATION_CLICK) {
            timeClickItem = System.currentTimeMillis()
            return true
        }
        return false
    }

//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        mRecyclerView = null
    }


    fun hasEmptyView(): Boolean {
        return dataList.isEmpty()
    }

}