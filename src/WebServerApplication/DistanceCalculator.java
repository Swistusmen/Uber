package WebServerApplication;

public class DistanceCalculator {
    int x; //0-10
    int y; //0-10

    public DistanceCalculator(int x, int y)throws Exception{
        if(x<0 || x>10 || y<0 || y>10){
            throw new Exception("Our services don't operate that far!");
        }
        this.x=x;
        this.y=y;
    }



}
