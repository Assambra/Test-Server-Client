using UnityEngine;

public class Player : MonoBehaviour
{
    public bool IsLocalPlayer;
    public string PlayerName;

    [SerializeField] private CharacterController characterController;

}
