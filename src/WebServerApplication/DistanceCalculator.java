package WebServerApplication;

import java.awt.*;
import java.util.HashMap;

public class DistanceCalculator {
    HashMap<String,Point> cityMap; //maps the city and the coordinates (square 4x4)

    public DistanceCalculator(){
        cityMap=new HashMap<String,Point>();
        cityMap.put("Debniki",new Point(1,1));
        cityMap.put("Kazimierz",new Point(20,10));
        cityMap.put("Zablocie",new Point(17,3));
        cityMap.put("Blonia",new Point(9,5));
        cityMap.put("Wawel",new Point(11,6));
        cityMap.put("Grzegorzki",new Point(2,15));
        cityMap.put("AGH",new Point(8,8));
        cityMap.put("Kleparz",new Point(12,2));
        cityMap.put("Teatr Slowackiego",new Point(10,10));
    }

    public double getDistance(String currentPosition, String destinationPosition) throws Exception
    {
        Point current;
        Point destination;
        if((current= cityMap.get(currentPosition))==null)
            throw new Exception("There is no such a location in our system- current");
        if((destination= cityMap.get(destinationPosition))==null)
            throw new Exception("There is no such a location in our system- destination");
        return Math.sqrt(Math.pow(current.x-destination.x,2)+Math.pow(current.y-destination.y,2));
    }
}
