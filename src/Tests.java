import ievents.*;
import market.Price;
import tools.ClassicVolatilitySeasonality;
import tools.GBM;
import tools.Tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by author.
 */
public class Tests {

    public Tests(){}

    public void run(){

        /**
         * TimeTotMoveScalLaw on real data:
         */
//        int numDeltas = 100;
//        TimeTotMoveScalLaw timeTotMoveScalLaw = new TimeTotMoveScalLaw(0.0001f, 0.05f, numDeltas);
////        String FILE_PATH = "D:/Data/";
//
////        String fileName = "/Bitcoin/krakenUSD.csv"; int nDecimals = 5;
//
////        String fileName = "EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5; String delimeter = ",";
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//
//        String FILE_PATH = "D:/Data/Stocks/SPX500/";
//        String fileName = "USA500IDXUSD_Ticks_2012.01.16_2017.01.01.csv"; int nDecimals = 3; String delimeter = ",";
//
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(delimeter).clone();
//            String priceLine;
//            while ((priceLine = bufferedReader.readLine()) != null) {
////                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, "", 1, 1, 0);
//                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                timeTotMoveScalLaw.run(aPrice);
//            }
//            timeTotMoveScalLaw.finish();
//            System.out.println("Delta + OS moves: ");
//            for (int i = 0; i < numDeltas; i++){
//                System.out.println(timeTotMoveScalLaw.getArrayDeltas()[i] + " , " + timeTotMoveScalLaw.getOsMoves()[i]);
//            }
//            timeTotMoveScalLaw.saveResults("Results");
//            double[] params = timeTotMoveScalLaw.computeParams();
//            System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }

        /**
         * TimeTotMoveScalLaw on GBM:
         */
//        int numDeltas = 100;
//        float nYears = 1.0f;
//        int numGenerations = 10000000;
//        long timeStep = (long) (nYears * 365 * 24 * 60 * 60 * 1000 / numGenerations);
//        TimeTotMoveScalLaw timeTotMoveScalLaw = new TimeTotMoveScalLaw(0.00035f, 0.07f, numDeltas);
//        GBM gbm = new GBM(1.336723f, 0.20f, 1.0f, numGenerations, 0);
//        long time = 0;
//        for (int i = 0; i < numGenerations; i++){
//            long value = (long) (gbm.generateNextValue() * 10000);
//            Price aPrice = new Price(value, value, time,  0);
//            timeTotMoveScalLaw.run(aPrice);
//            time += timeStep;
//        }
//        timeTotMoveScalLaw.finish();
//        System.out.println("Delta + OS moves: ");
//        for (int i = 0; i < numDeltas; i++){
//            System.out.println(timeTotMoveScalLaw.getArrayDeltas()[i] + " , " + timeTotMoveScalLaw.getOsMoves()[i]);
//        }
//        timeTotMoveScalLaw.saveResults("Results");
//        double[] params = timeTotMoveScalLaw.computeParams();
//        System.out.println("C: " + params[0] + ", E: " + params[1] + ", r: " + params[2]);


        /**
         * Testing the analytical equations by comparing their result with GBM with various trends and volatility:
         */
//        float deltaUp = 0.003f;
//        float deltaDown = 0.001f;
//        float startPrice = 0;
//        long nStepsPerYear = 1000000000;
//        int nExper = 1;
//
//        float minMu = 0.01f;
//        float maxMu = 0.11f;
//        float minSigma = 0.1f;
//        float maxSigma = 0.3f;
//        int numSteps = 3;
//
//        float[] arrayMu = new float[numSteps];
//        float[] arraySigma = new float[numSteps];
//        float[][] ratioUp = new float[numSteps][numSteps];
//        float[][] ratioDown = new float[numSteps][numSteps];
//
//        for (int aStep = 0; aStep < numSteps; aStep++){
//            arrayMu[aStep] = minMu + aStep * (maxMu - minMu) / (numSteps - 1);
//            arraySigma[aStep] = minSigma + aStep * (maxSigma - minSigma) / (numSteps - 1);
//        }
//
//        for (int experiment = 0; experiment < nExper; experiment++){
//            System.out.println("Experiment " + experiment);
//            for (int muIndex = 0; muIndex < numSteps; muIndex++){
//                for (int sigmaIndex = 0; sigmaIndex < numSteps; sigmaIndex++){
//
//                    double theorTimeUp = theoreticalTimeUP(deltaUp, arrayMu[muIndex], arraySigma[sigmaIndex]);
//                    double theorTimeDown = theoreticalTimeDOWN(deltaDown, arrayMu[muIndex], arraySigma[sigmaIndex]);
//                    double theorNumDC = theoreticalNumDC(deltaUp, deltaDown, arrayMu[muIndex], arraySigma[sigmaIndex]);
//
//                    int nPrevDC = 0;
//                    int nDCup = 0;
//                    int nDCdown = 0;
//                    double sumTimeUp = 0;
//                    double sumTimeDown = 0;
//                    double stdErrTup = 0;
//                    double stdErrTdown = 0;
//
//                    tools.ABMgenerator abMgenerator = new tools.ABMgenerator(startPrice, arrayMu[muIndex], arraySigma[sigmaIndex], nStepsPerYear);
//
//                    market.ATick aTick = new market.ATick(startPrice);
//
//                    Runner runner = new Runner(deltaUp, deltaDown, aTick, 1);
//
//                    for (int i = 0; i < nStepsPerYear; i++){
//                        double generatedPrice = abMgenerator.runStep();
//                        aTick = new market.ATick(generatedPrice);
//                        int runResult = runner.run(aTick);
//
//                        if (runResult != 0){
//                            double hereTimeToEvent = (double)(i - nPrevDC) / nStepsPerYear;
//                            if (runResult == 1){
//                                sumTimeUp += hereTimeToEvent;
//                                stdErrTup += Math.pow(hereTimeToEvent - theorTimeUp, 2);
//                                nDCup += 1;
//                            } else if (runResult == -1) {
//                                sumTimeDown += hereTimeToEvent;
//                                stdErrTdown += Math.pow(hereTimeToEvent - theorTimeDown, 2);
//                                nDCdown += 1;
//                            }
//                            nPrevDC = i;
//                        }
//                    }
//
//                    ratioUp[muIndex][sigmaIndex] += (sumTimeUp / nDCup) / nExper;
//                    ratioDown[muIndex][sigmaIndex] += (sumTimeDown / nDCdown) / nExper;
//
//                    double averageTimeUp = sumTimeUp / nDCup;
//                    double averageTimeDown = sumTimeDown / nDCdown;
//                    double experToTheorTimeUP = averageTimeUp / theorTimeUp;
//                    double experToTheorTimeDOWN = averageTimeDown / theorTimeDown;
//                    double experToTheorNumDC = (nDCup + nDCdown) / theorNumDC;
//                    stdErrTup = Math.sqrt(stdErrTup / nDCup) / Math.sqrt(nDCup);
//                    stdErrTdown = Math.sqrt(stdErrTdown / nDCdown) / Math.sqrt(nDCdown);
//
//
//                    ////// Output of each single experiment: ////////
//                    System.out.println("Mu: " + arrayMu[muIndex] + ", sigma: " + arraySigma[sigmaIndex] +
//                            ", ExperNumDC: " + (nDCup + nDCdown) + ", TheorNumDc: " + theorNumDC + ", RatExpTheorNumDC: " + experToTheorNumDC +
//                            ", ExperTimeUp: " + averageTimeUp + ", TheorTimeUp: " + theorTimeUp + ", RatioExpTheorTimeUp: " + experToTheorTimeUP +
//                            ", ExperTimeDown: " + averageTimeDown + ", TheorTimeDown: " + theorTimeDown + ", RatioExpTheorTimeDown: " + experToTheorTimeDOWN +
//                            ", StdErrTup: " + stdErrTup + ", StdErrTdown: " + stdErrTdown);
//                }
//            }
//        }
//
//        ////////   Aggregated statistics of numerous experiments (if any):  ///////
//        System.out.println("Up:");
//        for (int muIndex = 0; muIndex < numSteps; muIndex++) {
//            System.out.print("Mu = " + arrayMu[muIndex] + ": ");
//            for (int sigmaIndex = 0; sigmaIndex < numSteps; sigmaIndex++) {
//                System.out.print(ratioUp[muIndex][sigmaIndex] / theoreticalTimeUP(deltaUp, arrayMu[muIndex], arraySigma[sigmaIndex]) + ", ");
//            }
//            System.out.println();
//        }
//        System.out.println("Down:");
//        for (int muIndex = 0; muIndex < numSteps; muIndex++) {
//            System.out.print("Mu = " + arrayMu[muIndex] + ": ");
//            for (int sigmaIndex = 0; sigmaIndex < numSteps; sigmaIndex++) {
//                System.out.print(ratioDown[muIndex][sigmaIndex] / theoreticalTimeDOWN(deltaDown, arrayMu[muIndex], arraySigma[sigmaIndex]) + ", ");
//            }
//            System.out.println();
//        }
//


        /**
         * Testing the analytical equations by comparing their result with GBM with fixed parameters (for simplicity):
         */
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
//            tools.ABMgenerator abMgenerator = new tools.ABMgenerator(startPrice, mu, sigma, nStepsPerYear);
//
//            market.ATick aTick = new market.ATick(startPrice);
//
//            Runner runner = new Runner(deltaUp, deltaDown, aTick, 1);
//
//            for (int i = 0; i < nStepsPerYear; i++){
//                aTick = new market.ATick(abMgenerator.runStep());
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


        /**
         * Building Heat map of the NumDC using real prices and various sets of thresholds up and down:
         */
////        String fileName = "D://Data/EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv";
////        String fileName = "D://Data/Bitcoin/krakenUSD.csv";
//
//        String fileName = "USA500IDXUSD_Ticks_2012.01.16_2017.01.01.csv"; int nDecimals = 3; String delimeter = ",";
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        String FILE_PATH = "D:/Data/Stocks/SPX500/";
//
//
//        float minDelta = 0.001f;
//        float maxDelta = 0.041f;
//
//        int numSteps = 40;
//
//        float[] arrDeltas = new float[numSteps];
//        float deltaStep = (maxDelta - minDelta) / numSteps;
//        for ( int i = 0; i < numSteps; i++){
//            arrDeltas[i] = minDelta + i * deltaStep;
//        }
//
//        int[][] numDCs = new int[numSteps][numSteps];
//        Runner[][] runners = new Runner[numSteps][numSteps];
//        for (int i = 0; i < numSteps; i++){
//            for (int j = 0; j < numSteps; j++){
//                runners[i][j] = new Runner(arrDeltas[i], arrDeltas[j], 1, false);
//            }
//        }
//
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            String line;
//            long lineIndex = 0;
//            bufferedReader.readLine(); // header
//            while ((line = bufferedReader.readLine()) != null) {
//                String[] elements = line.split(delimeter);
//                Price aPrice = Tools.priceLineToPrice(line, ",", nDecimals, dateFormat, 1, 2, 0);
//                for (int i = 0; i < numSteps; i++) {
//                    for (int j = 0; j < numSteps; j++) {
//                        int result = runners[i][j].run(aPrice);
//                        if (result != 0) {
//                            numDCs[i][j] += 1;
//                        }
//                    }
//                }
//                if (lineIndex % 1000000 == 0){
//                    System.out.println(elements[0]);
//                }
//                lineIndex++;
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//        for (int i = 0; i < numSteps; i++){
//            for (int j = 0; j < numSteps; j++){
//                System.out.print(numDCs[i][j] + ", ");
//            }
//            System.out.println("");
//        }


        /**
         * ievents/InstantaneousVolatility on real data:
         */
////        float[] thresholds = {0.000125f, 0.000392f, 0.000951f, 0.004585f}; // thresholds for FX
////        float[] thresholds = {0.000941f, 0.003338f, 0.008941f, 0.051332f}; // for BTC
//        float[] thresholds = {0.00005844169f, 0.00024560764f, 0.00075064411f, 0.005445305f}; // thresholds for SPX500
//
//
//        for (float threshold : thresholds){
//            InstantaneousVolatility instantaneousVolatility = new InstantaneousVolatility(threshold);
//            String FILE_PATH = "D:/Data/";
////        String fileName = "EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
////        String fileName = "EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 3;
////        String fileName = "EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
////        String fileName = "/Bitcoin/krakenUSD.csv"; int nDecimals = 5;
//            String fileName = "/Stocks/SPX500/USA500IDXUSD_Ticks_2012.01.16_2017.01.01.csv"; int nDecimals = 3;
//            String delimeter = ",";
//            String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//            try {
//                BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//                bufferedReader.readLine().split(delimeter).clone();
//                String priceLine;
//                while ((priceLine = bufferedReader.readLine()) != null) {
//                    Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
////                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, "", 1, 1, 0); // for the BTC data file (the one we have)
//                    instantaneousVolatility.run(price);
//                }
//                instantaneousVolatility.finish();
//                System.out.println("Total volatility: " + instantaneousVolatility.getTotalVolat());
//                System.out.println("Annual volatility: " + instantaneousVolatility.getAnnualVolat());
//            } catch (Exception ex){
//                ex.printStackTrace();
//            }
//        }


        /**
         * ievents/RealizedVolatility on real data:
         */
////        float[] thresholds = {0.000125f, 0.000392f, 0.000951f, 0.004585f}; // thresholds for FX
////        float[] thresholds = {0.000941f, 0.003338f, 0.008941f, 0.051332f}; // for BTC
//        float[] thresholds = {0.00005844169f, 0.00024560764f, 0.00075064411f, 0.005445305f}; // thresholds for SPX500
//
//
//        for (float threshold : thresholds){
//            RealizedVolatility realizedVolatility = new RealizedVolatility(threshold);
//            String FILE_PATH = "D:/Data/";
////        String fileName = "EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
////        String fileName = "EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 3;
////        String fileName = "EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
////        String fileName = "/Bitcoin/krakenUSD.csv"; int nDecimals = 5;
//        String fileName = "/Stocks/SPX500/USA500IDXUSD_Ticks_2012.01.16_2017.01.01.csv"; int nDecimals = 3;
//
//            String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//            try {
//                BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//                bufferedReader.readLine().split(",").clone();
//                String priceLine;
//                while ((priceLine = bufferedReader.readLine()) != null) {
//                    Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
////                Price price = Tools.priceLineToPrice(priceLine, ",", nDecimals, "", 1, 1, 0);
//                    realizedVolatility.run(price);
//                }
//                realizedVolatility.finish();
//                System.out.println("Total volatility: " + realizedVolatility.getTotalVolatility());
//                realizedVolatility.normalizeVolatility(realizedVolatility.getTotalVolatility());
//                System.out.println("Annual volatility: " + realizedVolatility.getNormalizedVolatility());
//            } catch (Exception ex){
//                ex.printStackTrace();
//            }
//        }


        /**
         * InstantaneousVolatilitySeasonality which only computes number of DCs and connects them with Instantaneous
         * volatility, implemented to real data:
         */
//        InstantaneousVolatilitySeasonality instantaneousVolatilitySeasonality = new InstantaneousVolatilitySeasonality(0.0001, 600000L);
//
//        String FILE_PATH = "D:/Data/";
////        String fileName = "EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
////        String fileName = "EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 3;
////        String fileName = "EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
////        String fileName = "/Bitcoin/krakenUSD.csv"; int nDecimals = 5;
//        String fileName = "/Stocks/SPX500/USA500IDXUSD_Ticks_2012.01.16_2017.01.01.csv"; int nDecimals = 3;
//
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            long i = 0L;
//            while ((priceLine = bufferedReader.readLine()) != null){
//                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
////                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, "", 1, 1, 0);
//                instantaneousVolatilitySeasonality.run(aPrice);
//                if (i % 100000 == 0){
//                    System.out.println(new Date(aPrice.getTime()));
//                }
//                i++;
//            }
//            double[] volSeasonality = instantaneousVolatilitySeasonality.finish();
//            for (int index = 0; index < volSeasonality.length; index++){
//                System.out.println(index + ",  " + volSeasonality[index]);
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }


