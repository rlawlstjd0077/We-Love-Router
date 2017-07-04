package controlsystem.viewmodel;

import javafx.beans.property.StringProperty;

/**
 * Created by dsm_025 on 2017-05-08.
 */
public class ConnectionListTableRowViewModel {
    private StringProperty assgiend;
    private StringProperty address;

    public ConnectionListTableRowViewModel(StringProperty assgiend, StringProperty address) {
        this.assgiend = assgiend;
        this.address = address;
    }

    public StringProperty assgiendProperty() {
        return assgiend;
    }

    public StringProperty addressProperty() {
        return address;
    }


}
