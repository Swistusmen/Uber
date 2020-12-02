package CommonDataTypes;

public class TransactionData {
    public double TotalCost;
    public double DriverIncome;
    public double Tax;
    public double AppIncome;

    public TransactionData()
    {
        this.TotalCost=-1.0;
        this.DriverIncome=-1.0;
        this.Tax=-1.0;
        this.AppIncome=-1.0;
    };

    public TransactionData(double cost, double driver, double tax,double income )
    {
        this.TotalCost=cost;
        this.DriverIncome=income;
        this.Tax=tax;
        this.AppIncome=income;
    }

    public double Sum()
    {
        return this.TotalCost+this.DriverIncome+this.Tax+this.AppIncome;
    }
}
