package me.nixuge.azspoof.config;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Cache {
    @Setter
    private boolean azSpoof = true;
    @Setter
    private boolean savePackets = false;

    public void setSpoofMode(String mode) {
        this.spoofType = mode.contains("5") ? SpoofType.PAC5DIGIT : SpoofType.PACALONE;
    }
    private SpoofType spoofType = SpoofType.PACALONE;
}
