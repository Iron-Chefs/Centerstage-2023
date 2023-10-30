package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Ramsay {
    //drivetrain
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;

//lift
DcMotor liftMotor1;
DcMotor liftMotor2;

//intake
DcMotor intakeMotor;

//Grabber

Servo pivotServo;
Servo grabberServo;

    public Ramsay() {
        frontLeftMotor = hardwareMap.dcMotor.get("fl");
        backLeftMotor = hardwareMap.dcMotor.get("bl");
        frontRightMotor = hardwareMap.dcMotor.get("fr");
        backRightMotor = hardwareMap.dcMotor.get("br");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //lift init
        liftMotor1 = hardwareMap.dcMotor.get("lift1");
        liftMotor2 = hardwareMap.dcMotor.get("lift2");
        //intake init
        intakeMotor = hardwareMap.dcMotor.get("intakeMotor");
        //grabber init
        pivotServo = hardwareMap.servo.get("pivot");
        grabberServo = hardwareMap.servo.get("grabber");

    }
    public void drive(double straight, double strafe, double turn) {
            double denominator = Math.max(Math.abs(straight) + Math.abs(strafe) + Math.abs(turn), 1);
            double frontLeftPower = (straight + strafe + turn) / denominator;
            double backLeftPower = (straight - strafe + turn) / denominator;
            double frontRightPower = (straight - strafe - turn) / denominator;
            double backRightPower = (straight + strafe - turn) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
    }
    public void closeGrabber() {
        grabberServo.setPosition(0);
    }
    public void openGrabber() {
        grabberServo.setPosition(1);
    }
    
    public void lift(double power) {
    liftMotor1.setPower(power);
    liftMotor2.setPower(power);
    }
}