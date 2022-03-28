// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy
package edu.uncw.icons;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class that defines a general purpose icon to be used in JavaFX.
 * <p>
 *     An Icon is an arbitrary SVGPath restrained to a specific height.
 *     Icon's should be wrapped in an {@link edu.uncw.btns.IconBtn} before being used.
 * </p>
 */
public class Icon extends AbsIcon {
    private final StringProperty hoverColor = new SimpleStringProperty(getColor());

    public Icon(int height, IconType iconType) {
        init(height, iconType);
    }

    /**
     * Defines Icon style when hovered over in the UI.
     */
    public void hoverIn() {
        styleIcon(getHoverColor());
    }

    /**
     * Defines Icon style when no longer hovered over in UI.
     */
    public void hoverOut() {
        styleIcon(getColor());
    }

    /**
     * The color used when an Icon is hovered over.
     * @return String representing color in hexadecimal.
     */
    public String getHoverColor() {
        return hoverColor.get();
    }

    /**
     * Specify the color of the Icon when hovered over.
     * @param hoverColor String representing color in hexadecimal.
     */
    public void setHoverColor(String hoverColor) {
        this.hoverColor.set(hoverColor);
    }

}
