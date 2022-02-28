package functions.meta;
import functions.Function;


public class Shift implements Function{


    private Function fun;
    private double kx;
    private double ky;


    public Shift(Function a, double kx, double ky)
    {
        this.fun = a;
        this.kx = kx;
        this.ky = ky;
    }

    @Override
    public double getLeftDomainBorder() {
        return fun.getLeftDomainBorder() + kx;
    }

    @Override
    public double getRightDomainBorder() {
        return fun.getRightDomainBorder() + kx;
    }

    @Override
    public double getFunctionValue(double x) {
        return fun.getFunctionValue(x) + ky;
    }
}
