package functions;
import java.io.*;



public abstract class TabulatedFunctions
{
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount)
    {
        double[] temp =new double[pointsCount];
        if (function.getLeftDomainBorder() < leftX || function.getRightDomainBorder() < rightX)
            throw new IllegalArgumentException();
        else
        {
            double interval = (rightX - leftX) / (pointsCount - 1);
            double l = leftX;
            FunctionPoint a;
            for (int i = 0; i < pointsCount; ++i){
                temp[i] = function.getFunctionValue(l);
                l += interval;
            }
            return new ArrayTabulatedFunction(leftX, rightX, temp);
        }
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException
    {
        DataOutputStream out2 = new DataOutputStream(out);
        out2.writeInt(function.getPointsCount());
        for(int i = 0; i < function.getPointsCount(); ++i)
        {
            out2.writeDouble(function.getPoint(i).getX());
            out2.writeDouble(function.getPoint(i).getY());
        }
        out2.close();
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        DataInputStream in1 = new DataInputStream(in);
        int size = in1.readInt();
        FunctionPoint[] temp = new FunctionPoint[size];

        for(int i = 0; i < size; ++i)
        {
            temp[i] = new FunctionPoint(in1.readDouble(),in1.readDouble());
        }

        return new LinkedListTabulatedFunction(temp);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException{
//Метод записи табулированной функции в символьный поток
        int num = function.getPointsCount();
        PrintWriter writer = new PrintWriter(out);
        writer.println(num);
        for (int i = 0; i < num; i++) {
            writer.println(function.getPointX(i));
            writer.println(function.getPointY(i));
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException{
//Метод чтения табулированной функции из символьного потока
        StreamTokenizer of = new StreamTokenizer(in); //создали объект потока
        of.nextToken();
        int num = (int) of.nval;
        FunctionPoint[] points = new FunctionPoint[num];
        double x, y;
        for (int i = 0; i < num; i++) {
            of.nextToken();
            x = of.nval;
            of.nextToken();
            y = of.nval;
            points[i] = new FunctionPoint(x, y);
        }
        return new ArrayTabulatedFunction(points);
    }
}


