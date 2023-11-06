package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.function.Consumer;
import org.firstinspires.ftc.robotcore.external.function.Continuation;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamServer;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;


@Autonomous
public class VisionTest extends OpMode {

    final Scalar BLUE_HSV_HIGH = new Scalar(130, 255, 255);
    final Scalar BLUE_HSV_LOW = new Scalar(100, 50, 50);
    final Scalar RED1_HSV_HIGH = new Scalar(15, 255, 255);
    final Scalar RED1_HSV_LOW = new Scalar(0, 50, 50);
    final Scalar RED2_HSV_HIGH = new Scalar(180, 255, 255);
    final Scalar RED2_HSV_LOW = new Scalar(165, 50, 50);

    boolean aWasPressed = false;
    ViewMode viewMode = ViewMode.Raw;

    enum ViewMode {
        Raw,
        RedMask,
        RedThreshold,
        BlueMask,
        BlueThreshold,
        CombinedMask,
    }

    VisionPortal visionPortal;

    private class TestDetector implements VisionProcessor, CameraStreamSource {
        //Store frames to send to DS
        Mat rawFrame = new Mat();
        Mat frameHSV = new Mat();
        Mat red1Thresh = new Mat();
        Mat red2Thresh = new Mat();
        Mat redThresh = new Mat();
        Mat blueThresh = new Mat();
        Mat combinedMask = new Mat();

        @Override
        public void init(int width, int height, CameraCalibration calibration) {
            // Nothing to init
        }

        @Override
        public Object processFrame(Mat frame, long captureTimeNanos) {
            // Save raw frame
            frame.copyTo(rawFrame);

            // Convert to HSV for easier color finding
            Imgproc.cvtColor(frame, frameHSV, Imgproc.COLOR_RGB2HSV);

            // Red in HSV is at the top and bottom of H range so needs 2 thresholds that get combined
            Core.inRange(frameHSV, RED1_HSV_LOW, RED1_HSV_HIGH, red1Thresh);
            Core.inRange(frameHSV, RED2_HSV_LOW, RED2_HSV_HIGH, red2Thresh);
            Core.bitwise_or(red1Thresh, red2Thresh, redThresh);

            // Blue threshold
            Core.inRange(frameHSV, BLUE_HSV_LOW, BLUE_HSV_HIGH, blueThresh);

            return null;
        }

        @Override
        public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
            // This is for drawing to RC screen so doesn't matter
        }

        // Return frame for DS display based on viewMode
        @Override
        public void getFrameBitmap(Continuation<? extends Consumer<Bitmap>> continuation) {
            // Create image based on viewMode
            Mat result = new Mat();
            switch (viewMode) {
                case Raw:
                    result = rawFrame;
                    break;
                case RedMask:
                    rawFrame.copyTo(result, redThresh);
                    break;
                case RedThreshold:
                    result = redThresh;
                    break;
                case BlueMask:
                    rawFrame.copyTo(result, blueThresh);
                    break;
                case BlueThreshold:
                    result = blueThresh;
                    break;
                case CombinedMask:
                    Core.bitwise_or(blueThresh, redThresh, combinedMask);
                    rawFrame.copyTo(result, combinedMask);
                    break;
            }

            // Send result to DS
            Bitmap resultBitmap = Bitmap.createBitmap(result.width(), result.height(), Bitmap.Config.RGB_565);
            Utils.matToBitmap(result, resultBitmap);
            continuation.dispatch(bitmapConsumer -> bitmapConsumer.accept(resultBitmap));
        }
    }

    @Override
    public void init() {
        TestDetector processor = new TestDetector();
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), processor);
        CameraStreamServer.getInstance().setSource(processor);
    }

    @Override
    public void init_loop() {
        handle_input();
    }

    @Override
    public void loop() {
        handle_input();
    }

    void handle_input() {
        if (gamepad1.a) {
            if (!aWasPressed) {
                switch (viewMode) {
                    case Raw:
                        viewMode = ViewMode.RedMask;
                        break;
                    case RedMask:
                        viewMode = ViewMode.RedThreshold;
                        break;
                    case RedThreshold:
                        viewMode = ViewMode.BlueMask;
                        break;
                    case BlueMask:
                        viewMode = ViewMode.BlueThreshold;
                        break;
                    case BlueThreshold:
                        viewMode = ViewMode.CombinedMask;
                        break;
                    case CombinedMask:
                        viewMode = ViewMode.Raw;
                        break;
                }
                telemetry.addData("View Mode", viewMode.name());
                telemetry.update();
            }
            aWasPressed = true;
        } else {
            aWasPressed = false;
        }
    }
}
