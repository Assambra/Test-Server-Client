using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour
{
    [SerializeField] Player player;

    private bool[] inputs;
    private int clientTick;

    private float lastYRotation;
    private float currentYRotation;

    private void Start()
    {
        inputs = new bool[4];
        lastYRotation = player.transform.rotation.y;
    }

    private void Update()
    {
        if (!player.IsLocalPlayer)
            return;

        GetUserInput();
    }

    private void FixedUpdate()
    {
        if (!player.IsLocalPlayer)
            return;

        currentYRotation = player.transform.rotation.y;

        if (player.IsLocalPlayer)
            clientTick++;

        if (inputs[0] || inputs[1] || inputs[2] || inputs[3] || lastYRotation != currentYRotation)
        {
            SendInput();
            lastYRotation = currentYRotation;
        }
            

        for(int i = 0; i < inputs.Length; i++)
            inputs[i] = false;

        float xPos = Mathf.Sin(transform.eulerAngles.y * Mathf.Deg2Rad) * Mathf.Cos(transform.eulerAngles.x * Mathf.Deg2Rad);
        float yPos = Mathf.Sin(-transform.eulerAngles.x * Mathf.Deg2Rad);
        float zPos = Mathf.Cos(transform.eulerAngles.x * Mathf.Deg2Rad) * Mathf.Cos(transform.eulerAngles.y * Mathf.Deg2Rad);

        print(xPos + ", " + yPos + ", " + zPos + ", " + transform.forward);
    }

    private void GetUserInput()
    {
        if (Input.GetKey(KeyCode.W))
            inputs[0] = true;

        if (Input.GetKey(KeyCode.S))
            inputs[1] = true;

        if (Input.GetKey(KeyCode.A))
            inputs[2] = true;

        if (Input.GetKey(KeyCode.D))
            inputs[3] = true;
    }

    private void SendInput()
    {
        NetworkManager.Instance.SendPlayerInput(clientTick, inputs, player.transform.rotation, player.transform.forward);
    }
}
