using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.constant;
using com.tvd12.ezyfoxserver.client.entity;
using com.tvd12.ezyfoxserver.client.request;
using com.tvd12.ezyfoxserver.client.support;
using com.tvd12.ezyfoxserver.client.unity;
using Object = System.Object;
using UnityEngine;
using com.tvd12.ezyfoxserver.client.factory;

public class NetworkManager : EzyDefaultController
{
    public static NetworkManager Instance { get; private set; }

    private void Awake()
    {
        if (Instance != null && Instance != this)
            Destroy(this);
        else
            Instance = this;
    }

    private new void OnEnable()
    {
        base.OnEnable();
        AddHandler<EzyObject>(Commands.PLAY, OnPlayResponse);
        AddHandler<EzyArray>(Commands.SYNC_POSITION, OnPlayerSyncPosition);
        AddHandler<EzyObject>(Commands.ENTITY_SPAWN, OnEntitySpawn);
        
    }

    private void Update()
    {
        EzyClients.getInstance()
                .getClient(socketConfigVariable.Value.ZoneName)
                .processEvents();
    }

    public void Login(string username, string password)
    {
        LOGGER.debug("Login username = " + username + ", password = " + password);
        LOGGER.debug("Socket clientName = " + socketProxy.getClient().getName());

        socketProxy.onLoginSuccess<Object>(HandleLoginSuccess);
        socketProxy.onAppAccessed<Object>(HandleAppAccessed);
        socketProxy.onUdpHandshake<Object>(HandleUdpHandshake);

        // Login to socket server
        socketProxy.setLoginUsername(username);
        socketProxy.setLoginPassword(password);

        socketProxy.setUrl(socketConfigVariable.Value.TcpUrl);
        socketProxy.setUdpPort(socketConfigVariable.Value.UdpPort);
        socketProxy.setDefaultAppName(socketConfigVariable.Value.AppName);
        socketProxy.setTransportType(EzyTransportType.UDP);
        

        socketProxy.connect();
    }

    public new void Disconnect()
    {
        base.Disconnect();
    }

    private void HandleLoginSuccess(EzySocketProxy proxy, Object data)
    {
        LOGGER.debug("Log in successfully");
    }

    private void HandleUdpHandshake(EzySocketProxy proxy, Object data)
    {
        LOGGER.debug("HandleUdpHandshake");
        socketProxy.send(new EzyAppAccessRequest(socketConfigVariable.Value.AppName));
    }


    private void HandleAppAccessed(EzyAppProxy proxy, Object data)
    {
        LOGGER.debug("App access successfully");
        PlayRequest();
    }

    #region REQUESTS

    private void PlayRequest()
    {
        LOGGER.debug("Send PlayRequest");
        appProxy.send(Commands.PLAY);
    }


    public void SendPlayerInput(int time, bool[] inputs, Quaternion rotation, Vector3 transformForward)
    {
        EzyObject data = EzyEntityFactory
            .newObjectBuilder()
            .append("t", time)
            .append("i", inputs)
            .append(
                "r",
                EzyEntityFactory.newArrayBuilder()
                    .append(rotation.eulerAngles.x)
                    .append(rotation.eulerAngles.y)
                    .append(rotation.eulerAngles.z)
                    .build()
            )
            .build();
        appProxy.send(Commands.PLAYER_INPUT, data);
    }

    #endregion

    #region RESPONSE

    private void OnPlayResponse(EzyAppProxy proxy, EzyObject data)
    {
        LOGGER.debug("OnPlayResponse");
        GameManager.Instance.ChangeScene(Scenes.World);
    }

    private void OnEntitySpawn(EzyAppProxy proxy, EzyObject data)
    {
        string entityName = data.get<string>("entityName");
        bool isLocalPlayer = data.get<bool>("isLocalPlayer");
        EzyArray position = data.get<EzyArray>("position");
        EzyArray rotation = data.get<EzyArray>("rotation");

        Vector3 initalPosition = new Vector3(position.get<float>(0), position.get<float>(1), position.get<float>(2));
        Vector3 initalRotation = new Vector3(rotation.get<float>(0), rotation.get<float>(1), rotation.get<float>(2));

        GameManager.Instance.SpawnEntity(entityName, isLocalPlayer, initalPosition, initalRotation);
    }

    private void OnPlayerSyncPosition(EzyAppProxy proxy, EzyArray data)
    {
        LOGGER.debug("OnPlayerSyncPosition: " + data);
        string playerName = data.get<string>(0);
        EzyArray positionArray = data.get<EzyArray>(1);
        EzyArray rotationArray = data.get<EzyArray>(2);
        int time = data.get<int>(3);
        Vector3 position = new Vector3(
            positionArray.get<float>(0),
            positionArray.get<float>(1),
            positionArray.get<float>(2)
        );
        Vector3 rotation = new Vector3(
            rotationArray.get<float>(0),
            rotationArray.get<float>(1),
            rotationArray.get<float>(2)
        );

        LOGGER.debug("SyncPosition for player: " + playerName + " reseive position: " + position + ", rotation: " + rotation + " time: " + time);

        GameManager.Instance.MoveEntity(playerName, position, Quaternion.Euler(rotation));
    }

    private void OnError(EzyAppProxy proxy, EzyArray data)
    {
        LOGGER.error("Error: " + data);
    }

    #endregion
}
