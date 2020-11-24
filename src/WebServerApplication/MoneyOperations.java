package WebServerApplication;


import CommonDataTypes.Ride;
import CommonDataTypes.RideState;

public class MoneyOperations  {
double client_outcome;
double driver_income;
double app_tax=0.2,cancel_tax=0.5;

    public MoneyOperations()  {    }

    public void GetClientMoney(Ride ride) throws Exception {
        PriceCalculator price = new PriceCalculator(ride);
        this.client_outcome=price.getPrice();
    }

    public void SendClientMoneyBack(Ride ride) throws Exception {
        PriceCalculator price = new PriceCalculator(ride);
        this.client_outcome=(1-cancel_tax)*price.getPrice();
    }

    public void SendDriverMoney(Ride ride) throws Exception {
        PriceCalculator price = new PriceCalculator(ride);
        this.driver_income=(1-app_tax)* price.getPrice();
    }

    public double getClient_outcome() {
        return client_outcome;
    }

    public double getDriver_income() {
        return driver_income;
    }

}
