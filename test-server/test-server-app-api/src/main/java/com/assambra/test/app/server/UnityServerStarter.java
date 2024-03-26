package com.assambra.test.app.server;

import java.io.IOException;

public class UnityServerStarter {
    public void spawnUnityServer(String username, String password) {
        try {

            ProcessBuilder pb = new ProcessBuilder("D:\\Game Builts\\ServerWorld\\ServerWorld.exe", username, password);
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
