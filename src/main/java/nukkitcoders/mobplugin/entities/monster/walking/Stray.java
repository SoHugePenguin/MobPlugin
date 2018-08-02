package nukkitcoders.mobplugin.entities.monster.walking;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBow;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.MobEquipmentPacket;
import nukkitcoders.mobplugin.entities.monster.WalkingMonster;
import nukkitcoders.mobplugin.route.WalkerRouteFinder;
import nukkitcoders.mobplugin.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Stray extends WalkingMonster {

    public static final int NETWORK_ID = 46;

    public Stray(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
        route = new WalkerRouteFinder(this);
    }

    @Override
    public void initEntity() {
        super.initEntity();

        setMaxHealth(20);
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public String getName() {
        return "Stray";
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.99f;
    }

    @Override
    public void spawnTo(Player player) {
        super.spawnTo(player);

        MobEquipmentPacket pk = new MobEquipmentPacket();
        pk.eid = getId();
        pk.item = new ItemBow();
        pk.hotbarSlot = 0;
        player.dataPacket(pk);
    }

    @Override
    public boolean entityBaseTick(int tickDiff) {
        boolean hasUpdate;

        hasUpdate = super.entityBaseTick(tickDiff);

        int time = getLevel().getTime() % Level.TIME_FULL;
        if (!isOnFire() && !level.isRaining() && (time < 12567 || time > 23450)) {
            setOnFire(100);
        }

        return hasUpdate;
    }

    @Override
    public void attackEntity(Entity player) {
	    return;
    }

    @Override
    public Item[] getDrops() {
        List<Item> drops = new ArrayList<>();
        if (lastDamageCause instanceof EntityDamageByEntityEvent) {
            int bones = Utils.rand(0, 3);
            int arrows = Utils.rand(0, 3);
            for (int i = 0; i < bones; i++) {
                drops.add(Item.get(Item.BONE, 0, 1));
            }
            for (int i = 0; i < arrows; i++) {
                drops.add(Item.get(Item.ARROW, 0, 1));
            }
        }
        return drops.toArray(new Item[drops.size()]);
    }

    @Override
    public int getKillExperience() {
        return 5;
    }

}
