// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy
package edu.uncw.btns;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;


abstract class AbsBtn extends Button {
    private final StringProperty btnColor = new SimpleStringProperty("none");
    private final StringProperty btnRadius = new SimpleStringProperty("none");
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final StringProperty btnHoverColor = new SimpleStringProperty(getBtnColor());
    private final StringProperty btnSelectedColor = new SimpleStringProperty(getBtnColor());

    public AbsBtn() {
        setBtnStyle(getBtnColor());
        btnColor.addListener((observableValue, oldStr, newStr) -> setBtnStyle(newStr));
        btnRadius.addListener((observableValue, oldStr, newStr) -> setBtnStyle(getBtnColor()));
    }

    protected void setBtnStyle(String color) {
        setStyle("-fx-background-color: " + color + ";-fx-background-radius: " + getBtnRadius());
    }

    public void setStaticSize(double width, double height) {
        setMinSize(width, height);
        setPrefSize(width, height);
        setMaxSize(width, height);
    }

    public String getBtnColor() {
        return btnColor.get();
    }

    public String getBtnRadius() {
        return btnRadius.get();
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
        if (selected) {
            setBtnStyle(getBtnSelectedColor());
        } else {
            setBtnStyle(getBtnColor());
        }
    }

    public String getBtnHoverColor() {
        return btnHoverColor.get();
    }

    public StringProperty btnHoverColorProperty() {
        return btnHoverColor;
    }

    public void setBtnHoverColor(String btnHoverColor) {
        this.btnHoverColor.set(btnHoverColor);
    }

    public String getBtnSelectedColor() {
        return btnSelectedColor.get();
    }

    public void setBtnSelectedColor(String btnSelectedColor) {
        this.btnSelectedColor.set(btnSelectedColor);
    }
}
