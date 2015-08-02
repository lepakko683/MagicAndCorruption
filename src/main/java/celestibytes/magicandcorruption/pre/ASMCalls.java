package celestibytes.magicandcorruption.pre;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import celestibytes.magicandcorruption.asm.MagicAndCorruption_ASM;
import celestibytes.magicandcorruption.pre.util.IAsmHooks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ASMCalls {
	
	public static final ASMClassLoader loader = new ASMClassLoader();
	private static IAsmHooks asmHooks = null;
	
	/** May return null */
	public static IAsmHooks getAsmHooks() {
		if(asmHooks != null) {
			return asmHooks;
		}
		
		boolean obfuscated = MagicAndCorruption_ASM.isObfuscatedEnv(); // TODO: ?
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		
		MethodVisitor mv;
		cw.visit(Opcodes.V1_6, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER, "celestibytes/magicandcorruption/pre/AsmHook", null, "java/lang/Object", new String[] {Type.getInternalName(IAsmHooks.class)});
		cw.visitSource(null, null); // TODO: code!
		cw.visitField(Opcodes.ACC_PUBLIC, "instance", "Ljava/lang/Object;", null, null);
		
		mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "(Ljava/lang/Object;)V", null, null);
		mv.visitCode();
		mv.visitVarInsn(Opcodes.ALOAD, 0);
		mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		
		mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "resizePotionArray", "(I)V", null, null);
		mv.visitCode(); // 2 = old array, 3 = new array
		mv.visitFieldInsn(Opcodes.GETSTATIC, obfuscated ? "rv" : "net/minecraft/potion/Potion", obfuscated ? "a" : "potionTypes", "[L" + (obfuscated ? "rv" : "net/minecraft/potion/Potion") + ";");
		mv.visitInsn(Opcodes.DUP);
		mv.visitInsn(Opcodes.ARRAYLENGTH);
		mv.visitVarInsn(Opcodes.ISTORE, 5);
		mv.visitVarInsn(Opcodes.ASTORE, 2);
		mv.visitVarInsn(Opcodes.ILOAD, 1);
		mv.visitTypeInsn(Opcodes.ANEWARRAY, obfuscated ? "rv" : "net/minecraft/potion/Potion");
		mv.visitVarInsn(Opcodes.ASTORE, 3);
		mv.visitInsn(Opcodes.ICONST_0);
		mv.visitVarInsn(Opcodes.ISTORE, 4);
		Label loop = new Label();
		mv.visitLabel(loop);
		mv.visitVarInsn(Opcodes.ALOAD, 3); // tgt array
		mv.visitVarInsn(Opcodes.ILOAD, 4); // tgt index
		mv.visitVarInsn(Opcodes.ALOAD, 2); // src array
		mv.visitVarInsn(Opcodes.ILOAD, 4); // src index
		mv.visitInsn(Opcodes.AALOAD);  // get tgt value
		mv.visitInsn(Opcodes.AASTORE);
		mv.visitIincInsn(4, 1);
		mv.visitVarInsn(Opcodes.ILOAD, 4);
		mv.visitVarInsn(Opcodes.ILOAD, 5);
		mv.visitJumpInsn(Opcodes.IF_ICMPLT, loop);
		mv.visitVarInsn(Opcodes.ALOAD, 3);
		mv.visitFieldInsn(Opcodes.PUTSTATIC, obfuscated ? "rv" : "net/minecraft/potion/Potion", obfuscated ? "a" : "potionTypes", "[L" + (obfuscated ? "rv" : "net/minecraft/potion/Potion") + ";");
		mv.visitInsn(Opcodes.RETURN);
		mv.visitMaxs(0, 0);
		mv.visitEnd();
		mv = null;
		cw.visitEnd();
		
		byte[] classBytes = cw.toByteArray();
		
		Class<?> asmHooksClass = loader.define("celestibytes/magicandcorruption/pre/AsmHook", classBytes);
		try {
			asmHooks = (IAsmHooks) asmHooksClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return asmHooks;
	}
	
	public static String getCountStringForStack(ItemStack stack) {
		if(stack.stackSize == 666) {
			return EnumChatFormatting.RED + String.valueOf(stack.stackSize);
		} else if(stack.stackSize == 1000) {
			return EnumChatFormatting.AQUA + "1K";
		}
		
		if(stack.stackSize < 64) {
			return String.valueOf(stack.stackSize);
		} else if(stack.stackSize < 128) {
			return EnumChatFormatting.LIGHT_PURPLE + String.valueOf(stack.stackSize);
		} else if(stack.stackSize < 256) {
			return EnumChatFormatting.BLUE + String.valueOf(stack.stackSize);
		} else if(stack.stackSize < 512) {
			return EnumChatFormatting.GOLD + String.valueOf(stack.stackSize);
		} else if(stack.stackSize < 1000) {
			return EnumChatFormatting.GREEN + String.valueOf(stack.stackSize);
		}
		
		return String.valueOf(stack.stackSize);
	}
	
	/** returns false if the provided stack got empty and therefore we can "break" out of the loop */
	public static boolean splitStackInSlot(Slot slot, ItemStack stack) {
		int slotLimit = slot.getSlotStackLimit();
		if(stack.stackSize > slotLimit) {
			slot.putStack(stack.splitStack(slotLimit));
			slot.onSlotChanged();
		} else {
			slot.putStack(stack.copy());
			slot.onSlotChanged();
			stack.stackSize = 0;
			
			return true;
		}
		
		return false;
	}
	
	public static void handleCycleExtraItems(ItemStack[] stacks, World world, EntityPlayer plr) {
		System.out.println("Call!");
		if(stacks.length > 1) {
			for(int i = 1; i < stacks.length; i++) {
				if(stacks[i] != null) {
					System.out.println("extra stack size: " + stacks[i].stackSize + ", name: " + stacks[i].getDisplayName() + ", meta: " + stacks[i].getItemDamage());
				}
				//if(!plr.inventory.addItemStackToInventory(stacks[i].copy())) {
				if(!addExtraStack(stacks[i].copy(), plr)) {
					EntityItem item = new EntityItem(world, plr.posX, plr.posY + plr.eyeHeight/2f, plr.posZ, stacks[i]);
					world.spawnEntityInWorld(item);
				}
			}
		}
	}
	
	private static boolean addExtraStack(ItemStack stack, EntityPlayer plr) { // TODO!
		InventoryPlayer inv = plr.inventory;
		
		for(int i = 0; i < inv.mainInventory.length; i++) {
			if(i != inv.currentItem) {
				ItemStack is = inv.mainInventory[i];
				if(is != null) {
					if(is.getItem() == stack.getItem() && is.getItemDamage() == stack.getItemDamage() && ItemStack.areItemStacksEqual(is, stack)) {
						int space = Math.min(is.getMaxStackSize(), inv.getInventoryStackLimit()) - is.stackSize;
						if(space > 0) {
							int add = Math.min(space, stack.stackSize);
							stack.stackSize -= add;
							is.stackSize += add;
							
							if(stack.stackSize <= 0) {
								return true;
							}
						}
					}
				}
			}
		}
		
		for(int i = 0; i < inv.mainInventory.length; i++) {
			if(i != inv.currentItem) {
				if(inv.mainInventory[i] == null) {
					int put = Math.min(stack.stackSize, inv.getInventoryStackLimit());
					inv.mainInventory[i] = stack.splitStack(put);
					
					if(stack.stackSize <= 0) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public static void debugOutput(int id) { // celestibytes/magicandcorruption/ASMCalls debugOutput (I)V false
		switch(id) {
		case 0:
			System.out.println("nosend");
			break;
		case 1:
			System.out.println("sendContainerAndContentsToPlayer");
			break;
		case 2:
			System.out.println("stepassist on");
			break;
		case 3:
			System.out.println("stepassist off");
			break;
		default:
			System.out.println("unknown code: " + id);
		}
	}
	
	private static class ASMClassLoader extends ClassLoader {
		
		public ASMClassLoader() {
			super(ASMClassLoader.class.getClassLoader());
		}
		
		public Class<?> define(String name, byte[] classBytes) {
			return defineClass(name, classBytes, 0, classBytes.length);
		}
		
	}
}
