package com.imceits.aungtuntun.toolsbox.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter <Type : RecyclerView.ViewHolder?, T> : RecyclerView.Adapter<Type>() {

    public abstract fun setData(@Nullable data: List<T>)
}