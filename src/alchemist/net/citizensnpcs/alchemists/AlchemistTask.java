package net.citizensnpcs.alchemists;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.citizensnpcs.SettingsManager;
import net.citizensnpcs.resources.npclib.HumanNPC;

public class AlchemistTask implements Runnable {
	private HumanNPC npc;
	private int id;

	public AlchemistTask(HumanNPC npc) {
		this.npc = npc;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Alchemist alchemist = npc.getType("alchemist");
		ArrayList<ItemStack> required = new ArrayList<ItemStack>();
		for (String item : alchemist.getRecipe(alchemist.getCurrentRecipeID())
				.split(",")) {
			required.add(AlchemistManager.getStackByString(item));
		}
		ArrayList<ItemStack> npcInvContents = new ArrayList<ItemStack>();
		PlayerInventory npcInv = npc.getInventory();
		for (ItemStack i : npcInv.getContents()) {
			npcInvContents.add(i);
		}
		if (npcInvContents.containsAll(required)) {
			// clear all ingredients from the inventory
			npcInv.clear();
			// add the resulting item into the inventory
			if (new Random().nextInt(100) <= SettingsManager
					.getInt("AlchemistFailedCraftChance")) {
				npcInv.addItem(new ItemStack(alchemist.getCurrentRecipeID()));
			} else {
				npcInv.addItem(new ItemStack(SettingsManager
						.getInt("AlchemistFailedCraftItem")));
			}
			npc.getPlayer().updateInventory();
			kill();
		}
		// make sure lists are clear for next iteration
		required.clear();
		npcInv.clear();
	}

	public void addID(int id) {
		this.id = id;
	}

	private void kill() {
		run();
		Bukkit.getServer().getScheduler().cancelTask(id);
	}
}