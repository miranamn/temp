package functions.meta;
import functions.Function;

public class Sum implements Function{
    private Function fun1;
    private Function fun2;

    public Sum(Function fun1, Function fun2)
    {
        this.fun1 = fun1;
        this.fun2 = fun2;
    }

    @Override
    public double getFunctionValue(double x) {
        return fun1.getFunctionValue(x) + fun2.getFunctionValue(x);
    }

    @Override
    public double getRightDomainBorder() {
        if (fun1.getRightDomainBorder() <= fun2.getRightDomainBorder())
            return fun1.getRightDomainBorder();
        else
            return fun2.getRightDomainBorder();
    }

    @Override
    public double getLeftDomainBorder() {
        if (fun1.getLeftDomainBorder() >= fun2.getLeftDomainBorder())
            return fun1.getLeftDomainBorder();
        else
            return fun2.getLeftDomainBorder();
    }
}
