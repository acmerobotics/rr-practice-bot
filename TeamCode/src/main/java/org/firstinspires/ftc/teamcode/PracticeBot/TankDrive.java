package org.firstinspires.ftc.teamcode.PracticeBot;

/**
 * Created by Emma on 3/25/2018.
 */
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="TankDrive", group="TankDrive")
public class TankDrive extends OpMode {
     private DcMotor leftFrontMotor = null;
     private DcMotor leftBackMotor = null;
     private DcMotor rightFrontMotor = null;
     private DcMotor rightBackMotor = null;

    // private DcMotor liftMotor = null;



    public void init() {
        leftFrontMotor = hardwareMap.dcMotor.get("leftFront");
        leftBackMotor = hardwareMap.dcMotor.get("leftBack");
        rightFrontMotor = hardwareMap.dcMotor.get("rightFront");
        rightBackMotor = hardwareMap.dcMotor.get("rightBack");
        //liftMotor = hardwareMap.dcMotor.get("lift");

        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);
        //liftMotor.setPower(0);


    }

    public void loop() {
        double forward = -gamepad1.left_stick_y;
        double turn = -gamepad1.right_stick_x;

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

    public void stop() {
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);
    }
}
