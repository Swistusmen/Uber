package WebServerApplication;

import CommonDataTypes.Ride;

public class PriceCalculator {
    double price;
    double price_factor=3;

    private double calculate_distance(Ride ride) throws Exception {
        String start=ride.inputAddress;
        String destination=ride.outputAddress;

        char[] s = new char[start.length()];
        char[] d = new char[destination.length()];

        for (int i = 0; i < start.length(); i++) {
            s[i] = start.charAt(i);
            d[i] = destination.charAt(i);
        }
        int sx=Character.getNumericValue(s[0]);
        int sy=Character.getNumericValue(s[1]);
        int dx=Character.getNumericValue(d[0]);
        int dy=Character.getNumericValue(d[1]);

        DistanceCalculator a = new DistanceCalculator(sx,sy);
        DistanceCalculator b = new DistanceCalculator(dx,dy);

        double distance=Math.sqrt(((b.x-a.x)^2) + ((b.y-a.y)^2));

        return distance;
    }


    public PriceCalculator (Ride ride) throws Exception {
        price=5+price_factor*calculate_distance(ride);  //5 - price
        this.price = (double) Math.round(price * 100) / 100;
    }

    public double getPrice() {
        return price;
    }

}
