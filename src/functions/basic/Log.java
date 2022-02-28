package functions.basic;
import functions.Function;

public class Log implements Function
{
    private double base;

    public Log (double base)
    {
        this.base =  base;
    }
    public double getLeftDomainBorder(){
        return Double.MIN_NORMAL;
    }

    public double getRightDomainBorder()
    {
        return Double.MAX_VALUE;
    }

    public double getFunctionValue(double x)
    {
        return Math.log(x)/Math.log(base);
    }
}
