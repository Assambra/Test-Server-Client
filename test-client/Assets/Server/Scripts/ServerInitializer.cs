using System;
using UnityEngine;

public class ServerInitializer : MonoBehaviour
{
    void Start()
    {
        string[] args = Environment.GetCommandLineArgs();

        // �berpr�fe, ob gen�gend Argumente vorhanden sind
        if (args.Length >= 3) // Beachte, dass args[0] den Pfad zur Anwendung enth�lt
        {
            string username = args[1];
            string password = args[2];

            // Hier kannst du die �bergebenen Argumente verwenden
            Debug.Log("Received username: " + username);
            Debug.Log("Received password: " + password);
        }
        else
        {
            Debug.LogError("Insufficient command line arguments. Expected format: [executable] username password");
        }
    }
}
