package com.envyful.simple.sts.forge.player;

import com.envyful.api.forge.player.ForgeEnvyPlayer;
import com.envyful.api.forge.player.attribute.AbstractForgeAttribute;
import com.envyful.api.player.EnvyPlayer;
import com.envyful.simple.sts.forge.SimpleSTSForge;

public class STSAttribute extends AbstractForgeAttribute<SimpleSTSForge> {

    private int selectedSlot = -1;

    public STSAttribute(SimpleSTSForge manager, EnvyPlayer<?> parent) {
        super(manager, (ForgeEnvyPlayer) parent);
    }

    public int getSelectedSlot() {
        return this.selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
