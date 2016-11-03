import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Vladimir Petrov on 03.05.2016.
 */
public class ABMgenerator {

    public double currentValue;
    public boolean initiated;
    protected double initialValue;
    protected double mue;
    protected double sigma;
    public ArrayList<Float> generatedList;
    public int numOfSteps;
    public double deltaT;
    public double sqrtDeltaT;
    private Random r;
    public double trendIncrease;

    public ABMgenerator(float initialValue, float mue, float sigma, int numOfStepsInput) {
        this.sigma = sigma;
        this.initialValue = initialValue;
        this.mue = mue;
        generatedList = new ArrayList<>();
        initiated = false;
        currentValue = initialValue;
        numOfSteps = numOfStepsInput;
        deltaT = 1 / (float) numOfSteps;
        sqrtDeltaT = Math.sqrt(deltaT);

        r = new Random();
    }


    public double getInitialValue() {
        return initialValue;
    }


    public double getMue() {
        return mue;
    }


    public double getSigma() {
        return sigma;
    }


    public double St() {
        double St = currentValue + mue * deltaT + r.nextGaussian() * sigma * sqrtDeltaT;
        generatedList.add((float) St);
        return St;
    }


    public double runStep() {
        if (!initiated) {
            initiated = true;
            return initialValue;
        } else {
            currentValue = St();
            return currentValue;
        }
    }


    public void createGeneratedList(int numberOfValues) {
        generatedList = new ArrayList<Float>();
        for (int i = 0; i < numberOfValues; i++) {
            currentValue =  St();
            generatedList.add((float) currentValue);
        }
    }


    public ArrayList<Float> getGeneratedList() {
        return generatedList;
    }


    public void saveGeneratedListToFile(String fileName) {
        try {
            String nameOfFinelFile = "Results/" + fileName + "_" + initialValue + "_" + mue + "_" + sigma + "_" + numOfSteps + ".csv";
            nameOfFinelFile.replace(".", "-");
            PrintWriter writer = new PrintWriter(nameOfFinelFile, "UTF-8");
            int index = 0;
            while (index < generatedList.size()) {
                writer.println(generatedList.get(index));
                index += 1;
            }
            writer.close();
            System.out.println("The generated file is saved like " + nameOfFinelFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}