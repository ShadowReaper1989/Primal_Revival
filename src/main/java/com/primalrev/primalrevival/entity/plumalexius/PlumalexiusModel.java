package com.primalrev.primalrevival.entity.plumalexius;

import com.geckolib.model.DefaultedEntityGeoModel;
import com.primalrev.primalrevival.PrimalRevival;
import net.minecraft.resources.Identifier;

public class PlumalexiusModel extends DefaultedEntityGeoModel<PlumalexiusEntity> {
    public PlumalexiusModel() {
        super(Identifier.fromNamespaceAndPath(PrimalRevival.MODID, "plumalexius"));
    }
}