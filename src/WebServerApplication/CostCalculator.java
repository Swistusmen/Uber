package WebServerApplication;

import CommonDataTypes.Ride;

public class CostCalculator {
    double costForStart; //for maintaining application,
    double costFactor=3;

    public CostCalculator(double price, double priceFactor)
    {
        this.costForStart=price;
        this.costFactor=priceFactor;
    }

    double getCost(double distance) throws Exception
    {
        if(distance<0)
            throw new Exception("Bad logic in server! Distance is lower than 0");
        return distance*this.costFactor+this.costForStart;
    }

}
