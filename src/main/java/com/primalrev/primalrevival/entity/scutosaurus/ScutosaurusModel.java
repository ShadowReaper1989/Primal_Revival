package com.primalrev.primalrevival.entity.scutosaurus;

import com.geckolib.model.DefaultedEntityGeoModel;
import com.primalrev.primalrevival.PrimalRevival;
import net.minecraft.resources.Identifier;

public class ScutosaurusModel extends DefaultedEntityGeoModel<ScutosaurusEntity> {
    public ScutosaurusModel() {
        super(Identifier.fromNamespaceAndPath(PrimalRevival.MODID, "scutosaurus"));
    }
}