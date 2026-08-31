package com.primalrev.primalrevival.entity.liopleurodon;

import com.geckolib.model.DefaultedEntityGeoModel;
import com.primalrev.primalrevival.PrimalRevival;
import net.minecraft.resources.Identifier;

public class LiopleurodonModel extends DefaultedEntityGeoModel<LiopleurodonEntity> {
    public LiopleurodonModel() {
        super(Identifier.fromNamespaceAndPath(PrimalRevival.MODID, "liopleurodon"));
    }
}
