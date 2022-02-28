package functions;
import java.io.*;

public class ArrayTabulatedFunction implements TabulatedFunction, Externalizable, Serializable, Cloneable{

    private FunctionPoint[] arr;
    private int n;


    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX || pointsCount < 2)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            n = pointsCount;

            arr = new FunctionPoint[pointsCount * 2];

            double interval = (rightX - leftX) / (pointsCount - 1);
            for (int i = 0; i < pointsCount; ++i)
            {
                arr[i] = new FunctionPoint(leftX + i * interval, 0);

            }
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
        if (leftX >= rightX || values.length < 2) {
            throw new IllegalArgumentException();
        } else {
            n = values.length;

            arr = new FunctionPoint[values.length * 2];

            double interval = (rightX - leftX) / (values.length - 1);
            for (int i = 0; i < values.length; ++i) {
                arr[i] = new FunctionPoint(leftX + i * interval, values[i]);
            }
        }
    }

    public ArrayTabulatedFunction(FunctionPoint[] a)
    {
        if (a.length < 2)
        {
            throw  new IllegalArgumentException();
        }
        int i = 0;

        while (i < a.length - 1)
        {
            if(a[i].getX() > a[i+1].getX()) {
                break;
            }
            ++i;
        }

        if(i != a.length - 1)
        {
            throw  new IllegalArgumentException();
        }

        arr = new FunctionPoint[a.length*2];

        for (int j = 0; j < a.length; ++j )
        {
            arr[j] = a[j];
        }
        n = a.length;
    }

    public double getLeftDomainBorder()
    {
        return arr[0].getX();
    }

    public double getRightDomainBorder() {
            return arr[n - 1].getX();
    }

    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        } else {

            int i = 0;

            while ((!(arr[i].getX() <= x && x <= arr[i + 1].getX())) || !(i < n)) {
                ++i;
            }
            if (arr[i].getX() == x) {
                return arr[i].getY();
            }
            if (arr[i + 1].getX() == x) {
                return arr[i + 1].getY();
            }

            //y=kx+b
            double n_y, k, b;

            k = (arr[i + 1].getY() - arr[i].getY()) / (arr[i + 1].getX() - arr[i].getX());
            b = arr[i].getY() - k * arr[i].getX();
            return n_y = k * x + b;
        }
    }

    public int getPointsCount() {
        return n;
    }

    public FunctionPoint getPoint(int index)
    {
        if (index < 0 || index >= n) {
            throw new FunctionPointIndexOutOfBoundsException();
        } else {
            return arr[index];
        }
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index < 0 || index >= n) {
            throw new FunctionPointIndexOutOfBoundsException();
        } else {
            if ((arr[index - 1].getX() < point.getX()) && (point.getX() < arr[index + 1].getX())) {
                arr[index] = point;
            }
            else
            {
                throw  new InappropriateFunctionPointException();
            }
        }
    }

    public double getPointX(int index) {
        if (index < 0 || index >= n) {
            throw new FunctionPointIndexOutOfBoundsException();

        } else {
            return arr[index].getX();
        }
    }

    public double getPointY(int index) {
        if (index < 0 || index >= n) {
            throw new FunctionPointIndexOutOfBoundsException();

        } else {
            return arr[index].getY();
        }
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException{
        if (index < 0 || index >= n) {
            throw new FunctionPointIndexOutOfBoundsException();
        } else {
            if ((arr[index - 1].getX() < x) && (x < arr[index + 1].getX())) {
                FunctionPoint nw = new FunctionPoint(x, arr[index].getY());
                arr[index] = nw;
            }
            else
            {
                throw new InappropriateFunctionPointException();
            }
        }
    }

    public void setPointY(int index, double y) {
        if (index < 0 || index>n)
        {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        else {
            FunctionPoint nw = new FunctionPoint(arr[index].getX(), y);
            arr[index] = nw;
        }

    }

    public void deletePoint(int index) {
        if (n < 3) {
            throw new IllegalStateException();
        } else {
            if (index < 0 || index >= n) {
                throw new FunctionPointIndexOutOfBoundsException();
            } else {
                System.arraycopy(arr, index + 1, arr, index, n - index - 1);
                --n;
            }
        }
    }

     public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        int i = 0;
        while ((i < n) || (arr[i].getX() == point.getX())) {
            ++i;
        }
        if (i != n) {
            throw new InappropriateFunctionPointException();
        } else {
            if (!(n < arr.length)) {
                FunctionPoint[] tempFpArray = new FunctionPoint[n * 2];
                System.arraycopy(arr, 0, tempFpArray, 0, n);
                arr = tempFpArray;
            }

            i = 0;

            if (arr[n - 1].getX() < point.getX()) {
                arr[n] = point;

            } else {
                while ((i < n) && (arr[i].getX() < point.getX())) {
                    ++i;
                }
                System.arraycopy(arr, i, arr, i + 1, n);
                arr[i] = point;

            }
            ++n;
        }
    }


    @Override
    public String toString() {
       String res = "{";
       res += arr[0];
       for(int i = 1; i < n; ++i)
       {
           res +=  ", " + arr[i].toString();
       }
       res += "}";
       return res;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof TabulatedFunction)){
            return false;
        }
        else if (o instanceof ArrayTabulatedFunction)
        {
            if(n != ((ArrayTabulatedFunction) o).n){
                return false;
            }
            else
            {
                int i = 0;
                while (i < n) {
                   if(!arr[i].equals(((ArrayTabulatedFunction) o).arr[i]))
                   {
                       break;
                   }
                   ++i;
                }
                if (i == n)
                {
                    return true;
                }
                else
                {
                    return false;
                }

            }
        }
        else
        {
            if(n != ((TabulatedFunction) o).getPointsCount()){
                return false;
            }
            else
            {
                int i = 0;
                while ( i < n) {

                    if (!(arr[i].equals(((TabulatedFunction) o).getPoint(i))))
                        break;
                    ++i;
                }
                if (i == n)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
    }
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(arr);
        out.writeObject(n);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        arr = (FunctionPoint[]) in.readObject();
        n = in.read();
    }

    @Override
    public int hashCode() {
        int result = 31;
        result += 31 * result + (int)n;
        for (int i = 0; i < n; i++){
            result += 31 * result + arr[i].hashCode();
        }
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        try {
            FunctionPoint[] a = new FunctionPoint[n];
            for(int i = 0; i < this.n; ++i)
            {
                a[i] = this.arr[i];
            }
            return (Object) new ArrayTabulatedFunction(a);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void addNodeByIndex(int index, FunctionPoint data) throws InappropriateFunctionPointException {
        if (index < 0 || index > n - 1){
            throw new FunctionPointIndexOutOfBoundsException();
        }
        if (arr[index - 1].getX() < data.getX() && arr[index].getX() > data.getX()){
            FunctionPoint[] c = new FunctionPoint[arr.length + 10];
            System.arraycopy(arr,0,c,0,index);
            c[index] = data;
            System.arraycopy(arr,index,c,index+1,arr.length - index);
            arr = c;
            n += 1;
        }
        else {
            throw new InappropriateFunctionPointException();
        }
    }
}
