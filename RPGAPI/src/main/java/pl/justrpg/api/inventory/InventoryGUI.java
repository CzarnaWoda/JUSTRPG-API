package pl.justrpg.api.inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class InventoryGUI
  implements Listener
{
  private Inventory inventory;
  private Map<Integer, IAction> actions;

public InventoryGUI(Plugin plugin, String title, int rows)
  {
    this.inventory = Bukkit.createInventory(null, rows * 9, ChatColor.translateAlternateColorCodes('&', title));
    this.actions = new HashMap();
    
    Bukkit.getPluginManager().registerEvents(this, plugin);
  }
  
  public void setItem(int slot, ItemStack itemStack, IAction clickAction)
  {
    slot = slot > this.inventory.getSize() ? slot % this.inventory.getSize() : slot;
    this.inventory.setItem(slot, itemStack);
    this.actions.put(Integer.valueOf(slot), clickAction);
  }

  public InventoryGUI setAllItems(ItemStack[] items)
  {
    for (int i = 0; i < this.inventory.getSize(); i++) {
      this.inventory.setItem(i, items[i]);
    }
    return this;
  }
  
  public InventoryGUI setAction(int slot, IAction action)
  {
    if (action != null) {
      this.actions.put(Integer.valueOf(slot), action);
    } else {
      this.actions.remove(Integer.valueOf(slot));
    }
    return this;
  }
  
  public InventoryGUI setOpenAction(IAction action)
  {
    if (action != null) {
      this.actions.put(Integer.valueOf(-1), action);
    } else {
      this.actions.remove(Integer.valueOf(-1));
    }
    return this;
  }
  
  public ItemStack getItem(int i)
  {
    return this.inventory.getItem(i);
  }
  
  public InventoryGUI setCloseAction(IAction action)
  {
    if (action != null) {
      this.actions.put(Integer.valueOf(-2), action);
    } else {
      this.actions.remove(Integer.valueOf(-2));
    }
    return this;
  }
  
  public void openInventory(Player player)
  {
    player.openInventory(this.inventory);
  }
  
  public InventoryGUI openInventory(Player[] players)
  {
    for (Player player : players) {
      openInventory(player);
    }
    return this;
  }
  
  public InventoryGUI openInventory(Collection<? extends Player> players)
  {
    for (Player player : players) {
      openInventory(player);
    }
    return this;
  }
  
  public Inventory get()
  {
    return this.inventory;
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent e)
  {
    if (e.getInventory().equals(this.inventory))
    {
      e.setCancelled(true);
      IAction action = (IAction)this.actions.get(Integer.valueOf(e.getRawSlot()));
      if (action != null) {
        action.execute((Player)e.getWhoClicked(), e.getInventory(), e.getRawSlot(), e.getInventory().getItem(e.getRawSlot()));
      } else {
        e.getWhoClicked().openInventory(e.getInventory());
      }
    }
  }
  
  @EventHandler
  public void onInventoryMoveItemEvent(InventoryMoveItemEvent e)
  {
    if (e.getDestination().equals(this.inventory)) {
      e.setCancelled(true);
    }
    if (e.getInitiator().equals(this.inventory)) {
      e.setCancelled(true);
    }
    if (this.inventory.equals(e.getSource())) {
      e.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onInventoryOpen(InventoryOpenEvent e)
  {
    if (e.getInventory().equals(this.inventory))
    {
      IAction action = (IAction)this.actions.get(Integer.valueOf(-1));
      if (action != null) {
        action.execute((Player)e.getPlayer(), e.getInventory(), -1, null);
      }
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent e)
  {
    if (e.getInventory().equals(this.inventory))
    {
      IAction action = (IAction)this.actions.get(Integer.valueOf(-2));
      if (action != null) {
        action.execute((Player)e.getPlayer(), e.getInventory(), -1, null);
      }
    }
  }
  
  @EventHandler
  public void onInventoryInteractEvent(InventoryInteractEvent e)
  {
    if (this.inventory.equals(e.getInventory())) {
      e.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onInventoryDragEvent(InventoryDragEvent e)
  {
    if (e.getInventory().equals(this.inventory)) {
      e.setCancelled(true);
    }
  }
}