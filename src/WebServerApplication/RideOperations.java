package WebServerApplication;
import CommonDataTypes.Ride;
import CommonDataTypes.RideState;

public class RideOperations {

    public void RunRide(Ride ride) throws Exception {
        MoneyOperations currentride=new MoneyOperations();

        ride.state=RideState.Ordered;
        currentride.GetClientMoney(ride);

        ride.state=RideState.Finished;
        currentride.SendDriverMoney(ride);

    }




}