        /**
         * ClassicVolatilitySeasonality employees the traditional variance to compute the volatility pattern over a week,
         * implemented to real data:
         */
//        ClassicVolatilitySeasonality classicVolatilitySeasonality = new ClassicVolatilitySeasonality(600000L);
//
//        String FILE_PATH = "D:/Data/";
////        String fileName = "EURUSD_UTC_1 Min_Bid_2010.12.31_2016.01.01.csv"; int nDecimals = 5;
////        String fileName = "EURJPY_UTC_1 Min_Bid_2010.12.31_2016.01.01.csv"; int nDecimals = 3;
////        String fileName = "EURGBP_UTC_1 Min_Bid_2010.12.31_2016.01.01.csv"; int nDecimals = 5;
////        String fileName = "/Bitcoin/1minBTCBitstamp20162018.csv"; int nDecimals = 2;
//        String fileName = "/Stocks/SPX500/USA500IDXUSD_1 Min_Bid_2012.01.16_2017.01.01.csv"; int nDecimals = 3;
//
//        String dateFormat = "yyyy.MM.dd HH:mm:ss";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine();
//            String priceLine;
//            long i = 0L;
//            while ((priceLine = bufferedReader.readLine()) != null){
////                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 4, 1, 0);
////                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 5, 2, 1);
//                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//
//                classicVolatilitySeasonality.run(aPrice);
//                if (i % 100000 == 0){
//                    System.out.println(new Date(aPrice.getTime()));
//                }
//                i++;
//            }
//            double[] volSeasonality = classicVolatilitySeasonality.finish();
//            for (int index = 0; index < volSeasonality.length; index++){
//                System.out.println(index + ",  " + volSeasonality[index]);
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well


