package algorithm.cluster;

import model.FriendModelNomalized;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeans {
    int NUM_CLUSTERS ;
    private List<FriendModelNomalized> points = new ArrayList<>();
    private List<Cluster> clusters = new ArrayList<>();

    public KMeans(){};
    public KMeans(List<FriendModelNomalized> points, List<Cluster> clusters) {
        for(Cluster cl: clusters)
        {
            if(cl.getCentroid() == null) throw new IllegalArgumentException("Please assign centroid cho cluster "+cl.getId());
        }
        this.points = points;
        this.clusters = clusters;
        NUM_CLUSTERS = clusters.size();
    }

    public void calculate() {
        boolean finish = false;
        int iteration = 0;

        // Add in new data, one at a time, recalculating centroids with each new one.
        while(!finish) {
            //Clear cluster state
            clearClusters();

            List<FriendModelNomalized> lastCentroids = getCentroids();

            //Assign points to the closer cluster
            assignCluster();

            //Calculate new centroids.
            calculateCentroids();

            iteration++;

            List<FriendModelNomalized> currentCentroids = getCentroids();

            //Calculates total distance between new and old Centroids
            double distance = 0;
            for(int i = 0; i < lastCentroids.size(); i++) {
                distance += FriendModelNomalized.distance(lastCentroids.get(i),currentCentroids.get(i));
            }
            System.out.println("#################");
            System.out.println("Iteration: " + iteration);
            System.out.println("Centroid distances: " + distance);
            System.out.println("#################");
            plotClusters();

            System.out.println();
            if(distance == 0) {
                finish = true;
            }
        }
    }

    private void clearClusters() {
        for(Cluster cluster : clusters) {
            cluster.clear();
        }
    }

    private List<FriendModelNomalized> getCentroids() {
        List<FriendModelNomalized> centroids = new ArrayList<>();
        for(Cluster cluster : clusters) {
            FriendModelNomalized f = cluster.getCentroid();
            centroids.add(f);
        }
        return centroids;
    }

    private void assignCluster() {
        for(FriendModelNomalized point : points) {
            double d = FriendModelNomalized.distance(point,clusters.get(0).getCentroid());
            int clusterID = 0;
            for(Cluster cluster: clusters){
                if(FriendModelNomalized.distance(point,cluster.getCentroid()) < d){
                    clusterID = cluster.getId();
                    d = FriendModelNomalized.distance(point,cluster.getCentroid());
                }
            }
            point.setClusterID(clusterID);
            clusters.get(clusterID).addPoint(point);
        }
    }

    private void calculateCentroids() {
        for(Cluster cluster : clusters) {
            List<FriendModelNomalized> listPoints = cluster.getPoints();
            int pointNum = listPoints.size();

            double sumHome = 0;
            double sumResi = 0;
            double sumGrou = 0;
            double sumReac = 0;

            if(pointNum != 0){
               for(FriendModelNomalized f:listPoints){
                   sumHome += f.getHometownInverseDistance();
                   sumResi += f.getResidenceInverseDistance();
                   sumGrou += f.getGroupsMutualNum();
                   sumReac += f.getReactionsNum();
               }
               FriendModelNomalized centroid = new FriendModelNomalized(sumHome/pointNum,
                       sumResi/pointNum,
                       sumGrou/pointNum,
                       sumReac/pointNum);
               centroid.setClusterID(cluster.getId());
               cluster.setCentroid(centroid);
            }
        }
    }

    private void plotClusters(){
        for(Cluster cluster: clusters){
            System.out.println("Cluster"+cluster.getId()+" has "+cluster.getPoints().size()+ " elements ");
            System.out.println("Centroid : " + cluster.getCentroid().toString());
            for(FriendModelNomalized f: cluster.getPoints()){
                System.out.println(f.toString());
            }
            System.out.println("----------------*****----------------");
        }
    }

    // ----------
    public  List<FriendModelNomalized> createPoints(int number){
        List<FriendModelNomalized> list = new ArrayList<>();
        Random ran = new Random();
        for(int i=0;i<number;i++){
             list.add(new FriendModelNomalized(ran.nextDouble(),ran.nextDouble(),ran.nextDouble(),ran.nextDouble()));
        }
        return list;
    }
    public  List<Cluster> createClusters(int number){
        List<Cluster> list = new ArrayList<>();
        Random ran = new Random();
        for(int i=0;i<number;i++){
            FriendModelNomalized centroid = new FriendModelNomalized(ran.nextDouble(),ran.nextDouble(),ran.nextDouble(),ran.nextDouble());
            int id = i;
            list.add(new Cluster(id,centroid));
        }
        return list;
    }



    public static void main(String[] args) {
        KMeans k1 = new KMeans();
        List<FriendModelNomalized> points = k1.createPoints(2000);
        List<Cluster> clusters = k1.createClusters(4);
        KMeans k = new KMeans(points,clusters);

//        System.out.println(points);
//        System.out.println(clusters);
        k.calculate();
    }
}

