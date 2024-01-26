package com.clefal.mmofx.common.packets;

import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.library_of_exile.main.Packets;

public class S2CPacketRegister {

    public static void register() {
        int i = 2000;

        Packets.registerServerToClient(MMORPG.NETWORK, new askForFXConfigPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new SpellParticlePacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new SpellFXInitPacket(), i++);

    }
}
