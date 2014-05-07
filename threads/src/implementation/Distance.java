/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.nlogo.api.ExtensionException;

/**
 *
 * @author nico
 */
public class Distance {

    public static ArrayList<String[]> threadsList = new ArrayList<String[]>();
    public static ArrayList<Integer> randomNumbersList = new ArrayList<Integer>();
    public static TreeMap<Integer, ArrayList<Integer>> threadsPerForumMap = new TreeMap<Integer, ArrayList<Integer>>();
    public static TreeMap<String, ForumDistribution> distributionsPerForumMap = new TreeMap<String, ForumDistribution>();
    public static TreeMap<String, ArrayList<Double>> bestParametersMap = new TreeMap<String, ArrayList<Double>>();

    public static Map<Integer, Double> createCumulativeDistribution(Map<Integer, Integer> distribution) {
        Map<Integer, Integer> distributionMap = new TreeMap<Integer, Integer>(distribution);
        int maxThreadSize = Collections.max(distributionMap.keySet());
        for (int i = 1; i < maxThreadSize; i++) {
            if (!distributionMap.containsKey(i)) {
                distributionMap.put(i, 0);
            }
        }
        Object[] keyArray = distributionMap.keySet().toArray();
        Map<Integer, Double> cumulativeMap = new TreeMap<Integer, Double>();
        Double totalsum = 0.0;
        for (Integer i : distributionMap.values()) {
            totalsum += i;
        }
        for (int i = 0; i < keyArray.length; i++) {
            Double sum = 0.0;
            for (int j = 0; j <= i; j++) {
                sum += distributionMap.get((Integer) keyArray[j]);
            }
            cumulativeMap.put((Integer) keyArray[i], sum / totalsum);
        }
        return cumulativeMap;
    }

    public static double getDistance(int forumID, int startPercent, int endPercent, String simulationFile) throws ExtensionException {
        TreeMap<Integer, Integer> realDataDistribution = null;
        try {
            realDataDistribution = distributionsPerForumMap.get(String.valueOf(forumID) + String.valueOf(startPercent) + String.valueOf(endPercent)).getDistribution();
        } catch (NullPointerException ex) {
            throw new ExtensionException("Forum with given ID not found or sample not drawn yet");
        }
        LinkedList<HashMap<Integer, Integer>> simulationDistributions = parseSimulationOutputFile(simulationFile);
        double sumDistances = 0;
        for (HashMap<Integer, Integer> simDistr : simulationDistributions) {
            double currentDistance = getSmirnovDistance(realDataDistribution, simDistr);
            sumDistances += currentDistance;
        }
        double averageDistance = sumDistances / (double) simulationDistributions.size();
        return averageDistance;
    }

    // this method is not used anymore
    public static double getDistanceFromFile(String originalFile, String simulationFile) {
        HashMap<Integer, Integer> realDataDistribution = parseSimulationOutputFile(originalFile).pop();
        LinkedList<HashMap<Integer, Integer>> simulationDistributions = parseSimulationOutputFile(simulationFile);
        double sumDistances = 0;
        for (HashMap<Integer, Integer> simDistr : simulationDistributions) {
            double currentDistance = getSmirnovDistance(realDataDistribution, simDistr);
            sumDistances += currentDistance;
        }
        double averageDistance = sumDistances / (double) simulationDistributions.size();
        return averageDistance;
    }

