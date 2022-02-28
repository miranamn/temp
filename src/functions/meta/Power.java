package functions.meta;
import functions.Function;

public class Power implements Function {

    private Function base;
    private double step;

    public Power(Function base, double step)
    {
        this.base = base;
        this.step = step;
    }

    @Override
    public double getFunctionValue(double x) {
        return  Math.pow(base.getFunctionValue(x), step);
    }

    @Override
    public double getRightDomainBorder() {
        return base.getRightDomainBorder();
    }

    @Override
    public double getLeftDomainBorder() {
        return base.getLeftDomainBorder();
    }
}
