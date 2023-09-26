using System;

public sealed class Commands
{
    public const String PLAY = "play";
    public const String SYNC_POSITION = "s";
    public const String ENTITY_SPAWN = "entitySpawn";
    public const String PLAYER_INPUT = "playerInput";

    public const String ERROR = "error";

    private Commands() { }
}
