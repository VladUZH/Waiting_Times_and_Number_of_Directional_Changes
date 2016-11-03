import java.util.ArrayList;

/**
 * Created by Vladimir Petrov on 02.11.2016.
 */
public class Monte_Carlo {

    public static void main(String[] args){

        float deltaUp = 0.1f;
        float deltaDown = 0.2f;

        float startPrice = 10;

        int nStepsPerYear = 10000000;


        for (float mu = 0.1f; mu <= 1f; mu += 0.1f){
            for (float sigma = 0.1f; sigma <= 0.5f; sigma += 0.1f){

                System.out.println("Mu = " + mu + ", Sigma = " + sigma);

                ABMgenerator abMgenerator = new ABMgenerator(startPrice, mu, sigma, nStepsPerYear);

                ATick aTick = new ATick(startPrice);

                Runner runner = new Runner(deltaUp, deltaDown, aTick, 1);

                int nPrevDC = 0;
//                int nPrevDCdown = 0;

                ArrayList<Integer> timeBeforeUP = new ArrayList<>();
                ArrayList<Integer> timeBeforeDown = new ArrayList<>();
//                ArrayList<Integer> timeBetween = new ArrayList<>();

//                int nPrevDC = 0;


                for (int i = 0; i < nStepsPerYear; i++){

                    double generatedPrice = abMgenerator.runStep();

                    aTick = new ATick(generatedPrice);

                    int runResult = runner.run(aTick);



                    if (runResult != 0){

//                        double percentage = (runner.prevExtreme - aTick.price) / runner.prevExtreme * 100;
//                        System.out.println("Return: " + runResult + ", current price: " + aTick.price + ", extreme: " + runner.prevExtreme + ", percentage: " + percentage + ", time between: " + (i - nPrevDC));

                        if (runResult == 1){
                            timeBeforeUP.add(i - nPrevDC);
                        } else if (runResult == -1) {
                            timeBeforeDown.add(i - nPrevDC);
                        }
//                        timeBetween.add(i - nPrevDC);

                        nPrevDC = i;
                    }


                }

                int sumTimeUp = 0;
                for (int aTime : timeBeforeUP){
                    sumTimeUp += aTime;
                }
                float averageTimeUp = sumTimeUp / (float) timeBeforeUP.size();
                System.out.println("Average time before Up: " + averageTimeUp + "(" + (averageTimeUp / nStepsPerYear) + ")" + ", Theoretical: " + theoreticalTimeUP(deltaUp, mu, sigma));

                int sumTimeDown = 0;
                for (int aTime : timeBeforeDown){
                    sumTimeDown += aTime;
                }
                float averageTimeDown = sumTimeDown / (float) timeBeforeDown.size();
                System.out.println("Average time before Down: " + averageTimeDown + "(" + (averageTimeDown / nStepsPerYear) + ")" + ", Theoretical: " + theoreticalTimeDOWN(deltaDown, mu, sigma));
//
//                int sumTime = 0;
//                for (int aTime : timeBetween){
//                    sumTime += aTime;
//                }
//                float averageTime = sumTime / (float) timeBetween.size();
//                System.out.println("Average time between each: " + averageTime);




            }



        }





    }


    public static double theoreticalTimeUP(float deltaUp, float mu, float sigma){ //  time between the last down next UP
        return (Math.exp(2 * mu / (sigma * sigma) * deltaUp) - 2 * mu / (sigma * sigma) * deltaUp - 1) / (2 * mu * mu / (sigma * sigma));
    }

    public static double theoreticalTimeDOWN(float deltaDown, float mu, float sigma){ // time between last up and the next DOWN
        return (Math.exp(-2 * mu / (sigma * sigma) * deltaDown) + 2 * mu / (sigma * sigma) * deltaDown - 1) / (2 * mu * mu / (sigma * sigma));
    }

    public static double theoreticalNoTrendTimeUP (float deltaUp, float sigma){
        return deltaUp * deltaUp / (sigma * sigma);
    }

    public static double theoreticalNoTrendTimeDOWN (float deltaDown, float sigma){
        return deltaDown * deltaDown / (sigma * sigma);
    }



}
