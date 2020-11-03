package DriverMobileApplication;

public interface DriverOperations {
  Ride LookForAClient();
    float CheckWallet();
    boolean AcceptARide();
    boolean ReportDriveHasEnded();
    boolean ReportAProblem();  
}
