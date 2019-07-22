import model.FriendModel;
import model.FriendModelNomalized;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private List<FriendModel> getListFriends(int number){
        List<FriendModel> results = new ArrayList<>();
        Random ran = new Random();
        for(int i=0;i<number;i++){
            results.add(new FriendModel("111"+i+ran.nextInt(1000),0,10,
                    ran.nextInt(100), ran.nextInt(100),ran.nextInt(100),ran.nextInt(100),
                    ran.nextInt(100),ran.nextInt(100),ran.nextInt(100),ran.nextInt(100),
                    ran.nextDouble()*1000, ran.nextDouble()*1000,ran.nextInt(100)));
        }
        return results;
    }
    public static void main(String[] args) {
      Main m = new Main();
      List<FriendModel> frs = m.getListFriends(10);
      List<FriendModelNomalized> frns = FriendModel.normalize(frs);
      for(FriendModelNomalized f:frns){
          System.out.println(f.toString());
      }
    }
}