        /**
         * RealizedVolatilitySeasonality which connects variability of overshoots with the realized volatility,
         * applied to real data:
         */
//        RealizedVolatilitySeasonality realizedVolatilitySeasonality = new RealizedVolatilitySeasonality(0.01, 600000L);
//
//        String FILE_PATH = "D:/Data/";
////          String fileName = "EURUSD_UTC_Ticks_Bid_2016.02.02_2017.01.31.csv"; int nDecimals = 5;
//
////        String fileName = "EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
////        String fileName = "EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 3;
////        String fileName = "EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
//        String fileName = "/Bitcoin/krakenUSD.csv"; int nDecimals = 5;
//
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            long i = 0L;
//            while ((priceLine = bufferedReader.readLine()) != null){
////                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, "", 1, 1, 0);
//                realizedVolatilitySeasonality.run(aPrice);
//                if (i % 100000 == 0){
//                    System.out.println(new Date(aPrice.getTime()));
//                }
//                i++;
//            }
//            double[] volActivity = realizedVolatilitySeasonality.finish();
//            for (int index = 0; index < volActivity.length; index++){
//                System.out.println(index + ",  " + volActivity[index]);
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }



        /**
         * InstantaneousVolatility calculator and its dependence on the size of the used threshold delta. GBM is used as
         * the process with known volatility:
         */
