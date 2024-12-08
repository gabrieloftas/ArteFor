package com.example.myapplication

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.RenderEffect
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi

// Definindo as matrizes de cores para cada tipo de daltonismo
val tritanopiaMatrix = ColorMatrix().apply {
    set(floatArrayOf(
        1f, 0f, 0f, 0f, 0f,
        0f, 0.5f, 0.5f, 0f, 0f,
        0f, 0.5f, 0.5f, 0f, 0f,
        0f, 0f, 0f, 1f, 0f
    ))
}

val deuteranopiaMatrix = ColorMatrix().apply {
    set(floatArrayOf(
        0.625f, 0.375f, 0f, 0f, 0f,
        0.7f, 0.3f, 0f, 0f, 0f,
        0.3f, 0.7f, 0f, 0f, 0f,
        0f, 0f, 0f, 1f, 0f
    ))
}

val protanopiaMatrix = ColorMatrix().apply {
    set(floatArrayOf(
        0.567f, 0.433f, 0f, 0f, 0f,
        0.558f, 0.442f, 0f, 0f, 0f,
        0f, 0.242f, 0.758f, 0f, 0f,
        0f, 0f, 0f, 1f, 0f
    ))
}

// Função para aplicar o filtro de daltonismo na View
@RequiresApi(Build.VERSION_CODES.S)
fun applyDaltonismFilter(view: View, colorMatrix: ColorMatrix) {
    val colorFilter = ColorMatrixColorFilter(colorMatrix)
    val renderEffect = RenderEffect.createColorFilterEffect(colorFilter)
    view.setRenderEffect(renderEffect)
}

// Função para remover o filtro
@RequiresApi(Build.VERSION_CODES.S)
fun removeFilter(view: View) {
    view.setRenderEffect(null) // Remove o efeito de cor
}
// Função para aplicar o filtro com base na seleção
@RequiresApi(Build.VERSION_CODES.S)
fun applyDaltonismFilterIfSelected(view: View, selectedFilter: String) {
    when (selectedFilter) {
        "Tritanopia" -> applyDaltonismFilter(view, tritanopiaMatrix)
        "Deuteranopia" -> applyDaltonismFilter(view, deuteranopiaMatrix)
        "Protanopia" -> applyDaltonismFilter(view, protanopiaMatrix)
        "Default" -> removeFilter(view)
    }
}