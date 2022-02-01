package com.example.hackernews.ui.main.adapter

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


import androidx.core.content.ContextCompat
import com.example.hackernews.BaseApp
import com.example.hackernews.R
import com.example.hackernews.utils.Commons

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator




abstract class SwipeGesture : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )

            .addBackgroundColor( Commons.getColor(R.color.red_500))
            .addSwipeLeftLabel( Commons.getString(R.string.delete))
            .setSwipeLeftLabelColor(Commons.getColor(R.color.white))
            .create()

            .decorate()
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }


}