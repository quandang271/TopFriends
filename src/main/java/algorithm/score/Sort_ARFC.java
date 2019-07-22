package algorithm.score;


import java.util.Random;

public class Sort_ARFC {
    private static Sort_ARFC sort_arfc;
    public static Sort_ARFC getInstance(){
        if(sort_arfc == null)
            sort_arfc = new Sort_ARFC();
        return sort_arfc;
    }

    public Sort_ARFC(){};
    private float[] score;
    MatrixFunctions matrixFunctions = MatrixFunctions.getInstance();

    public Sort_ARFC(float[][] matrix,int[] dottedFeatureVector){
        float[][] normalMatrix = matrixFunctions.getNormalizeMatrix(matrix);
        float[] idealVector = matrixFunctions.getIdealFriendVector(normalMatrix,dottedFeatureVector);
        float[] unIdealFriendVector = matrixFunctions.getUnIdealFriendVector(normalMatrix,dottedFeatureVector);
        this.score = matrixFunctions.getFriendScores(normalMatrix,idealVector,unIdealFriendVector);
        matrixFunctions.printVector(score);
    }

    public float[][] getMatrix(int num){
        float[][] result = new float[num][4];
        Random ran = new Random();
        for(int i=0;i<num;i++){
           for(int j=0;j<4;j++){
               result[i][j] = ran.nextInt(1000);
           }
        }
        return result;
    }

    public static void main(String[] args) {
//        int[][] matrix = new int[][]{{0,1,2,6},{0,4,5,2},{0,2,3,2},{0,4,5,12}};
        float[][] matrix = Sort_ARFC.getInstance().getMatrix(20000);
        int[] dottedFeatureVector = new int[]{1,1,0,0};
        MatrixFunctions.getInstance().printMatrix(matrix);
        Sort_ARFC sort_arfc = new Sort_ARFC(matrix,dottedFeatureVector);
        System.out.println(sort_arfc.score.length);
    }
}
