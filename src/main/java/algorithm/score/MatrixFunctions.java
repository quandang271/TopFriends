package algorithm.score;
import org.apache.commons.math3.ml.distance.EuclideanDistance;


public class MatrixFunctions {
    private static MatrixFunctions matrixFunctions;
    public static MatrixFunctions getInstance(){
        if(matrixFunctions == null)
            matrixFunctions = new MatrixFunctions();
        return matrixFunctions;
    }
    public float[][] getNormalizeMatrix(float[][] matrix){
        int m =0, n =0; // m - so ban be, n - so features
        if(matrix.length > 0)
            m = matrix.length;
        else throw new IllegalArgumentException("Doi so ko hop le !!");
        if (matrix[0].length > 0)
            n = matrix[0].length;
        else throw new IllegalArgumentException("Doi so ko hop le !!");

        float[][] result = new float[m][n];
        int[] totalCol = new int[n];
        for(int j=0 ; j<n; j++)
        {
            for(int i =0;i<m;i++){
                totalCol[j] += Math.pow(matrix[i][j],2);
            }
        }
        for(int j=0 ; j<n; j++)
        {
            for(int i =0;i<m;i++){
                if(totalCol[j] != 0)
                    result[i][j] = matrix[i][j]*1.0F/totalCol[j];
                else
                    result[i][j] = 0;
            }
        }
        return result;
    }

    public float[][] getWeightedMatrix(float[][] matrix,float[] weights){
        int m =0, n =0; // m - so ban be, n - so features
        if(matrix.length > 0)
            m = matrix.length;
        else throw new IllegalArgumentException("Doi so ko hop le !!");
        if (matrix[0].length > 0)
            n = matrix[0].length;
        else throw new IllegalArgumentException("Doi so ko hop le !!");
        if(weights.length != n)
            throw new IllegalArgumentException("Do dai mang weight khong bang so cot ma tran !!");
        float[][] result = new float[m][n];
        for(int j=0;j<n;j++){
            for(int i=0;i<m;i++){
                result[i][j] = weights[j]*matrix[i][j];
            }
        }
        return result;
    }

    public float[] getIdealFriendVector(float[][] wMatrix, int[] dottedFeatureVector) {
        // dottedFeatureVector dang [0,1,1,0,0 ...] 1 - feature tich cuc, 0 - feature tieu cuc
        int m = 0, n = 0; // m - so ban be, n - so features
        if (wMatrix.length > 0)
            m = wMatrix.length;
        else throw new IllegalArgumentException("Doi so ko hop le !!");
        if (wMatrix[0].length > 0)
            n = wMatrix[0].length;
        else throw new IllegalArgumentException("Doi so ko hop le !!");
        if (dottedFeatureVector.length != n)
            throw new IllegalArgumentException("Vector danh dau feature co do dai khong hop li !!");

        float[] idealFeature = new float[n];
        for (int j = 0; j < n; j++) {
            float max = wMatrix[0][j], min = wMatrix[0][j];
            if (dottedFeatureVector[j] != 0 && dottedFeatureVector[j] != 1)
                throw new IllegalArgumentException("Vector danh dau feature sai gia tri (0,1) !!");

            // get max value
            if (dottedFeatureVector[j] == 1) {
                for (int i = 0; i < m; i++) {
                    if (wMatrix[i][j] > max)
                        max = wMatrix[i][j];
                }
                idealFeature[j] = max;
            }

            // get min value
            if (dottedFeatureVector[j] == 0) {
                for (int i = 0; i < m; i++) {
                    if (wMatrix[i][j] < min)
                        min = wMatrix[i][j];
                }
                idealFeature[j] = min;
            }
        }
        return idealFeature;
    }


    public float[] getUnIdealFriendVector(float[][] wMatrix, int[] dottedFeatureVector){
        // dottedFeatureVector dang [0,1,1,0,0 ...] 1 - feature tich cuc, 0 - feature tieu cuc
        int m =0, n =0; // m - so ban be, n - so features
        if(wMatrix.length > 0)
            m = wMatrix.length;
        else throw new IllegalArgumentException("Doi so ko hop le !!");
        if (wMatrix[0].length > 0)
            n = wMatrix[0].length;
        else throw new IllegalArgumentException("Doi so ko hop le !!");
        if(dottedFeatureVector.length != n) throw new IllegalArgumentException("Vector danh dau feature co do dai khong hop li !!");

        float[] unIdealFeature = new float[n];
        for(int j=0;j<n;j++){
            float max = wMatrix[0][j],min = wMatrix[0][j];
            if(dottedFeatureVector[j] != 0 && dottedFeatureVector[j] != 1)
                throw new IllegalArgumentException("Vector danh dau feature sai gia tri (0,1) !!");

            // get min value
            if(dottedFeatureVector[j] == 1){
                for(int i=0;i< m;i++){
                    if(wMatrix[i][j] < min)
                        min = wMatrix[i][j];
                }
                unIdealFeature[j] = min;
            }

            // get max value
            if(dottedFeatureVector[j] == 0){
                for(int i=0;i< m;i++){
                    if(wMatrix[i][j] > max)
                        max = wMatrix[i][j];
                }
                unIdealFeature[j] = max;
            }
        }
        return unIdealFeature;
    }

    // tinh score cho m friends
    public float[] getFriendScores(float[][] wMatrix, float[] idealFeature, float[] unIdealFeature){
        int m =0, n =0; // m - so ban, n - so features
        if(wMatrix.length > 0)
            m = wMatrix.length;
        else throw new IllegalArgumentException("Doi so ko hop le !!");
        if (wMatrix[0].length > 0)
            n = wMatrix[0].length;
        else throw new IllegalArgumentException("Doi so ko hop le !!");
        float[] scoreResults = new float[m];

        EuclideanDistance euclideanDistance = new EuclideanDistance();
        for(int i=0;i<m;i++){
            float diit=0, dimu=0;
            for(int j=0;j<n;j++){
                diit += Math.pow(wMatrix[i][j]-idealFeature[j],2);
                dimu += Math.pow(wMatrix[i][j]-unIdealFeature[j],2);
            }
            diit = (float) Math.sqrt(diit);
            dimu = (float) Math.sqrt(dimu);
            scoreResults[i] = dimu/(diit+dimu);
        }
        return scoreResults;
    }

    // In ma tran
    public void printMatrix(float[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    // In vector
    public void printVector(float[] vector){
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
//        float[][] matrix = new float[][]{{0.12F,0.23F,0.34F},{0.65F,0.94F,0.035F}};
//        int[] dottedVector = new int[]{0,1,0};
//        MatrixFunctions m = new MatrixFunctions();
//        float[] idealVector = m.getUnIdealFriendVector(matrix,dottedVector);
//        m.printVector(idealVector);

    }
}