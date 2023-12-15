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

            // lt in, rt out
            // Gives a number between 0 and 1
            double lt = gamepad2.left_trigger;
            double rt = -1*gamepad2.right_trigger;
            double intakeSpeed = lt+rt;

            // Wants a number between -1 and 1
            ramsay.setIntake(intakeSpeed);

            ramsay.drive(y, x, rx);

            //open and close hand based on x button
            if (gamepad2.x) {
                ramsay.openHand();
            } else {
                ramsay.closeHand();
            }
            if (gamepad2.a) {
                ramsay.armOut();
            } else {
                ramsay.armIn();
            }
        }
    }
}