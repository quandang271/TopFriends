package model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FriendModel {
    protected String uid;
    protected double score;
    protected int clusterID ;

    private int like;
    private int share;
    private int wow;
    private int haha;
    private int sad;
    private int love;
    private int angry;
    private int cmtCount;

    private double hometownDistance ;
    private double residenceDistance;
    private int groupsMutualNum ;

    // Normalize
    public static List<FriendModelNomalized> normalize(List<FriendModel> friends){
       List<FriendModelNomalized> results = new ArrayList<>();

       int totalLike =0,totalShare =0,totalWow =0, totalHaha =0,totalSad =0,totalLove =0,totalAngry =0,totalCmtCount =0;
       int totalGroupMutualNum =0;
       double totalHometownDistance =0;
       double totalResideceDistance =0;
       for(FriendModel fr:friends){
           totalLike += fr.like;
           totalShare += fr.share;
           totalWow += fr.wow;
           totalHaha += fr.haha;
           totalSad += fr.sad;
           totalLove += fr.love;
           totalAngry += fr.angry;
           totalCmtCount += fr.cmtCount;
           totalHometownDistance += fr.hometownDistance;
           totalResideceDistance += fr.residenceDistance;
           totalGroupMutualNum += fr.groupsMutualNum;
       }

       for (FriendModel fr: friends){
           double reactionRate = (fr._divide(fr.getLike(),totalLike)
                   + fr._divide(fr.getShare(),totalShare)
                   + fr._divide(fr.getWow(),totalWow)
                   + fr._divide(fr.getHaha(),totalHaha)
                   + fr._divide(fr.getSad(),totalSad)
                   + fr._divide(fr.getLove(),totalLove)
                   + fr._divide(fr.getAngry(),totalAngry)
                   + fr._divide(fr.getCmtCount(),totalCmtCount))/8;
           double homeTownDistanceRate = fr._divide(fr.getHometownDistance(),totalHometownDistance);
           double residenceDistanceRate = fr._divide(fr.getResidenceDistance(),totalResideceDistance);
           double groupMutualRate = fr._divide(fr.getGroupsMutualNum(),totalGroupMutualNum);
           results.add(new FriendModelNomalized(homeTownDistanceRate,residenceDistanceRate,groupMutualRate,reactionRate));
       }

       return results;
    }

    private double _divide(double tuso, double mauso){
        if(mauso == 0)
            return 0;
        else
            return tuso/mauso;
    }
}
