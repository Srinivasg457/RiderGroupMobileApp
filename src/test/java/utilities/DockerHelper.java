package utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DockerHelper {

    public static boolean isDockerRunning() {
        try {
            Process process = Runtime.getRuntime().exec("docker ps");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean appiumRunning = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("appium")) {
                    appiumRunning = true;
                    break;
                }
            }

            process.waitFor();
            return appiumRunning;

        } catch (Exception e) {
            return false;
        }
    }

    public static void startAppiumContainer() {
        try {
            System.out.println("🚀 Starting Appium Docker container...");

            String[] commands = {
                    "docker", "run", "--privileged", "-d",
                    "-p", "4723:4723",
                    "--name", "appium-container",
                    "-v", "/dev/kvm:/dev/kvm",
                    "-v", "/dev/bus/usb:/dev/bus/usb",
                    "appium/appium"
            };

            Process process = Runtime.getRuntime().exec(commands);
            process.waitFor();

            // Wait for Appium to start
            Thread.sleep(10000);
            System.out.println("✅ Appium Docker container started");

        } catch (Exception e) {
            System.out.println("❌ Failed to start Appium Docker: " + e.getMessage());
        }
    }

    public static void stopAppiumContainer() {
        try {
            System.out.println("🛑 Stopping Appium Docker container...");

            Runtime.getRuntime().exec("docker stop appium-container");
            Runtime.getRuntime().exec("docker rm appium-container");

            System.out.println("✅ Appium Docker container stopped");

        } catch (Exception e) {
            System.out.println("⚠️ Error stopping container: " + e.getMessage());
        }
    }
}
