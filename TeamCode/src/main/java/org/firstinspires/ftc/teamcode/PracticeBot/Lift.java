package org.firstinspires.ftc.teamcode.PracticeBot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by ACME Robotics on 4/15/2018.
 */

public class Lift {
    public static final double RADIUS = 1; //inches
    public static final double DISTANCE_LIFT = -15; //inches
    public static final double REVOLUTIONS_LIFT = DISTANCE_LIFT/ (RADIUS * 2 * Math.PI);
    public static final double PUSLE_REVOLUTION = 560;
    public static final double DEADZONE = 10;
    public static final double STALL = -.147;
    public static final double MAX = -.75;

    private DcMotor liftMotor;
    private double setPoint;
    private double down;
    private Telemetry telemetry;
    public PIDController pidController;

    public Lift (HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;

        liftMotor = hardwareMap.dcMotor.get("liftMotor");
        pidController = new PIDController(-.005, 0, 0);
        liftMotor.setPower(0);
        down = liftMotor.getCurrentPosition();

    }

    public void update () {
        double error = liftMotor.getCurrentPosition() - setPoint;
        double feedback = pidController.update(error);
        double feedforward = 0;
        if (Math.abs(error) < DEADZONE) {
            feedforward = STALL;
        }
        else if (error > 0){
            feedforward = MAX;
        }
        liftMotor.setPower(feedback);

        telemetry.addData("error", error);
        telemetry.addData("feedforward", feedforward);
        telemetry.addData("feedback", feedback);

    }

    public void up () {
        setPoint = down + REVOLUTIONS_LIFT * PUSLE_REVOLUTION;

    }

    public void down () {
        setPoint = down;
    }


}

