// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy
package edu.uncw.icons;

import javafx.beans.property.*;
import javafx.scene.layout.Region;
import javafx.scene.shape.SVGPath;

/**
 * Abstract Icon class that extends a JavaFX region. See {@link Icon}.
 */
public abstract class AbsIcon extends Region {
    private static final StringProperty defaultColor = new SimpleStringProperty("#333333");
    private final StringProperty color = new SimpleStringProperty(getDefaultColor());
    private final StringProperty selectedColor = new SimpleStringProperty(getColor());
    private final DoubleProperty ratio = new SimpleDoubleProperty();
    private final ObjectProperty<IconType> type = new SimpleObjectProperty<>();
    private final BooleanProperty selected = new SimpleBooleanProperty(false);

    /**
     * Initializes the icon via subclass instantiation.
     * @param height int in pixels representing the bound height of the Icon.
     * @param iconType enum value of {@link IconType} referenced to draw the Icon.
     */
    protected void init(int height, IconType iconType) {
        setType(iconType);
        setSize(height);
        styleIcon(getColor());
        defaultColor.addListener((observableValue, oldStr, newStr) -> {
            if (getColor().equals(oldStr)) {
                setColor(newStr);
            }
        });
    }

    protected void styleIcon(String color) {
        setStyle("-fx-background-color: " + color + ";-fx-background-radius: none");
    }

    protected void build() {
        SVGPath svg = new SVGPath();
        svg.setContent(IconPaths.get(getType()));
        setRatio(svg);
        setShape(svg);
        setScaleShape(true);
    }

    public void setSize(int height) {
        double width = height * getRatio();
        setMinSize(width, height);
        setPrefSize(width, height);
        setMaxSize(width, height);
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String iconColor) {
        this.color.set(iconColor);
        if (!isSelected()) {
            styleIcon(iconColor);
        }
    }

    public double getRatio() {
        return ratio.get();
    }

    protected void setRatio(SVGPath svg) {
        this.ratio.set(svg.getBoundsInLocal().getMaxX() / svg.getBoundsInLocal().getMaxY());
    }

    public IconType getType() {
        return type.get();
    }

    public void setType(IconType type) {
        this.type.set(type);
        build();
    }

    public String getSelectedColor() {
        return selectedColor.get();
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor.set(selectedColor);
        if (isSelected()) {
            styleIcon(selectedColor);
        } else {
            styleIcon(getColor());
        }
    }

    /**
     * Whether the Icon has been selected/ clicked on.
     * @return boolean representing the Icon's selected property.
     */
    public boolean isSelected() {
        return selected.get();
    }

    /**
     * Specify whether the icon has been selected or not.
     * @param selected boolean representing the Icon's selected property.
     */
    public void setSelected(boolean selected) {
        this.selected.set(selected);
        if (selected) {
            styleIcon(getSelectedColor());
        } else {
            styleIcon(getColor());
        }
    }

    /**
     * The default color of the Icon.
     * @return String representing the default color in hexadecimal.
     */
    public static String getDefaultColor() {
        return defaultColor.get();
    }
}
