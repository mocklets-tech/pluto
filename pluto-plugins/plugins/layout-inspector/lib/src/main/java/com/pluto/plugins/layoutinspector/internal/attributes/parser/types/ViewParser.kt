package com.pluto.plugins.layoutinspector.internal.attributes.parser.types

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import com.pluto.plugins.layoutinspector.internal.attributes.parser.Attribute
import com.pluto.plugins.layoutinspector.internal.attributes.parser.AttributeTag
import com.pluto.plugins.layoutinspector.internal.attributes.parser.IParser
import com.pluto.plugins.layoutinspector.internal.attributes.parser.ParserUtils.Companion.formatDrawable
import com.pluto.plugins.layoutinspector.internal.attributes.parser.ParserUtils.Companion.formatLayoutParam
import com.pluto.plugins.layoutinspector.internal.attributes.parser.ParserUtils.Companion.formatVisibility
import com.pluto.utilities.extensions.px2dp

internal class ViewParser : IParser<View>() {

    override fun getTypeAttributes(view: View): List<Attribute> {
        val attributes = arrayListOf<Attribute>()
        val params: ViewGroup.LayoutParams = view.layoutParams
        val paramsAttribute = Attribute("layout_params", params.javaClass.name, parameterizedTypeString)
        attributes.add(paramsAttribute)
        val widthAttribute = Attribute("layout_width", formatLayoutParam(params.width, view.width), parameterizedTypeString, AttributeTag.LayoutWidth)
        attributes.add(widthAttribute)
        val heightAttribute = Attribute("layout_height", formatLayoutParam(params.height, view.height), parameterizedTypeString, AttributeTag.LayoutHeight)
        attributes.add(heightAttribute)
        val visibilityAttribute =
            Attribute("visibility", formatVisibility(view.visibility), parameterizedTypeString, AttributeTag.Visibility)
        attributes.add(visibilityAttribute)
        val paddingLeftAttribute = Attribute(
            "padding_left", "${view.paddingLeft.px2dp} dp", parameterizedTypeString, AttributeTag.PaddingLeft
        )
        attributes.add(paddingLeftAttribute)
        val paddingTopAttribute = Attribute(
            "padding_top", "${view.paddingTop.px2dp} dp", parameterizedTypeString, AttributeTag.PaddingTop
        )
        attributes.add(paddingTopAttribute)
        val paddingRightAttribute = Attribute(
            "padding_right", "${view.paddingRight.px2dp} dp", parameterizedTypeString, AttributeTag.PaddingRight
        )
        attributes.add(paddingRightAttribute)
        val paddingBottomAttribute = Attribute(
            "padding_bottom", "${view.paddingBottom.px2dp} dp", parameterizedTypeString, AttributeTag.PaddingBottom
        )
        attributes.add(paddingBottomAttribute)
        if (view.layoutParams != null && view.layoutParams is MarginLayoutParams) {
            val marginLayoutParams: MarginLayoutParams = view.layoutParams as MarginLayoutParams
            val marginLeftAttribute = Attribute(
                "margin_left", "${marginLayoutParams.leftMargin.px2dp} dp", parameterizedTypeString, AttributeTag.MarginLeft
            )
            attributes.add(marginLeftAttribute)
            val marginTopAttribute = Attribute(
                "margin_top", "${marginLayoutParams.topMargin.px2dp} dp", parameterizedTypeString, AttributeTag.MarginTop
            )
            attributes.add(marginTopAttribute)
            val marginRightAttribute = Attribute(
                "margin_right", "${marginLayoutParams.rightMargin.px2dp} dp", parameterizedTypeString, AttributeTag.MarginRight
            )
            attributes.add(marginRightAttribute)
            val marginBottomAttribute = Attribute(
                "margin_bottom", "${marginLayoutParams.bottomMargin.px2dp} dp", parameterizedTypeString, AttributeTag.MarginBottom
            )
            attributes.add(marginBottomAttribute)
        }
        val translationXAttribute = Attribute("translationX", "${view.translationX.px2dp} dp", parameterizedTypeString)
        attributes.add(translationXAttribute)
        val translationYAttribute = Attribute("translationY", "${view.translationY.px2dp} dp", parameterizedTypeString)
        attributes.add(translationYAttribute)
        val backgroundAttribute = Attribute("background", formatDrawable(view.background), parameterizedTypeString)
        attributes.add(backgroundAttribute)
        val alphaAttribute = Attribute("alpha", view.alpha.toString(), parameterizedTypeString, AttributeTag.Alpha)
        attributes.add(alphaAttribute)
        val tagAttribute = Attribute("tag", view.tag?.toString(), parameterizedTypeString)
        attributes.add(tagAttribute)
        val enableAttribute = Attribute("enable", view.isEnabled.toString(), parameterizedTypeString)
        attributes.add(enableAttribute)
        val clickAttribute = Attribute("clickable", view.isClickable.toString(), parameterizedTypeString)
        attributes.add(clickAttribute)
        val longClickableAttribute = Attribute("long_clickable", view.isLongClickable.toString(), parameterizedTypeString)
        attributes.add(longClickableAttribute)
        val focusAttribute = Attribute("focusable", view.isFocusable.toString(), parameterizedTypeString)
        attributes.add(focusAttribute)
        val contentDescriptionAttribute = Attribute("content_dscrptn", view.contentDescription?.toString(), parameterizedTypeString)
        attributes.add(contentDescriptionAttribute)
        return attributes
    }
}