    public static LinkedList<HashMap<Integer, Integer>> parseSimulationOutputFile(String filepath) {
        LinkedList<HashMap<Integer, Integer>> result = new LinkedList<HashMap<Integer, Integer>>();
        BufferedReader br = null;
        HashMap<Integer, Integer> distribution = new HashMap<Integer, Integer>();
        try {
            br = new BufferedReader(new FileReader(new File(filepath)));
            String line;
            int threadLength;
            int frequency;
            int runCount;
            String[] lineArray;
            int lastRunCount = 1;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                lineArray = line.split(",");
                try {
                    threadLength = Integer.parseInt(lineArray[0]);
                    frequency = Integer.parseInt(lineArray[1]);
                    runCount = Integer.parseInt(lineArray[2]);
                    if (firstLine) {
                        lastRunCount = runCount;
                        firstLine = false;
                    }
                    if (runCount != lastRunCount) {
                        lastRunCount = runCount;
                        result.add(distribution);
                        distribution = new HashMap<Integer, Integer>();
                    }
                    distribution.put(threadLength, frequency);
                } catch (NumberFormatException ex) {
                    Logger.getLogger(Distance.class.getName()).log(Level.SEVERE, "A line could not be parsed (probably just the header line)");
                }
            }
            result.add(distribution);
        } catch (IOException ex) {
            Logger.getLogger(Distance.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Distance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    static double getSmirnovDistance(Map<Integer, Integer> frequencyTable1, Map<Integer, Integer> frequencyTable2) {
        Map<Integer, Double> cumulMap1 = createCumulativeDistribution(frequencyTable1);
        Object[] keyArray1 = cumulMap1.keySet().toArray();
        Map<Integer, Double> cumulMap2 = createCumulativeDistribution(frequencyTable2);
        Object[] keyArray2 = cumulMap2.keySet().toArray();

        ArrayList<Double> diffList = new ArrayList<Double>();
        int minSize = Math.min(keyArray1.length, keyArray2.length);
        for (int i = 0; i < minSize; i++) {
            diffList.add(Math.abs(cumulMap1.get((Integer) keyArray1[i]) - cumulMap2.get((Integer) keyArray2[i])));
        }
        if (keyArray1.length > minSize) {
            for (int i = minSize + 1; i < keyArray1.length; i++) {
                diffList.add(1.0 - cumulMap1.get((Integer) keyArray1[i]));
            }

        } else if (keyArray2.length > minSize) {
            for (int i = minSize + 1; i < keyArray2.length; i++) {
                diffList.add(1.0 - cumulMap2.get((Integer) keyArray2[i]));
            }
        }

        return Collections.max(diffList);
    }

    public static void readThreadsFile(String forumFilepath) {
        if (threadsList.isEmpty()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(new File(forumFilepath)));
                String line;
                String[] lineArray;
                while ((line = br.readLine()) != null) {
                    lineArray = line.split(",");
                    threadsList.add(lineArray);
                }
            } catch (IOException ex) {
                Logger.getLogger(Distance.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(Distance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * this method draws a sample of size endPercent-startPercent and returns
     * the size of this sample which corresponds to the number of content items
     */
    public static double drawSample(int forumID, int startPercent, int endPercent) {
        if (!distributionsPerForumMap.containsKey(String.valueOf(forumID) + String.valueOf(startPercent) + String.valueOf(endPercent))) {
            ForumDistribution sampledForumDistribution = new ForumDistribution();
            ArrayList<Integer> threadLengths = threadsPerForumMap.get(forumID);
            Integer numberOfThreads = threadLengths.size();
            Integer startIndex;
            if (startPercent == 0.0) {
                startIndex = 0;
            } else {
                startIndex = (int) Math.round((startPercent / (double) 100) * numberOfThreads) - 1;
            }
            Integer endIndex = (int) Math.round((endPercent / (double) 100) * numberOfThreads) - 1;

            for (int i = startIndex; i <= endIndex; i++) {
                sampledForumDistribution.addThread(threadLengths.get(i));
            }

            distributionsPerForumMap.put(String.valueOf(forumID) + String.valueOf(startPercent) + String.valueOf(endPercent), sampledForumDistribution);
            dumpDistributionToFile(sampledForumDistribution, forumID, startPercent, endPercent);

            return (double) sampledForumDistribution.getNumberOfContentItems();
        } else {
            return (double) distributionsPerForumMap.get(String.valueOf(forumID) + String.valueOf(startPercent) + String.valueOf(endPercent)).getNumberOfContentItems();
        }
    }

    public static void createThreadsPerForumMap(String postsFilePath, String permutationFilePath) {
        if (threadsPerForumMap.isEmpty()) {

            readThreadsFile(postsFilePath);
            readRandomNumbersFile(permutationFilePath);

            Integer forumID;
            Integer threadLength;
            for (Integer lineNumber : randomNumbersList) {
                String[] lineArray = threadsList.get(lineNumber - 1);
                forumID = Integer.parseInt(lineArray[2]);
                threadLength = Integer.parseInt(lineArray[1]);
                if (!threadsPerForumMap.containsKey(forumID)) {
                    threadsPerForumMap.put(forumID, new ArrayList<Integer>(threadLength));
                } else {
                    threadsPerForumMap.get(forumID).add(threadLength);
                }
            }
        }
    }

    public static void readRandomNumbersFile(String permutationFilePath) {
        if (randomNumbersList.isEmpty()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(new File(permutationFilePath)));
                String line;
                while ((line = br.readLine()) != null) {
                    randomNumbersList.add(Integer.valueOf(line));
                }
            } catch (IOException ex) {
                Logger.getLogger(Distance.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(Distance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static void dumpDistributionToFile(ForumDistribution sampledForumDistribution, int forumID, int startPercent, int endPercent) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("./Threads/sample_distributions/sample-distribution_" + forumID + "_start_" + startPercent + "_end_" + endPercent + ".csv"));
            TreeMap<Integer, Integer> distribution = distributionsPerForumMap.get(String.valueOf(forumID) + String.valueOf(startPercent) + String.valueOf(endPercent)).getDistribution();
            for (Entry e : distribution.entrySet()) {
                writer.write(String.valueOf(e.getKey()) + "," + String.valueOf(e.getValue()));
                writer.newLine();
            }

        } catch (IOException ex) {
            Logger.getLogger(Distance.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Distance.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static double getNewThreadProb(int forumID, int startPercent, int endPercent) {
        ForumDistribution forumDistr = distributionsPerForumMap.get(String.valueOf(forumID) + String.valueOf(startPercent) + String.valueOf(endPercent));
        return forumDistr.getNumberOfThreads() / (double) forumDistr.getNumberOfContentItems();
    }

    public static void readAnnealingResults(String annealingFile, String mode) {
        if (bestParametersMap.isEmpty()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(new File(annealingFile)));
                String line;
                String[] infos;
                String forum;
                Double distance;
                Double USERVIEWGEOMETRICVALUEP = null;
                Double newThreadProb = null;
                Double filterShowAll = null;
                Double filterShowWithNoReply = null;
                Double filterShowHasReply = null;
                Double powerValue = null;
                ArrayList<Double> params;

                boolean whatFollowsIsData = false;

                while ((line = br.readLine()) != null) {
                    if (whatFollowsIsData) {
                        infos = line.split(",");
                        forum = infos[1].replaceAll("\"", "");
                        distance = Double.parseDouble(infos[6].replaceAll("\"", ""));
                        if (mode.equals("pa")) {
                            //"[run number]","forumid","mode","startPercent","endPercent","[step]","lastAcceptedDistance","powerValue","newThreadProb"
                            powerValue = Double.parseDouble(infos[7].replaceAll("\"", ""));
                            newThreadProb = Double.parseDouble(infos[8].replaceAll("\"", ""));
                        } else {
                            //"[run number]","forumid","mode","startPercent","endPercent","[step]","lastAcceptedDistance","USERVIEWGEOMETRICVALUEP","newThreadProb","filterShowAll","filterShowWithNoReply","filterShowHasReply"
                            USERVIEWGEOMETRICVALUEP = Double.parseDouble(infos[7].replaceAll("\"", ""));
                            newThreadProb = Double.parseDouble(infos[8].replaceAll("\"", ""));
                            filterShowAll = Double.parseDouble(infos[9].replaceAll("\"", ""));
                            filterShowWithNoReply = Double.parseDouble(infos[10].replaceAll("\"", ""));
                            filterShowHasReply = Double.parseDouble(infos[11].replaceAll("\"", ""));
                        }

                        if (!bestParametersMap.containsKey(forum)) {
                            params = new ArrayList<Double>();
                            if (mode.equals("pa")) {
                                params.add(distance);
                                params.add(powerValue);
                                params.add(newThreadProb);
                            } else {
                                params.add(distance);
                                params.add(USERVIEWGEOMETRICVALUEP);
                                params.add(newThreadProb);
                                params.add(filterShowAll);
                                params.add(filterShowWithNoReply);
                                params.add(filterShowHasReply);
                            }
                            bestParametersMap.put(forum, params);
                        } else {
                            if (distance < bestParametersMap.get(forum).get(0)) {
                                params = new ArrayList<Double>();
                                params.add(distance);
                                if (mode.equals("pa")) {
                                    params.add(powerValue);
                                    params.add(newThreadProb);
                                } else {
                                    params.add(USERVIEWGEOMETRICVALUEP);
                                    params.add(newThreadProb);
                                    params.add(filterShowAll);
                                    params.add(filterShowWithNoReply);
                                    params.add(filterShowHasReply);
                                }
                                bestParametersMap.put(forum, params);
                            }
                        }

                    }
                    if (line.contains("[run number]")) {
                        whatFollowsIsData = true;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Distance.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(Distance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
