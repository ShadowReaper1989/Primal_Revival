package com.primalrev.primalrevival.entity.apatosaurus;

import com.geckolib.model.DefaultedEntityGeoModel;
import com.primalrev.primalrevival.PrimalRevival;
import net.minecraft.resources.Identifier;

public class ApatosaurusModel extends DefaultedEntityGeoModel<ApatosaurusEntity> {
    public ApatosaurusModel() {
        super(Identifier.fromNamespaceAndPath(PrimalRevival.MODID, "apatosaurus"));
    }
}
