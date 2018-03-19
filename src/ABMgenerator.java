import java.util.Random;

/**
 */
public class ABMgenerator {

    public double currentValue;
    public boolean initiated;
    protected double initialValue;
    protected double mue;
    protected double sigma;
    public long numOfSteps;
    public double deltaT;
    public double sqrtDeltaT;
    private Random r;


    public ABMgenerator(float initialValue, float mue, float sigma, long numOfStepsInput) {
        this.sigma = sigma;
        this.initialValue = initialValue;
        this.mue = mue;
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


    public double runStep() {
        if (!initiated) {
            initiated = true;
            currentValue = initialValue;
            return currentValue;
        } else {
            currentValue += mue * deltaT + r.nextGaussian() * sigma * sqrtDeltaT;
            return currentValue;
        }
    }

}