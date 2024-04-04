package uk.co.gurbir.PROJECT;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MessageService {
    private final StringProperty messageProperty = new SimpleStringProperty();

    public void addMessage(String message) {
        messageProperty.set(message);
    }

    public StringProperty messageProperty() {
        return messageProperty;
    }
}
