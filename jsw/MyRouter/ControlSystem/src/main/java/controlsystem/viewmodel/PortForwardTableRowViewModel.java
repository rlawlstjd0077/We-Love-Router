package controlsystem.viewmodel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by dsm_025 on 2017-04-30.
 */
public class PortForwardTableRowViewModel {
    private StringProperty name;
    private StringProperty ip;
    private IntegerProperty extPort;
    private IntegerProperty intPort;

    public PortForwardTableRowViewModel(StringProperty name, StringProperty ip, IntegerProperty extPort, IntegerProperty intPort) {
        this.name = name;
        this.ip = ip;
        this.extPort = extPort;
        this.intPort = intPort;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty ipProperty() {
        return ip;
    }

    public IntegerProperty extPortProperty() {
        return extPort;
    }

    public IntegerProperty intPortProperty() {
        return intPort;
    }
}
