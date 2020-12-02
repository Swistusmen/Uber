package WebServerApplication;

import java.awt.*;
import java.util.HashMap;

public class DistanceCalculator {
    HashMap<String,Point> cityMap; //maps the city and the coordinates (square 4x4)

    public DistanceCalculator(){
        cityMap=new HashMap<String,Point>();
        cityMap.put("Debniki",new Point(1,1));
        cityMap.put("Kazimierz",new Point(2,1));
        cityMap.put("Zablocie",new Point(3,1));
        cityMap.put("Blonia",new Point(1,2));
        cityMap.put("Wawel",new Point(2,2));
        cityMap.put("Grzegorzki",new Point(3,2));
        cityMap.put("AGH",new Point(1,2));
        cityMap.put("Kleparz",new Point(2,2));
        cityMap.put("Teatr Slowackiego",new Point(3,2));
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
