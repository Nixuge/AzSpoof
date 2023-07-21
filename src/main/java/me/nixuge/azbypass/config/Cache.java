package me.nixuge.azbypass.config;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Cache {
    @Setter
    private boolean azBypass = false;

    public void setBypassMode(String mode) {
        this.bypassType = mode.contains("5") ? BypassType.PAC5DIGIT : BypassType.PACALONE;
    }
    private BypassType bypassType = BypassType.PACALONE;
}
