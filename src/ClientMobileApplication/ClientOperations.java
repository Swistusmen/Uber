package ClientMobileApplication;
import CommonDataTypes.Ride;

public interface ClientOperations {
   Ride LookForADrive();
   void ReserveADrive();
}
