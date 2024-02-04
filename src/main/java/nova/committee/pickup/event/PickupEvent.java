package nova.committee.pickup.event;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PickupEvent {
    @SubscribeEvent
    public static void entityInteract(PlayerInteractEvent rawEvent) {
        if (rawEvent instanceof PlayerInteractEvent.EntityInteract event) {
            Player player = event.getPlayer();
            Entity target = event.getTarget();
            ItemStack hand = event.getItemStack();
            if (target instanceof ItemFrame frameEntity) {
                ItemStack inside = frameEntity.getItem().copy();
                if (!inside.isEmpty()) {
                    if (hand.isEmpty()) {
                        player.setItemInHand(event.getHand(), inside);
                        frameEntity.setItem(ItemStack.EMPTY);
                        event.setCancellationResult(InteractionResult.SUCCESS);
                        event.setCanceled(true);
                    } else if (!hand.isEmpty() && player.isShiftKeyDown()) {
                        if (!inside.equals(hand, true)) {
                            frameEntity.setItem(hand.split(1));
                            player.addItem(inside);
                            event.setCancellationResult(InteractionResult.SUCCESS);
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }
}
