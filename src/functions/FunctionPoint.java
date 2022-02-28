package functions;
import java.io.*;

public class FunctionPoint implements Serializable, Cloneable
{

    private double x;
    private double y;

    public FunctionPoint() //Конструктор по умолчанию
    {
        x = 0;
        y = 0;
    }

    public FunctionPoint(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point)
    {
        x = point.x;
        y = point.y;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    @Override
    public String toString(){
        return "(" + x + ";" + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof FunctionPoint))
            return false;
        else if (x == ((FunctionPoint) o).x && y == ((FunctionPoint) o).y)
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        int result = 31 + ((int)(Double.doubleToLongBits(this.x))) ^ ((int)((Double.doubleToLongBits(this.x) >> 32)));
        return 31 * result + ((int) (Double.doubleToLongBits(this.y)) ^((int) (Double.doubleToLongBits(this.y) >> 32)));

    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        return (Object) new  FunctionPoint(this.x,this.y);
    }

}