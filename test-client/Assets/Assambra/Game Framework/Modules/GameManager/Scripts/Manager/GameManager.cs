using JetBrains.Annotations;
using System.Collections.Generic;
using UnityEngine;

public class GameManager : MonoBehaviour
{
    public static GameManager Instance { get; private set; }

    [field: SerializeField] public SceneHandler sceneHandler { get; private set; }
    [field: SerializeField] public CameraController cameraController { get; private set; }

    [SerializeField] private GameObject playerPrefab;
    
    private Dictionary<string, GameObject> players = new Dictionary<string, GameObject>();

    public void Awake()
    {
        if (Instance != null && Instance != this)
            Destroy(this);
        else
            Instance = this;

        SceneHandler.OnSceneChanged += OnSceneChanged;
    }

    private void Start()
    {
        cameraController.ChangeCameraPreset("PreGameCamera");
    }

    private void OnDestroy()
    {
        SceneHandler.OnSceneChanged -= OnSceneChanged;
    }

    public void ChangeScene(Scenes scene)
    {
        sceneHandler.CurrentScene = sceneHandler.Scenes[(int)scene];
    }

    private void OnSceneChanged()
    {
        Debug.Log("Scene changed");
    }

    public void SpawnEntity(string entityName, bool isLocalPlayer, Vector3 position, Vector3 rotation)
    {
        GameObject pgo = GameObject.Instantiate(playerPrefab); //position, Quaternion.Euler(rotation));
        players.Add(entityName, pgo);

        Player player = pgo.GetComponent<Player>();
        pgo.name = entityName;
        player.PlayerName = pgo.name;

        if(isLocalPlayer)
        {
            player.IsLocalPlayer = true;
            cameraController.ChangeCameraPreset("GameCamera");
            cameraController.CameraTarget = pgo;
        }
    }

    public void MoveEntity(string playerName, Vector3 position, Quaternion rotation)
    {
        GameObject playerGameObject = players[playerName];
        Player player = playerGameObject.GetComponent<Player>();
        //player.characterController.Move(position);
        player.transform.position = position;
        player.transform.rotation = rotation;
    }
}
