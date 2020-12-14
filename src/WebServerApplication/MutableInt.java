package WebServerApplication;
public class MutableInt {
    volatile static public int val;
    static synchronized  public int getVal(){return val;};
    static synchronized public void setVal(int a){val=a;};
}
