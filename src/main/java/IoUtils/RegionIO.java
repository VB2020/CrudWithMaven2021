package IoUtils;

import model.Region;
import view.RegionView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegionIO {
    public static int getMaxId(List<Region> regions){
        int maxId;
        if(regions.isEmpty()){
            maxId = 0;
        }
        else {
            regions.sort(Comparator.comparing(Region::getId));
            maxId = regions.get(regions.size() - 1).getId();;
        }
        return maxId;
    }

    public static boolean containRegion(List<Region> regions, Region region){
        AtomicBoolean flag = new AtomicBoolean(false);
        regions.forEach((region1) -> {
            if (region1.getId() == region.getId()){
                flag.set(true);
            }
        });
        return flag.get();
    }

      public static List<Region> delRegion(List<Region> l1, List<Region> l2){
        List<Region> res = new ArrayList<>();
        l1.stream().filter((a) -> !containRegion(l2, a)).filter((a) -> !a.getName().equals(RegionView.deleted))
        .forEach(res::add);
        return res;
    }

      public static List<Region> notDelLabel(List<Region> l1, List<Region> l2){
        List<Region> res = new ArrayList<>();
        l1.stream().filter((a) -> containRegion(l2, a)).filter((a) -> !a.getName().equals(RegionView.deleted))
                .forEach(res::add);
        return res;
    }

    public static List<Region> notDelRegion(List<Region> all, List<Region> regions) {
        return new ArrayList<>();
    }
}
