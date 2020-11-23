package CommonDataTypes;

import java.io.Serializable;

public enum RideState implements Serializable {
    Ordered,
    Unordered,
    Canceled,
    Finished
}
