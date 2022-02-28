package doc;

import com.company.FXMLMainForm;
import functions.LinkedListTabulatedFunction;
import functions.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TabulatedFunctionDoc implements TabulatedFunction
{
    private TabulatedFunction tb;
    private String filename;
    private boolean modified = false;
    private boolean fileNameAssigned = false;
    FXMLMainForm FXMLController;



    public TabulatedFunctionDoc(){};

    public void newFunction (double leftX, double rightX, int pointsCount)
    {
        tb = new LinkedListTabulatedFunction(leftX, rightX, pointsCount);
        modified = true;
    }

    public void tabulateFunction(Function function, double leftX, double rightX, int pointsCount)
    {
        tb = TabulatedFunctions.tabulate(function, leftX, rightX, pointsCount);
        modified = true;
    }

    public void saveFunctionAs(String DocName){
        JSONObject JObject = new JSONObject();
        JObject.put("F", tb.toString());
        try (FileWriter file = new FileWriter(DocName)){
            this.filename = filename;
            file.write(JObject.toJSONString());
            file.flush();
            modified = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFunction(String fileName){
        try (FileReader reader = new FileReader(fileName))
        {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            String temp = (String) jsonObject.get("F");
            FileWriter file = new FileWriter("t.txt");
            file.write(temp);
            FileReader reader1 = new FileReader("t.txt");
            file.close();
            tb = TabulatedFunctions.readTabulatedFunction(reader1);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void saveFunction(){
        JSONObject JObject = new JSONObject();
        JObject.put("F", tb.toString());
        try (FileWriter file = new FileWriter(filename + ".json")){
            file.write(JObject.toJSONString());
            file.flush();
            modified = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TabulatedFunction getTb(){
        return tb;
    }

    private void callRedraw() {
        this.FXMLController.redraw();
    }

    public void registerRedrawFunctionController(FXMLMainForm fxmlMainForm) {
        this.FXMLController = fxmlMainForm;
        callRedraw();
    }

    @Override
    public double getLeftDomainBorder() {
        return tb.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return tb.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return tb.getFunctionValue(x);
    }

    @Override
    public int getPointsCount() {
        return tb.getPointsCount();
    }

    @Override
    public FunctionPoint getPoint(int index) {
        return getPoint(index);
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        tb.setPoint(index, point);
    }

    @Override
    public double getPointX(int index) {
        return tb.getPointX(index);
    }

    @Override
    public double getPointY(int index) {
        return tb.getPointY(index);
    }

    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        tb.setPointX(index, x);
    }

    @Override
    public void setPointY(int index, double y) {
        tb.setPointY(index, y);
    }

    @Override
    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        tb.deletePoint(index);
        callRedraw();
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        tb.addPoint(point);
        callRedraw();
    }

    public Object clone() throws CloneNotSupportedException {
        TabulatedFunctionDoc documentTabulatedFunction = new TabulatedFunctionDoc();
        documentTabulatedFunction.tb =(TabulatedFunction) this.tb.clone();
        documentTabulatedFunction.modified = this.modified;
        documentTabulatedFunction.fileNameAssigned = this.fileNameAssigned;
        return documentTabulatedFunction;
    }

    @Override
    public String toString()
    {
        return this.tb.toString();
    }

    @Override
    public int hashCode() {
        return this.tb.hashCode();
    }
    @Override
    public void addNodeByIndex(int index, FunctionPoint data) throws InappropriateFunctionPointException{
        tb.addNodeByIndex(index, data);
        callRedraw();
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof TabulatedFunctionDoc) {
            TabulatedFunctionDoc comperedTabulatedFunctionDoc = (TabulatedFunctionDoc) obj;
            return this.tb.equals(comperedTabulatedFunctionDoc.tb) &&
                    this.filename.equals(comperedTabulatedFunctionDoc.filename) &&
                    this.fileNameAssigned == comperedTabulatedFunctionDoc.fileNameAssigned &&
                    this.modified == comperedTabulatedFunctionDoc.modified;
        }
        else
            return false;
    }


}
