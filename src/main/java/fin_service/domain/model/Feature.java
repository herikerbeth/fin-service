package fin_service.domain.model;

import jakarta.persistence.Entity;

@Entity(name = "tb_feature")
public class Feature extends BaseItem {

    public Feature(String icon, String description) {
        super();
    }

    public Feature() {

    }
}
