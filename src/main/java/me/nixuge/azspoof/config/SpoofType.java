package me.nixuge.azspoof.config;

public enum SpoofType {
    PAC5DIGIT, // Works on the standalone plugin & on Funcraft, but Funcraft sends encrypted packets
    PACALONE // Works only on Funcraft, but Funcraft sends clear packets
}

// Note: all strings starting with PAC spoof funcraft
// 5 nums after = loading the "new" az launcher, obf packets
// anything else (nothing or other) = old, working just fine