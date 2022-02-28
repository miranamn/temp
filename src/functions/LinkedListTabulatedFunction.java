package functions;

import java.io.*;

public class LinkedListTabulatedFunction implements TabulatedFunction, Externalizable, Serializable
{
    private FunctionNode head;
    private int lenghtList;

    public LinkedListTabulatedFunction() {
        this.head = new FunctionNode();
        this.lenghtList = 0;
    }

    public LinkedListTabulatedFunction(FunctionPoint[] a)
    {
        this(a.length);
        if (a.length < 2)
        {
            throw  new IllegalArgumentException();
        }
        int i =0;

        while (i < a.length - 1)
        {
            if(a[i].getX() > a [i+1].getX()) {
                break;
            }

            ++i;
        }

        if(i != a.length - 1)
        {
            throw  new IllegalArgumentException();
        }

        FunctionNode r = head.next;

        for (int j = 0; j < a.length; ++j )
        {
            r.point = a[j];
            r = r.next;
        }
        lenghtList = a.length;
    }

    private LinkedListTabulatedFunction(int amount) {
        this.head = new FunctionNode();
        FunctionNode tempNode = this.head;
        while(amount-- > 0)
        {
            tempNode = tempNode.next = this.addNodeToTail();
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount){
        this(pointsCount);
        if (leftX >= rightX || pointsCount < 2) {
            throw new IllegalArgumentException();
        } else {

            lenghtList = pointsCount;
            double interval = (rightX - leftX) / (pointsCount - 1);
            for (int i = 0; i < pointsCount; ++i)
            {
                this.getNodeByIndex(i).point = new FunctionPoint(leftX + i * interval, 0);
            }
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values) {
        this(values.length);
        if (leftX >= rightX || values.length < 2) {
            throw new IllegalArgumentException();
        } else {

            lenghtList = values.length;
            double interval = (rightX - leftX) / (values.length - 1);
            for (int i = 0; i < values.length; ++i)
            {
                this.getNodeByIndex(i).point=new FunctionPoint(leftX + i * interval, values[i]);
            }
        }
    }

    private FunctionNode getNodeByIndex(int index)
    {
        if (index < 0 || index > lenghtList)
        {
            throw new  FunctionPointIndexOutOfBoundsException();
        }
        else
        {
            FunctionNode temp = head.next;
            while (index-- > 0) {
                temp = temp.next;
            }
            return temp;
        }
    }

    private FunctionNode addNodeToTail()
    {
        FunctionNode currNode = this.head;
        int i = this.lenghtList;

        while(i-- > 0) {
            currNode = currNode.next;
        }

        currNode.next = new FunctionNode();
        if(currNode != this.head)
            currNode.next.prev = currNode;

        currNode.next.next = this.head.next;

        lenghtList++;
        return currNode.next;
    }

    public FunctionNode addNodeToTail(FunctionPoint point) throws InappropriateFunctionPointException {
        if (head.next == head || getNodeByIndex(lenghtList - 1).point.getX() < point.getX()) {
            FunctionNode node = new FunctionNode(point);
            lenghtList += 1;
            if (head.next == head) {
                head.next = head.prev = node;
                node.next = node.prev = node;
                return node;
            } else if (head.next == head.prev) {
                head.prev.prev = head.prev.next = node;
                node.next = node.prev = head.prev;
                head.prev = node;
                return node;
            } else {
                head.next.prev = node;
                head.prev.next = node;
                node.next = head.next;
                node.prev = head.prev;
                head.prev = node;
                return node;
            }
        }
        else {
            throw new InappropriateFunctionPointException();
        }
    }

    public void addNodeByIndex(int index, FunctionPoint data) throws InappropriateFunctionPointException {
        if (index < 0 || index > lenghtList - 1){
            throw new FunctionPointIndexOutOfBoundsException();
        }
        FunctionNode node = new FunctionNode(data);
        FunctionNode point = getNodeByIndex(index);
        if (index == 0 && point.point.getX() > node.point.getX()) {
            head.prev.next = node;
            node.prev = point.prev;
            point.prev = node;
            node.next = point;
            head.next = node;
        } else if (point.prev.point.getX() < node.point.getX() && point.point.getX() > node.point.getX()) {
            point.prev.next = node;
            node.prev = point.prev;
            node.next = point;
            point.prev = node;
        } else {
            throw new InappropriateFunctionPointException();
        }
        lenghtList += 1;

    }
    private   FunctionNode deleteNodeByIndex(int index)
    {
        FunctionNode nodeToBeDeleted = this.getNodeByIndex(index);
        FunctionNode prevNode = nodeToBeDeleted.prev;
        FunctionNode nextNode = nodeToBeDeleted.next;

        if(nodeToBeDeleted != null) {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;

            if(index == 0)
                this.head.next = nextNode;
        }

        return nodeToBeDeleted;
    }








    public double getLeftDomainBorder()
    {
        return head.next.point.getX();
    }

    public double getRightDomainBorder() {
        return getNodeByIndex(lenghtList-1).point.getX();
    }

    public double getFunctionValue(double x) {
        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        } else {
            FunctionNode r=head.next;

            while (!(r.point.getX() <= x && x <= r.next.point.getX())) {
                r=r.next;
            }
            if (r.point.getX() == x) {
                return r.point.getY();
            }
            if (r.next.point.getX() == x) {
                return r.next.point.getY();
            }

            //y=kx+b
            double n_y, k, b;

            k = (r.next.point.getY() - r.point.getY()) / (r.next.point.getX() - r.point.getX());
            b =r.point.getY() - k * r.point.getX();
            return n_y = k * x + b;
        }
    }

    public int getPointsCount() {
        return lenghtList;
    }

    public FunctionPoint getPoint(int index) {
        if (index < 0 || index >=   lenghtList) {
            throw new FunctionPointIndexOutOfBoundsException();
        } else {

            return getNodeByIndex(index).point;
        }
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (index < 0 || index >= lenghtList) {
            throw new FunctionPointIndexOutOfBoundsException();
        } else {
            FunctionNode r= getNodeByIndex(index);

            if ((r.prev.point.getX() < point.getX()) && (point.getX() < r.next.point.getX())) {
                r.point = point;
            }
            else
            {
                throw  new InappropriateFunctionPointException();
            }
        }
    }

    public double getPointX(int index) {
        if (index < 0 || index >= lenghtList) {
            throw new FunctionPointIndexOutOfBoundsException();

        } else {
            return getNodeByIndex(index).point.getX();
        }
    }

    public double getPointY(int index) {
        if (index < 0 || index >= lenghtList) {
            throw new FunctionPointIndexOutOfBoundsException();

        } else {
            return getNodeByIndex(index).point.getY();
        }
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException{
        if (index < 0 || index >= lenghtList) {
            throw new FunctionPointIndexOutOfBoundsException();
        } else {
            FunctionNode r= getNodeByIndex(index);
            if ((r.prev.point.getX() < x) && (x < r.next.point.getX())) {
                FunctionPoint nw = new FunctionPoint(x, r.point.getY());
                r.point = nw;
            }
            else
            {
                throw new InappropriateFunctionPointException();
            }
        }
    }

    public void setPointY(int index, double y) {
        if (index < 0 || index > lenghtList)
        {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        else {

            FunctionPoint nw = new FunctionPoint(getNodeByIndex(index).point.getX(), y);
            getNodeByIndex(index).point = nw;
        }

    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if(lenghtList > 2) {
            if (index >= 0 && index < lenghtList - 1) {
                this.deleteNodeByIndex(index);
            } else if (index == lenghtList - 1) {
                this.deleteNodeByIndex(index);
            }else
                throw new FunctionPointIndexOutOfBoundsException();

            lenghtList--;
        }else {
            throw new InappropriateFunctionPointException();
        }
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (head.next == head || getNodeByIndex(lenghtList - 1).point.getX() < point.getX()) {
            FunctionNode node = new FunctionNode(point);
            lenghtList += 1;
            if (head.next == head) {
                head.next = head.prev = node;
                node.next = node.prev = node;
            } else if (head.next == head.prev) {
                head.prev.prev = head.prev.next = node;
                node.next = node.prev = head.prev;
                head.prev = node;
            } else {
                head.next.prev = node;
                head.prev.next = node;
                node.next = head.next;
                node.prev = head.prev;
                head.prev = node;
            }
        }
        else {
            throw new InappropriateFunctionPointException();
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.head);

        out.writeObject(this.lenghtList);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this. head = (FunctionNode) in.readObject();

        this.lenghtList = in.read();
    }

    @Override
    public String toString() {
        String res = "{";
        res += this.head.next.point.toString();
        FunctionNode a = this.head.next.next;
        for(int i = 1; i < lenghtList; ++i)
        {
            res +=  ", " + a.point.toString();
            a = a.next;
        }
        res += "}";
        return res;
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof TabulatedFunction)){
            return false;
        }
        else if (o instanceof LinkedListTabulatedFunction)
        {
            if(lenghtList != ((LinkedListTabulatedFunction) o).lenghtList){
                return false;
            }
            else
            {
                int i = 0;
                FunctionNode a = this.head.next;
                FunctionNode b = ((LinkedListTabulatedFunction)o).head.next;
                while ( i < lenghtList) {
                    if (!a.point.equals(b.point) ) {
                        break;
                    }
                    ++i;
                    a = a.next;
                    b = b.next;
                }
                if (i == lenghtList)
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
            if(lenghtList != ((TabulatedFunction) o).getPointsCount()){
                return false;
            }
            else
            {
                int i = 0;
                FunctionNode a = this.head.next;
                while ( i < lenghtList) {
                    if (!a.point.equals(((TabulatedFunction) o).getPoint(i))) {
                        break;
                    }
                    ++i;
                    a= a.next;
                }
                if (i == lenghtList)
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

    @Override
    public int hashCode() {
        int result = 31;
        result += 31 * result + (int)lenghtList;
        FunctionNode a = this.head.next;
        for (int i = 0; i < lenghtList; i++){
            result += 31 * result + a.point.hashCode();
            a = a.next;
        }
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        try {
            FunctionPoint[] a = new FunctionPoint[lenghtList];
            FunctionNode b = this.head.next;
            for (int i = 0; i < this.lenghtList; ++i) {
                a[i] = b.point;
                b = b.next;
            }
            return (Object) new LinkedListTabulatedFunction(a);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }






    private class FunctionNode
    {
      private  FunctionPoint point;
      private FunctionNode next;
      private FunctionNode prev;

        public FunctionNode()
        {
            next=this;
            prev=this;
        }

        public FunctionNode(FunctionPoint point)
        {
            this.point = point;
            next = prev = this;
        }
    }
}