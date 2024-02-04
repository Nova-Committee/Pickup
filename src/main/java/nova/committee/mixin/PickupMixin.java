package nova.committee.mixin;

import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrameEntity.class)
public class PickupMixin {

	@Inject(method = "interact", at = {@At("HEAD")}, cancellable = true)
	private void pickup(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		ItemFrameEntity frameEntity = ((ItemFrameEntity) (Object) this);
		ItemStack handStack = player.getStackInHand(hand);
		ItemStack insideStack = frameEntity.getHeldItemStack().copy();
		if (!insideStack.isEmpty()) {
			if (handStack.isEmpty()) {
				player.setStackInHand(hand, insideStack);
				frameEntity.setHeldItemStack(ItemStack.EMPTY);
				cir.setReturnValue(ActionResult.SUCCESS);
			} else if (player.isSneaking()) {
				if (!insideStack.isItemEqual(handStack)) {
					frameEntity.setHeldItemStack(handStack.split(1));
					player.giveItemStack(insideStack);
					cir.setReturnValue(ActionResult.SUCCESS);
				}
			}
		}
	}

}