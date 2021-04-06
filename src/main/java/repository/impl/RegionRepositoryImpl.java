package repository.impl;

import IoUtils.IoUtils;
import IoUtils.RegionIO;
import model.Region;
import repository.RegionRepository;
import view.RegionView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RegionRepositoryImpl implements RegionRepository {
    File fileName = new File("./src/main/java/resource/regions.json");
    @Override
    public Region getById(Integer id) throws FileNotFoundException {
        List<Region> regions = getAllInternal();
        return regions.stream().filter(any_label -> any_label.getId() == id).findFirst().orElse(new Region());
    }

    @Override
    public List<Region> getAll() throws FileNotFoundException {
        if (Objects.isNull(IoUtils.readFromFile(fileName))){
            return new ArrayList<>();
        }
        else{
            return IoUtils.readFromFile(fileName);
        }
    }

    @Override
    public void save(Region region) throws FileNotFoundException {
        List<Region> regions = getAllInternal();
        try {
            regions.add(region);
            IoUtils.writeToFile(regions, fileName);
        }
        catch (Exception er){
            System.out.println("Id not exist");
        }

    }

    @Override
    public void deleteById(Integer id) throws Exception {
        List<Region> regions = getAllInternal();
        if (RegionIO.getMaxId(regions) == id) {
            regions.forEach((region) ->
            {
                if (region.getId() == id) {
                    region.setName(RegionView.deleted);
                }
            });
        } else {
            regions.removeIf((region) -> region.getId() == id);
        }
        IoUtils.writeToFile(regions, fileName);
    }

    @Override
    public void update(Region region) throws FileNotFoundException {
        List<Region> regions = getAllInternal();
         try{
            regions.forEach((region1) -> {
                if (region1.getId() == region.getId()) {
                    region1.setId(region.getId());
                    region1.setName(region.getName());
                }
            });
                regions.add(region);
            IoUtils.writeToFile(regions, fileName);
        }
        catch (Exception er){
            System.out.println("Id not exist");
        }

    }

    private List<Region> getAllInternal() throws FileNotFoundException {
        if (Objects.isNull(IoUtils.readFromFile(fileName))){
            return new ArrayList<>();
        }
        else{
            return IoUtils.readFromFile(fileName);
        }
    }
}
