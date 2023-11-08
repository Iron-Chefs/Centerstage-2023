package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.ColorDetector.*;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamServer;
import org.firstinspires.ftc.vision.VisionPortal;

import org.opencv.core.Rect;

@Autonomous
public class VisionTest extends OpMode {
    ColorDetector detector;
    VisionPortal visionPortal;

    @Override
    public void init() {
        // Initialize and configure detector and camera
        Rect leftZone = centerRect(120, 280, 150, 150);
        Rect midZone = centerRect(320, 280, 150, 150);
        Rect rightZone = centerRect(520, 280, 150, 150);
        detector = new ColorDetector(telemetry, TargetColor.RED, ViewMode.RAW, leftZone, midZone, rightZone);
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), detector);
        CameraStreamServer.getInstance().setSource(detector);
    }

    @Override
    public void init_loop() {
        handle_input();
    }

    @Override
    public void loop() {
        handle_input();
    }

    // Switch the viewModes and targetColors
    void handle_input() {
        if (gamepad1.x) {
            detector.targetColor = TargetColor.BLUE;
        } else if (gamepad1.b) {
            detector.targetColor = TargetColor.RED;
        }
        if (gamepad1.a) {
            detector.viewMode = ViewMode.RAW;
        } else if (gamepad1.y) {
            detector.viewMode = ViewMode.THRESHOLD;
        }
    }
}
