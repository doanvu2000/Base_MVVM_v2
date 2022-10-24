package com.dd.company.baseapp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import com.dd.company.baseapp.base.BaseActivity
import com.dd.company.baseapp.base.fragment.BaseFragment
import com.dd.company.baseapp.base.BaseVMActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.Serializable
import java.lang.reflect.ParameterizedType

fun <KClass> Context.getGenericSuperclass(position: Int): Class<KClass> {
    return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[position] as Class<KClass>
}

fun FragmentActivity.getBaseVMActivity(): BaseVMActivity<*, *>? {
    return if (this is BaseVMActivity<*, *>) {
        this
    } else null
}

fun FragmentActivity.getBaseActivity(): BaseActivity<*>? {
    return if (this is BaseActivity<*>) {
        this
    } else null
}

fun FragmentActivity.getBaseFragment(): BaseFragment<*>? {
    return if (this is BaseFragment<*>) {
        this
    } else null
}

fun Fragment.getBaseFragment(): BaseFragment<*>? {
    return if (this is BaseFragment<*>) {
        this
    } else null
}

fun Activity.callResultActivity() {
    callResultActivity(null)
}

fun Activity.callResultActivity(bundle: Bundle?) {
    val returnIntent = Intent()
    if (bundle != null) returnIntent.putExtras(bundle)
    setResult(Activity.RESULT_OK, returnIntent)
    finish()
}

fun Context.openActivity(pClass: Class<out Activity>) {
    openActivity(pClass, null)
}

fun Context.openActivity(pClass: Class<out Activity>, isFinish: Boolean) {
    openActivity(pClass)
    if (isFinish) {
        (this as Activity).finish()
    }
}

fun Context.openActivity(pClass: Class<out Activity>, bundle: Bundle?) {
    val intent = Intent(this, pClass)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}


fun Context.openActivityForResult(
    pClass: Class<out Activity?>?,
    bundle: Bundle?,
    requestCode: Int,
) {
    val intent = Intent(this, pClass)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    (this as Activity).startActivityForResult(intent, requestCode)
}

fun Activity.openActivityForResult(pClass: Class<out Activity?>?, requestCode: Int) {
    openActivityForResult(pClass, null, requestCode)
}

fun <K : Serializable> pushParcelableBundle(k: K): Bundle {
    val bundle = Bundle()
    bundle.putSerializable(k.javaClass.name, k)
    return bundle
}

fun <K : Parcelable> pushParcelableBundle(k: K): Bundle {
    val bundle = Bundle()
    bundle.putParcelable(k.javaClass.name, k)
    return bundle
}

fun <K : Parcelable> AppCompatActivity.getParcelableBundle(key: String): K? {
    return when (intent != null && intent.extras!!.containsKey(key)) {
        true -> intent.extras?.getParcelable(key)
        else -> null
    }
}

/***
 * Fragment navigator in activity
 *
 */

private fun FragmentActivity.idFragmentContainer(): Int =
    getBaseActivity()?.idFragmentContainer() ?: 0

fun FragmentActivity.replaceFragment(
    fragment: Fragment,
    addToBackStack: Boolean? = false, transit: Int? = FragmentTransaction.TRANSIT_UNSET,
) {
    val transaction = this.supportFragmentManager.beginTransaction()
//        .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
        .setReorderingAllowed(true)
        .replace(idFragmentContainer(), fragment, fragment::class.java.name)
    addToBackStack?.let { if (it) transaction.addToBackStack(fragment::class.java.name) }
    transit?.let { if (it != FragmentTransaction.TRANSIT_UNSET) transaction.setTransition(it) }
    transaction.commit()
}

/**
 * Child fragment
 */

private fun Fragment.idFragmentContainer(): Int = getBaseFragment()?.idFragmentContainer() ?: 0

fun Fragment.replaceFragment(
    fragment: Fragment,
    addToBackStack: Boolean? = false, transit: Int? = FragmentTransaction.TRANSIT_UNSET,
) {
    val transaction = requireActivity().supportFragmentManager.beginTransaction()
//        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
        .setReorderingAllowed(true)
        .replace(requireActivity().idFragmentContainer(), fragment, fragment::class.java.name)
    addToBackStack?.let { if (it) transaction.addToBackStack(fragment::class.java.name) }
    transit?.let { if (it != FragmentTransaction.TRANSIT_UNSET) transaction.setTransition(it) }
    transaction.commit()
}

fun Fragment.replaceChildFragment(
    fragment: Fragment,
    addToBackStack: Boolean? = false, transit: Int? = FragmentTransaction.TRANSIT_UNSET,
) {
    val transaction = childFragmentManager.beginTransaction()
//        .setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
        .setReorderingAllowed(true)
        .replace(idFragmentContainer(), fragment, fragment::class.java.name)
    addToBackStack?.let { if (it) transaction.addToBackStack(fragment::class.java.name) }
    transit?.let { if (it != FragmentTransaction.TRANSIT_UNSET) transaction.setTransition(it) }
    transaction.commit()
}

