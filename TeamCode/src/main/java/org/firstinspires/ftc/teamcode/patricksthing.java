package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class patricksthing extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor patrick = hardwareMap.dcMotor.get("patrick");
        Servo urmom = hardwareMap.servo.get("urmom");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (gamepad1.a) {
                patrick.setPower(-1);
            } else if (gamepad1.b) {
                patrick.setPower(1);
            } else {
                patrick.setPower(0);
            }

            if (gamepad1.x) {
                urmom.setPosition(1);
            } else if (gamepad1.y){
                urmom.setPosition(0);
            }

        }
    }
}