//        double minDelta = 0.00005; double maxDelta = 0.006;
//        int numSteps = 80;
//        System.out.println("Delta, AnnualVolat");
//        for (double delta = minDelta; delta <= maxDelta; delta += (maxDelta-minDelta) / numSteps){
//            InstantaneousVolatility instantaneousVolatility = new InstantaneousVolatility(delta);
//            double nYears = 1.0;
//            long nGenrations = (long) (nYears * 60 * 60 * 24 * 365L); // one tick each second
//            int nDecimals = 5;
//            GBM gbm = new GBM(1f, 0.15f, (float) nYears, nGenrations, 0);
//            for (long i = 0; i < nGenrations; i++){
//                double generatedValue = gbm.generateNextValue();
//                long longValue = (long) (generatedValue * Math.pow(10, nDecimals));
//                Price price = new Price(longValue, longValue, i * 1000L, nDecimals);
//                instantaneousVolatility.run(price);
//            }
//            instantaneousVolatility.finish();
//            System.out.println(delta + "," + instantaneousVolatility.getAnnualVolat());
//        }



        /**
         * RealizedVolatility calculator and its dependence on the size of the used threshold delta. GBM is used as
         * the process with known volatility:
         */
//        double minDelta = 0.00005; double maxDelta = 0.003;
//        int numSteps = 40;
//        System.out.println("Delta, AnnualVolat");
//        for (double delta = minDelta; delta <= maxDelta; delta += (maxDelta-minDelta) / numSteps) {
//            RealizedVolatility realizedVolatility = new RealizedVolatility(delta);
//            double nYears = 1.0;
//            long nGenrations = (long) (nYears * 60 * 60 * 24 * 365L);
//            int nDecimals = 5;
//            GBM gbm = new GBM(1f, 0.15f, (float) nYears, nGenrations, 0);
//            for (long i = 0; i < nGenrations; i++) {
//                double generatedValue = gbm.generateNextValue();
//                long longValue = (long) (generatedValue * Math.pow(10, nDecimals));
//                Price price = new Price(longValue, longValue, i * 1000L, nDecimals);
//                realizedVolatility.run(price);
//            }
//            realizedVolatility.finish();
//            realizedVolatility.normalizeVolatility(realizedVolatility.getTotalVolatility());
//            System.out.println(delta + "," + realizedVolatility.getNormalizedVolatility());
//        }



        /**
         * Test NumDCperPeriod with real data in physical time:
         */
