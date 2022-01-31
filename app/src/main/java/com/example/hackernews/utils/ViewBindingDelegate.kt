package com.example.hackernews.utils




import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Use [BaseFragment]'s secondary constructor so fragment.requireView() does not throws an Exception
 */
class ViewBindingDelegate<T : ViewBinding>(private inline val bindingFunction: (v: View) -> T) :
    ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    @MainThread
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {

        if (binding != null) return binding!!

        val view = thisRef.requireView()
        val lifecycleObserver = BindingLifecycleObserver()
        thisRef.viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        return bindingFunction(view).also { vb ->
            binding = vb
        }
    }

    private inner class BindingLifecycleObserver : DefaultLifecycleObserver {

        @MainThread
        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            val handler = Handler(Looper.getMainLooper())
            /* Fragment.onDestroyView gets called after LifecycleObserver's onDestroy.
            * With handler.post() with secure the binding gets null out after the onDestroyView function
            * runs.
            * */
            handler.post {
                binding = null
                Log.d("", "Binding have been null out.")
            }
        }
    }
}

inline fun <reified T : ViewBinding> Fragment.viewBinding(noinline bindingFunction: (v: View) -> T): ReadOnlyProperty<Fragment, T> {
    return ViewBindingDelegate(bindingFunction)
}