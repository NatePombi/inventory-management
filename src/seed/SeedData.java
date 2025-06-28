package seed;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SeedData {

    public SeedData(){

    }

    public Path getPath(){

        return Paths.get("data","inventory.json");
    }
}
