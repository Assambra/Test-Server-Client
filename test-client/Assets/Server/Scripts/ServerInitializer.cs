using System;
using UnityEngine;

public class ServerInitializer : MonoBehaviour
{
    void Start()
    {
        string[] args = Environment.GetCommandLineArgs();

        // Überprüfe, ob genügend Argumente vorhanden sind
        if (args.Length >= 3) // Beachte, dass args[0] den Pfad zur Anwendung enthält
        {
            string username = args[1];
            string password = args[2];

            // Hier kannst du die übergebenen Argumente verwenden
            Debug.Log("Received username: " + username);
            Debug.Log("Received password: " + password);
        }
        else
        {
            Debug.LogError("Insufficient command line arguments. Expected format: [executable] username password");
        }
    }
}
