// CSC 331 JavaFX Group Project
// By: Aaron Csetter, Nicholas Bradley, Noah Davis, Steven McCarthy
package edu.uncw.btns;

import edu.uncw.icons.Icon;
import edu.uncw.icons.IconType;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameBoardBtn extends AbsBtn {
    private final HoverEvent hoverEvent = new HoverEvent();
    private final int value;
    private final int row;
    private final int col;
    private boolean flagged = false;
    private boolean demoMode = false;

    public GameBoardBtn(int value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;
        setStaticSize(30, 30);
        setBtnSelectedColor("#cccccc");
        setBtnHoverColor("#abfffe");
        setBtnHoverEffects(true);
        setCursor(Cursor.HAND);
        if (value > 0) styleText();
    }

    public int getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public boolean isDemoMode() {
        return demoMode;
    }

    public void setDemoMode(boolean demoMode) {
        this.demoMode = demoMode;
    }

    public void show() {
        if (value == -1 && !isFlagged()) {
            setGraphic(new Icon(25, IconType.MINE));
            setSelected(true);
        } else if (value != -1 && isFlagged()) {
            setGraphic(new Icon(25, IconType.X) {{
                    setColor("#e54234");
            }});
        }
    }

    public void select() {
        if (value == -1) {
            setGraphic(new Icon(25, IconType.MINE) {{
                setBtnSelectedColor("#e54234");
            }});
        } else {
            if (value != 0) setText(String.valueOf(value));
        }

        setSelected(true);
        setCursor(Cursor.DEFAULT);
    }

    public void flag() {
        if (isSelected()) return;

        this.flagged = !flagged;
        if (isFlagged()) {
            Icon icon = new Icon(25, IconType.FLAG);
            icon.setColor("#e54234");
            setGraphic(icon);
        } else {
            setGraphic(null);
        }
    }

    private void styleText() {
        setFont(Font.font(Font.getDefault().getFamily(), FontWeight.BOLD, 12));
        switch(value) {
            case 1:
                setTextFill(Paint.valueOf("#0000ff"));
                break;
            case 2:
                setTextFill(Paint.valueOf("#1ea603"));
                break;
            case 3:
                setTextFill(Paint.valueOf("#e54234"));
                break;
            case 4:
                setTextFill(Paint.valueOf("#9900ff"));
                break;
            case 5:
                setTextFill(Paint.valueOf("#ff6a00"));
                break;
            case 6:
                setTextFill(Paint.valueOf("#00d5ff"));
                break;
            case 7:
                setTextFill(Paint.valueOf("#ff00a6"));
                break;
            case 8:
            default:
                setTextFill(Paint.valueOf("000000"));
        }
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


    class HoverEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            if (!isSelected()) {
                if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
                    setBtnStyle(getBtnHoverColor());
                    if (isDemoMode()) {
                        if(!isFlagged() && !isSelected()) {
                            if (getValue() > 0) {
                                setText(String.valueOf(getValue()));
                            }

                            if (getValue() < 0) {
                                setGraphic(new Icon(25, IconType.MINE));
                            }
                        }
                    }
                } else if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
                    setBtnStyle(getBtnColor());
                    if (isDemoMode()) {
                        if(!isFlagged() && !isSelected()) {
                            if (getValue() > 0) {
                                setText(null);
                            }

                            if (getValue() < 0) {
                                setGraphic(null);
                            }
                        }
                    }
                }
            }
        }
    }
}
