import java.util.ArrayList;

/**
 */
public class Waiting_Times_and_Number_of_Directional_Changes {

    public static void main(String[] args){

        //////  Experiment with various trends and volatility /////

        float deltaUp = 0.003f;
        float deltaDown = 0.001f;
        float startPrice = 0;
        long nStepsPerYear = 1000000000;
        int nExper = 1;


        float minMu = 0.01f;
        float maxMu = 0.11f;
        float minSigma = 0.1f;
        float maxSigma = 0.3f;
        int numSteps = 3;

        float[] arrayMu = new float[numSteps];
        float[] arraySigma = new float[numSteps];
        float[][] ratioUp = new float[numSteps][numSteps];
        float[][] ratioDown = new float[numSteps][numSteps];

        for (int aStep = 0; aStep < numSteps; aStep++){
            arrayMu[aStep] = minMu + aStep * (maxMu - minMu) / (numSteps - 1);
            arraySigma[aStep] = minSigma + aStep * (maxSigma - minSigma) / (numSteps - 1);
        }

        for (int experiment = 0; experiment < nExper; experiment++){
            System.out.println("Experiment " + experiment);
            for (int muIndex = 0; muIndex < numSteps; muIndex++){
                for (int sigmaIndex = 0; sigmaIndex < numSteps; sigmaIndex++){

                    double theorTimeUp = theoreticalTimeUP(deltaUp, arrayMu[muIndex], arraySigma[sigmaIndex]);
                    double theorTimeDown = theoreticalTimeDOWN(deltaDown, arrayMu[muIndex], arraySigma[sigmaIndex]);
                    double theorNumDC = theoreticalNumDC(deltaUp, deltaDown, arrayMu[muIndex], arraySigma[sigmaIndex]);

                    int nPrevDC = 0;
                    int nDCup = 0;
                    int nDCdown = 0;
                    double sumTimeUp = 0;
                    double sumTimeDown = 0;
                    double stdErrTup = 0;
                    double stdErrTdown = 0;

                    ABMgenerator abMgenerator = new ABMgenerator(startPrice, arrayMu[muIndex], arraySigma[sigmaIndex], nStepsPerYear);

                    ATick aTick = new ATick(startPrice);

                    Runner runner = new Runner(deltaUp, deltaDown, aTick, 1);

                    for (int i = 0; i < nStepsPerYear; i++){
                        double generatedPrice = abMgenerator.runStep();
                        aTick = new ATick(generatedPrice);
                        int runResult = runner.run(aTick);

                        if (runResult != 0){
                            double hereTimeToEvent = (double)(i - nPrevDC) / nStepsPerYear;
                            if (runResult == 1){
                                sumTimeUp += hereTimeToEvent;
                                stdErrTup += Math.pow(hereTimeToEvent - theorTimeUp, 2);
                                nDCup += 1;
                            } else if (runResult == -1) {
                                sumTimeDown += hereTimeToEvent;
                                stdErrTdown += Math.pow(hereTimeToEvent - theorTimeDown, 2);
                                nDCdown += 1;
                            }
                            nPrevDC = i;
                        }
                    }

                    ratioUp[muIndex][sigmaIndex] += (sumTimeUp / nDCup) / nExper;
                    ratioDown[muIndex][sigmaIndex] += (sumTimeDown / nDCdown) / nExper;

                    double averageTimeUp = sumTimeUp / nDCup;
                    double averageTimeDown = sumTimeDown / nDCdown;
                    double experToTheorTimeUP = averageTimeUp / theorTimeUp;
                    double experToTheorTimeDOWN = averageTimeDown / theorTimeDown;
                    double experToTheorNumDC = (nDCup + nDCdown) / theorNumDC;
                    stdErrTup = Math.sqrt(stdErrTup / nDCup) / Math.sqrt(nDCup);
                    stdErrTdown = Math.sqrt(stdErrTdown / nDCdown) / Math.sqrt(nDCdown);


                    ////// Output of each single experiment: ////////
                    System.out.println("Mu: " + arrayMu[muIndex] + ", sigma: " + arraySigma[sigmaIndex] +
                            ", ExperNumDC: " + (nDCup + nDCdown) + ", TheorNumDc: " + theorNumDC + ", RatExpTheorNumDC: " + experToTheorNumDC +
                            ", ExperTimeUp: " + averageTimeUp + ", TheorTimeUp: " + theorTimeUp + ", RatioExpTheorTimeUp: " + experToTheorTimeUP +
                            ", ExperTimeDown: " + averageTimeDown + ", TheorTimeDown: " + theorTimeDown + ", RatioExpTheorTimeDown: " + experToTheorTimeDOWN +
                            ", StdErrTup: " + stdErrTup + ", StdErrTdown: " + stdErrTdown);
                }
            }
        }

        ////////   Aggregated statistics of numerous experiments (if any):  ///////
        System.out.println("Up:");
        for (int muIndex = 0; muIndex < numSteps; muIndex++) {
            System.out.print("Mu = " + arrayMu[muIndex] + ": ");
            for (int sigmaIndex = 0; sigmaIndex < numSteps; sigmaIndex++) {
                System.out.print(ratioUp[muIndex][sigmaIndex] / theoreticalTimeUP(deltaUp, arrayMu[muIndex], arraySigma[sigmaIndex]) + ", ");
            }
            System.out.println();
        }
        System.out.println("Down:");
        for (int muIndex = 0; muIndex < numSteps; muIndex++) {
            System.out.print("Mu = " + arrayMu[muIndex] + ": ");
            for (int sigmaIndex = 0; sigmaIndex < numSteps; sigmaIndex++) {
                System.out.print(ratioDown[muIndex][sigmaIndex] / theoreticalTimeDOWN(deltaDown, arrayMu[muIndex], arraySigma[sigmaIndex]) + ", ");
            }
            System.out.println();
        }

        ///////////////////////////////////////////////////////////////////////////




//        ///////// Experiments with fixed parameters /////////////////////////////

//        float deltaUp = 0.003f;
//        float deltaDown = 0.001f;
//        float startPrice = 0;
//        long nStepsPerYear = 1000000;
//        int nExper = 100;
//
//
//        float mu = 0.05f;
//        float sigma = 0.2f;
//
//
//        for (int experiment = 0; experiment < nExper; experiment++){
//
//            int nPrevDC = 0;
//            int nDCup = 0;
//            int nDCdown = 0;
//            double sumTimeUp = 0;
//            double sumTimeDown = 0;
//
//            ABMgenerator abMgenerator = new ABMgenerator(startPrice, mu, sigma, nStepsPerYear);
//
//            ATick aTick = new ATick(startPrice);
//
//            Runner runner = new Runner(deltaUp, deltaDown, aTick, 1);
//
//            for (int i = 0; i < nStepsPerYear; i++){
//                aTick = new ATick(abMgenerator.runStep());
//
//                int runResult = runner.run(aTick);
//                if (runResult != 0){
//                    if (runResult == 1){
//                        sumTimeUp += (double)(i - nPrevDC) / nStepsPerYear;
//                        nDCup += 1;
//                    } else if (runResult == -1) {
//                        sumTimeDown += (double)(i - nPrevDC) / nStepsPerYear;
//                        nDCdown += 1;
//                    }
//                    nPrevDC = i;
//                }
//            }
//
//            double averageTimeUp = sumTimeUp / nDCup;
//            double averageTimeDown = sumTimeDown / nDCdown;
//
//            System.out.println("Exp " + experiment + ", ratio up: " + averageTimeUp / theoreticalTimeUP(deltaUp, mu, sigma) +
//                    ", ratio down: " + averageTimeDown / theoreticalTimeDOWN(deltaDown, mu, sigma));
//        }

//        ////////////////////////////////////////////////////////////////////


    }


    public static double theoreticalTimeUP(float dUp, float mu, float sigma){
        return (Math.exp(-(2 * mu * dUp)/(sigma * sigma)) + (2 * mu * dUp)/(sigma * sigma) - 1) / (2 * mu * mu / (sigma * sigma));
    }

    public static double theoreticalTimeDOWN(float dDown, float mu, float sigma){
        return (Math.exp((2 * mu * dDown)/(sigma * sigma)) - (2 * mu * dDown)/(sigma * sigma) - 1) / (2 * mu * mu / (sigma * sigma));
    }

    public static double theoreticalNumDC(float dUp, float dDown, float mu, float sigma){
        return (2 * 2 * mu * mu / (sigma * sigma)) / (Math.exp(-(2 * mu * dUp) / (sigma * sigma)) +
                Math.exp((2 * mu * dDown) / (sigma * sigma)) + 2 * mu * (dUp - dDown) / (sigma * sigma) - 2);
    }

    public static double theoreticalNoTrendTimeUP (float deltaUp, float sigma){
        return deltaUp * deltaUp / (sigma * sigma);
    }

    public static double theoreticalNoTrendTimeDOWN (float deltaDown, float sigma){
        return deltaDown * deltaDown / (sigma * sigma);
    }



}
