package com.pluto.plugins.layoutinspector.internal

import android.graphics.Rect
import android.view.View

internal class Element(val view: View) {

    private val originRect: Rect = Rect()
    val rect: Rect = Rect()
    private val location = IntArray(2)
    private val parentElement: Element?
        get() {
            val parentView: Any = view.parent
            return if (parentView is View) {
                Element(parentView)
            } else {
                null
            }
        }

    init {
        reset()
        originRect.set(rect.left, rect.top, rect.right, rect.bottom)
    }

    fun reset() {
        view.getLocationOnScreen(location)
        val width = view.width
        val height = view.height

        val left = location[0]
        val right = left + width
        val top = location[1]
        val bottom = top + height

        rect[left, top, right] = bottom
    }

    fun offset(dx: Float, dy: Float) {
        view.translationX = view.translationX + dx
        view.translationY = view.translationY + dy
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if ((o == null) || (javaClass != o.javaClass)) return false
        val element = o as Element
        return view == element.view
    }

    override fun hashCode(): Int {
        return view.hashCode()
    }
}