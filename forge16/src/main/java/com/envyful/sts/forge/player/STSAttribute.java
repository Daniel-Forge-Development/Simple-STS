package com.envyful.sts.forge.player;

import com.envyful.api.forge.player.ForgeEnvyPlayer;
import com.envyful.api.forge.player.attribute.AbstractForgeAttribute;
import com.envyful.api.player.EnvyPlayer;
import com.envyful.sts.forge.EnvySTSForge;

import java.util.UUID;

public class STSAttribute extends AbstractForgeAttribute<EnvySTSForge> {

    private int selectedSlot = -1;

    public STSAttribute(EnvySTSForge manager, EnvyPlayer<?> parent) {
        super(manager, (ForgeEnvyPlayer) parent);
    }

    public STSAttribute(UUID uuid) {
        super(uuid);
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
