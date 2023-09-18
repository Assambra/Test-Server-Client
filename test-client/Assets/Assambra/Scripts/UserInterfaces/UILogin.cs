using Assambra.Shared.Helper;
using UnityEngine;
using TMPro;

public class UILogin : MonoBehaviour
{
    [SerializeField] TMP_InputField inputFieldUsername;
    [SerializeField] TMP_InputField inputFieldPassword;

    private string password;
    private string username;

    public void OnButtonLogin()
    {
        Debug.Log("OnButtonLogin");

        username = inputFieldUsername.text;
        password = inputFieldPassword.text;

        NetworkManager.Instance.Login(username, password);
    }

    public void OnButtonQuit()
    {
        Debug.Log("OnButtonQuit");
        NetworkManager.Instance.Disconnect();

        Helper.Quit();
    }

}
