package audaki.cart_engine.mixin;

import audaki.cart_engine.AceGameRules;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartBehavior;
import net.minecraft.world.entity.vehicle.NewMinecartBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.IntConsumer;

@Mixin(NewMinecartBehavior.class)
public abstract class NewMinecartBehaviorMixin extends MinecartBehavior {

    protected NewMinecartBehaviorMixin(AbstractMinecart abstractMinecart) {
        super(abstractMinecart);
    }

    @Inject(at = @At("HEAD"), method = "getMaxSpeed", cancellable = true)
    public void _getMaxSpeed(ServerLevel level, CallbackInfoReturnable<Double> cir) {
        if (!minecart.isRideable()) {
            return;
        }

        IntConsumer setSpeed = (speed) -> {
            if (speed == 0) {
                return;
            }
            cir.setReturnValue(speed * (this.minecart.isInWater() ? 0.5 : 1.0) / 20.0);
            cir.cancel();
        };

        Entity passenger = minecart.getFirstPassenger();
        if (passenger == null) {
            setSpeed.accept(level.getGameRules().getInt(AceGameRules.MINECART_MAX_SPEED_EMPTY_RIDER));
            return;
        }

        if (passenger instanceof Player) {
            setSpeed.accept(level.getGameRules().getInt(AceGameRules.MINECART_MAX_SPEED_PLAYER_RIDER));
            return;
        }

        setSpeed.accept(level.getGameRules().getInt(AceGameRules.MINECART_MAX_SPEED_OTHER_RIDER));
    }
}
