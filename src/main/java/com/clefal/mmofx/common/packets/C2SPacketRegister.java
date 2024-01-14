package com.clefal.mmofx.common.packets;

import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.library_of_exile.main.Packets;

public class C2SPacketRegister {
    public static void register() {

        int i = 200;
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new FXConfigCheckerPacket(), i++);

    }

}
