package functions.basic;
import functions.Function;

public abstract class TrigonometricFunction implements Function
{

    public abstract double getFunctionValue(double x);

    public double getLeftDomainBorder() {
        return Double.MIN_VALUE;
    }

    public double getRightDomainBorder() {
        return Double.MAX_VALUE;
    }
}
