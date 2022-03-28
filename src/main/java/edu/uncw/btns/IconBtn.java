// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy
package edu.uncw.btns;

import edu.uncw.icons.Icon;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * A feature extended JavaFX button that uses {@link Icon}s.
 */
public class IconBtn extends AbsBtn {
    private final StringProperty iconColor = new SimpleStringProperty();
    private final StringProperty iconHoverColor = new SimpleStringProperty();
    private final StringProperty iconSelectedColor = new SimpleStringProperty();
    private final HoverEvent hoverEvent = new HoverEvent();

    private final ObjectProperty<Icon> icon = new SimpleObjectProperty<>();

    public IconBtn(Icon icon) {
        setIcon(icon);
        bindIcon();
        setGraphic(icon);
    }

    private void bindIcon() {
        setIconColor(getIcon().getColor());
        setIconHoverColor(getIcon().getHoverColor());
        setIconSelectedColor(getIcon().getSelectedColor());
        iconColor.addListener(
                (observableValue, oldStr, newStr) -> getIcon().setColor(newStr)
        );
        iconHoverColor.addListener(
                (observableValue, oldStr, newStr) -> getIcon().setHoverColor(newStr)
        );
        iconSelectedColor.addListener(
                (observableValue, oldStr, newStr) -> getIcon().setSelectedColor(newStr)
        );
    }

    /**
     * Toggles styling on hover for the btn.
     * @param val boolean specifying enabled or disabled
     */
    public void setBtnHoverEffects(boolean val) {
        if (val) {
            addEventFilter(MouseEvent.ANY, hoverEvent);
        } else {
            removeEventFilter(MouseEvent.ANY, hoverEvent);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        getIcon().setSelected(selected);
    }

    /**
     * @return {@link Icon} of the btn.
     */
    public Icon getIcon() {
        return icon.get();
    }

    /**
     * Sets the btn's {@link Icon}.
     * @param icon {@link Icon} to use in the btn.
     */
    public void setIcon(Icon icon) {
        this.icon.set(icon);
        bindIcon();
        setGraphic(icon);
    }

    /**
     * Sets the color of the icon.
     * @param iconColor String representing color in hexadecimal
     */
    public void setIconColor(String iconColor) {
        this.iconColor.set(iconColor);
    }

    /**
     * Sets the color of the icon while hovered over.
     * @param iconHoverColor String representing color in hexadecimal.
     */
    public void setIconHoverColor(String iconHoverColor) {
        this.iconHoverColor.set(iconHoverColor);
    }

    /**
     * Sets the color of the icon when selected.
     * @param iconSelectedColor String representing color in hexadecimal.
     */
    public void setIconSelectedColor(String iconSelectedColor) {
        this.iconSelectedColor.set(iconSelectedColor);
    }

    class HoverEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (!isSelected()) {
                if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
                    getIcon().hoverIn();
                    setBtnStyle(getBtnHoverColor());
                } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
                    getIcon().hoverOut();
                    setBtnStyle(getBtnColor());
                }
            }
        }
    }
}
