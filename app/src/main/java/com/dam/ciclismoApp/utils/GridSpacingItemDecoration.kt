package com.dam.ciclismoApp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int, // Número de columnas
    private val spacing: Int, // Espaciado en píxeles
    private val includeEdge: Boolean // Incluir márgenes en los bordes
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // Posición del ítem
        val column = position % spanCount // Columna en la que está el ítem
        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) { // Si está en la primera fila
                outRect.top = spacing
            }
            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) { // Si no está en la primera fila
                outRect.top = spacing
            }
        }
    }
}