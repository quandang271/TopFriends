package algorithm;
import model.FriendModel;
import java.util.*;

public class TopFriends {

    private void sortFriends(List<FriendModel> listFriends){
        Collections.sort(listFriends, new Comparator<FriendModel>() {
            public int compare(FriendModel f1, FriendModel f2) {
                double scoreF1CompreF2 = f1.getScore() - f2.getScore();
                if(scoreF1CompreF2 > 0)
                    return -1;
                else if (scoreF1CompreF2 < 0)
                    return 1;
                else return 0;
            }
        });
    }

    public List<FriendModel> getTopFriends(int count, List<FriendModel> listFriends){
        if(count <= 0) throw new IllegalArgumentException("Count <= 0 !! Set parameter again, please !!");
        int iSize = listFriends.size();
        if(count > iSize) throw new IllegalArgumentException("Count too large !! You should set count < "+ iSize);

        this.sortFriends(listFriends);
        List<FriendModel> result = new ArrayList<FriendModel>();
        for(int i=0;i<count;i++){
            result.add(listFriends.get(i));
        }
        return result;
    }
}
