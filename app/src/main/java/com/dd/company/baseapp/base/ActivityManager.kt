package com.dd.company.baseapp.base

import android.app.Activity

object ActivityManager {

    private val activities by lazy {
        mutableListOf<Activity>()
    }

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
        activity.finish()
    }

    fun getTopActivity(): Activity? {
        return if (activities.isEmpty()) {
            null
        } else activities[activities.size - 1]
    }

    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }

    fun getActivityActive(nameClass: String): Boolean {
        return getTopActivity()?.javaClass?.name == nameClass
    }

    fun getSingleActivity(): Boolean {
        return if (activities.isEmpty()) {
            false
        } else activities.size == 1
    }

    fun emptyActivity(): Boolean {
        return activities.isEmpty()
    }
}