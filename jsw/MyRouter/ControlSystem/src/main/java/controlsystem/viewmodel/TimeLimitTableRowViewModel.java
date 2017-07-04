package controlsystem.viewmodel;

import javafx.beans.property.StringProperty;

/**
 * Created by dsm_025 on 2017-05-01.
 */
public class TimeLimitTableRowViewModel {
    private StringProperty name;
    private StringProperty start;
    private StringProperty end;

    public TimeLimitTableRowViewModel(StringProperty name, StringProperty start, StringProperty end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty startProperty() {
        return start;
    }

    public StringProperty endProperty() {
        return end;
    }
}
