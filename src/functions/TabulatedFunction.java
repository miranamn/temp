package functions;

public interface TabulatedFunction extends Function{

    public int getPointsCount();

    public FunctionPoint getPoint(int index);

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;

    public double getPointX(int index);

    public double getPointY(int index);

    public void setPointX(int index, double x) throws InappropriateFunctionPointException;

    public void setPointY(int index, double y);

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException;

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    public Object clone() throws CloneNotSupportedException;

    public void addNodeByIndex(int index, FunctionPoint data) throws InappropriateFunctionPointException;
}
