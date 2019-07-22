package model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class FriendModelNomalized extends FriendModel{

//    public FriendModelNomalized(double hometownInverseDistance, double residenceInverseDistance, double groupsMutualNum, double reactionsNum){
//        this.hometownInverseDistance = hometownInverseDistance;
//        this.residenceInverseDistance = residenceInverseDistance;
//        this.groupsMutualNum = groupsMutualNum;
//        this.reactionsNum = reactionsNum;
//    }


    // normalize [0-1], distance of (hometown and residence are inversed)
    private double hometownInverseDistance ;
    private double residenceInverseDistance ;
    private double groupsMutualNum ;
    private double reactionsNum ;

    public void setClusterID(int clusterID) {
        this.clusterID = clusterID;
    }

    //Calculates the distance between two points.
    public static double distance(FriendModelNomalized point, FriendModelNomalized centroid) {
        return Math.sqrt( Math.pow((centroid.getHometownInverseDistance() - point.getHometownInverseDistance()), 2)
                + Math.pow((centroid.getResidenceInverseDistance() - point.getResidenceInverseDistance()), 2)
                + Math.pow((centroid.getGroupsMutualNum() - point.getGroupsMutualNum()), 2)
                + Math.pow((centroid.getReactionsNum() - point.getReactionsNum()), 2)
        );
    }

    public static void main(String[] args) {

    }
}
