package me.nixuge.azspoof.mixins.client.network;

import org.spongepowered.asm.mixin.Mixin;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ByteToMessageDecoder.class)
public class ByteToMessageDecoderMixin {
    @Inject(method = "callDecode", at = @At("HEAD"), remap = false)
    public void decode(CallbackInfo ci) {
        System.out.println("TESTCLASS: " + this.getClass().getName());
    }
}
