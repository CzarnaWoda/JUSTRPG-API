package pl.justrpg.api.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract interface IAction
{
  public abstract void execute(Player paramPlayer, Inventory paramInventory, int paramInt, ItemStack paramItemStack);
}
