package functions.meta;
import functions.Function;

public class Scale implements Function{

    private Function fun;
    private double kx;
    private double ky;

    public Scale(Function a,double kx, double ky)
    {
        this.fun = a;
        this.kx = kx;
        this.ky = ky;
    }

    @Override
    public double getFunctionValue(double x) {
        return fun.getFunctionValue(x) * ky;
    }

    @Override
    public double getRightDomainBorder() {
        return fun.getRightDomainBorder() * kx;
    }

    @Override
    public double getLeftDomainBorder() {
        return fun.getLeftDomainBorder() * kx;
    }
}
