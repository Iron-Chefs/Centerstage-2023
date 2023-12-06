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

            if (gamepad1.right_bumper) {
                speed = 1;
            } else {
                speed = .5;
            }

            double y = -gamepad1.left_stick_y * speed; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * speed;
            double rx = -gamepad1.right_stick_x * speed;

            double intakeInput = gamepad1.right_trigger;
            double intakeSpeed;
            if (gamepad1.right_bumper) {
                // Reverse if bumper is pressed
                intakeSpeed = -0.2 - (intakeInput * 0.8);
            } else {
                intakeSpeed = intakeInput;
            }
            ramsay.setIntake(intakeSpeed);

            ramsay.drive(y, x, rx);
        }
    }
}