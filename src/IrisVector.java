public class IrisVector {
    private final double[] parameters = new double[4];
    private final String name;
    IrisVector(String csvStr){
        String[] arr = csvStr.split(",");
        for(int i = 0; i < 4; i++){
            parameters[i] = Double.parseDouble(arr[i]);
        }
        name = arr[4];
    }

    public double[] getParameters() {
        return parameters;
    }

    public String getName() {
        return name;
    }

    public int getSize(){
        return parameters.length;
    }

    public double vectorsModulus2(IrisVector v) throws VectorSizeException {
        if(getSize() != v.getSize())
            throw new VectorSizeException();
        double result = 0;
        for(int i = 0; i < parameters.length; i++){
            result += Math.pow(parameters[i] - v.getParameters()[i], 2);
        }
        return result;
    }

    public static class VectorSizeException extends Exception{
        VectorSizeException(){
            super("You can't calculate the modulus of vectors of different sizes!");
        }
    }
}