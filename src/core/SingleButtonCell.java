/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;

public class SingleButtonCell<T> extends TableCell<T, T>{

    ClickListener<T> listener;

    public SingleButtonCell(ClickListener<T> listener) {
        this.listener = listener;
    }

    
    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText("");
            setGraphic(null);
        }else{
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
            icon.setFill(Color.WHITE);
            Button btn = new Button();
            btn.setFocusTraversable(false);
            btn.setPadding(new Insets(4));
            btn.getStyleClass().add("danger-btn");

            btn.setGraphic(icon);
            btn.setOnAction(v -> {
                listener.OnClick(item);
            });
            setGraphic(btn);
        }
    }
    
    
    
}
