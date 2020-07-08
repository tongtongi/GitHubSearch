package com.example.findcar.commons

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val lastItemPosition = parent.adapter?.let { it.itemCount - 1 } ?: 0

        when (parent.getChildAdapterPosition(view)) {
            0 -> outRect.set(space, space, space, space / 2)
            lastItemPosition -> outRect.set(space, space / 2, space, space)
            else -> outRect.set(space, space / 2, space, space / 2)
        }

    }
}