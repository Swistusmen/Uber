package WebServerApplication;


import CommonDataTypes.Ride;
import CommonDataTypes.RideState;
import CommonDataTypes.TransactionData;

import java.awt.*;

public class MoneyOperations  {
double driverIncomePerKilometer;
double taxInPercent;
double app_tax=0.2,cancel_tax=0.5;

    public MoneyOperations(double driverIncome, double tax, double app_tax)  {
        this.taxInPercent=tax;
        this.driverIncomePerKilometer=driverIncome;
        this.app_tax=app_tax;
    }

    public TransactionData makeAnOperation(double cost, double distance) {
        TransactionData data=new TransactionData();
        data.TotalCost=cost;
        data.DriverIncome=distance*driverIncomePerKilometer;
        data.AppIncome=distance*app_tax;
        data.Tax=data.AppIncome*taxInPercent/100+data.DriverIncome*taxInPercent/500;
        return data;
    }
}
