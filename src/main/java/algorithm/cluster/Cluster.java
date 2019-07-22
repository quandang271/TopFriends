package algorithm.cluster;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;
import model.FriendModelNomalized;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Cluster {
    public Cluster(int id){
        this.id = id;
        points = new ArrayList<>();
    }
    public Cluster(int id, FriendModelNomalized centroid){
        this.id = id;
        this.centroid = centroid;
        points = new ArrayList<>();
    }

    private List<FriendModelNomalized> points;
    private FriendModelNomalized centroid;

    @Setter(AccessLevel.NONE)
    private int id;

    public void addPoint(FriendModelNomalized point){
        if(point != null)
            points.add(point);
    }

    public void clear(){
        if(points != null)
            points.clear();
    }

    public static void main(String[] args) {
        KMeans k = new KMeans();
        List<Cluster> clusters = new ArrayList<>();
        List<FriendModelNomalized> fr = new ArrayList<>();
        clusters = k.createClusters(3);
        fr = k.createPoints(2);
        for(Cluster clus:clusters){
            clus.setPoints(fr);
        }
        System.out.println(clusters);
        for(Cluster clus:clusters){
            clus.clear();
        }
        System.out.println("-----");
        System.out.println(clusters.get(0));
    }
}