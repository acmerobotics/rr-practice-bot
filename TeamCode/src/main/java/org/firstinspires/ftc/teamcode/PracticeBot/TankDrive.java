package org.firstinspires.ftc.teamcode.PracticeBot;

/**
 * Created by Emma on 3/25/2018.
 */

        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TankDrive", group="TankDrive")
public class TankDrive extends OpMode {

     private DcMotor leftFrontMotor = null;
     private DcMotor leftBackMotor = null;
     private DcMotor rightFrontMotor = null;
     private DcMotor rightBackMotor = null;


    public void init() {
        leftFrontMotor = hardwareMap.dcMotor.get("leftFront");
        leftBackMotor = hardwareMap.dcMotor.get("leftBack");
        rightFrontMotor = hardwareMap.dcMotor.get("rightFront");
        rightBackMotor = hardwareMap.dcMotor.get("rightBack");


        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);


    }

    public void loop() {
        double left = gamepad1.left_stick_y;
        double right = gamepad1.right_stick_y;
        boolean up = gamepad1.dpad_up;
        boolean down = gamepad1.dpad_down;


        leftFrontMotor.setPower(left);
        leftBackMotor.setPower(left);
        rightFrontMotor.setPower(right);
        rightBackMotor.setPower(right);


    }

    public void stop() {
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);
    }
}
