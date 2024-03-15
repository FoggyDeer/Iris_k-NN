import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static ArrayList<IrisVector> trainData = new ArrayList<>();
    public static String test(IrisVector vector, int k) throws IrisVector.VectorSizeException {
        TreeMap<Double, String> calculatedData = new TreeMap<>();
        for(IrisVector irisVector : trainData){
            calculatedData.put(vector.vectorsModulus2(irisVector), irisVector.getName());
        }
        ArrayList<String> closestVectors = new ArrayList<>(calculatedData.values());
        closestVectors = new ArrayList<>(closestVectors.subList(0, k <= closestVectors.size() ? k - 1 : closestVectors.size()));
        HashMap<String, Integer> map = new HashMap<>();
        for(String s : closestVectors){
            map.put(s, map.get(s) != null ? map.get(s)+1 : 1);
        }
        Map.Entry<String, Integer> maxEntry = new AbstractMap.SimpleEntry<>("", 0);
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            if(maxEntry.getValue() < entry.getValue()){
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }
    public static String testFromFile(int k) throws FileNotFoundException, IrisVector.VectorSizeException {
        ArrayList<IrisVector> testData = readVectorsCSV("./data/Test-set.csv");
        double failings = 0;
        double amount = testData.size();

        for(IrisVector irisVector : testData){
            if(!irisVector.getName().equals(test(irisVector, k))){
                failings++;
            }
        }
        return ((amount - failings) / amount * 100) + "%";
    }

    public static void main(String[] args) throws FileNotFoundException {
        trainData = readVectorsCSV("./data/Train-set.csv");
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("1. Test data from Test-set.csv | 2. Enter custom vector | 3. Exit");
            try{
                switch (Integer.parseInt(scanner.nextLine())) {
                    case 1 -> {
                        System.out.println("Enter k value: ");
                        System.out.println("Accuracy: " + testFromFile(Integer.parseInt(scanner.nextLine())));
                    }
                    case 2 -> {
                        System.out.println("Enter iris data (0.0,0.0,0.0,0.0):");
                        IrisVector irisVector = new IrisVector(scanner.nextLine() + ", ");
                        System.out.println("Enter k value:");
                        int k = Integer.parseInt(scanner.nextLine());
                        System.out.println("Prediction: " + test(irisVector, k));
                    }
                    case 3 -> running = false;
                }
            }catch (NumberFormatException e){
                System.out.println("Wrong number format!");
            } catch (IrisVector.VectorSizeException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("-----------------------------------------------");
        }
    }

    public static ArrayList<IrisVector> readVectorsCSV(String path) throws FileNotFoundException {
        ArrayList<IrisVector> data = new ArrayList<>();
        File file = new File(path);
        Scanner fr = new Scanner(file);
        while (fr.hasNextLine()){
            data.add(new IrisVector(fr.nextLine()));
        }
        return data;
    }
}