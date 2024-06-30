package top.stillmisty.memescreator.data.model

import android.media.Image

class MemeRequestPara(
    val images: List<Image>,
    val texts: List<String>,
    val args: String
) {
}