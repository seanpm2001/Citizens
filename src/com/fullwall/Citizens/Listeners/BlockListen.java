package com.fullwall.Citizens.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.PluginManager;

import com.fullwall.Citizens.Citizens;
import com.fullwall.Citizens.Interfaces.Listener;
import com.fullwall.Citizens.NPCTypes.Questers.Quests.QuestManager;

public class BlockListen extends BlockListener implements Listener {

	@Override
	public void registerEvents() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_BREAK, this, Event.Priority.Normal,
				Citizens.plugin);
		pm.registerEvent(Event.Type.BLOCK_PLACE, this, Event.Priority.Normal,
				Citizens.plugin);
	}

	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		QuestManager.incrementQuest(event.getPlayer(), event);
	}

	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		QuestManager.incrementQuest(event.getPlayer(), event);
	}
}