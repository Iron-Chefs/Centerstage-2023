package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Ramsay {
    LinearOpMode opMode;

    // drivetrain
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;

    // lift
    DcMotor liftMotor1;
    DcMotor liftMotor2;

    // intake
    DcMotor intakeMotor;

    // Grabber
    Servo pivotServo;
    Servo grabberServo;

    public Ramsay(LinearOpMode opMode) {
        this.opMode = opMode;

        frontLeftMotor = this.opMode.hardwareMap.dcMotor.get("fl");
        backLeftMotor = this.opMode.hardwareMap.dcMotor.get("bl");
        frontRightMotor = this.opMode.hardwareMap.dcMotor.get("fr");
        backRightMotor = this.opMode.hardwareMap.dcMotor.get("br");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // lift init
        //liftMotor1 = hardwareMap.dcMotor.get("lift1");
        //liftMotor2 = hardwareMap.dcMotor.get("lift2");
        // intake init
        intakeMotor = this.opMode.hardwareMap.dcMotor.get("intakeMotor");
        //intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        // grabber init
        //pivotServo = hardwareMap.servo.get("pivot");
        //grabberServo = hardwareMap.servo.get("grabber");*/

    }




    public void drive(double straight, double strafe, double turn) {
        straight = -straight;
        strafe = -strafe;
        //turn = turn;
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

    public void setIntake(double speed) {
        intakeMotor.setPower(speed);
    }

    /*public void closeGrabber() {
        grabberServo.setPosition(0);
    }
    public void openGrabber() {
        grabberServo.setPosition(1);
    }
    
    public void lift(double power) {
    liftMotor1.setPower(power);
    liftMotor2.setPower(power);
    }*/
}