fun Fragment.getCurrentFragment(): BaseFragment<*>? {
    return childFragmentManager.findFragmentById(idFragmentContainer()) as? BaseFragment<*>?
}

fun popChildFragment(parentFragment: Fragment) {
    parentFragment.childFragmentManager.popBackStack()
}

fun FragmentActivity.popFragment(fragment: Fragment) {
    supportFragmentManager.popBackStack()
}

@SuppressLint("WrongConstant")
private fun commitTransaction(
    transaction: FragmentTransaction, addToBackStack: Boolean = false,
    transit: Int = FragmentTransaction.TRANSIT_UNSET,
) {
    if (addToBackStack) transaction.addToBackStack(null)
    if (transit != FragmentTransaction.TRANSIT_UNSET) transaction.setTransition(transit)
    transaction.commit()
}

fun AppCompatActivity.findFragment(TAG: String): Fragment? {
    return supportFragmentManager.findFragmentByTag(TAG)
}

fun findChildFragment(parentFragment: Fragment, TAG: String): Fragment? {
    return parentFragment.childFragmentManager.findFragmentByTag(TAG)
}

fun findLastChildFragment(parentFragment: Fragment): Fragment {
    return parentFragment.childFragmentManager.fragments.last()
}

fun FragmentActivity.getCurrentFragment(): BaseFragment<*> {
    return supportFragmentManager.findFragmentById(idFragmentContainer()) as BaseFragment<*>
}

fun Fragment.popChildFragment(parentFragment: Fragment = this) {
    parentFragment.childFragmentManager.popBackStack()
}

fun Fragment.findChildFragment(parentFragment: Fragment = this, TAG: String): Fragment? {
    return parentFragment.childFragmentManager.findFragmentByTag(TAG)
}

fun Fragment.findLastChildFragment(parentFragment: Fragment): Fragment {
    return parentFragment.childFragmentManager.fragments.last()
}

fun Fragment.goToDialogFragment(baseDialog: DialogFragment, bundle: Bundle?) {
    if (requireActivity().isFinishing) return
    CoroutineScope(Dispatchers.Main).launch {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        baseDialog.arguments = bundle
        baseDialog.show(fragmentManager, baseDialog::class.java.name)
    }
}

fun AppCompatActivity.goToDialogFragment(baseDialog: DialogFragment, bundle: Bundle?) {
    val activity: AppCompatActivity = this
    if (activity.isFinishing) return
    CoroutineScope(Dispatchers.Main).launch {
        val fragmentManager: FragmentManager = activity.supportFragmentManager
        baseDialog.arguments = bundle
        baseDialog.show(fragmentManager, baseDialog::class.java.name)
    }
}

fun Activity.openSMS(sms_body: String, phone: String) {
    val intent = Intent(
        Intent.ACTION_SENDTO,
        Uri.parse("smsto:$phone")
    )
    intent.putExtra("sms_body", sms_body)
    this.startActivity(intent)
}

fun Activity.callPhone(phone: String) {
    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
    startActivity(intent)
}

fun FragmentActivity.openNetWorkSetting() {
    try {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val cn = ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings")
        intent.component = cn
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    } catch (ignored: ActivityNotFoundException) {
        startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    }
}


fun openPlayStore(context: Context?, link: String) {
    if (context == null) return
    val uri = Uri.parse(link)
    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
    goToMarket.addFlags(
        Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    )
    try {
        context.startActivity(goToMarket)
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + context.packageName)
            )
        )
    }
}

inline fun <reified T> pushBundle(data: T): Bundle {
    val bundle = Bundle()
    when (data) {
        is Long -> bundle.putLong(T::class.java.name, data)
        is Int -> bundle.putInt(T::class.java.name, data)
        is Float -> bundle.putFloat(T::class.java.name, data)
        is Boolean -> bundle.putBoolean(T::class.java.name, data)
        is String -> bundle.putString(T::class.java.name, data.toString())
        is Parcelable -> bundle.putParcelable(T::class.java.name, data)
        is Serializable -> bundle.putSerializable(T::class.java.name, data)
        else -> bundle.putString(T::class.java.name, data.toString())
    }
    return bundle
}

fun <T> pushBundle(key: String, data: T): Bundle {
    val bundle = Bundle()
    when (data) {
        is Long -> bundle.putLong(key, data)
        is Int -> bundle.putInt(key, data)
        is Float -> bundle.putFloat(key, data)
        is Boolean -> bundle.putBoolean(key, data)
        is String -> bundle.putString(key, data.toString())
        is Parcelable -> bundle.putParcelable(key, data)
        is Serializable -> bundle.putSerializable(key, data)
        else -> bundle.putString(key, data.toString())
    }
    return bundle
}
