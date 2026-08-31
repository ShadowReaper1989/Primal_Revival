package com.primalrev.primalrevival.entity.liopleurodon.goal;

import com.primalrev.primalrevival.entity.liopleurodon.LiopleurodonEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class LiopleurodonTargetGoal extends NearestAttackableTargetGoal<Player> {

    public LiopleurodonTargetGoal(LiopleurodonEntity entity) {

        super(entity, Player.class, 10, true, false, null);
    }
}
