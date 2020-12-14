package WebServerApplication;
import CommonDataTypes.Ride;
import CommonDataTypes.RideState;

public class TicketObserver {
    RideState state=RideState.Unordered;
    private boolean updated=false;
    public int port=-1;

    public boolean wasTicketUpdatd(){
        return updated;
    }

    protected void setUpdate(RideState ride)
    {
        state=ride;
        updated=true;
    }

    protected void unsetUpdate()
    {
        state=RideState.Unordered;
        updated=false;
    }

}
