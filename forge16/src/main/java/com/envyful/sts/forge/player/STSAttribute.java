package com.envyful.sts.forge.player;

import com.envyful.api.forge.player.ForgeEnvyPlayer;
import com.envyful.api.forge.player.attribute.AbstractForgeAttribute;
import com.envyful.api.player.EnvyPlayer;
import com.envyful.sts.forge.EnvySTSForge;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class STSAttribute extends AbstractForgeAttribute<EnvySTSForge> {

    private static final long SECONDS_PER_MINUTE = 60;
    private static final long MINUTES_PER_HOUR = 60;
    private static final long SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    private int selectedSlot = -1;

    private long lastSold = -1;

    public STSAttribute(EnvySTSForge manager, EnvyPlayer<?> parent) {
        super(manager, (ForgeEnvyPlayer) parent);
    }

    public int getSelectedSlot() {
        return this.selectedSlot;
    }

    public void setSelectedSlot(int selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public boolean canSell() {
        if (this.lastSold == -1) {
            return true;
        }

        return (System.currentTimeMillis() - this.lastSold) > TimeUnit.SECONDS.toMillis(this.manager.getConfig().getCooldownSeconds());
    }

    public void updateLastSold() {
        this.lastSold = System.currentTimeMillis();
    }

    public void resetCooldown() {
        this.lastSold = -1;
    }

    public String getCooldownFormatted() {
        long seconds = Duration.ofMillis(
                (this.lastSold + TimeUnit.SECONDS.toMillis(this.manager.getConfig().getCooldownSeconds()))
                        - System.currentTimeMillis()).getSeconds();

        long hoursPart = (seconds / SECONDS_PER_HOUR) % 24;
        long minutesPart = (seconds / SECONDS_PER_MINUTE) % MINUTES_PER_HOUR;
        long secondsPart = seconds % SECONDS_PER_MINUTE;

        StringBuilder builder = new StringBuilder();

        if (hoursPart > 0) {
            builder.append(hoursPart).append("h ");
        }

        if (minutesPart > 0) {
            builder.append(minutesPart).append("m ");
        }

        if (secondsPart > 0) {
            builder.append(secondsPart).append("s");
        }

        return builder.toString();
    }

    @Override
    public void load() {

    }

    @Override
    public void save() {

    }
}
