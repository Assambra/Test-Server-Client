using com.tvd12.ezyfoxserver.client;
using com.tvd12.ezyfoxserver.client.constant;
using com.tvd12.ezyfoxserver.client.request;
using com.tvd12.ezyfoxserver.client.support;
using com.tvd12.ezyfoxserver.client.unity;
using Object = System.Object;

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

        // Login to socket server
        socketProxy.setLoginUsername(username);
        socketProxy.setLoginPassword(password);

        socketProxy.setUrl(socketConfigVariable.Value.TcpUrl);
        socketProxy.setUdpPort(socketConfigVariable.Value.UdpPort);
        socketProxy.setDefaultAppName(socketConfigVariable.Value.AppName);
        socketProxy.setTransportType(EzyTransportType.UDP);
        socketProxy.onUdpHandshake<Object>(HandleUdpHandshake);

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
    }
}