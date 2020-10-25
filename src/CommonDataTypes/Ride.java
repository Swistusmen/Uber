package CommonDataTypes;

import java.io.Serializable;

public class Ride implements Serializable {
    public String inputAddress;
    public String outputAddress;
    public String carNumber;
    public String phoneDriver;
    public String phoneClient;
    public RideState state=RideState.Unordered;
    public float price;

    public String printRide()
    {
        String text=inputAddress+" "+outputAddress+" "+carNumber+" "+phoneDriver+" "+phoneClient+" "+state.toString()+" "+price;
        return text;
    }
}

