package com.duzzi.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.duzzi.sdk.core.bean.base.BaseViewModel
import com.duzzi.ui.utils.ClassUtil

abstract class BaseFragment<VM : BaseViewModel, VB : ViewBinding> : Fragment() {
    var firstLoad: Boolean = true

    private var _binding: VB? = null
    val binding: VB get() = _binding!!
    lateinit var viewModel: VM

    lateinit var mContext: Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        viewModel = ViewModelProvider(this).get(ClassUtil.getClass(this, 0))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val type = javaClass.genericSuperclass
//        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<VB>
        val clazz = ClassUtil.getClass<VB>(this, 1)
        val method = clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        _binding = method.invoke(null, layoutInflater, container, false) as VB
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        addObserver()
    }

    abstract fun addObserver()

    abstract fun initView(view: View)

    override fun onResume() {
        super.onResume()
        if (firstLoad) {
            loadData()
            firstLoad = false
        }
    }

    abstract fun loadData()


    override fun onDestroyView() {
        _binding = null
        firstLoad = true
        viewModel.destroy()
        super.onDestroyView()
    }

}