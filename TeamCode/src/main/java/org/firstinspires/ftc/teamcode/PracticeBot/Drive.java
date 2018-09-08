package org.firstinspires.ftc.teamcode.PracticeBot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by ACME Robotics on 4/15/2018.
 */

public class Drive {

    private DcMotor leftFrontMotor = null;
    private DcMotor leftBackMotor = null;
    private DcMotor rightFrontMotor = null;
    private DcMotor rightBackMotor = null;

    public Drive (HardwareMap hardwareMap) {
        leftFrontMotor = hardwareMap.dcMotor.get("leftFront");
        leftBackMotor = hardwareMap.dcMotor.get("leftBack");
        rightFrontMotor = hardwareMap.dcMotor.get("rightFront");
        rightBackMotor = hardwareMap.dcMotor.get("rightBack");

        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);

    }

    public void   setSpeed (double forward, double turn) {

        double leftPower = forward - turn;
        double rightPower = forward + turn;
        double max = Math.max(Math.abs(leftPower), Math.abs(rightPower));


        if (max > 1){
            double scale = 1/max;
            leftPower *= scale;
            rightPower *= scale;
        }

        leftFrontMotor.setPower(leftPower);
        leftBackMotor.setPower(leftPower);
        rightFrontMotor.setPower(rightPower);
        rightBackMotor.setPower(rightPower);
    }
}
