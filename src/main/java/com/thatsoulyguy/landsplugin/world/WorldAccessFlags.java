package com.thatsoulyguy.landsplugin.world;

import java.util.EnumSet;

public enum WorldAccessFlags
{
    ALLOW_BLOCK_BREAK(0),
    ALLOW_BLOCK_PLACE(2),
    DENY_BLOCK_BREAK(4),
    DENY_BLOCK_PLACE(8),
    PUBLIC(16),
    PRIVATE(32),
    ERROR(-1);

    private int raw;

    WorldAccessFlags(int raw)
    {
        this.raw = raw;
    }

    public int GetRaw()
    {
        return raw;
    }

    public WorldAccessFlags GetFromRaw(int raw)
    {
        return switch (raw)
        {
            case 0 -> ALLOW_BLOCK_BREAK;
            case 1 -> ALLOW_BLOCK_PLACE;
            case 2 -> DENY_BLOCK_BREAK;
            case 3 -> DENY_BLOCK_PLACE;
            case 4 -> PUBLIC;
            case 5 -> PRIVATE;

            default -> ERROR;
        };
    }

    public static int Combine(WorldAccessFlags... flags)
    {
        int result = 0;

        for (WorldAccessFlags flag : flags)
            result |= flag.GetRaw();

        return result;
    }

    public static EnumSet<WorldAccessFlags> Decompose(int flags)
    {
        EnumSet<WorldAccessFlags> result = EnumSet.noneOf(WorldAccessFlags.class);

        for (WorldAccessFlags flag : WorldAccessFlags.values())
        {
            if (flag != WorldAccessFlags.ERROR && (flags & flag.GetRaw()) == flag.GetRaw())
                result.add(flag);
        }

        return result;
    }
}