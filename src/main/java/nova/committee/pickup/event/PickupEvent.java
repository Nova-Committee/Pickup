package nova.committee.pickup.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PickupEvent {
    @SubscribeEvent
    public static void entityInteract(PlayerInteractEvent rawEvent) {
        if (rawEvent instanceof PlayerInteractEvent.EntityInteract) {
            PlayerInteractEvent.EntityInteract event = (PlayerInteractEvent.EntityInteract) rawEvent;
            PlayerEntity player = event.getPlayer();
            Entity target = event.getTarget();
            ItemStack hand = event.getItemStack();
            if (target instanceof ItemFrameEntity) {
                ItemFrameEntity frameEntity = (ItemFrameEntity) target;
                ItemStack inside = frameEntity.getItem().copy();
                if (!inside.isEmpty()) {
                    if (hand.isEmpty()) {
                        player.setItemInHand(event.getHand(), inside);
                        frameEntity.setItem(ItemStack.EMPTY);
                        event.setCancellationResult(ActionResultType.SUCCESS);
                        event.setCanceled(true);
                    } else if (!hand.isEmpty() && player.isShiftKeyDown()) {
                        if (!inside.equals(hand, true)) {
                            frameEntity.setItem(hand.split(1));
                            player.addItem(inside);
                            event.setCancellationResult(ActionResultType.SUCCESS);
                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }
}
