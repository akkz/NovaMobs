package com.pikycz.novamobs.entities.animal.walking;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import static cn.nukkit.entity.Entity.DATA_FLAGS;
import cn.nukkit.entity.EntityCreature;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

import com.pikycz.novamobs.entities.animal.WalkingAnimal;
import com.pikycz.novamobs.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Rabbit extends WalkingAnimal {

    public static final int NETWORK_ID = 18;

    public Rabbit(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }
    
    @Override
    public String getName() {
        return "Rabbit";
    }

    @Override
    public float getWidth() {
        if (this.isBaby()) {
            return 0.2f;
        }
        return 0.4f;
    }

    @Override
    public float getHeight() {
        if (this.isBaby()) {
            return 0.25f;
        }
        return 0.5f;
    }

    @Override
    public double getSpeed() {
        return 1.2;
    }
    
    @Override
    public boolean isBaby() {
        return this.getDataFlag(DATA_FLAGS, Entity.DATA_FLAG_BABY);
    }

    @Override
    public void initEntity() {
        super.initEntity();
        this.setMaxHealth(3);
    }

    /**
     *
     * @param creature
     * @param distance
     * @return
     */
    public boolean targetOption(EntityCreature creature, double distance) { //TODO TAMING 
        if (creature instanceof Player) {
            Player player = (Player) creature;
            return player.spawned && player.isAlive() && !player.closed && player.getInventory().getItemInHand().getId() == Item.CARROT && distance <= 49;
        }
        return creature.isAlive() && creature.closed && distance <= 50;
    }

    @Override
    public Item[] getDrops() {
        if (this.isBaby()) {

        }
        List<Item> drops = new ArrayList<>();
        if (this.lastDamageCause instanceof EntityDamageByEntityEvent) {
            int rabbitHide = Utils.rand(0, 2); // drops 0-1 rabit hide
            int rawRabbit = Utils.rand(0, 2); // drops 0-1 raw rabit
            int rabbitfoot = Utils.rand(0, 101) <= 9 ? 1 : 0; //8.5% 
            for (int i = 0; i < rabbitHide; i++) {
                drops.add(Item.get(Item.RABBIT_HIDE, 0, 1));
            }
            for (int i = 0; i < rabbitfoot; i++) {
                drops.add(Item.get(Item.RABBIT_FOOT, 0, 1));
            }
            for (int i = 0; i < rawRabbit; i++) {
                drops.add(Item.get(this.isOnFire() ? Item.COOKED_RABBIT : Item.RAW_RABBIT, 0, 1));
            }
        }
        return drops.toArray(new Item[drops.size()]);
    }

    public int getKillExperience() {
        return Utils.rand(1, 4); // gain 1-3 experience
    }

}
