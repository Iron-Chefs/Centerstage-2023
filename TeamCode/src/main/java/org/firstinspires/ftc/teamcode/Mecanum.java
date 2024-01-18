package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Mecanum extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
    Ramsay ramsay = new Ramsay(this);
    double speed;
        waitForStart();


        if (isStopRequested()) return;

        while (opModeIsActive()) {

            // Turbo button controls
            if (gamepad1.right_bumper) {
                speed = 1;
            } else {
                speed = .5;
            }

            // Drive Controls
            double y = -gamepad1.left_stick_y * speed; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * speed;
            double rx = -gamepad1.right_stick_x * speed;

            ramsay.drive(y, x, rx);

            // Intake Controls
            double lt = gamepad2.left_trigger;
            double rt = -1*gamepad2.right_trigger;
            double intakeSpeed = lt+rt;

            ramsay.setIntake(intakeSpeed);

            double liftCommand = -gamepad2.left_stick_y;
            ramsay.lift(liftCommand);

            // Pivot Controls
            if (gamepad2.a) {
                ramsay.openPivot();
            }
            if (gamepad2.b) {
                ramsay.closePivot();
            }

            // Grabber Controls
            if (gamepad2.x) {
                ramsay.openGrabber();
            }
            if (gamepad2.y) {
                ramsay.closeGrabber();
            }
        }
    }
}


/*
Gamepad 1 Controls
- Right Bumper = Turbo Button
- Gamepad 1 Joysticks = Drive train controls

Gamepad 2 Controls
- Left Trigger = Out Intake
- Right Trigger = In Intake
- Left Joystick = Lift Controls
- A = Open Pivot Servo
- B = Close Pivot Servo
- X = Open Grabber Servo
- Y = Close Grabber Servo
 */