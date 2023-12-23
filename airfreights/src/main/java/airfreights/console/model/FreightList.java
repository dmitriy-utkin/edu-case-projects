package airfreights.console.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FreightList {
    public FreightList() {
        this.freightList = new ArrayList<>();
    }

    private List<Freight> freightList;

    public void updateFreightList(Freight freight) {
        freightList.add(freight);
    }
}
