package me.nixuge.azbypass.config;

public enum BypassType {
    PAC5DIGIT, // Works on the standalone plugin & on Funcraft, but Funcraft sends encrypted packets
    PACALONE // Works only on Funcraft, but Funcraft sends clear packets
}

// Note: all strings starting with PAC bypass funcraft
// 5 nums after = loading the "new" az launcher, obf packets
// anything else (nothing or other) = old, working just fine