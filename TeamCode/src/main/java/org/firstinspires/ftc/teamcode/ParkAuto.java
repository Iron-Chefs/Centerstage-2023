package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class ParkAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Ramsay ramsay = new Ramsay(this);
        double speed;
        waitForStart();

        ramsay.drive(1,0,0); // Go forwards
        sleep(1500);                // Wait 1 sec
        ramsay.drive(-1,0,0);
        sleep(250);
        ramsay.drive(0,0,0);// Stop
    }
}