//        NumDCperPeriod numDCperPeriod = new NumDCperPeriod(0.0001, 1008, false);
//
//        String FILE_PATH = "/Users/vladimir/Documents/Data/";
////        String fileName = "Forex/EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5; String outName = "numDCperBinEURUSD.csv";
////        String fileName = "Forex/EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 3; String outName = "numDCperBinEURJPY.csv";
////        String fileName = "Forex/EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5; String outName = "numDCperBinEURGBP.csv";
////        String fileName = "Crypto/krakenUSD.csv"; int nDecimals = 5; String outName = "numDCperBinBTC.csv";
//        String fileName = "Stocks/SPX500/USA500IDXUSD_Ticks_2012.01.16_2017.01.01.csv"; int nDecimals = 3; String outName = "TEST_numDCperBinSPX500.csv";
//
//
//        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
//            bufferedReader.readLine().split(",").clone();
//            String priceLine;
//            long i = 0;
//            while ((priceLine = bufferedReader.readLine()) != null){
//                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
////                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, "", 1, 1, 0);
//                numDCperPeriod.run(aPrice);
//                if (i % 100000 == 0){
//                    System.out.println(new Date(aPrice.getTime()));
//                }
//                i++;
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // Write results into a file:
//        ArrayList<Integer> binIndexesArray = numDCperPeriod.getBinIndexesArray();
//        ArrayList<Integer> numDCsPerBinArray = numDCperPeriod.getNumDCsPerBinArray();
//        try{
//            PrintWriter writer = new PrintWriter("Results/" + outName, "UTF-8");
//            for (int i = 0; i < binIndexesArray.size(); i++){
//                writer.println(binIndexesArray.get(i) + "," + numDCsPerBinArray.get(i));
//            }
//            writer.close();
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        // works well


        /**
         * Test NumDCperPeriod with real data in theta time:
         */
        // Upload volatility seasonality:
        double[] weeklyActivitySeasonality = new double[1008];
        try{
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("SeasonalityEURUSD.csv"));
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("SeasonalityEURJPY.csv"));
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("SeasonalityEURGBP.csv"));
//            BufferedReader bufferedReader = new BufferedReader(new FileReader("SeasonalityBTCUSD.csv"));
            BufferedReader bufferedReader = new BufferedReader(new FileReader("SeasonalitySPX500.csv"));

            String line;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null){
                weeklyActivitySeasonality[i] = Double.parseDouble(line);
                i += 1;
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        //
        NumDCperPeriod numDCperPeriod = new NumDCperPeriod(0.0001, 1008, true);
        numDCperPeriod.uploadWeeklyActivitySeasonality(weeklyActivitySeasonality);

        String FILE_PATH = "/Users/vladimir/Documents/Data/";
//        String fileName = "Forex/EURUSD_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
//        String fileName = "Forex/EURJPY_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 3;
//        String fileName = "Forex/EURGBP_UTC_Ticks_Bid_2011-01-01_2016-01-01.csv"; int nDecimals = 5;
//        String fileName = "/Crypto/krakenUSD.csv"; int nDecimals = 5;
        String fileName = "Stocks/SPX500/USA500IDXUSD_Ticks_2012.01.16_2017.01.01.csv"; int nDecimals = 3;

        String dateFormat = "yyyy.MM.dd HH:mm:ss.SSS";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH + fileName));
            bufferedReader.readLine().split(",").clone();
            String priceLine;
            long i = 0;
            while ((priceLine = bufferedReader.readLine()) != null){
                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, dateFormat, 1, 2, 0);
//                Price aPrice = Tools.priceLineToPrice(priceLine, ",", nDecimals, "", 1, 1, 0); // for the BTC file
                numDCperPeriod.run(aPrice);
                if (i % 100000 == 0){
                    System.out.println(new Date(aPrice.getTime()));
                }
                i++;
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        // Write results into a file:
        ArrayList<Integer> binIndexesArray = numDCperPeriod.getBinIndexesArray();
        ArrayList<Integer> numDCsPerBinArray = numDCperPeriod.getNumDCsPerBinArray();
        try{
            PrintWriter writer = new PrintWriter("Results/numDCperBinThetaSPX500_TEST.csv", "UTF-8");
            for (int i = 0; i < binIndexesArray.size(); i++){
                writer.println(binIndexesArray.get(i) + "," + numDCsPerBinArray.get(i));
            }
            writer.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        // works well




    }





}
